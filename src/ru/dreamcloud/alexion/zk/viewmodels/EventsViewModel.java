package ru.dreamcloud.alexion.zk.viewmodels;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class EventsViewModel {
	
	private String testLabel;

	public String getTestLabel() {
		return testLabel;
	}

	public void setTestLabel(String testLabel) {
		this.testLabel = testLabel;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
    public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		if(Sessions.getCurrent().getAttribute("test") != null){
			setTestLabel(Sessions.getCurrent().getAttribute("test").toString());
		}
    }    
    
    @Command
    public void event() {
    	
    }

}
