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

import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.Resolution;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class ResolutionViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private Resolution selected;

	public Resolution getSelected() {
		return selected;
	}

	public void setSelected(Resolution selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property resolutionsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Resolution> resolutionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Resolution", null));

	public ArrayList<Resolution> getResolutionsList() {
		return resolutionsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!resolutionsList.isEmpty()) {
			selected = resolutionsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("resolutionsList")
    public void addResolutionItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("resolutionItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/resolutionwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("resolutionsList")
    public void editResolutionItem() {
    	if(!resolutionsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("resolutionItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/resolutionwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("resolutionsList")
    public void removeResolutionItem() {
    	if(!resolutionsList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						params.put("searchTerm", new String());						
						for (PatientHistory ph : selected.getPatientHistories()) {
							ph.setResolution(null);
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
    @NotifyChange("resolutionsList")
    public void search(@BindingParam("searchTerm") String term) {
    	resolutionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Resolution", "where e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
