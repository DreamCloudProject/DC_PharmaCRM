package ru.dreamcloud.pharmatracker.zk.services;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.pharmatracker.model.authentication.CommonRole;
import ru.dreamcloud.pharmatracker.model.authentication.CommonRule;
import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class AuthenticationService implements Serializable {
	
	private CommonUserInfo currentProfile;	
	
	public CommonUserInfo getCurrentProfile() {
		Session session = Sessions.getCurrent();
		currentProfile = (CommonUserInfo)session.getAttribute("userInfo");
		return currentProfile;
	}	

	public AuthenticationService() {		
	}
	
	public String hashToMd5(String str) throws NoSuchAlgorithmException {    	 	 
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
 
        byte byteData[] = md.digest();
        
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }        
		return sb.toString();
	}
	
	public Boolean initAccess(){
		Boolean result = false;
		Session session = Sessions.getCurrent();
		HttpSession httpSession = (HttpSession)session.getNativeSession();
		String jsessionid = httpSession.getId();
        CommonUserInfo userInfo = (CommonUserInfo)session.getAttribute("userInfo");
        if(userInfo == null){
			result = false;
		} else {
			if(userInfo.getSessionid().equalsIgnoreCase(jsessionid)){
				result =  true;
			} else {
				result =  false;								
			}					
		}
		return result;
	}
	
	public Boolean authenticate(CommonUserInfo currentUserInfo) {
		Boolean result = false;
		try {
			String md5Pass = hashToMd5(currentUserInfo.getPassword());	
			List<CommonUserInfo> matchUsers = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", "where e.login='"+currentUserInfo.getLogin()+"' and e.password='"+md5Pass+"'"));
			Session session = Sessions.getCurrent();
			HttpSession httpSession = (HttpSession)session.getNativeSession();
			String jsessionid = httpSession.getId();
				
			if(!matchUsers.isEmpty()){
				currentUserInfo = matchUsers.get(0);
				if(currentUserInfo.getSessionid() == null){
					currentUserInfo.setSessionid(jsessionid);
					DataSourceLoader.getInstance().mergeRecord(currentUserInfo);
				} else {
					if(!currentUserInfo.getSessionid().equalsIgnoreCase(jsessionid)){
						currentUserInfo.setSessionid(jsessionid);
						DataSourceLoader.getInstance().mergeRecord(currentUserInfo);								
					}					
				}
				result = true;			
				session.setAttribute("userInfo", currentUserInfo);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean checkAccessRights(CommonRole role, String componentName){
		Boolean result = true;
		if(role != null){
			List<Integer> args = new ArrayList<Integer>();
			args.add(role.getRoleId());
			List<CommonRule> globalRules = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonRule", "where e.roles IN :args", args));
			for (CommonRule rule : globalRules) {
				if(rule.getComponentName().equals(componentName)){
					result = false;
				}
			}
		}
		return result;
	}
	
	public Boolean checkAdminRights(CommonRole role){
		Boolean result = true;
		if(role != null){
			List<Integer> args = new ArrayList<Integer>();
			args.add(role.getRoleId());
			List<CommonRule> globalRules = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonRule", "where e.roles IN :args", args));
			for (CommonRule rule : globalRules) {
				if(rule.getComponentName().equals("AdminDisabled")){
					result = false;
				}
			}
		}
		return result;
	}
	
    public void logout() {
        Session session = Sessions.getCurrent();
        session.removeAttribute("userInfo");
    }
}