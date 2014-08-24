package ru.dreamcloud.alexion.zk.viewmodels.tabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Patient;
import ru.dreamcloud.persistence.jpa.DataSourceLoader;

public class PatientViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private Patient selected;

	public Patient getSelected() {
		return selected;
	}

	public void setSelected(Patient selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property attPersonsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Patient> patientsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Patient", null));

	public ArrayList<Patient> getPatientsList() {
		return patientsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!patientsList.isEmpty()) {
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
    public void editPatientItem() {
    	if(!patientsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("patientItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/patientwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("patientsList")
    public void removePatientItem() {
    	if(!patientsList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						params.put("searchTerm", new String());
						DataSourceLoader.getInstance().removeRecord(selected);
						BindUtils.postGlobalCommand(null, null, "search", params);
						Clients.showNotification("Запись успешно удалена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
					}
					
				}
			});
    	}
    }
    
    @GlobalCommand
    @Command
    @NotifyChange("patientsList")
    public void search(@BindingParam("searchTerm") String term) {
    	patientsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Patient", "e.firstname LIKE '%"+term+"%' or e.middlename LIKE '%"+term+"%' or e.lastname LIKE '%"+term+"%'"));
    }

}
