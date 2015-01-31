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

import ru.dreamcloud.pharmatracker.model.Doctor;
import ru.dreamcloud.pharmatracker.model.DoctorRank;

public class MasterDoctorTilePanelVM {
	
	/**************************************
	  Property currentDoctor	 
	***************************************/
	private Doctor currentDoctor;	
	
	public Doctor getCurrentDoctor() {
		return currentDoctor;
	}

	public void setCurrentDoctor(Doctor currentDoctor) {
		this.currentDoctor = currentDoctor;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentDoctor") Doctor doctor) {		
		currentDoctor = doctor;
	}
	
    @Command
    @NotifyChange("currentDoctor")
    public void editDoctorItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("doctorItem", currentDoctor);
    	params.put("actionType", "EDIT");
    	params.put("rankType", DoctorRank.MASTER_DOCTOR);
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/doctorwindow.zul", null, params);
        window.doModal();
    }
    
	@GlobalCommand
	@Command
	@NotifyChange("currentDoctor")
	public void refreshMasterDoctorTilePanel(@BindingParam("doctor") Doctor doctor){
		currentDoctor = doctor;
	}
}
