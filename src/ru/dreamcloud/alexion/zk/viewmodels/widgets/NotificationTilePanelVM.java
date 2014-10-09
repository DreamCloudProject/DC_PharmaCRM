package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.Notification;
import ru.dreamcloud.alexion.model.NotificationState;
import ru.dreamcloud.alexion.model.NotificationType;
import ru.dreamcloud.alexion.zk.services.AuthenticationService;
import ru.dreamcloud.alexion.zk.services.SchedulerService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class NotificationTilePanelVM {
	
	/**************************************
	 * Property schedulerService
	 ***************************************/
	private SchedulerService schedulerService;	
	
	/**************************************
	 * Property authenticationService
	 ***************************************/
	private AuthenticationService authenticationService;	

	/**************************************
	 * Property selected
	 ***************************************/
	private Notification selected;

	public Notification getSelected() {
		return selected;
	}

	public void setSelected(Notification selected) {
		this.selected = selected;
	}
	
	/**************************************
	 * Property notificationsList
	 ***************************************/
	private List<Notification> notificationsList;

	public List<Notification> getNotificationsList() {
		return notificationsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		Session session = Sessions.getCurrent();
		authenticationService = new AuthenticationService();
		schedulerService = (SchedulerService)session.getAttribute("schedulerService");
		//session.setAttribute("notificationTilePanelVM", NotificationTilePanelVM.this);
		refreshNotificationsList();
	}
	
    @Command
    @NotifyChange("notificationsList")
    public void readNotification(@BindingParam("notificationItem") final Notification notificationItem) {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	notificationItem.setNotificationState(NotificationState.READ);
    	DataSourceLoader.getInstance().mergeRecord(notificationItem);
    	refreshNotificationsList();
    	schedulerService.initSchedulerJobs();
		BindUtils.postGlobalCommand(null, null, "refreshNotificationsCount", null);
    	Event eventItem = notificationItem.getEvent();
    	params.put("eventItem", eventItem);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
        window.doModal();
    }
    
	@GlobalCommand
	@Command
    @NotifyChange("notificationsList")
    public void refreshNotificationsList() {
		notificationsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Notification", 
				"where e.userInfo.userInfoId=" + authenticationService.getCurrentProfile().getUserInfoId() +
				" and e.notificationType="+NotificationType.class.getName()+".OVERDUE" + 
				" and e.notificationState="+NotificationState.class.getName()+".NOT_READ"));
	}
}
