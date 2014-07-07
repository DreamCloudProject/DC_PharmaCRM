package ru.dreamcloud.pharmacrm.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	@Column(name="resolution_type")
	private Integer resolutionType;
	
	@Column(name="diagnosis_type")
	private Integer diagnosisType;

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

	public Integer getResolutionType() {
		return resolutionType;
	}

	public void setResolutionType(Integer resolutionType) {
		this.resolutionType = resolutionType;
	}

	public Integer getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(Integer diagnosisType) {
		this.diagnosisType = diagnosisType;
	}
	

}
