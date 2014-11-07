package ru.dreamcloud.alexion.zk.viewmodels;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.indirection.IndirectList;
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
	 * Property allEntities
	 ***************************************/
	private List<Object> allEntities;
	
	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		if(Sessions.getCurrent().getAttribute("resultListSearchTerm") != null){			
			allEntities = DataSourceLoader.getInstance().getAllEntities(null);
			patientHistoriesList = retrieveResultsList(Sessions.getCurrent().getAttribute("resultListSearchTerm").toString().toLowerCase());			
			Clients.clearBusy();
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
    
    private boolean isContainsInFieldsValues(Object object, String term) throws IllegalArgumentException, IllegalAccessException{
    	Class c = object.getClass();
		Field[] entityFields = c.getDeclaredFields();
		boolean result = false;
		for (Field field : entityFields) {
			Class fieldType = field.getType();
			field.setAccessible(true);
			if (fieldType.getName().equals(String.class.getName())) {										
				if((field.get(object) != null) 
					&& (field.get(object).toString().toLowerCase().indexOf(term.toLowerCase()) != -1)){
					result = true;					
				}
			}
			if((!result) && (field.get(object) != null) && (isDataSourceEntity(field.get(object)))){
				result = isContainsInFieldsValues(field.get(object),term);
			}
			if(result){
				break;
			}
		}
    	return result;
    }
    
    private boolean isDataSourceEntity(Object obj){
    	boolean result = false;
    	for (Object entity : allEntities) {
    		if((entity != null) && (obj != null)){
    			if(entity.getClass().getName().equals(obj.getClass().getName())){
    				result = true;
    				break;
    			}
    		}
		}
    	return result;
    }
    
    private List<PatientHistory> retrieveResultsList(String term) {
    	List<PatientHistory> patientHistories = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".OPEN"));
    	List<PatientHistory> resultsList = new ArrayList<PatientHistory>();
    	boolean isContains;
    	try {
	    	for (PatientHistory ph : patientHistories) {
	    		isContains = false;
	        	Class c = ph.getClass();
	    		Field[] phFields = c.getDeclaredFields();	    		
	    		for (Field field : phFields) {
	    			field.setAccessible(true);
	    			if(isDataSourceEntity(field.get(ph))){
	    				if((!isContains) && (field.get(ph) != null)){	    					
	    	    			isContains = isContainsInFieldsValues(field.get(ph), term);
	    	    		}
	    			}
    				if((!isContains) && (field.get(ph) != null) && (field.getType().getName().equals(List.class.getName()))){
    					List<Object> listItems = new ArrayList((List)field.get(ph));
						for (Object item : listItems) {
							if(!isContains) {
								isContains = isContainsInFieldsValues(item, term);
							}
						}
					}    				
    				if(isContains){
    					break;
    				}
	    		}
	    		
	    		/*if((!isContains) && (ph.getAttperson() != null)){	    			
	    			isContains = isContainsInFieldsValues(ph.getAttperson(), term);
	    		}
	    		if((!isContains) && (ph.getPatient() != null)){
	    			isContains = isContainsInFieldsValues(ph.getPatient(), term);
	    		}
	    		if((!isContains) && (ph.getCurator() != null)){
	    			isContains = isContainsInFieldsValues(ph.getCurator(), term);
	    		}
	    		if((!isContains) && (ph.getDoctor() != null)){
	    			isContains = isContainsInFieldsValues(ph.getDoctor(), term);
	    		}
	    		if((!isContains) && (ph.getLawyer() != null)){
	    			isContains = isContainsInFieldsValues(ph.getLawyer(), term);
	    		}
	    		if((!isContains) && (ph.getMedicalExpert() != null)){
	    			isContains = isContainsInFieldsValues(ph.getMedicalExpert(), term);
	    		}
	    		if((!isContains) && (ph.getNurse() != null)){
	    			isContains = isContainsInFieldsValues(ph.getNurse(), term);
	    		}
	    		if((!isContains) && (ph.getProject() != null)){
	    			isContains = isContainsInFieldsValues(ph.getProject(), term);
	    		}
	    		if((!isContains) && (ph.getResolution() != null)){
	    			isContains = isContainsInFieldsValues(ph.getResolution(), term);
	    		}
	    		if((!isContains) && (!ph.getEvents().isEmpty())){
	    			for (Event event : ph.getEvents()) {
	    				isContains = isContainsInFieldsValues(event, term);
					}
	    		}*/	    		
	    		if(isContains){
	    			resultsList.add(ph);
	    		}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return resultsList;
    }
}
