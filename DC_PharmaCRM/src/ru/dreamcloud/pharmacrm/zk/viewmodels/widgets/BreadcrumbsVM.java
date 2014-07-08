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
	private Object currentPage;
	
	public Object getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Object currentPage) {
		this.currentPage = currentPage;
	}	

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("breadcrumbs") Object bCrumbs,
					 @ExecutionArgParam("currentPage") Object page) {
		breadCrumbs = bCrumbs;
		currentPage = page;
	}
}
