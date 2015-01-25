package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Event;
import ru.dreamcloud.pharmatracker.model.Notification;
import ru.dreamcloud.pharmatracker.model.NotificationType;
import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.PatientHistoryStatus;
import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;
import ru.dreamcloud.pharmatracker.zk.services.SchedulerService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class ShortcutMenuVM {
	
	/**************************************
	  Property authService	 
	***************************************/
	private AuthenticationService authService;
	
	/**************************************
	  Property schedulerService	 
	***************************************/
	private SchedulerService schedulerService;
	
	/**************************************
	  Property isVisibleCreateNewEvent	 
	***************************************/
	private Boolean isVisibleCreateNewEvent;	
	
	public Boolean getIsVisibleCreateNewEvent() {
		return isVisibleCreateNewEvent;
	}

	public void setIsVisibleCreateNewEvent(Boolean isVisibleCreateNewEvent) {
		this.isVisibleCreateNewEvent = isVisibleCreateNewEvent;
	}
	
	/**************************************
	  Property isVisibleEditPatientHistory	 
	***************************************/
	private Boolean isVisibleEditPatientHistory;

	public Boolean getIsVisibleEditPatientHistory() {
		return isVisibleEditPatientHistory;
	}

	public void setIsVisibleEditPatientHistory(Boolean isVisibleEditPatientHistory) {
		this.isVisibleEditPatientHistory = isVisibleEditPatientHistory;
	}
	
	/**************************************
	  Property isVisibleClosePatientHistory	 
	***************************************/
	private Boolean isVisibleClosePatientHistory;	

	public Boolean getIsVisibleClosePatientHistory() {
		return isVisibleClosePatientHistory;
	}

	public void setIsVisibleClosePatientHistory(Boolean isVisibleClosePatientHistory) {
		this.isVisibleClosePatientHistory = isVisibleClosePatientHistory;
	}
	
	/**************************************
	  Property createPermission	 
	***************************************/
	private Boolean createPermission;	

	public Boolean getCreatePermission() {
		return createPermission;
	}

	public void setCreatePermission(Boolean createPermission) {
		this.createPermission = createPermission;
	}

	/**************************************
	  Property editPermission	 
	***************************************/
	private Boolean editPermission;	
	
	public Boolean getEditPermission() {
		return editPermission;
	}

	public void setEditPermission(Boolean editPermission) {
		this.editPermission = editPermission;
	}

	/**************************************
	  Property deletePermission	 
	***************************************/
	private Boolean deletePermission;	

	public Boolean getDeletePermission() {
		return deletePermission;
	}

	public void setDeletePermission(Boolean deletePermission) {
		this.deletePermission = deletePermission;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Session session = Sessions.getCurrent();
		authService = new AuthenticationService();
		schedulerService = (SchedulerService)session.getAttribute("schedulerService");
		if(view.getPage().getId().equals("MainPage")){
			isVisibleCreateNewEvent = false;
			isVisibleEditPatientHistory = false;
			isVisibleClosePatientHistory = false;
		} else {
			isVisibleCreateNewEvent = true;
			isVisibleEditPatientHistory = true;
			isVisibleClosePatientHistory = true;
		}
		createPermission = authService.checkAccessRights(authService.getCurrentProfile().getRole(),"CreateDisabled");
		editPermission = authService.checkAccessRights(authService.getCurrentProfile().getRole(),"EditDisabled");
		deletePermission = authService.checkAccessRights(authService.getCurrentProfile().getRole(),"DeleteDisabled");
	}
	
	@Command
	public void createNewPatientHistory(){
		final HashMap<String, Object> params = new HashMap<String, Object>();    	
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/patienthistorywindow.zul", null, params);
        window.doModal();
	}
	
	@Command
	public void createNewEvent(){
    	final HashMap<String, Object> params = new HashMap<String, Object>();    	
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
        window.doModal();
	}
	

	@Command
	public void editPatientHistory(){
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	if(Sessions.getCurrent().getAttribute("currentPatientHistory") != null){
			PatientHistory ph = (PatientHistory)Sessions.getCurrent().getAttribute("currentPatientHistory");
	    	params.put("patientHistoryItem", ph);
	    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/patienthistorywindow-additional.zul", null, params);
	        window.doModal();
    	}
	}
	
	@Command
	public void closePatientHistory(){
    	if(Sessions.getCurrent().getAttribute("currentPatientHistory") != null){
			final PatientHistory ph = (PatientHistory)Sessions.getCurrent().getAttribute("currentPatientHistory");			
	    	Messagebox.show("Вы уверены что хотите закрыть историю пациента "+ph.getPatient().getFullname()+"?", "Закрытие истории пациента", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<org.zkoss.zk.ui.event.Event>() {			
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						ph.setPatientHistoryStatus(PatientHistoryStatus.CLOSED);
						DataSourceLoader.getInstance().mergeRecord(ph);						
						for (Event ev : ph.getEvents()) {
							for (Notification nt : ev.getNotifications()){
								if(nt.getNotificationType() == NotificationType.ACTIVE){
									schedulerService.removeScheduledTask("Task_"+nt.getNotificationId());
								}
								DataSourceLoader.getInstance().removeRecord(nt);
							}
						}
						params.put("patientHistory", ph);
						BindUtils.postGlobalCommand(null, null, "refreshPatientHistory", params);						
						Clients.showNotification("История пациента закрыта!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
						Executions.sendRedirect(Labels.getLabel("panels.index.URL"));
					}
					
				}

			});

	    	
    	}
	}
	
	@Command
	public void logout(){
		authService.logout();
		schedulerService.cancelAllSchedulerJobs();
		Executions.sendRedirect(Labels.getLabel("pages.login.URL"));
	}
	
}
