package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class ShortcutMenuVM {
	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init() {		
	}
	
	@Command
	public void createNewPatientHistory(){    	
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/patienthistorywindow.zul", null, null);
        window.doModal();
	}
}
