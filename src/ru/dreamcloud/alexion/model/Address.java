package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import javax.persistence.*;

/*
delimiter $$

CREATE TABLE `addresses` (
  `address_id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `home_number` int(11) DEFAULT NULL,
  `corps_number` int(11) DEFAULT NULL,
  `apartment_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
 */
@Entity
@Table(name="addresses")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "address_id")
	private int addressId;

	@Column(name="apartment_number")
	private int apartmentNumber;

	@Column(name="corps_number")
	private int corpsNumber;

	@Column(name="home_number")
	private int homeNumber;

	private String street;

	private String title;

	public Address(int apartment, int corps, int home, String street, String title) {
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

	public int getApartmentNumber() {
		return this.apartmentNumber;
	}

	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public int getCorpsNumber() {
		return this.corpsNumber;
	}

	public void setCorpsNumber(int corpsNumber) {
		this.corpsNumber = corpsNumber;
	}

	public int getHomeNumber() {
		return this.homeNumber;
	}

	public void setHomeNumber(int homeNumber) {
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

}