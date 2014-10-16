package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Document;
import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.MessageType;
import ru.dreamcloud.alexion.model.Notification;
import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.model.Resolution;
import ru.dreamcloud.alexion.zk.services.SchedulerService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class EventTilePanelVM {
	
	/**************************************
	 * Property schedulerService
	 ***************************************/
	private SchedulerService schedulerService;

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
	 * Property eventsListTODO
	 ***************************************/
	private List<Event> eventsListTODO;

	public List<Event> getEventsListTODO() {
		return eventsListTODO;
	}
	
	/**************************************
	 * Property eventsListPROGRESS
	 ***************************************/
	private List<Event> eventsListPROGRESS;

	public List<Event> getEventsListPROGRESS() {
		return eventsListPROGRESS;
	}
	
	/**************************************
	 * Property eventsListDONE
	 ***************************************/
	private List<Event> eventsListDONE;

	public List<Event> getEventsListDONE() {
		return eventsListDONE;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ExecutionArgParam("currentPatientHistory") PatientHistory patientHistory) {
		if(patientHistory != null){
			Session session = Sessions.getCurrent();
			schedulerService = (SchedulerService)session.getAttribute("schedulerService");
			patientHistoryItem = patientHistory;
			eventsListTODO = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".TODO"));
			eventsListPROGRESS = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".IN_PROGRESS"));
			eventsListDONE = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".DONE"));
		}
	}
	
	@Command
    @NotifyChange({"eventsList","patientHistoryItem"})
    public void editEventItem(@BindingParam("eventItem") final Event eventItem) {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("eventItem", eventItem);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
        window.doModal();
    }
    
	@Command
    @NotifyChange({"eventsListTODO","eventsListPROGRESS","eventsListDONE","patientHistoryItem"})
    public void removeEventItem(@BindingParam("eventItem") final Event eventItem) {
    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<org.zkoss.zk.ui.event.Event>() {			
			@Override
			public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
				if (Messagebox.ON_YES.equals(event.getName())){
					final HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("searchTerm", new String());
					DataSourceLoader.getInstance().removeRecord(eventItem);
					for (Document docToDelete : eventItem.getDocuments()) {
						File fileToDelete = new File(docToDelete.getFileURL());
						Files.deleteAll(fileToDelete);
					}
					List<Event> eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()));
					if(eventItem.getMessageType() == MessageType.TODO){
						eventsListTODO.remove(eventItem);
						BindUtils.postGlobalCommand(null, null, "searchEventsTODO", params);						
					}
					if(eventItem.getMessageType() == MessageType.IN_PROGRESS){
						eventsListPROGRESS.remove(eventItem);						
						BindUtils.postGlobalCommand(null, null, "searchEventsPROGRESS", params);						
					}
					if(eventItem.getMessageType() == MessageType.DONE){
						eventsListDONE.remove(eventItem);
						BindUtils.postGlobalCommand(null, null, "searchEventsDONE", params);
					}
					patientHistoryItem.setEvents(eventsList);
					BindUtils.postGlobalCommand(null, null, "refreshCalendar", null);
					Clients.showNotification("Запись успешно удалена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
				}
				
			}

		});
    }
    
    @GlobalCommand
    @Command
    @NotifyChange({"eventsListTODO","patientHistoryItem"})
    public void searchEventsTODO(@BindingParam("searchTerm") String term) {
    	eventsListTODO = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".TODO and (e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%')"));
    }
    
    @GlobalCommand
    @Command
    @NotifyChange({"eventsListPROGRESS","patientHistoryItem"})
    public void searchEventsPROGRESS(@BindingParam("searchTerm") String term) {
		eventsListPROGRESS = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".IN_PROGRESS and (e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%')"));		
    }
    
    @GlobalCommand
    @Command
    @NotifyChange({"eventsListDONE","patientHistoryItem"})
    public void searchEventsDONE(@BindingParam("searchTerm") String term) {
		eventsListDONE = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".DONE and (e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%')"));
    }
    
	@Command
    @NotifyChange({"eventsListTODO","eventsListPROGRESS","eventsListDONE","patientHistoryItem"})
	public void changeMessageType(@BindingParam("messageType")String messageType,
								 @BindingParam("target")Component evDragged) {
		String[] evId = evDragged.getId().split("_");
		Object pk = Integer.valueOf(evId[1]);
		selected = (Event)DataSourceLoader.getInstance().getRecord(Event.class, pk);
		Clients.showNotification("EventId:"+selected.getEventId()+", MessageType: "+messageType, Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		if(messageType.equals("TODO")){
			selected.setMessageType(MessageType.TODO);		}
		if(messageType.equals("PROGRESS")){
			selected.setMessageType(MessageType.IN_PROGRESS);
		}
		if(messageType.equals("DONE")){
			selected.setMessageType(MessageType.DONE);
		}
		DataSourceLoader.getInstance().mergeRecord(selected);
		eventsListTODO = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".TODO"));
		eventsListPROGRESS = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".IN_PROGRESS"));
		eventsListDONE = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()+" and e.messageType="+MessageType.class.getName()+".DONE"));
		List<Event> eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()));
		patientHistoryItem.setEvents(eventsList);
	}

}
