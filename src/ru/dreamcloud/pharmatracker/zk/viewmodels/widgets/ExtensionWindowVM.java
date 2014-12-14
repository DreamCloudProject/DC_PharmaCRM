package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Extension;
import ru.dreamcloud.pharmatracker.model.MedicalExpert;
import ru.dreamcloud.pharmatracker.model.Patient;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class ExtensionWindowVM {

	@Wire("#ExtensionWindow")
	private Window win;
	
	/**************************************
	 * Property currentExtensionItem
	 ***************************************/
	private Extension currentExtensionItem;
	
	public Extension getCurrentExtensionItem() {
		return currentExtensionItem;
	}

	public void setCurrentExtensionItem(Extension currentExtensionItem) {
		this.currentExtensionItem = currentExtensionItem;
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
	 * Property newIconName
	 ***************************************/
	private String newIconName;	

	public String getNewIconName() {
		return newIconName;
	}

	public void setNewIconName(String newIconName) {
		this.newIconName = newIconName;
	}
	
	/**************************************
	 * Property allExtensionsList
	 ***************************************/
	
	private List<Extension> allExtensionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Extension", null));	
	
	public List<Extension> getAllExtensionsList() {
		return allExtensionsList;
	}

	/**************************************
	 * Methods
	 ***************************************/

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("extensionItem") Extension currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);

		if (this.actionType.equals("NEW")) {
			currentExtensionItem = new Extension();
		}

		if (this.actionType.equals("EDIT")) {
			currentExtensionItem = currentItem;
		}

	}
	
	@Command
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
	
		DataSourceLoader.getInstance().mergeRecord(currentExtensionItem);
		
		if (actionType.equals("NEW")) {			
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}		
		
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
    @GlobalCommand
    @Command
    @NotifyChange({"currentExtensionItem","allExtensionsList"})
    public void createNewExtension(){
    	
    }
    
	
	@Command
	public void closeThis() {
		win.detach();
	}
}
