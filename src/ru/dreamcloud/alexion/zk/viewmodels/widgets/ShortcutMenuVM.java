package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.authentication.UserInfo;

public class ShortcutMenuVM {
	/**************************************
	  Property validateForShow	 
	***************************************/
	private Boolean validateForShow;	

	public Boolean getValidateForShow() {
		return validateForShow;
	}

	public void setValidateForShow(Boolean validateForShow) {
		this.validateForShow = validateForShow;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("visibleForObject") Object obj) {
		if(obj != null){
			this.validateForShow = validateObjectForShow(obj);
		} else {
			this.validateForShow = true;
		}
	}
	
	@Command
	public void createNewPatientHistory(){
		final HashMap<String, Object> params = new HashMap<String, Object>();    	
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/patienthistorywindow.zul", null, params);
        window.doModal();
	}
	
	@Command
	public void createNewEvent(){
    	final HashMap<String, Object> params = new HashMap<String, Object>();    	
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
        window.doModal();
	}
	
	private Boolean validateObjectForShow(Object visibleForObject){
		if(visibleForObject instanceof PatientHistory){
			return false;
		}
		return true;
	}
	
	
}
