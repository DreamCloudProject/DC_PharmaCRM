package ru.dreamcloud.alexion.model;

import org.zkoss.util.resource.Labels;

public enum MessageType {
	TODO(Labels.getLabel("messages.eventMessageType.TODO.name")),
	IN_PROGRESS(Labels.getLabel("messages.eventMessageType.PROGRESS.name")),
	DONE(Labels.getLabel("messages.eventMessageType.DONE.name"));
		
    private final String name;    
    
    public String getName() {
		return name;
	}
    
	private MessageType(String n){
      name=n;      
    }
}
