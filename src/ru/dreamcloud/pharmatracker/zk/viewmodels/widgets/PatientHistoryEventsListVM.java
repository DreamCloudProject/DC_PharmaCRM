package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Filedownload;

import ru.dreamcloud.pharmatracker.model.Document;
import ru.dreamcloud.pharmatracker.model.DocumentAccess;
import ru.dreamcloud.pharmatracker.model.Event;
import ru.dreamcloud.pharmatracker.model.MessageType;
import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class PatientHistoryEventsListVM {
	
	/**************************************
	  Property authService	 
	***************************************/
	private AuthenticationService authService;
	
	/**************************************
	  Property viewDocuments	 
	***************************************/
	private Boolean viewDocuments;
	
	public Boolean getViewDocuments() {
		return viewDocuments;
	}

	public void setViewDocuments(Boolean viewDocuments) {
		this.viewDocuments = viewDocuments;
	}
	
	/**************************************
	 * Property selected
	 ***************************************/
	private Event selected;	

	public Event getSelected() {
		return selected;
	}

	public void setSelected(Event selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property currentPatientHistory
	 ***************************************/
	private PatientHistory currentPatientHistory;	

	public PatientHistory getCurrentPatientHistory() {
		return currentPatientHistory;
	}

	public void setCurrentPatientHistory(PatientHistory currentPatientHistory) {
		this.currentPatientHistory = currentPatientHistory;
	}
	
	/**************************************
	 * Property requiredDocumentLevels
	 ***************************************/
	private List<DocumentAccess> requiredDocumentLevels;	

	public List<DocumentAccess> getRequiredDocumentLevels() {
		return requiredDocumentLevels;
	}

	public void setRequiredDocumentLevels(List<DocumentAccess> requiredDocumentLevels) {
		this.requiredDocumentLevels = requiredDocumentLevels;
	}

	/**************************************
	 * Property eventsList
	 ***************************************/
	private List<Event> eventsList;

	public List<Event> getEventsList() {
		return eventsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ExecutionArgParam("currentPatientHistory") PatientHistory patientHistory) {
		authService = new AuthenticationService();
		viewDocuments = authService.checkAccessRights(authService.getCurrentProfile().getRole(), "ViewDocumentsDisabled");
		requiredDocumentLevels = authService.getRequiredAccessLevels(authService.getCurrentProfile().getRole());
		if(patientHistory != null){
			currentPatientHistory = patientHistory;
			//eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+currentPatientHistory.getPatientHistoriesId()));
			List<Event> eventsListToFilter = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+currentPatientHistory.getPatientHistoriesId()));
			eventsList = authService.getFilteredEventsList(eventsListToFilter, requiredDocumentLevels);
		}
	}
	
    @Command
    @NotifyChange("eventsList")
    public void openDocument(@BindingParam("documentItem") final Document docItem) {
    	try {
    		File requestedFile = new File(docItem.getFileURL());
			Filedownload.save(requestedFile, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @GlobalCommand
    @Command
    @NotifyChange("eventsList")
    public void refreshSidebarEventsList(@BindingParam("searchTerm") String term) {    	
    	//eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+currentPatientHistory.getPatientHistoriesId()));
    	List<Event> eventsListToFilter = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+currentPatientHistory.getPatientHistoriesId()));
		eventsList = authService.getFilteredEventsList(eventsListToFilter, requiredDocumentLevels);
    }

}
