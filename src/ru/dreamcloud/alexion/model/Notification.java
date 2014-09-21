package ru.dreamcloud.alexion.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
delimiter $$

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `date_time_start` datetime DEFAULT NULL,
  `date_time_end` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `event` int(11) DEFAULT NULL,
  `notification_type` enum('READ','UNREAD') DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id_UNIQUE` (`notification_id`),
  KEY `fk_event_id_idx` (`event`),
  CONSTRAINT `fk_event_notification` FOREIGN KEY (`event`) REFERENCES `events` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
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
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "event")
	private Event event;
	
	@Enumerated(EnumType.STRING)
	@Column(name="notification_type")
	private NotificationType notificationType;
	
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

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

}
