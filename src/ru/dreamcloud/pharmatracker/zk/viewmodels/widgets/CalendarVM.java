package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.calendar.impl.SimpleCalendarEvent;
import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Event;
import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;
import ru.dreamcloud.pharmatracker.zk.viewmodels.widgets.calendar.AdvancedCalendarEvent;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class CalendarVM {
	
	@Wire("#EventCalendar")
	private Calendars eventCalendar;
	
	/**************************************
	  Property authService	 
	***************************************/
	private AuthenticationService authService;
	
	/**************************************
	 * Property calendarModel
	 ***************************************/	
	private SimpleCalendarModel calendarModel;	
	
	public SimpleCalendarModel getCalendarModel() {
		return calendarModel;
	}

	public void setCalendarModel(SimpleCalendarModel calendarModel) {
		this.calendarModel = calendarModel;
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
	 * Property eventsList
	 ***************************************/
	private List<Event> eventsList;

	public List<Event> getEventsList() {
		return eventsList;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view,
					 @ExecutionArgParam("currentPatientHistory")  PatientHistory phItem) {
		Selectors.wireComponents(view, this, false);
		patientHistoryItem = phItem;
		authService = new AuthenticationService();
		eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()));
		calendarModel = new SimpleCalendarModel(getCalendarEvents(eventsList));		
	}
	
	private List<CalendarEvent> getCalendarEvents(List<Event> currentEvents){		
		List<CalendarEvent> calendarEvents = new ArrayList<CalendarEvent>();
		for (Event ev : currentEvents) {
			AdvancedCalendarEvent ce = new AdvancedCalendarEvent();
			ce.setBeginDate(ev.getDateTimeStart());
			ce.setEndDate(ev.getDateTimePlan());
			ce.setContent(ev.getDescription());
			ce.setHeaderColor("red");
			ce.setTitle(ev.getTitle());
			ce.setEventId(String.valueOf(ev.getEventId()));
			calendarEvents.add(ce);
		}
		return calendarEvents;
	}
	
	@Command
	public void createEvent(@BindingParam("calendarEvent")CalendarsEvent event){
		if(authService.checkAccessRights(authService.getCurrentProfile().getRole(),"Create")){
			Event entityEvent = new Event();
			entityEvent.setDateTimeStart(new Timestamp(event.getBeginDate().getTime()));
			entityEvent.setDateTimeReg(new Timestamp(event.getEndDate().getTime()));
			entityEvent.setDateTimePlan(new Timestamp(event.getEndDate().getTime()));
			entityEvent.setDateTimeEnd(new Timestamp(event.getEndDate().getTime()));
	    	final HashMap<String, Object> params = new HashMap<String, Object>();    	
	    	params.put("eventItem", entityEvent);
	    	params.put("actionType", "NEW");
	    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
	        window.doModal();
		} else {
			Clients.showNotification("У Вас нет прав на создание!", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);
		}
	}
	
	@Command
	@NotifyChange({"calendarModel","patientHistoryItem","eventsList"})
	public void updateEvent(@BindingParam("calendarEvent")CalendarsEvent event){
		AdvancedCalendarEvent advEvent =(AdvancedCalendarEvent)event.getCalendarEvent();
		Event entityEvent = (Event)DataSourceLoader.getInstance().getRecord(Event.class, Integer.valueOf(advEvent.getEventId()));
		entityEvent.setDateTimeStart(new Timestamp(event.getBeginDate().getTime()));
		entityEvent.setDateTimeReg(new Timestamp(event.getEndDate().getTime()));
		entityEvent.setDateTimePlan(new Timestamp(event.getEndDate().getTime()));
		entityEvent.setDateTimeEnd(new Timestamp(event.getEndDate().getTime()));
		DataSourceLoader.getInstance().mergeRecord(entityEvent);
		refreshCalendar();		
	}
	
	
	@Command
	@NotifyChange({"calendarModel","patientHistoryItem","eventsList"})
	public void editEvent(@BindingParam("calendarEvent")CalendarsEvent event){
		if(authService.checkAccessRights(authService.getCurrentProfile().getRole(),"Edit")){
			AdvancedCalendarEvent advEvent =(AdvancedCalendarEvent)event.getCalendarEvent();
			Event entityEvent = (Event)DataSourceLoader.getInstance().getRecord(Event.class, Integer.valueOf(advEvent.getEventId()));
			final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("eventItem", entityEvent);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/eventwindow.zul", null, params);
	        window.doModal();
		} else {
			Clients.showNotification("У Вас нет прав на редактирование!", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);
		}
	}
	
    @GlobalCommand
    @Command
    @NotifyChange({"calendarModel","patientHistoryItem","eventsList"})
    public void refreshCalendar() {
    	eventsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Event", "where e.patientHistory.patientHistoriesId="+patientHistoryItem.getPatientHistoriesId()));
    	calendarModel = new SimpleCalendarModel(getCalendarEvents(eventsList));
    }
	

}
