package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.AttendantPerson;

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
	
    @Command
    @NotifyChange("currentAttendantPerson")
    public void editAttPersonItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("attPersonItem", currentAttendantPerson);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/attpersonwindow.zul", null, params);
        window.doModal();
    }
	
}
