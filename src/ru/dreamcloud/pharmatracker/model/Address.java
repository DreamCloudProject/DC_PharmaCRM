package ru.dreamcloud.pharmatracker.model;

import java.io.Serializable;
import javax.persistence.*;

/*
delimiter $$

CREATE TABLE `addresses` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `apartment_number` varchar(255) DEFAULT NULL,
  `corps_number` varchar(255) DEFAULT NULL,
  `home_number` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `contact_info` int(11) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `fk_contact_info_address` (`contact_info`),
  CONSTRAINT `fk_contact_info_address` FOREIGN KEY (`contact_info`) REFERENCES `contact_info` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
 */
@Entity
@Table(name="addresses")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "address_id")
	private int addressId;

	@Column(name="apartment_number")
	private String apartmentNumber;

	@Column(name="corps_number")
	private String corpsNumber;

	@Column(name="home_number")
	private String homeNumber;

	private String street;

	private String title;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "contact_info")
	private ContactInfo contactInfo;
	
	public Address() {
	}

	public Address(String apartment, String corps, String home, String street, String title) {
		setApartmentNumber(apartment);
		setCorpsNumber(corps);
		setHomeNumber(home);
		setStreet(street);
		setTitle(title);
	}

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getApartmentNumber() {
		return this.apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getCorpsNumber() {
		return this.corpsNumber;
	}

	public void setCorpsNumber(String corpsNumber) {
		this.corpsNumber = corpsNumber;
	}

	public String getHomeNumber() {
		return this.homeNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}	

}