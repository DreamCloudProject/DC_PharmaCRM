package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Doctor;

public class DoctorTilePanelVM {
	
	/**************************************
	  Property rankName	 
	***************************************/
	private String rankName;
	
	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

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
	public void init(@ExecutionArgParam("rankName") String name,@ExecutionArgParam("currentDoctor") Doctor doctor) {		
		currentDoctor = doctor;
		rankName = name;
	}
	
    @Command
    @NotifyChange("currentDoctor")
    public void editDoctorItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("doctorItem", currentDoctor);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/doctorwindow.zul", null, params);
        window.doModal();
    }
}
