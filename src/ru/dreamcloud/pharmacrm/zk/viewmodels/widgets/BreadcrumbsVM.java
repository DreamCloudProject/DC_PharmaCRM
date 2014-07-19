package ru.dreamcloud.pharmacrm.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

public class BreadcrumbsVM {
	/**************************************
	  Property breadCrumbs	 
	***************************************/
	private Object breadCrumbs;
	
	public Object getBreadCrumbs() {
		return breadCrumbs;
	}

	public void setBreadCrumbs(Object breadCrumbs) {
		this.breadCrumbs = breadCrumbs;
	}
	
	/**************************************
	  Property currentPage	 
	***************************************/
	private String currentPageName;
	
	public String getCurrentPageName() {
		return currentPageName;
	}

	public void setCurrentPageName(String currentPageName) {
		this.currentPageName = currentPageName;
	}	

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("breadcrumbs") Object bCrumbs,
					 @ExecutionArgParam("currentPageName") String page) {
		breadCrumbs = bCrumbs;
		currentPageName = page;
	}
}
