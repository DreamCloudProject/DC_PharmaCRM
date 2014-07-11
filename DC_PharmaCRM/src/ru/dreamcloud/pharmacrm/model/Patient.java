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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="patients")
public class Patient implements Serializable {
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="patient_id")
	private Integer patientId;
	
	private String firstname;
	
	private String lastname;
	
	private String middlename;
	
	private Integer age;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "resolution_type", referencedColumnName = "resolution_id")
	private Resolution resolutionType;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "diagnosis_type", referencedColumnName = "diagnosis_id")
	private Diagnosis diagnosisType;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="patient_id")
    public List<Event> events;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Resolution getResolutionType() {
		return resolutionType;
	}

	public void setResolutionType(Resolution resolutionType) {
		this.resolutionType = resolutionType;
	}

	public Diagnosis getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(Diagnosis diagnosisType) {
		this.diagnosisType = diagnosisType;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	

}
