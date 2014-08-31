package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Project;
import ru.dreamcloud.authentication.persistence.jpa.CommonRole;
import ru.dreamcloud.authentication.persistence.jpa.CommonUserInfo;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class CommonUserInfoWindowVM {
	
	@Wire("#CommonUserInfoWindow")
	private Window win;
	
	/**************************************
	 * Property currentUserInfoItem
	 ***************************************/
	private CommonUserInfo currentUserInfoItem;

	public CommonUserInfo getCurrentUserInfoItem() {
		return currentUserInfoItem;
	}

	public void setCurrentUserInfoItem(CommonUserInfo currentUserInfoItem) {
		this.currentUserInfoItem = currentUserInfoItem;
	}
	
	/**************************************
	 * Property currentRoleItem
	 ***************************************/
	private CommonRole currentRoleItem;	
	
	public CommonRole getCurrentRoleItem() {
		return currentRoleItem;
	}

	public void setCurrentRoleItem(CommonRole currentRoleItem) {
		this.currentRoleItem = currentRoleItem;
	}
	
	/**************************************
	 * Property actionType
	 ***************************************/
	private String actionType;

	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}	

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	/**************************************
	 * Property allRolesList
	 ***************************************/
	
	private List<CommonRole> allRolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRole", null));
	
	public List<CommonRole> getAllRolesList() {
		return allRolesList;
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("commonUserInfoItem") CommonUserInfo currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		currentRoleItem = new CommonRole();
		
		if (this.actionType.equals("NEW")) {
			currentUserInfoItem = new CommonUserInfo();			
		}

		if (this.actionType.equals("EDIT")) {
			currentUserInfoItem = currentItem;						
		}		
	}
	
	@Command
	@NotifyChange("currentUserInfoItem")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		
		if (actionType.equals("NEW")) {
			DataSourceLoader.getInstance().addRecord(currentUserInfoItem);
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			DataSourceLoader.getInstance().updateRecord(currentUserInfoItem);
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}
		
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();		
	}
	
	@Command
	public void closeThis() {
		win.detach();
	}

}
