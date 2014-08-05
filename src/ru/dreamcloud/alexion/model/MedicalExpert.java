package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import javax.persistence.*;


/*
delimiter $$

CREATE TABLE `medical_experts` (
  `medical_expert_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `middlename` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`medical_expert_id`),
  KEY `fk_contact_info_medexpert_idx` (`contact_info`),
  CONSTRAINT `fk_contact_info_medexpert` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
*/
@Entity
@Table(name="medical_experts")
public class MedicalExpert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="medical_expert_id")
	private int medicalExpertId;

	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="contact_info")
	private ContactInfo contactInfo;

	private String firstname;

	private String lastname;

	private String middlename;

	public MedicalExpert() {
	}

	public int getMedicalExpertId() {
		return this.medicalExpertId;
	}

	public void setMedicalExpertId(int medicalExpertId) {
		this.medicalExpertId = medicalExpertId;
	}

	public ContactInfo getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

}