package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class EventTilePanelVM {

	/**************************************
	 * Property selected
	 ***************************************/
	private Event selected;

	public Event getSelected() {
		return selected;
	}

	public void setSelected(Event selected) {
		this.selected = selected;
	}
	
	/**************************************
	 * Property patientHistoryItem
	 ***************************************/
	
	private PatientHistory patientHistoryItem;	
	
	public PatientHistory getPatientHistoryItem() {
		return patientHistoryItem;
	}

	public void setPatientHistoryItem(PatientHistory patientHistoryItem) {
		this.patientHistoryItem = patientHistoryItem;
	}

	/**************************************
	 * Property eventsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Event> eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", null));

	public ArrayList<Event> getEventsList() {
		return eventsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ExecutionArgParam("currentPatientHistory") PatientHistory patientHistory) {
		patientHistoryItem = patientHistory;
		if (!eventsList.isEmpty()) {
			selected = eventsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("eventsList")
    public void addEventItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("eventItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("eventsList")
    public void editEventItem() {
    	if(!eventsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("eventItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("eventsList")
    public void removeEventItem() {
    	if(!eventsList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<org.zkoss.zk.ui.event.Event>() {			
				@Override
				public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
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
    @NotifyChange("eventsList")
    public void search(@BindingParam("searchTerm") String term) {
    	eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
