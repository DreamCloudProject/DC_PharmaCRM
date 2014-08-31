package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*
delimiter $$

CREATE TABLE `projects` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id_UNIQUE` (`project_id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="projects")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="project_id")
	private int projectId;

	private String description;

	private String title;
	
	@OneToMany(cascade={CascadeType.PERSIST}, mappedBy="project")
	private List<PatientHistory> patientHistories;

	public Project() {
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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

	public List<PatientHistory> getPatientHistories() {
		return patientHistories;
	}

	public void setPatientHistories(List<PatientHistory> patientHistories) {
		this.patientHistories = patientHistories;
	}

}