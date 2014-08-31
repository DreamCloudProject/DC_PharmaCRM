package ru.dreamcloud.alexion.model;

/*delimiter $$

CREATE TABLE `diagnosis` (
  `diagnosis_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`diagnosis_id`),
  UNIQUE KEY `diagnosis_id_UNIQUE` (`diagnosis_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$

*/

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="diagnosis")
public class Diagnosis implements Serializable {
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="diagnosis_id")
	private Integer diagnosisId;
	private String title;
	private String description;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, mappedBy="diagnosis")
	private List<Patient> patients;
	
	public Diagnosis() {
		// TODO Auto-generated constructor stub
	}
	
	public Diagnosis(String title, String description) {
		setTitle(title);
		setDescription(description);
	}
	
	public Integer getDiagnosisId() {
		return diagnosisId;
	}
	
	public void setDiagnosisId(Integer diagnosisId) {
		this.diagnosisId = diagnosisId;
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

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	
}
