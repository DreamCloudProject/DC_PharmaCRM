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

import ru.dreamcloud.alexion.model.authentication.CommonUserInfo;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class CommonUserInfoTilePanelVM {

	/**************************************
	 * Property selected
	 ***************************************/
	private CommonUserInfo selected;

	public CommonUserInfo getSelected() {
		return selected;
	}

	public void setSelected(CommonUserInfo selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property commonUserInfosList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<CommonUserInfo> commonUserInfosList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", null));

	public ArrayList<CommonUserInfo> getCommonUserInfosList() {
		return commonUserInfosList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!commonUserInfosList.isEmpty()) {
			selected = commonUserInfosList.get(0);
		}
	}
	
    @Command
    @NotifyChange("commonUserInfosList")
    public void addCommonUserInfoItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("commonUserInfoItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/commonuserinfowindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("commonUserInfosList")
    public void editCommonUserInfoItem() {
    	if(!commonUserInfosList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("commonUserInfoItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/commonuserinfowindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("commonUserInfosList")
    public void removeCommonUserInfoItem() {
    	if(!commonUserInfosList.isEmpty()) {
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
    @NotifyChange("commonUserInfosList")
    public void search(@BindingParam("searchTerm") String term) {
    	commonUserInfosList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", "where e.lastname LIKE '%"+term+"%' or e.firstname LIKE '%"+term+"%' or e.middlename LIKE '%"+term+"%'"));
    }

}
