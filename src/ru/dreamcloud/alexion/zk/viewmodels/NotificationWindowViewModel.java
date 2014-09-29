package ru.dreamcloud.alexion.zk.viewmodels;

import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Notification;

public class NotificationWindowViewModel {
	
	@Wire("#NotificationWindow")
	private Window win;
	
	/**************************************
	 * Property currentNotification
	 ***************************************/
	
	private Notification currentNotification;
	
	public Notification getCurrentNotification() {
		return currentNotification;
	}

	public void setCurrentNotification(Notification currentNotification) {
		this.currentNotification = currentNotification;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
    public void init(@ContextParam(ContextType.VIEW) Component view, 
    				 @ExecutionArgParam("notificationItem") Notification currentItem) {
		Selectors.wireComponents(view, this, false);				
    }
	
	@Command
	@NotifyChange("currentNotification")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());	
		
		BindUtils.postGlobalCommand(null, null, "search", params);			
		win.detach();
		
	}	
	
	@Command
	public void closeThis() {
		win.detach();
	}

}
