package ru.dreamcloud.alexion.zk.viewmodels.widgets;

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

import ru.dreamcloud.authentication.persistence.jpa.CommonRole;
import ru.dreamcloud.persistence.jpa.DataSourceLoader;

public class CommonRoleTilePanelVM {

	/**************************************
	 * Property selected
	 ***************************************/
	private CommonRole selected;

	public CommonRole getSelected() {
		return selected;
	}

	public void setSelected(CommonRole selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property commonRolesList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<CommonRole> commonRolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRole", null));

	public ArrayList<CommonRole> getCommonRolesList() {
		return commonRolesList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!commonRolesList.isEmpty()) {
			selected = commonRolesList.get(0);
		}
	}
	
    @Command
    @NotifyChange("commonRolesList")
    public void addCommonRoleItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("commonRoleItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/commonrolewindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("commonRolesList")
    public void editCommonRoleItem() {
    	if(!commonRolesList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("commonRoleItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/commonrolewindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("commonRolesList")
    public void removeCommonRoleItem() {
    	if(!commonRolesList.isEmpty()) {
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
    @NotifyChange("commonRolesList")
    public void search(@BindingParam("searchTerm") String term) {
    	commonRolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRole", "e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
