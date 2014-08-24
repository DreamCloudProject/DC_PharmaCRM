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

import ru.dreamcloud.alexion.model.EventReason;
import ru.dreamcloud.persistence.jpa.DataSourceLoader;

public class EventReasonViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private EventReason selected;

	public EventReason getSelected() {
		return selected;
	}

	public void setSelected(EventReason selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property eventReasonsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<EventReason> eventReasonsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("EventReason", null));

	public ArrayList<EventReason> getEventReasonsList() {
		return eventReasonsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!eventReasonsList.isEmpty()) {
			selected = eventReasonsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("eventReasonsList")
    public void addEventReasonItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("eventReasonItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventreasonwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("eventReasonsList")
    public void editEventReasonItem() {
    	if(!eventReasonsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("eventReasonItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventreasonwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("eventReasonsList")
    public void removeEventReasonItem() {
    	if(!eventReasonsList.isEmpty()) {
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
    @NotifyChange("eventReasonsList")
    public void search(@BindingParam("searchTerm") String term) {
    	eventReasonsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("EventReason", "e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
