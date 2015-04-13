package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.util.jpa.DataSourceLoader;

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
		patientHistory = phItem;
		Sessions.getCurrent().setAttribute("currentPatientHistory", patientHistory);
	}
}
