package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.io.Files;
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

import ru.dreamcloud.alexion.model.Document;
import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.MessageType;
import ru.dreamcloud.alexion.model.Notification;
import ru.dreamcloud.alexion.model.NotificationType;
import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.model.PatientHistoryStatus;
import ru.dreamcloud.alexion.model.authentication.CommonRole;
import ru.dreamcloud.alexion.zk.services.AuthenticationService;
import ru.dreamcloud.alexion.zk.services.SchedulerService;
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
	  Methods	 
	***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Session session = Sessions.getCurrent();
		authService = new AuthenticationService();
		schedulerService = (SchedulerService)session.getAttribute("schedulerService");
		List<CommonRole> matchRoles = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRole", "where e.title='"+view.getPage().getId()+"'"));
		CommonRole pageRole = matchRoles.isEmpty() ? null : matchRoles.get(0);		
		isVisibleCreateNewEvent = authService.checkAccessRights(pageRole,"Добавить событие");
		isVisibleEditPatientHistory = authService.checkAccessRights(pageRole, "Редактировать историю пациента");
		isVisibleClosePatientHistory = authService.checkAccessRights(pageRole, "Закрыть историю пациента");
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
