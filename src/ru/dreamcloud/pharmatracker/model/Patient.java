package ru.dreamcloud.pharmatracker.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
delimiter $$

CREATE TABLE `patients` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `birthdate` datetime DEFAULT NULL,
  `diagnosis` int(11) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  `disability` enum('GROUP1','GROUP2','GROUP3','CHILDREN') DEFAULT NULL,
  PRIMARY KEY (`patient_id`),
  UNIQUE KEY `patient_id_UNIQUE` (`patient_id`),
  KEY `fk_contact_info_idx` (`contact_info`),
  KEY `fk_diagnosis_idx` (`diagnosis`),
  CONSTRAINT `fk_contact_info_patient` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_diagnosis` FOREIGN KEY (`diagnosis`) REFERENCES `diagnosis` (`diagnosis_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
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
	
	private Timestamp birthdate;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "diagnosis", referencedColumnName = "diagnosis_id")
	private Diagnosis diagnosis;
	
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="contact_info")
	private ContactInfo contactInfo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="disability")
	private DisabilityGroup disability;
	
	@OneToMany(cascade={CascadeType.PERSIST}, mappedBy="patient")
	private List<PatientHistory> patientHistories;
	
	public Patient() {
		// TODO Auto-generated constructor stub
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

	public Timestamp getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
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
	
	public DisabilityGroup getDisability() {
		return disability;
	}

	public void setDisability(DisabilityGroup disability) {
		this.disability = disability;
	}

	public List<PatientHistory> getPatientHistories() {
		return patientHistories;
	}

	public void setPatientHistories(List<PatientHistory> patientHistories) {
		this.patientHistories = patientHistories;
	}

	@Transient
	public String getFullname(){
		String fullname = new String();
		if(lastname != null){
			fullname += lastname;
		}
		if(firstname != null){
			fullname += " " + firstname;
		}
		if(middlename != null){
			fullname += " " + middlename;
		}		
		return fullname;
	}

}
