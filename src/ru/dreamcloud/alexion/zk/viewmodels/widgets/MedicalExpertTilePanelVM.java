package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import ru.dreamcloud.alexion.model.MedicalExpert;

public class MedicalExpertTilePanelVM {
	
	/**************************************
	  Property currentMedicalExpert	 
	***************************************/
	private MedicalExpert currentMedicalExpert;	
	
	public MedicalExpert getCurrentMedicalExpert() {
		return currentMedicalExpert;
	}

	public void setCurrentMedicalExpert(MedicalExpert currentMedicalExpert) {
		this.currentMedicalExpert = currentMedicalExpert;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentMedicalExpert") MedicalExpert medicalExpert) {		
		currentMedicalExpert = medicalExpert;
	}
}
