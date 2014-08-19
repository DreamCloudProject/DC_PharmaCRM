package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class ShortcutMenuVM {
	
	private Boolean phPage;

	public Boolean getPhPage() {
		return phPage;
	}

	public void setPhPage(Boolean phPage) {
		this.phPage = phPage;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("patientHistoryPage") Boolean phPage) {
		this.phPage = phPage;
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
}
