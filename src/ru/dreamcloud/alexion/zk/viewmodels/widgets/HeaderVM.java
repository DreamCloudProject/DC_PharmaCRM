package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

public class HeaderVM {
	/**************************************
	  Property adminPage	 
	***************************************/
	private Boolean adminPage;
	
	public Boolean getAdminPage() {
		return adminPage;
	}

	public void setAdminPage(Boolean adminPage) {
		this.adminPage = adminPage;
	}	

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("admin") Boolean isAdminPage) {
		adminPage = isAdminPage;
	}
}
