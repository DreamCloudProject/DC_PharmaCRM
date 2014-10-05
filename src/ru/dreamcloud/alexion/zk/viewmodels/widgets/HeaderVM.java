package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;

import ru.dreamcloud.alexion.model.Notification;
import ru.dreamcloud.alexion.model.NotificationState;
import ru.dreamcloud.alexion.model.NotificationType;
import ru.dreamcloud.alexion.model.authentication.CommonUserInfo;
import ru.dreamcloud.alexion.zk.services.AuthenticationService;
import ru.dreamcloud.alexion.zk.services.SchedulerService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class HeaderVM {
	
	class SchedulerTask extends TimerTask {
		private Desktop desktop;		
		private Notification notification;
		private CommonUserInfo userInfo;		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public SchedulerTask(Notification notification, Desktop desktop, CommonUserInfo userInfo) {
			this.notification = notification;
	        this.desktop = desktop;
	        this.userInfo = userInfo;
		}

		@Override
		public void run() {
			notification.setNotificationType(NotificationType.OVERDUE);
			DataSourceLoader.getInstance().updateRecord(notification);
			notificationsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Notification", 
					"where e.userInfo.userInfoId=" + userInfo.getUserInfoId() +
					" and e.notificationType="+NotificationType.class.getName()+".OVERDUE" + 
					" and e.notificationState="+NotificationState.class.getName()+".NOT_READ"));
			notificationsCount = notificationsList.size();
			if(notificationsCount > 0){
				visibleCount = true;
			} else {
				visibleCount = false;
			}
			Executions.schedule(desktop, new EventListener<Event>() {
				public void onEvent(Event event) {
					String result = String.valueOf(event.getData());
					BindUtils.postNotifyChange(null, null, HeaderVM.this, "notificationsCount");
					BindUtils.postNotifyChange(null, null, HeaderVM.this, "notificationsList");
					BindUtils.postNotifyChange(null, null, HeaderVM.this, "visibleCount");
					BindUtils.postGlobalCommand(null, null, "refreshNotificationsList", null);
					playSound();
		        }
			}, new Event("onEvent", null, notificationsCount));			
		}
		
		private void playSound(){
			try {
				String notifyWavPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/bootstrap/v3/template/media/notify.wav");
				Clip clip = AudioSystem.getClip();
				File notifyWav = new File(notifyWavPath);
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(notifyWav);
		        clip.open(inputStream);
		        clip.start();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		}
		
	}
	
	/**************************************
	 * Property authenticationService
	 ***************************************/
	private AuthenticationService authenticationService;
	
	/**************************************
	 * Property schedulerService
	 ***************************************/
	private SchedulerService schedulerService;

	/**************************************
	 * Property notificationsList
	 ***************************************/
	private List<Notification> notificationsList;

	public List<Notification> getNotificationsList() {
		return notificationsList;
	}
	
	public void setNotificationsList(List<Notification> notificationsList) {
		this.notificationsList = notificationsList;
	}
	
	/**************************************
	 * Property notificationsCount
	 ***************************************/
	private int notificationsCount;

	public int getNotificationsCount() {
		return notificationsCount;
	}
	
	public void setNotificationsCount(int notificationsCount) {
		this.notificationsCount = notificationsCount;
	}
	
	/**************************************
	 * Property visibleCount
	 ***************************************/
	private Boolean visibleCount;	
	
	public Boolean getVisibleCount() {
		return visibleCount;
	}

	public void setVisibleCount(Boolean visibleCount) {
		this.visibleCount = visibleCount;
	}

	/**************************************
	 * Property currentDesktop
	 ***************************************/
	
	private Desktop currentDesktop;		
	
	public Desktop getCurrentDesktop() {
		return currentDesktop;
	}

	public void setCurrentDesktop(Desktop currentDesktop) {
		this.currentDesktop = currentDesktop;
	}
	
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
	
	/**************************************
	 * Property currentDate
	 ***************************************/
	
	private Timestamp currentDate;

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ContextParam(ContextType.DESKTOP) Desktop desktop) {
		Session session = Sessions.getCurrent();
		authenticationService = new AuthenticationService();
		schedulerService = (SchedulerService)session.getAttribute("schedulerService");
		currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		currentDesktop = desktop;
		currentDesktop.enableServerPush(true);
		timer = new Timer();
		refreshNotificationsCount();
	}
	
	@AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
    }
	
	@GlobalCommand
	@Command
    @NotifyChange({"notificationsCount","notificationsList","visibleCount"})
    public void refreshNotificationsCount() {
		notificationsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Notification", 
				"where e.userInfo.userInfoId=" + authenticationService.getCurrentProfile().getUserInfoId() +
				" and e.notificationType="+NotificationType.class.getName()+".OVERDUE" + 
				" and e.notificationState="+NotificationState.class.getName()+".NOT_READ"));
		notificationsCount = notificationsList.size();
		if(notificationsCount > 0){
			visibleCount = true;
		} else {
			visibleCount = false;
		}
		scheduleJobs();
    }
    
	private void scheduleJobs() {
		Session session = Sessions.getCurrent();
		currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		for (Notification notification : schedulerService.getUserNotifications()) {
			if(notification.getNotificationType() == NotificationType.ACTIVE){
				timer.schedule(new SchedulerTask(notification, currentDesktop, authenticationService.getCurrentProfile()), notification.getDateTimeEnd());				
			}
		}
	}
}
