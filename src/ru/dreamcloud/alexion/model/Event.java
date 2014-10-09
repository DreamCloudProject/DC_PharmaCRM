package ru.dreamcloud.alexion.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*delimiter $$

CREATE TABLE `events` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(1024) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `date_time_start` datetime NOT NULL,
  `date_time_end` datetime NOT NULL,
  `notification_create_flag` varchar(5) DEFAULT NULL,
  `message_type` enum('TODO','IN_PROGRESS','DONE') DEFAULT NULL,
  `patient_history` int(11) DEFAULT NULL,
  `event_reason` int(11) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `event_id_UNIQUE` (`event_id`),
  KEY `fk_ph_event_idx` (`patient_history`),
  KEY `fk_er_event_idx` (`event_reason`),
  CONSTRAINT `fk_er_event` FOREIGN KEY (`event_reason`) REFERENCES `event_reasons` (`event_reason_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ph_event` FOREIGN KEY (`patient_history`) REFERENCES `patient_histories` (`patient_histories_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="events")
public class Event implements Serializable{
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="event_id")
	private Integer eventId;
	
	private String title;
	
	private String description;
	
	@Column(name="date_time_start")
	private Timestamp dateTimeStart;
	
	@Column(name="date_time_end")
	private Timestamp dateTimeEnd;

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "patient_history")
	private PatientHistory patientHistory;
	
	@Column(name="notification_create_flag")
	private String notificationCreateFlag;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="event")
    private List<Document> documents;
	
	@Enumerated(EnumType.STRING)
	@Column(name="message_type")
	private MessageType messageType;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "event_reason")
	private EventReason eventReason;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="event")
    private List<Notification> notifications;
	
	public Event() {
		// TODO Auto-generated constructor stub
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
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

	public PatientHistory getPatientHistory() {
		return patientHistory;
	}

	public void setPatientHistory(PatientHistory patientHistory) {
		this.patientHistory = patientHistory;
	}

	public String getNotificationCreateFlag() {
		return notificationCreateFlag;
	}

	public void setNotificationCreateFlag(String notificationCreateFlag) {
		this.notificationCreateFlag = notificationCreateFlag;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public EventReason getEventReason() {
		return eventReason;
	}

	public void setEventReason(EventReason eventReason) {
		this.eventReason = eventReason;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
	
}
