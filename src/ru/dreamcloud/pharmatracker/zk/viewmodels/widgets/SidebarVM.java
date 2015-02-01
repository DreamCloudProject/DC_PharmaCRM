package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;

public class SidebarVM {
	
	/**************************************
	  Property isVisibleEventsList	 
	***************************************/
	private Boolean isVisibleEventsList;	
	
	public Boolean getIsVisibleEventsList() {
		return isVisibleEventsList;
	}

	public void setIsVisibleEventsList(Boolean isVisibleEventsList) {
		this.isVisibleEventsList = isVisibleEventsList;
	}

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
	  Property currentPatientHistory	 
	***************************************/
	private PatientHistory currentPatientHistory;	

	public PatientHistory getCurrentPatientHistory() {
		return currentPatientHistory;
	}

	public void setCurrentPatientHistory(PatientHistory currentPatientHistory) {
		this.currentPatientHistory = currentPatientHistory;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
					 @ExecutionArgParam("currentPage") String page,
					 @ExecutionArgParam("currentPatientHistory") PatientHistory phItem) {
		Session session = Sessions.getCurrent();
		if(view.getPage().getId().equals("PatientHistoryDetailPage")){
			isVisibleEventsList = true;			
		} else {
			isVisibleEventsList = false;
		}
		if(page != null){
			currentPage = page;
		}
		if(phItem != null){
			currentPatientHistory = phItem;
		}
		if(session.getAttribute("userInfo") != null){
			currentProfile = (CommonUserInfo)session.getAttribute("userInfo");
		}
	}
}
