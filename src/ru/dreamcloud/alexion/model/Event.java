package ru.dreamcloud.alexion.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
  `patient` int(11) DEFAULT NULL,
  `notification_create_flag` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `event_id_UNIQUE` (`event_id`),
  KEY `fk_patient_id_idx` (`patient`),
  CONSTRAINT `fk_patient_event` FOREIGN KEY (`patient`) REFERENCES `patients` (`patient_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$
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
	private Date dateTimeStart;
	
	@Column(name="date_time_end")
	private Date dateTimeEnd;

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "patient")
	private Patient patient;
	
	@Column(name="notification_create_flag")
	private String notificationCreateFlag;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="event")
    private List<Notification> notifications;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="event")
    private List<Document> documents;
	
	public Event() {
		// TODO Auto-generated constructor stub
	}
	
	public Event(String title, String description, Date dateTimeStart, Date dateTimeEnd, Patient patient, String notificationCreateFlag) {
		setTitle(title);
		setDescription(description);
		setDateTimeStart(dateTimeStart);
		setDateTimeEnd(dateTimeEnd);
		setNotificationCreateFlag(notificationCreateFlag);
		setPatient(patient);
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

	public Date getDateTimeStart() {
		return dateTimeStart;
	}

	public void setDateTimeStart(Date dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}

	public Date getDateTimeEnd() {
		return dateTimeEnd;
	}

	public void setDateTimeEnd(Date dateTimeEnd) {
		this.dateTimeEnd = dateTimeEnd;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getNotificationCreateFlag() {
		return notificationCreateFlag;
	}

	public void setNotificationCreateFlag(String notificationCreateFlag) {
		this.notificationCreateFlag = notificationCreateFlag;
	}	
	
}
