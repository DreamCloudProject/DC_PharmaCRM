package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.AttendantPerson;
import ru.dreamcloud.pharmatracker.model.MedicalExpert;

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
	
    @Command
    @NotifyChange("currentMedicalExpert")
    public void editMedicalExpertItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("medicalExpertItem", currentMedicalExpert);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/medicalexpertwindow.zul", null, params);
        window.doModal();
    }
    
	@GlobalCommand
	@Command
	@NotifyChange("currentMedicalExpert")
	public void refreshMedicalExpertTilePanel(@BindingParam("medicalExpert") MedicalExpert medExpert){
		currentMedicalExpert = medExpert;
	}
    
}
