package ru.dreamcloud.pharmatracker.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
delimiter $$

CREATE TABLE `regions` (
  `region_id` int(11) NOT NULL AUTO_INCREMENT,
  `district` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`region_id`),
  UNIQUE KEY `region_id_UNIQUE` (`region_id`),
  KEY `fk_district_idx` (`district`),
  CONSTRAINT `fk_district` FOREIGN KEY (`district`) REFERENCES `districts` (`district_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="regions")
public class Region implements Serializable {
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="region_id")
	private Integer regionId;	
	private String title;
	private String description;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "district")
	private District district;
	
	public Region() {
		// TODO Auto-generated constructor stub
	}
	
	public Region(District district, String title, String description) {
		setDistrict(district);
		setTitle(title);
		setDescription(description);
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}	

}
