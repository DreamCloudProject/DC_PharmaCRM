package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Patient;
import ru.dreamcloud.pharmatracker.model.PatientHistory;

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
	
    @Command
    @NotifyChange("currentPatient")
    public void editPatientItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("patientItem", currentPatient);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/patientwindow.zul", null, params);
        window.doModal();
    }
    
	@GlobalCommand
	@Command
	@NotifyChange("currentPatient")
	public void refreshPatientTilePanel(@BindingParam("patient") Patient pItem){
		currentPatient = pItem;
	}
}
