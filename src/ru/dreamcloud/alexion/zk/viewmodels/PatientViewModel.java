package ru.dreamcloud.alexion.zk.viewmodels;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

import ru.dreamcloud.alexion.model.Patient;

public class PatientViewModel {
	/**************************************
	  Property patient 
	***************************************/
	private Patient patient;	

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
    public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		if(Sessions.getCurrent().getAttribute("patient") != null){
			setPatient((Patient)Sessions.getCurrent().getAttribute("patient"));
		} else {
			Executions.sendRedirect(Labels.getLabel("panels.index.URL"));
		}
    }    

}
