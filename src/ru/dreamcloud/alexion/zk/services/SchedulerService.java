package ru.dreamcloud.alexion.zk.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.Notification;
import ru.dreamcloud.alexion.model.NotificationState;
import ru.dreamcloud.alexion.model.NotificationType;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class SchedulerService {
	
	class SchedulerTask extends TimerTask {

		@Override
		public void run() {
			System.out.println("Time's up!");
			this.cancel();			
		}
		
	}
	
	/**************************************
	 * Property currentDate
	 ***************************************/
	
	private Timestamp currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	/**************************************
	 * Property authenticationService
	 ***************************************/
	private AuthenticationService authenticationService;	
	
	/**************************************
	 * Property currentNotifications
	 ***************************************/
	private List<Notification> currentNotifications;
	
	/**************************************
	 * Property timer
	 ***************************************/
	private Timer timer;	
	
	public Timer getTimer() {
		return timer;
	}	

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public SchedulerService() {
		authenticationService = new AuthenticationService();
	}
	
	public void initSchedulerJobs() {		
		if(authenticationService.getCurrentProfile() != null){
			Session session = Sessions.getCurrent();
			
			if(((SchedulerService)session.getAttribute("schedulerService")) != null){
				timer = ((SchedulerService)session.getAttribute("schedulerService")).getTimer() != null ? ((SchedulerService)session.getAttribute("schedulerService")).getTimer() : new Timer();
			} else {
				timer = new Timer();
			}
			
			currentNotifications = getUserNotifications();
			for (Notification notification : currentNotifications) {
				if(notification.getDateTimeEnd().after(currentDate)){
					if(notification.getNotificationType() == NotificationType.NOT_ACTIVE){
						timer.schedule(new SchedulerTask(), notification.getDateTimeEnd());
						notification.setNotificationType(NotificationType.ACTIVE);
						DataSourceLoader.getInstance().updateRecord(notification);
					}
				} else {
					if(notification.getNotificationState() == NotificationState.READ){
						DataSourceLoader.getInstance().removeRecord(notification);
					}
				}
			}
			session.setAttribute("schedulerService", this);
			
		}
	}
	
	public void addSchedulerJob(Event event) {
		Notification notification = getNotificationByEvent(event);
		if(notification == null){
			notification = new Notification();
			notification.setTitle(event.getTitle());
			notification.setDescription(event.getDescription());
			notification.setDateTimeStart(event.getDateTimeStart());
			notification.setDateTimeEnd(event.getDateTimeEnd());
			notification.setNotificationType(NotificationType.NOT_ACTIVE);
			notification.setNotificationState(NotificationState.NOT_READ);
			notification.setEvent(event);
			notification.setUserInfo(authenticationService.getCurrentProfile());
			DataSourceLoader.getInstance().addRecord(notification);
		} else {
			notification.setTitle(event.getTitle());
			notification.setDescription(event.getDescription());
			notification.setDateTimeStart(event.getDateTimeStart());
			notification.setDateTimeEnd(event.getDateTimeEnd());
			notification.setNotificationType(NotificationType.NOT_ACTIVE);
			notification.setNotificationState(NotificationState.NOT_READ);
			notification.setEvent(event);
			notification.setUserInfo(authenticationService.getCurrentProfile());
			DataSourceLoader.getInstance().updateRecord(notification);
		}
		initSchedulerJobs();
	}
	
	public void removeSchedulerJob(Event event) {
		Notification notification = getNotificationByEvent(event);
		if(notification != null){
			DataSourceLoader.getInstance().removeRecord(notification);
		}
		initSchedulerJobs();
	}
	
	public void cancelAllSchedulerJobs(){
		timer.cancel();
	}
	
	public Boolean isContainsNotification(Event event) {		
		currentNotifications = getUserNotifications();		
		for (Notification notification : currentNotifications) {
			if(notification.getEvent().getEventId() == event.getEventId()){
				return true;				
			}
		}
		return false;
	}
	
	private Notification getNotificationByEvent(Event event) {
		currentNotifications = getUserNotifications();		
		for (Notification notification : currentNotifications) {
			if(notification.getEvent().getEventId() == event.getEventId()){
				return notification;				
			}
		}
		return null;
	}
	
	private List<Notification> getUserNotifications() {
		currentNotifications = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Notification", "where e.userInfo.userInfoId="+authenticationService.getCurrentProfile().getUserInfoId()));
		return currentNotifications;
	}

}
