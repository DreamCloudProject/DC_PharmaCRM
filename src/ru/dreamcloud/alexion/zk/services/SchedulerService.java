package ru.dreamcloud.alexion.zk.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.Notification;
import ru.dreamcloud.alexion.model.NotificationState;
import ru.dreamcloud.alexion.model.NotificationType;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class SchedulerService {
	
	/**************************************
	 * Property authenticationService
	 ***************************************/
	private AuthenticationService authenticationService;	
	
	/**************************************
	 * Property currentNotifications
	 ***************************************/
	private List<Notification> currentNotifications;
	
	/**************************************
	 * Property currentDate
	 ***************************************/
	
	private Timestamp currentDate;

	public SchedulerService() {
		authenticationService = new AuthenticationService();
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
	


}
