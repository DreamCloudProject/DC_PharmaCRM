package ru.dreamcloud.alexion.model;

import org.zkoss.util.resource.Labels;

public enum PhoneType {		
    HOME(Labels.getLabel("messages.phoneTypes.home.name"),Labels.getLabel("messages.phoneTypes.home.icon")),
    MOBILE(Labels.getLabel("messages.phoneTypes.mobile.name"),Labels.getLabel("messages.phoneTypes.mobile.icon")),
    WORK(Labels.getLabel("messages.phoneTypes.work.name"),Labels.getLabel("messages.phoneTypes.work.icon"));
    
    private final String name;
    
    private PhoneType(String n, String i){
      name=n;
      icon=i;
    }
    
    public String getName(){
      return name;
    }
    
    private final String icon;
    
    public String getIcon(){
        return icon;
      }
}