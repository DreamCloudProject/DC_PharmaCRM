package ru.dreamcloud.pharmacrm.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

public class SidebarVM {
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
		currentPage = page;
	}
}
