package ru.dreamcloud.alexion.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.dreamcloud.alexion.model.authentication.CommonUserInfo;

/*
delimiter $$

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time_start` datetime DEFAULT NULL,
  `date_time_end` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `event` int(11) DEFAULT NULL,
  `user_info` int(11) DEFAULT NULL,
  `notification_type` enum('ACTIVE','NOT_ACTIVE','OVERDUE') DEFAULT NULL,
  `notification_state` enum('READ','NOT_READ') DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id_UNIQUE` (`notification_id`),
  KEY `fk_event_id_idx` (`event`),
  KEY `fk_userinfo_notification_idx` (`user_info`),
  CONSTRAINT `fk_event_notification` FOREIGN KEY (`event`) REFERENCES `events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userinfo_notification` FOREIGN KEY (`user_info`) REFERENCES `common_user_info` (`user_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="notifications")
public class Notification implements Serializable{
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="notification_id")
	private Integer notificationId;
	
	@Column(name="date_time_start")
	private Timestamp dateTimeStart;
	
	@Column(name="date_time_end")
	private Timestamp dateTimeEnd;
	
	private String title;
	
	private String description;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "event")
	private Event event;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "user_info")
	private CommonUserInfo userInfo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="notification_type")
	private NotificationType notificationType;
	
	@Enumerated(EnumType.STRING)
	@Column(name="notification_state")
	private NotificationState notificationState;
	
	public Notification() {
		// TODO Auto-generated constructor stub
	}
	
	public Notification(Timestamp dateTimeStart, Timestamp dateTimeEnd, String title, String description, Event event) {
		setDateTimeStart(dateTimeStart);
		setDateTimeEnd(dateTimeEnd);
		setTitle(title);
		setDescription(description);
		setEvent(event);
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Timestamp getDateTimeStart() {
		return dateTimeStart;
	}

	public void setDateTimeStart(Timestamp dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}

	public Timestamp getDateTimeEnd() {
		return dateTimeEnd;
	}

	public void setDateTimeEnd(Timestamp dateTimeEnd) {
		this.dateTimeEnd = dateTimeEnd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}	

	public CommonUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(CommonUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public NotificationState getNotificationState() {
		return notificationState;
	}

	public void setNotificationState(NotificationState notificationState) {
		this.notificationState = notificationState;
	}	

}
