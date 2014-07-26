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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
delimiter $$

CREATE TABLE `patients` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `resolution` int(11) DEFAULT NULL,
  `diagnosis` int(11) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`patient_id`),
  UNIQUE KEY `patient_id_UNIQUE` (`patient_id`),
  KEY `fk_contact_info_idx` (`contact_info`),
  KEY `fk_diagnosis_idx` (`diagnosis`),
  KEY `fk_resolution_idx` (`resolution`),
  CONSTRAINT `fk_contact_info_patient` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_diagnosis` FOREIGN KEY (`diagnosis`) REFERENCES `diagnosis` (`diagnosis_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_resolution` FOREIGN KEY (`resolution`) REFERENCES `resolutions` (`resolution_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$

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
    @JoinColumn(name = "resolution", referencedColumnName = "resolution_id")
	private Resolution resolution;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "diagnosis", referencedColumnName = "diagnosis_id")
	private Diagnosis diagnosis;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="contact_info")
	private ContactInfo contactInfo;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="patient")
    private List<Event> events;
	
	public Patient(String firstname, String middlename, String lastname, Integer age, Resolution resolution, Diagnosis diagnosis, ContactInfo contactInfo) {
		setFirstname(firstname);
		setLastname(lastname);
		setMiddlename(middlename);
		setAge(age);
		setResolution(resolution);
		setDiagnosis(diagnosis);		
	}
	
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

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Diagnosis getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Diagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
