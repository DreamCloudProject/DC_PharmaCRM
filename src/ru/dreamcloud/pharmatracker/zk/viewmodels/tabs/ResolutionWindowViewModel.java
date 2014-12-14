package ru.dreamcloud.pharmatracker.zk.viewmodels.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Project;
import ru.dreamcloud.pharmatracker.model.Region;
import ru.dreamcloud.pharmatracker.model.Resolution;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class ResolutionWindowViewModel {

	@Wire("#ResolutionWindow")
	private Window win;
	
	/**************************************
	 * Property currentResolutionItem
	 ***************************************/
	private Resolution currentResolutionItem;
	
	public Resolution getCurrentResolutionItem() {
		return currentResolutionItem;
	}

	public void setCurrentResolutionItem(Resolution currentResolutionItem) {
		this.currentResolutionItem = currentResolutionItem;
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
	 * Property projectsList
	 ***************************************/
	private List<Project> projectsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Project", null));
	
	public List<Project> getProjectsList() {
		return projectsList;
	}

	public void setProjectsList(List<Project> projectsList) {
		this.projectsList = projectsList;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("resolutionItem") Resolution currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);

		if (this.actionType.equals("NEW")) {
			currentResolutionItem = new Resolution();
		}

		if (this.actionType.equals("EDIT")) {
			currentResolutionItem = currentItem;
		}

	}
	
	@Command
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		
		DataSourceLoader.getInstance().mergeRecord(currentResolutionItem);
		
		if (actionType.equals("NEW")) {		
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
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
