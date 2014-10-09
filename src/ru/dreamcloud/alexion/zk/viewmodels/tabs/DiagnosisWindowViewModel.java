package ru.dreamcloud.alexion.zk.viewmodels.tabs;

import java.util.HashMap;

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

import ru.dreamcloud.alexion.model.Diagnosis;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class DiagnosisWindowViewModel {

	@Wire("#DiagnosisWindow")
	private Window win;
	
	/**************************************
	 * Property currentDiagnosisItem
	 ***************************************/
	private Diagnosis currentDiagnosisItem;
	
	public Diagnosis getCurrentDiagnosisItem() {
		return currentDiagnosisItem;
	}

	public void setCurrentDiagnosisItem(Diagnosis currentDiagnosisItem) {
		this.currentDiagnosisItem = currentDiagnosisItem;
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

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("diagnosisItem") Diagnosis currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);

		if (this.actionType.equals("NEW")) {
			currentDiagnosisItem = new Diagnosis();
		}

		if (this.actionType.equals("EDIT")) {
			currentDiagnosisItem = currentItem;
		}

	}
	
	@Command
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		
		DataSourceLoader.getInstance().mergeRecord(currentDiagnosisItem);
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
