package ru.dreamcloud.pharmatracker.zk.viewmodels;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;

import ru.dreamcloud.pharmatracker.model.PhoneNumber;
import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;
import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class LoginViewModel {
	
	/**************************************
	 * Property authenticationService
	 ***************************************/
	private AuthenticationService authenticationService;
	
	/**************************************
	 * Property currentUserInfo
	 ***************************************/
	
	private CommonUserInfo currentUserInfo;

	public CommonUserInfo getCurrentUserInfo() {
		return currentUserInfo;
	}

	public void setCurrentUserInfo(CommonUserInfo currentUserInfo) {
		this.currentUserInfo = currentUserInfo;
	}
	
	/**************************************
	 * Property allUsers
	 ***************************************/
	private List<CommonUserInfo> allUsers = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", null)); 
	
	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		authenticationService = new AuthenticationService();
		currentUserInfo = new CommonUserInfo();
	}
	
	@Command
	public void login() {
		if(authenticationService.authenticate(currentUserInfo)){
			Executions.sendRedirect(Labels.getLabel("panels.index.URL"));			
		} else {
			Clients.showNotification("Вы ввели не верный логин или пароль!", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);			
		}
	}

}
