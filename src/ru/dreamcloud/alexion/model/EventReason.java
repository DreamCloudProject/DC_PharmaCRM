package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import javax.persistence.*;


/*
delimiter $$

CREATE TABLE `event_reasons` (
  `event_reason_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`event_reason_id`)
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

}