package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import ru.dreamcloud.alexion.model.Nurse;

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
}
