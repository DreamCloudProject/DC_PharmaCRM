package ru.dreamcloud.alexion.zk.viewmodels;

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
import org.zkoss.zul.Listbox;

import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.model.PatientHistoryStatus;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class SearchResultsViewModel {
	
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
			patientHistoriesList = retrieveResultsList(Sessions.getCurrent().getAttribute("resultListSearchTerm").toString().toLowerCase());			
		} else {
			Executions.sendRedirect(Labels.getLabel("panels.index.URL"));
		}		
	}
	
    @Command    
    public void printResults(@BindingParam("ref") Listbox listbox) {
    	Sessions.getCurrent().setAttribute("listToPrint", patientHistoriesList);
    	Executions.sendRedirect(Labels.getLabel("services.print.URL"));
    }
	
    @Command    
    public void showPatientHistoryItem(@BindingParam("phItem")PatientHistory phItem) {
    	Sessions.getCurrent().setAttribute("currentPatientHistory", phItem);
    	Executions.sendRedirect(Labels.getLabel("pages.detail.patienthistory.URL"));
    }
    
    private List<PatientHistory> retrieveResultsList(String term) {
    	List<PatientHistory> patientHistories = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".OPEN"));
    	List<PatientHistory> resultsList = new ArrayList<PatientHistory>();
    	boolean isContains;
    	for (PatientHistory ph : patientHistories) {
    		isContains = false;
    		if(ph.getAttperson() != null){    		
				if(ph.getAttperson().getFullname().toLowerCase().indexOf(term) != -1){
					isContains = true;
				}
    		}
    		if(ph.getPatient() != null){
    			if(ph.getPatient().getFullname().toLowerCase().indexOf(term) != -1){
    				isContains = true;
    			}
    		}
    		if(ph.getCurator() != null){
    			if(ph.getCurator().getFullname().toLowerCase().indexOf(term) != -1){
    				isContains = true;
    			}
    		}
    		if(ph.getDoctor() != null){
    			if(ph.getDoctor().getFullname().toLowerCase().indexOf(term) != -1){
    				isContains = true;
    			}
    		}
    		if(ph.getLawyer() != null){
    			if(ph.getLawyer().getFullname().toLowerCase().indexOf(term) != -1){
    				isContains = true;
    			}
    		}
    		if(ph.getMedicalExpert() != null){
    			if(ph.getMedicalExpert().getFullname().toLowerCase().indexOf(term) != -1){
    				isContains = true;		
    			}
    		}
    		if(ph.getNurse() != null){
    			if(ph.getNurse().getFullname().toLowerCase().indexOf(term) != -1){
    				isContains = true;		
    			}
    		}
    		if(ph.getProject() != null){
    			if((ph.getProject().getTitle().toLowerCase().indexOf(term) != -1)
    					|| (ph.getProject().getDescription().toLowerCase().indexOf(term) != -1)){
    				isContains = true;
    			}
    		}
    		if(ph.getResolution() != null){
    			if((ph.getResolution().getTitle().toLowerCase().indexOf(term) != -1)
    					|| (ph.getResolution().getDescription().toLowerCase().indexOf(term) != -1)){
    				isContains = true;
    			}
    		}
    		if(isContains){
    			resultsList.add(ph);
    		}
		}
    	return resultsList;
    }
}
