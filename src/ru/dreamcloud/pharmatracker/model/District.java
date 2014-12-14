package ru.dreamcloud.pharmatracker.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
delimiter $$

CREATE TABLE `districts` (
  `district_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`district_id`),
  UNIQUE KEY `district_id_UNIQUE` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$
*/

@Entity
@Table(name="districts")
public class District implements Serializable{
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="district_id")
	private Integer districtId;	
	private String title;
	private String description;
	
	public District() {
		// TODO Auto-generated constructor stub
	}
	
	public District(String title, String description) {
		setTitle(title);
		setDescription(description);
	}
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, mappedBy="district")
    private List<Region> regions;
	
	public Integer getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
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

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	
}
