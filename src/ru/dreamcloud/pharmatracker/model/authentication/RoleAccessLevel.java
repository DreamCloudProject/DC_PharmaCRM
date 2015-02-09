package ru.dreamcloud.pharmatracker.model.authentication;

import org.zkoss.util.resource.Labels;

public enum RoleAccessLevel {
	GUEST(Labels.getLabel("messages.commonRoleAccess.GUEST.name")),
	USER(Labels.getLabel("messages.commonRoleAccess.USER.name")),
	EMPLOYEE(Labels.getLabel("messages.commonRoleAccess.EMPLOYEE.name")),
	OWNER(Labels.getLabel("messages.commonRoleAccess.OWNER.name"));
	
	private final String name;
	
	public String getName() {
		return name;
	}

	private RoleAccessLevel(String n){
		name=n;		
	}
	
	
}
