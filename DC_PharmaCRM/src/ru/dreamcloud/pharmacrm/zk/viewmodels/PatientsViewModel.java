package ru.dreamcloud.pharmacrm.zk.viewmodels;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmacrm.model.Patient;
import ru.dreamcloud.pharmacrm.utils.DataSourceLoader;

public class PatientsViewModel {
	
	private Patient selected;
	
    public Patient getSelected() {
		return selected;
	}

	public void setSelected(Patient selected) {
		this.selected = selected;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Patient> patientsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Patient", null));

	@Init
    public void init() {
        selected = patientsList.get(0);
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
    
    
	public ArrayList<Patient> getPatientsList() {
		return patientsList;
	}


}
