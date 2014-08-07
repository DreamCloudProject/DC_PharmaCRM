package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import javax.persistence.*;

/*
delimiter $$

CREATE TABLE `phone_numbers` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(45) DEFAULT NULL,
  `phone_type` enum('HOME','MOBILE','WORK') DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`phone_id`),
  KEY `fk_contact_info_phone_idx` (`contact_info`),
  CONSTRAINT `fk_contact_info_phone` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="phone_numbers")
public class PhoneNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "phone_id")
	private int phoneId;

	@Column(name="phone_number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(name="phone_type")
	private PhoneType phoneType;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "contact_info")
	private ContactInfo contactInfo;
	
	public PhoneNumber() {
		// TODO Auto-generated constructor stub
	}

	public PhoneNumber(String phoneNumber, PhoneType phoneType) {
		setPhoneNumber(phoneNumber);
		setPhoneType(phoneType);
	}

	public int getPhoneId() {
		return this.phoneId;
	}

	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public PhoneType getPhoneType() {
		return this.phoneType;
	}

	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}	

}