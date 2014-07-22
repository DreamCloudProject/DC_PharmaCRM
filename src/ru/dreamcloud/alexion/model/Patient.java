package ru.dreamcloud.alexion.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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

/*
delimiter $$

CREATE TABLE `patients` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `resolution_type` int(11) DEFAULT NULL,
  `diagnosis_type` int(11) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`patient_id`),
  UNIQUE KEY `patient_id_UNIQUE` (`patient_id`),
  KEY `fk_diagnosis_type_idx` (`diagnosis_type`),
  KEY `fk_resolution_type_idx` (`resolution_type`),
  KEY `fk_region_idx` (`contact_info`),
  CONSTRAINT `fk_contact_info` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_diagnosis_type` FOREIGN KEY (`diagnosis_type`) REFERENCES `diagnosis` (`diagnosis_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_resolution_type` FOREIGN KEY (`resolution_type`) REFERENCES `resolutions` (`resolution_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8$$

*/

@Entity
@Table(name="patients")
public class Patient implements Serializable {
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="patient_id")
	private Integer patientId;
	
	private String firstname;
	
	private String lastname;
	
	private String middlename;
	
	private Integer age;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "resolution_type", referencedColumnName = "resolution_id")
	private Resolution resolutionType;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "diagnosis_type", referencedColumnName = "diagnosis_id")
	private Diagnosis diagnosisType;	
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="patient")
    private List<Event> events;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="patient")
	private List<ContactInfo> contacts;

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Resolution getResolutionType() {
		return resolutionType;
	}

	public void setResolutionType(Resolution resolutionType) {
		this.resolutionType = resolutionType;
	}

	public Diagnosis getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(Diagnosis diagnosisType) {
		this.diagnosisType = diagnosisType;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<ContactInfo> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactInfo> contacts) {
		this.contacts = contacts;
	}	

}
