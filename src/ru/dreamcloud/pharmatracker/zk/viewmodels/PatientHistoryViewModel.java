package ru.dreamcloud.pharmatracker.zk.viewmodels;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

import ru.dreamcloud.pharmatracker.model.PatientHistory;

public class PatientHistoryViewModel {
	/**************************************
	  Property patientHistory 
	***************************************/
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
    public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		if(Sessions.getCurrent().getAttribute("currentPatientHistory") != null){
			setPatientHistory((PatientHistory)Sessions.getCurrent().getAttribute("currentPatientHistory"));
		} else {
			Executions.sendRedirect(Labels.getLabel("panels.index.URL"));
		}
    }    

}
