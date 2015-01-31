package ru.dreamcloud.pharmatracker.zk.viewmodels;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.PatientHistoryStatus;
import ru.dreamcloud.pharmatracker.zk.services.SearchService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class SearchResultsViewModel {
	/**************************************
	 * Property searchService
	 ***************************************/
	private SearchService searchService;
	
	/**************************************
	 * Property selected
	 ***************************************/
	private PatientHistory selected;

	public PatientHistory getSelected() {
		return selected;
	}

	public void setSelected(PatientHistory selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property patientHistoriesList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<PatientHistory> patientHistoriesList;

	public List<PatientHistory> getPatientHistoriesList() {
		return patientHistoriesList;
	}
	
	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		if(Sessions.getCurrent().getAttribute("resultListSearchTerm") != null){			
			searchService = new SearchService();
			List<PatientHistory> actualPH = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".OPEN"));
			patientHistoriesList = new ArrayList(searchService.retrieveResultsList(actualPH,Sessions.getCurrent().getAttribute("resultListSearchTerm").toString().toLowerCase()));			
			Clients.clearBusy();
		} else {
			Executions.sendRedirect(Labels.getLabel("panels.index.URL"));
		}		
	}
	
    @Command    
    public void printResults() {
    	Sessions.getCurrent().setAttribute("listToPrint", patientHistoriesList);
    	Executions.sendRedirect(Labels.getLabel("services.print.URL"));
    }
	
    @Command    
    public void showPatientHistoryItem(@BindingParam("phItem")PatientHistory phItem) {
    	Sessions.getCurrent().setAttribute("currentPatientHistory", phItem);
    	Executions.sendRedirect(Labels.getLabel("pages.detail.patienthistory.URL"));
    }
    
}
