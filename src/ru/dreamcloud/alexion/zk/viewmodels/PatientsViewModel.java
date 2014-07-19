package ru.dreamcloud.alexion.zk.viewmodels;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Patient;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class PatientsViewModel {
	
	private String postMessage;
	
	public String getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}

	/**************************************
	  Property selected	 
	***************************************/
	private Patient selected;
	
    public Patient getSelected() {
		return selected;
	}

	public void setSelected(Patient selected) {
		this.selected = selected;
	}
	
	/**************************************
	  Property patientsList	 
	***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Patient> patientsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Patient", null));
	
	public ArrayList<Patient> getPatientsList() {
		return patientsList;
	}
	
	/**************************************
	  Methods	 
	***************************************/
	@Init
    public void init() {		
		if(!patientsList.isEmpty()){
			selected = patientsList.get(0);
		}
    }
	
    @Command
    @NotifyChange("patientsList")
    public void addPatientItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("patientItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/patientwindow.zul", null, params);
    	window.doModal();
    }
    
    @Command
    @NotifyChange("patientsList")
    public void test() {
    	Sessions.getCurrent().setAttribute("test", getPostMessage());
    	Executions.sendRedirect("events.zul");
    	
    }

}
