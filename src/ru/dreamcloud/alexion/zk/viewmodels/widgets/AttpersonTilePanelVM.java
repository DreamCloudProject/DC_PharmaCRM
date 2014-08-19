package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import ru.dreamcloud.alexion.model.AttendantPerson;

public class AttpersonTilePanelVM {
	
	/**************************************
	  Property currentAttendantPerson
	***************************************/
	private AttendantPerson currentAttendantPerson;
	
	
	public AttendantPerson getCurrentAttendantPerson() {
		return currentAttendantPerson;
	}

	public void setCurrentAttendantPerson(AttendantPerson currentAttendantPerson) {
		this.currentAttendantPerson = currentAttendantPerson;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentAttendantPerson") AttendantPerson attperson) {		
		currentAttendantPerson = attperson;
	}
}
