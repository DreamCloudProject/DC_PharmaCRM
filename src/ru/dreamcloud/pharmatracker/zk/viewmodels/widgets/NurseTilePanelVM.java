package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Nurse;

public class NurseTilePanelVM {
	
	/**************************************
	  Property currentNurse	 
	***************************************/
	private Nurse currentNurse;	
	
	public Nurse getCurrentNurse() {
		return currentNurse;
	}

	public void setCurrentNurse(Nurse currentNurse) {
		this.currentNurse = currentNurse;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentNurse") Nurse nurse) {		
		currentNurse = nurse;
	}
	
    @Command
    @NotifyChange("currentNurse")
    public void editNurseItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("nurseItem", currentNurse);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/nursewindow.zul", null, params);
        window.doModal();
    }
	
}
