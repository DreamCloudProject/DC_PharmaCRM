package ru.dreamcloud.alexion.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*delimiter $$

CREATE TABLE `resolutions` (
  `resolution_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`resolution_id`),
  UNIQUE KEY `resolution_id_UNIQUE` (`resolution_id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="resolutions")
public class Resolution implements Serializable{
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="resolution_id")
	private Integer resolutionId;	
	private String title;
	private String description;
	
	public Resolution(String title, String description) {
		setTitle(title);
		setDescription(description);
	}

	public Integer getResolutionId() {
		return resolutionId;
	}

	public void setResolutionId(Integer resolutionId) {
		this.resolutionId = resolutionId;
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

}