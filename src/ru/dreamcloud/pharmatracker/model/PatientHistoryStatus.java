package ru.dreamcloud.pharmatracker.model;

import org.zkoss.util.resource.Labels;

public enum PatientHistoryStatus {
	OPEN(Labels.getLabel("messages.patientHistoryStatus.open.name")),
	CLOSED(Labels.getLabel("messages.patientHistoryStatus.closed.name"));
	
    private final String name;    
    
    public String getName() {
		return name;
	}
    
	private PatientHistoryStatus(String n){
      name=n;      
    }
}
