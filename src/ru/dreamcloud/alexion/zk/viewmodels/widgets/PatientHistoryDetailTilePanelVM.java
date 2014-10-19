package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.alexion.model.PatientHistory;

public class PatientHistoryDetailTilePanelVM {
	
	private PatientHistory patientHistory;

	public PatientHistory getPatientHistory() {
		return patientHistory;
	}

	public void setPatientHistory(PatientHistory patientHistory) {
		this.patientHistory = patientHistory;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentPatientHistory") PatientHistory phItem) {		
		refreshPatientHistory(phItem);
	}
	
	@GlobalCommand
	@Command
	@NotifyChange("patientHistory")
	public void refreshPatientHistory(@BindingParam("patientHistory") PatientHistory phItem){
		this.patientHistory = phItem;
		Sessions.getCurrent().setAttribute("currentPatientHistory", this.patientHistory);
	}
}
