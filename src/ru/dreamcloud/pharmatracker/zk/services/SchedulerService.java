package ru.dreamcloud.pharmatracker.zk.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.pharmatracker.model.Event;
import ru.dreamcloud.pharmatracker.model.Notification;
import ru.dreamcloud.pharmatracker.model.NotificationState;
import ru.dreamcloud.pharmatracker.model.NotificationType;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class SchedulerService {
	
	/**************************************
	 * Property authenticationService
	 ***************************************/
	private AuthenticationService authenticationService;	
	
	/**************************************
	 * Property scheduledTasks
	 ***************************************/
	
	Map<String,TimerTask> scheduledTasks;	
	
	public Map<String, TimerTask> getScheduledTasks() {
		return scheduledTasks;
	}

	/**************************************
	 * Property currentDate
	 ***************************************/
	
	private Timestamp currentDate;

	public SchedulerService() {
		authenticationService = new AuthenticationService();
		scheduledTasks = new HashMap<String, TimerTask>();
	}	
	
	public void initSchedulerJobs() {
		Session session = Sessions.getCurrent();
		currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for (Notification notification : getUserNotifications()) {
			if(notification.getDateTimeEnd().after(currentDate)){
				if(notification.getNotificationType() == NotificationType.NOT_ACTIVE){					
					notification.setNotificationType(NotificationType.ACTIVE);
					DataSourceLoader.getInstance().mergeRecord(notification);
				}
			} else {
				if(notification.getNotificationType() == NotificationType.ACTIVE){
					notification.setNotificationType(NotificationType.OVERDUE);
					DataSourceLoader.getInstance().mergeRecord(notification);
				}
				
				if(notification.getNotificationState() == NotificationState.READ){
					DataSourceLoader.getInstance().removeRecord(notification);
				}
			}
		}		
	}
	
	public void cancelAllSchedulerJobs(){
        Session session = Sessions.getCurrent();
        session.removeAttribute("schedulerService");
	}
	
	public Notification getNotificationByEvent(Event event) {	
		for (Notification notification : getUserNotifications()) {
			if(notification.getEvent().getEventId() == event.getEventId()){
				return notification;				
			}
		}
		return null;
	}
	
	public Boolean isContainsNotification(Event event) {
		for (Notification notification : getUserNotifications()) {
			if(notification.getEvent().getEventId() == event.getEventId()){
				return true;				
			}
		}
		return false;
	}
	
	public List<Notification> getUserNotifications() {
		return new ArrayList(DataSourceLoader.getInstance().fetchRecords("Notification", "where e.userInfo.userInfoId="+authenticationService.getCurrentProfile().getUserInfoId()));		
	}
	
	
	public void addScheduledTask(String taskId, TimerTask scheduledTask) {
		scheduledTasks.put(taskId, scheduledTask);
	}
	
	public void removeScheduledTask(String taskId) {
		scheduledTasks.get(taskId).cancel();
		scheduledTasks.remove(taskId);
	}
}
