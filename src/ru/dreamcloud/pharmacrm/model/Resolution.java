package ru.dreamcloud.pharmacrm.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="resolutions")
public class Resolution implements Serializable{
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="resolution_id")
	private Integer resolutionId;	
	private String title;
	private String description;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="resolutionType")
    public List<Patient> patients;

	public Integer getResolutionId() {
		return resolutionId;
	}

	public void setResolutionId(Integer resolutionId) {
		this.resolutionId = resolutionId;
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

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}	

}
