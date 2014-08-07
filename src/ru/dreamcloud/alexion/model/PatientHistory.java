package ru.dreamcloud.alexion.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*
delimiter $$

CREATE TABLE `patient_histories` (
  `patient_histories_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient` int(11) DEFAULT NULL,
  `resolution` int(11) DEFAULT NULL,
  `attperson` int(11) DEFAULT NULL,
  `medical_expert` int(11) DEFAULT NULL,
  `nurse` int(11) DEFAULT NULL,
  `project` int(11) DEFAULT NULL,
  PRIMARY KEY (`patient_histories_id`),
  UNIQUE KEY `patient_hystories_id_UNIQUE` (`patient_histories_id`),
  KEY `fk_resolution_idx` (`resolution`),
  KEY `fk_medexpert_idx` (`medical_expert`),
  KEY `fk_nurse_idx` (`nurse`),
  KEY `fk_project_idx` (`project`),
  KEY `fk_attperson_ph_idx` (`attperson`),
  KEY `fk_patient_ph_idx` (`patient`),
  CONSTRAINT `fk_attperson_ph` FOREIGN KEY (`attperson`) REFERENCES `attendant_persons` (`att_person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_medexpert_ph` FOREIGN KEY (`medical_expert`) REFERENCES `medical_experts` (`medical_expert_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_nurse_ph` FOREIGN KEY (`nurse`) REFERENCES `nurses` (`nurse_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_patient_ph` FOREIGN KEY (`patient`) REFERENCES `patients` (`patient_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_ph` FOREIGN KEY (`project`) REFERENCES `projects` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_resolution_ph` FOREIGN KEY (`resolution`) REFERENCES `resolutions` (`resolution_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
*/
@Entity
@Table(name="patient_histories")
public class PatientHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="patient_histories_id")
	private int patientHistoriesId;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "attperson", referencedColumnName = "att_person_id")
	private AttendantPerson attperson;

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "medical_expert", referencedColumnName = "medical_expert_id")
	private MedicalExpert medicalExpert;

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "nurse", referencedColumnName = "nurse_id")
	private Nurse nurse;

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "patient", referencedColumnName = "patient_id")
	private Patient patient;

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "project", referencedColumnName = "project_id")
	private Project project;

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "resolution", referencedColumnName = "resolution_id")
	private Resolution resolution;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="patientHistory")
    private List<Event> events;

	public PatientHistory() {
	}

	public int getPatientHistoriesId() {
		return this.patientHistoriesId;
	}

	public void setPatientHistoriesId(int patientHistoriesId) {
		this.patientHistoriesId = patientHistoriesId;
	}

	public AttendantPerson getAttperson() {
		return this.attperson;
	}

	public void setAttperson(AttendantPerson attperson) {
		this.attperson = attperson;
	}

	public MedicalExpert getMedicalExpert() {
		return medicalExpert;
	}

	public void setMedicalExpert(MedicalExpert medicalExpert) {
		this.medicalExpert = medicalExpert;
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}