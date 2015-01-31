package ru.dreamcloud.pharmatracker.model;

import org.zkoss.util.resource.Labels;

public enum DisabilityGroup {
	GROUP1(Labels.getLabel("messages.disabilityGroup.GROUP1.name")),
	GROUP2(Labels.getLabel("messages.disabilityGroup.GROUP2.name")),
	GROUP3(Labels.getLabel("messages.disabilityGroup.GROUP3.name")),
	CHILDREN(Labels.getLabel("messages.disabilityGroup.CHILDREN.name"));
	
	private final String name;	
	
	public String getName() {
		return name;
	}

	private DisabilityGroup(String n){
		name = n;
	}
}
