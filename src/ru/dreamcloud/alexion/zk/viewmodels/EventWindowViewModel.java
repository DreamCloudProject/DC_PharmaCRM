package ru.dreamcloud.alexion.zk.viewmodels;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Document;
import ru.dreamcloud.alexion.model.Event;
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
	  Methods	 
	***************************************/
	@Init
    public void init(@ContextParam(ContextType.VIEW) Component view, 
    				 @ExecutionArgParam("eventItem") Event currentItem,
    				 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		itemsToRemove = new ArrayList<Object>();
		documentItem = new Document();
		
		if (this.actionType.equals("NEW")) {
			currentEvent = new Event();
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
		//final HashMap<String, Object> params = new HashMap<String, Object>();	
		//DataSourceLoader.getInstance().addRecord(currentEvent);
		//Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);		
		//BindUtils.postGlobalCommand(null, null, "retrievePatientHistories", params);
		//win.detach();
	}
	
	@Command
	@NotifyChange("currentEvent")
	public void uploadDocument(@BindingParam("file")Media file) {
		System.out.println(file.getName());
		System.out.println(file.getContentType());
		System.out.println(file.getFormat());		
	}
	
    @Command
    @NotifyChange("documentList")
    public void removeDocument(@BindingParam("documentItem") final Document docItem) {
    	System.out.println("Удалить");    	
    }
	
	@Command
	public void closeThis() {
		win.detach();
	}	
}
