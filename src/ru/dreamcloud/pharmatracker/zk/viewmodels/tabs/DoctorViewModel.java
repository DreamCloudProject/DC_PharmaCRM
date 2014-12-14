package ru.dreamcloud.pharmatracker.zk.viewmodels.tabs;

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

import ru.dreamcloud.pharmatracker.model.Doctor;
import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class DoctorViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private Doctor selected;

	public Doctor getSelected() {
		return selected;
	}

	public void setSelected(Doctor selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property doctorsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Doctor> doctorsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Doctor", null));

	public ArrayList<Doctor> getDoctorsList() {
		return doctorsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!doctorsList.isEmpty()) {
			selected = doctorsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("doctorsList")
    public void addDoctorItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("doctorItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/doctorwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("doctorsList")
    public void editDoctorItem() {
    	if(!doctorsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("doctorItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/doctorwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("doctorsList")
    public void removeDoctorItem() {
    	if(!doctorsList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						params.put("searchTerm", new String());
						for (PatientHistory ph : selected.getPatientHistories()) {
							ph.setDoctor(null);
							DataSourceLoader.getInstance().mergeRecord(ph);
						}
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
    @NotifyChange("doctorsList")
    public void search(@BindingParam("searchTerm") String term) {
    	doctorsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Doctor", "where e.firstname LIKE '%"+term+"%' or e.middlename LIKE '%"+term+"%' or e.lastname LIKE '%"+term+"%'"));
    }

}
