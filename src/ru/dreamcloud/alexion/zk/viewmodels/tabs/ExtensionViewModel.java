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

import ru.dreamcloud.alexion.model.Extension;
import ru.dreamcloud.persistence.jpa.DataSourceLoader;

public class ExtensionViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private Extension selected;

	public Extension getSelected() {
		return selected;
	}

	public void setSelected(Extension selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property extensionsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Extension> extensionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Extension", null));

	public ArrayList<Extension> getExtensionsList() {
		return extensionsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!extensionsList.isEmpty()) {
			selected = extensionsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("extensionsList")
    public void addExtensionItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("extensionItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/extensionwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("extensionsList")
    public void editExtensionItem() {
    	if(!extensionsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("extensionItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/extensionwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("extensionsList")
    public void removeExtensionItem() {
    	if(!extensionsList.isEmpty()) {
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
    @NotifyChange("extensionsList")
    public void search(@BindingParam("searchTerm") String term) {
    	extensionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Extension", "e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
