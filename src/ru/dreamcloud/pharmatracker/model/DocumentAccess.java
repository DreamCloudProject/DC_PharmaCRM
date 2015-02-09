package ru.dreamcloud.pharmatracker.model;

import org.zkoss.util.resource.Labels;

public enum DocumentAccess {
	FOR_ALL(Labels.getLabel("messages.documentAccess.FOR_ALL.name")),
	FOR_EMPLOYEES(Labels.getLabel("messages.documentAccess.FOR_EMPLOYEES.name")),
	FOR_OWNERS(Labels.getLabel("messages.documentAccess.FOR_OWNERS.name"));
	
	private final String name;	
	
	public String getName() {
		return name;
	}

	private DocumentAccess(String n){
		name=n;		
	}
}
