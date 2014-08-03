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
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class AttPersonViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private AttendantPerson selected;

	public AttendantPerson getSelected() {
		return selected;
	}

	public void setSelected(AttendantPerson selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property attPersonsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<AttendantPerson> attPersonsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("AttendantPerson", null));

	public ArrayList<AttendantPerson> getAttPersonsList() {
		return attPersonsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!attPersonsList.isEmpty()) {
			selected = attPersonsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("attPersonsList")
    public void addAttPersonItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("attPersonItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/attpersonwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("attPersonsList")
    public void editAttPersonItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("attPersonItem", selected);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/attpersonwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("attPersonsList")
    public void removeAttPersonItem() {
    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
			@Override
			public void onEvent(Event event) throws Exception {
				if (Messagebox.ON_YES.equals(event.getName())){
					final HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("searchTerm", new String());
					DataSourceLoader.getInstance().removeRecord(selected, selected.getAttPersonId());
					BindUtils.postGlobalCommand(null, null, "search", params);
					Clients.showNotification("Запись успешно удалена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
				}
				
			}
		});
    }
    
    @GlobalCommand
    @Command
    @NotifyChange("attPersonsList")
    public void search(@BindingParam("searchTerm") String term) {
    	attPersonsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("AttendantPerson", "e.firstname LIKE '%"+term+"%' or e.middlename LIKE '%"+term+"%' or e.lastname LIKE '%"+term+"%'"));
    }

}
