package ru.dreamcloud.alexion.zk.viewmodels.widgets.calendar;
import org.zkoss.calendar.impl.SimpleCalendarEvent;


public class AdvancedCalendarEvent extends SimpleCalendarEvent {
	
	public AdvancedCalendarEvent() {	
	}
	
	/**************************************
	 * Property eventId
	 ***************************************/	
	private String eventId;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}	

}
