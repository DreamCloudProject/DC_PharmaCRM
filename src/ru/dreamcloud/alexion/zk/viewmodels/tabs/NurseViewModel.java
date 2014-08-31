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

import ru.dreamcloud.alexion.model.AttendantPerson;
import ru.dreamcloud.alexion.model.Nurse;
import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class NurseViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private Nurse selected;

	public Nurse getSelected() {
		return selected;
	}

	public void setSelected(Nurse selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property attPersonsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Nurse> nursesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Nurse", null));

	public ArrayList<Nurse> getNursesList() {
		return nursesList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!nursesList.isEmpty()) {
			selected = nursesList.get(0);
		}
	}
	
    @Command
    @NotifyChange("nursesList")
    public void addNurseItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("nurseItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/nursewindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("nursesList")
    public void editNurseItem() {
    	if(!nursesList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("nurseItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/nursewindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("nursesList")
    public void removeNurseItem() {
    	if(!nursesList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						params.put("searchTerm", new String());
						for (PatientHistory ph : selected.getPatientHistories()) {
							ph.setNurse(null);
							DataSourceLoader.getInstance().updateRecord(ph);
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
    @NotifyChange("nursesList")
    public void search(@BindingParam("searchTerm") String term) {
    	nursesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Nurse", "where e.firstname LIKE '%"+term+"%' or e.middlename LIKE '%"+term+"%' or e.lastname LIKE '%"+term+"%'"));
    }

}
