package ru.dreamcloud.alexion.zk.viewmodels;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Patient;

public class PatientsWindowViewModel {

	@Wire("#PatientWindow")
	private Window win;
	
	/**************************************
	 * Property currentPatientItem
	 ***************************************/
	private Patient currentPatientItem;

	/**
	 * @return the currentPatientItem
	 */
	public Patient getCurrentPatientItem() {
		return currentPatientItem;
	}

	/**
	 * @param currentPatientItem the currentPatientItem to set
	 */
	public void setCurrentPatientItem(Patient currentPatientItem) {
		this.currentPatientItem = currentPatientItem;
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
					 @ExecutionArgParam("patientItem") Patient currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);

		if (this.actionType.equals("NEW")) {
			currentPatientItem = new Patient();
		}

		if (this.actionType.equals("EDIT")) {
			currentPatientItem = currentItem;
		}

	}

}
