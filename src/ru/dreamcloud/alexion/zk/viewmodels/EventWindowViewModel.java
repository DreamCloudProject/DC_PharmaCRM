package ru.dreamcloud.alexion.zk.viewmodels;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Document;
import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.EventReason;
import ru.dreamcloud.alexion.model.Extension;
import ru.dreamcloud.alexion.model.MessageType;
import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class EventWindowViewModel {
	
	@Wire("#EventWindow")
	private Window win;
	
	/**************************************
	 * Property currentEvent
	 ***************************************/
	
	private Event currentEvent;
	
	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}
	
	/**************************************
	 * Property patientHistoryItem
	 ***************************************/
	
	private PatientHistory patientHistoryItem;	
	
	public PatientHistory getPatientHistoryItem() {
		return patientHistoryItem;
	}

	public void setPatientHistoryItem(PatientHistory patientHistoryItem) {
		this.patientHistoryItem = patientHistoryItem;
	}

	/**************************************
	 * Property documentItem
	 ***************************************/
	
	private Document documentItem;

	public Document getDocumentItem() {
		return documentItem;
	}

	public void setDocumentItem(Document documentItem) {
		this.documentItem = documentItem;
	}
	
	/**************************************
	 * Property documentList
	 ***************************************/
	
	private List<Document> documentList;	

	public List<Document> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<Document> documentList) {
		this.documentList = documentList;
	}
	
	/**************************************
	 * Property allEventReasonsList
	 ***************************************/
	
	private List<EventReason> allEventReasonsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("EventReason", null));	

	public List<EventReason> getAllEventReasonsList() {
		return allEventReasonsList;
	}
	
	/**************************************
	 * Property currentDate
	 ***************************************/
	
	private Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());

	/**************************************
	 * Property actionType
	 ***************************************/
	private String actionType;

	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	/**************************************
	 * Property itemsToRemove
	 ***************************************/
	
	private List<Object> itemsToRemove;
	
	/**************************************
	 * Property uploadFilesList
	 ***************************************/
	
	private HashMap<File,InputStream> uploadFilesList;

	/**************************************
	  Methods	 
	***************************************/
	@Init
    public void init(@ContextParam(ContextType.VIEW) Component view, 
    				 @ExecutionArgParam("eventItem") Event currentItem,
    				 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		itemsToRemove = new ArrayList<Object>();
		uploadFilesList = new HashMap<File, InputStream>();
		documentItem = new Document();
		if(Sessions.getCurrent().getAttribute("currentPatientHistory") != null){
			setPatientHistoryItem((PatientHistory)Sessions.getCurrent().getAttribute("currentPatientHistory"));
		}		
		if (this.actionType.equals("NEW")) {
			currentEvent = new Event();
			currentEvent.setDateTimeStart(currentDate);
			currentEvent.setDateTimeEnd(currentDate);
			documentList = new ArrayList<Document>();
		}
		if (this.actionType.equals("EDIT")) {
			currentEvent = currentItem;
			documentList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Document", "e.event.eventId="+currentEvent.getEventId()));
		}
    }
	
	@Command
	@NotifyChange("currentEvent")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		clearAllRemovedItems();		
		currentEvent.setDocuments(documentList);
		currentEvent.setMessageType(MessageType.UNREAD);
		currentEvent.setPatientHistory(patientHistoryItem);
		if (actionType.equals("NEW")) {
			DataSourceLoader.getInstance().addRecord(currentEvent);
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			DataSourceLoader.getInstance().updateRecord(currentEvent);
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}
		copyAllUploadedFiles();
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
	@Command
	@NotifyChange({"documentItem","documentList"})
	public void addNewDocument(@BindingParam("file")Media file) {		
		String filePath = Labels.getLabel("uploadedContentDir")+"/"+patientHistoryItem.getPatient().getFullname()+"/"+file.getFormat()+"/"+file.getName();
		File newFile = new File(filePath);
		List<Extension> extList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Extension", "e.extensionName = '"+file.getFormat().toUpperCase()+"'"));							
		uploadFilesList.put(newFile, file.getStreamData());
		documentItem.setDateCreated(currentDate);
		documentItem.setDateModified(currentDate);
		documentItem.setFileURL(filePath);
		documentItem.setTitle(file.getName());
		documentItem.setDescription(file.getContentType());
		if(extList.isEmpty()){
			extList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Extension", "e.extensionName = 'UNDEFINED'"));				
		}
		documentItem.setExtension(extList.get(0));
		documentItem.setEvent(currentEvent);
		documentList.add(documentItem);
		documentItem = new Document();
		Clients.showNotification("Документ прикреплен к событию! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
	}
	
    @Command
    @NotifyChange("documentList")
    public void removeDocument(@BindingParam("documentItem") final Document docItem) {
    	itemsToRemove.add(docItem);
    	documentList.remove(docItem);
    	Clients.showNotification("Документ удален! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);   	
    }
	
	@Command
	public void closeThis() {
		win.detach();
	}
	
	private void clearAllRemovedItems(){		
		for (Object entityObj : itemsToRemove) {
			DataSourceLoader.getInstance().removeRecord(entityObj);
		}		
	}
	
	private void copyAllUploadedFiles(){
		for(Entry<File, InputStream> entry : uploadFilesList.entrySet()) {
			File targetFile = entry.getKey();
		    InputStream srcFile = entry.getValue();
		    try {
		    	Files.copy(targetFile, srcFile);
			} catch (IOException e) {
				Clients.showNotification("Ошибка при загрузке документа!", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);
				e.printStackTrace();
			}
		}
		
		
	}
}
