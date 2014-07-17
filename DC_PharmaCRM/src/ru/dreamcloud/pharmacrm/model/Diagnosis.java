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
@Table(name="diagnosis")
public class Diagnosis implements Serializable {
	
	@Id	@GeneratedValue(strategy = IDENTITY)
	@Column(name="diagnosis_id")
	private Integer diagnosisId;
	private String title;
	private String description;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="diagnosisType")
	public List<Patient> patients;

}
