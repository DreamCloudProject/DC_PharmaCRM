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

import ru.dreamcloud.alexion.model.Project;
import ru.dreamcloud.persistence.jpa.DataSourceLoader;

public class ProjectViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private Project selected;

	public Project getSelected() {
		return selected;
	}

	public void setSelected(Project selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property projectsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Project> projectsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Project", null));

	public ArrayList<Project> getProjectsList() {
		return projectsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!projectsList.isEmpty()) {
			selected = projectsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("projectsList")
    public void addProjectItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("projectItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/projectwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("projectsList")
    public void editProjectItem() {
    	if(!projectsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("projectItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/projectwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("projectsList")
    public void removeProjectItem() {
    	if(!projectsList.isEmpty()) {
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
    @NotifyChange("projectsList")
    public void search(@BindingParam("searchTerm") String term) {
    	projectsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Project", "e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
