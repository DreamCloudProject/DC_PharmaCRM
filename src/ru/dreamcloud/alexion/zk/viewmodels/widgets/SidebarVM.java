package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.alexion.model.authentication.CommonUserInfo;

public class SidebarVM {
	
	/**************************************
	  Property currentProfile	 
	***************************************/
	private CommonUserInfo currentProfile;	
	
	public CommonUserInfo getCurrentProfile() {
		return currentProfile;
	}

	public void setCurrentProfile(CommonUserInfo currentProfile) {
		this.currentProfile = currentProfile;
	}

	/**************************************
	  Property currentPage	 
	***************************************/
	private String currentPage;
	
	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}	

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentPage") String page) {
		Session session = Sessions.getCurrent();
		currentPage = page;
		currentProfile = (CommonUserInfo)session.getAttribute("userInfo");
	}
}
