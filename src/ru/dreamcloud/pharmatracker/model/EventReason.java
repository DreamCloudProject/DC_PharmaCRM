package ru.dreamcloud.pharmatracker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/*
delimiter $$

CREATE TABLE `event_reasons` (
  `event_reason_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`event_reason_id`),
  UNIQUE KEY `event_reason_id_UNIQUE` (`event_reason_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="event_reasons")
public class EventReason implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="event_reason_id")
	private int eventReasonsId;

	private String description;

	private String title;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, mappedBy="eventReason")
	private List<Event> events;

	public EventReason() {
	}

	public int getEventReasonsId() {
		return this.eventReasonsId;
	}

	public void setEventReasonsId(int eventReasonsId) {
		this.eventReasonsId = eventReasonsId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}	

}