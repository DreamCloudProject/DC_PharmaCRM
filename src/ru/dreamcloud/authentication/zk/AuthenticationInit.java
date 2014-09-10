package ru.dreamcloud.authentication.zk;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericInitiator;

public class AuthenticationInit extends GenericInitiator {
	
	public AuthenticationInit() {	
	}
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		Session session = Sessions.getCurrent();
		String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
		String loginPage = Labels.getLabel("pages.login.URL");
		if(requestPath.indexOf(loginPage) == -1){
	        AuthenticationService authenticationService = new AuthenticationService();
			if(!authenticationService.initAccess()){
				Execution exec = Executions.getCurrent();
			    HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
			    response.sendRedirect(loginPage);
			    exec.setVoided(true); //no need to create UI since redirect will take place			    
			}		
		}
	}

}
