package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import javax.persistence.*;

/*
delimiter $$

CREATE TABLE `phone_numbers` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(45) DEFAULT NULL,
  `phone_type` enum('HOME','MOBILE','WORK') DEFAULT NULL,
  PRIMARY KEY (`phone_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8$$
*/
@Entity
@Table(name="phone_numbers")
public class PhoneNumber implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum PhoneType {		
	    HOME,
	    MOBILE,
	    WORK
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "phone_id")
	private int phoneId;

	@Column(name="phone_number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(name="phone_type")
	private PhoneType phoneType;	

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

}