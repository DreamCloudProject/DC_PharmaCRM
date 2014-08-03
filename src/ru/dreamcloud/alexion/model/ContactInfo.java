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

/*delimiter $$

CREATE TABLE `contact_info` (
  `contact_id` int(11) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `region` int(11) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `address` int(11) DEFAULT NULL,
  PRIMARY KEY (`contact_id`),
  UNIQUE KEY `contact_id_UNIQUE` (`contact_id`),
  KEY `fk_region_idx` (`region`),
  KEY `fk_phone_idx` (`phone`),
  KEY `fk_address_idx` (`address`),
  CONSTRAINT `fk_phone` FOREIGN KEY (`phone`) REFERENCES `phone_numbers` (`phone_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_address` FOREIGN KEY (`address`) REFERENCES `addresses` (`address_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_region` FOREIGN KEY (`region`) REFERENCES `regions` (`region_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="contact_info")
public class ContactInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="contact_id")
	private Integer contactId;
	
	private String city;
	
	private String country;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="region")
	private Region region;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="contactInfo")
	private List<PhoneNumber> phonesList;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="contactInfo")
	private List<Address> addressList;
	
	public ContactInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public ContactInfo(String city, String country, Region region, List<PhoneNumber> phonesList, String postalCode, List<Address> addressList) {
		setCity(city);
		setCountry(country);
		setRegion(region);
		setPostalCode(postalCode);
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<PhoneNumber> getPhonesList() {
		return phonesList;
	}

	public void setPhonesList(List<PhoneNumber> phonesList) {
		this.phonesList = phonesList;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}		
	
}
