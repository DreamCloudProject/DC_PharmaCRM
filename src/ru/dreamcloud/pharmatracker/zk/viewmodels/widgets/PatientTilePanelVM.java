package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import ru.dreamcloud.pharmatracker.model.Patient;

public class PatientTilePanelVM {
	
	/**************************************
	  Property currentPatient	 
	***************************************/
	private Patient currentPatient;

	public Patient getCurrentPatient() {
		return currentPatient;
	}

	public void setCurrentPatient(Patient currentPatient) {
		this.currentPatient = currentPatient;
	}
	
	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentPatient") Patient patient) {		
		currentPatient = patient;
	}
}
