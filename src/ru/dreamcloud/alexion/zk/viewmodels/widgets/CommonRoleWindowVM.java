package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.authentication.persistence.jpa.CommonRole;
import ru.dreamcloud.authentication.persistence.jpa.CommonRule;
import ru.dreamcloud.authentication.persistence.jpa.RuleAssociation;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class CommonRoleWindowVM {

	@Wire("#CommonRoleWindow")
	private Window win;
	
	/**************************************
	 * Property currentRoleItem
	 ***************************************/
	private CommonRole currentRoleItem;
	
	public CommonRole getCurrentRoleItem() {
		return currentRoleItem;
	}

	public void setCurrentRoleItem(CommonRole currentRoleItem) {
		this.currentRoleItem = currentRoleItem;
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
	 * Property allowProperty
	 ***************************************/
	private Boolean allowProperty;	
	
	public Boolean getAllowProperty() {
		return allowProperty;
	}

	public void setAllowProperty(Boolean allowProperty) {
		this.allowProperty = allowProperty;
	}

	/**************************************
	 * Property currentRuleItem
	 ***************************************/
	private CommonRule currentRuleItem;
	
	public CommonRule getCurrentRuleItem() {
		return currentRuleItem;
	}

	public void setCurrentRuleItem(CommonRule currentRuleItem) {
		this.currentRuleItem = currentRuleItem;
	}
	
	/**************************************
	 * Property newRuleItem
	 ***************************************/
	private CommonRule newRuleItem;	
	
	public CommonRule getNewRuleItem() {
		return newRuleItem;
	}

	public void setNewRuleItem(CommonRule newRuleItem) {
		this.newRuleItem = newRuleItem;
	}

	/**************************************
	 * Property currentRulesList
	 ***************************************/
	private List<RuleAssociation> currentRulesList;
	
	public List<RuleAssociation> getCurrentRulesList() {
		return currentRulesList;
	}

	public void setCurrentRulesList(List<RuleAssociation> currentRulesList) {
		this.currentRulesList = currentRulesList;
	}
	
	/**************************************
	 * Property allRulesList
	 ***************************************/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<CommonRule> allRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRule", null));	
	
	public List<CommonRule> getAllRulesList() {
		return allRulesList;
	}
	
	/**************************************
	 * Property itemsToUnlink
	 ***************************************/
	private List<Object> itemsToUnlink;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("commonRoleItem") CommonRole currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		currentRuleItem = new CommonRule();
		newRuleItem  = new CommonRule();
		itemsToUnlink = new ArrayList<Object>();
		
		if (this.actionType.equals("NEW")) {
			currentRoleItem = new CommonRole();
			currentRulesList = new ArrayList<RuleAssociation>();			
		}

		if (this.actionType.equals("EDIT")) {
			currentRoleItem = currentItem;
			currentRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("RuleAssociation", "where e.roleId="+currentRoleItem.getRoleId()));
			//currentRulesList = currentItem.getRules();
		}
		currentRoleItem.setRules(currentRulesList);
	}
	
	@Command
	@NotifyChange("currentRoleItem")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());

		if (actionType.equals("NEW")) {
			DataSourceLoader.getInstance().addRecord(currentRoleItem);
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			DataSourceLoader.getInstance().updateRecord(currentRoleItem);
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}		
		removeUnlinkItems();
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
    public void addNewRule() {    	
    	if(allRulesList.contains(currentRuleItem)){
    		List<RuleAssociation> activeRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("RuleAssociation", "where e.roleId="+currentRoleItem.getRoleId()));
    		for (RuleAssociation actRuleAssoc : activeRulesList) {
    			if(itemsToUnlink.contains(actRuleAssoc)) {
    				itemsToUnlink.remove(actRuleAssoc);
    			}    			
    		}
			
    		Boolean presence = false;    		
    		for (RuleAssociation ruleAssoc : currentRulesList) {
				if((ruleAssoc.getRuleId() == currentRuleItem.getRuleId()) 
						&& (ruleAssoc.getRoleId() == currentRoleItem.getRoleId())){
					presence = true;
					break;			
				}
			}			
    		
	    	if(!presence){	    		
				currentRoleItem.addRule(currentRuleItem, "true");
				currentRulesList = currentRoleItem.getRules();
				Clients.showNotification("Правило '"+currentRuleItem.getComponentName()+"' прикреплено!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
	    	} else {
	    		Clients.showNotification("Данное правило уже прикреплено!", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
	    	}
    	} else {
	    	Messagebox.show("Текущего правила нет в базе данных. Хотите ли вы его добавить?", "Новое правило", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override				
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						BindUtils.postGlobalCommand(null, null, "createNewRule", null);											
					}
				}
	    	});
	    	
    	}
    }
    
    @GlobalCommand
    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","newRuleItem"})
    public void createNewRule(){
    	List<RuleAssociation> roles = new ArrayList<RuleAssociation>();
    	newRuleItem.setRoles(roles);
    	currentRoleItem.addRule(newRuleItem, "true");
    	currentRulesList = currentRoleItem.getRules();
		Clients.showNotification("Правило '"+newRuleItem.getComponentName()+"' прикреплено! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		newRuleItem = new CommonRule();
    }
    

    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
    public void removeRule(){
    	Messagebox.show("Вы хотите удалить правило '"+currentRuleItem.getComponentName()+"' из базы данных?", "Удалить правило", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
			@Override				
			public void onEvent(Event event) throws Exception {
				if (Messagebox.ON_YES.equals(event.getName())){
					BindUtils.postGlobalCommand(null, null, "removeRuleFromDatabase", null);											
				}
			}
    	});
    }
    
    @GlobalCommand
    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
    public void removeRuleFromDatabase(){
    	List<RuleAssociation> rolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("RuleAssociation", "where e.ruleId="+currentRuleItem.getRuleId()));
		for(RuleAssociation ruleAssoc : rolesList){
			if(ruleAssoc.getRoleId() != currentRoleItem.getRoleId()){
				Clients.showNotification("Правило '"+currentRuleItem.getComponentName()+"' используется еще для других ролей!", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);
				return;			
			} else if (ruleAssoc.getRoleId() == currentRoleItem.getRoleId()){
				Clients.showNotification("Правило '"+currentRuleItem.getComponentName()+"' прикреплено к текущей роли! Чтобы удалить, нужно сначала открепить правило, а затем сохранить роль...", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);
				return;
			}	
		}
		DataSourceLoader.getInstance().removeRecord(currentRuleItem);
    	Clients.showNotification("Правило '"+currentRuleItem.getComponentName()+"' удален из базы данных! ", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    	
    }	

	@Command
	@NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
	public void unlinkRuleFromCurrentRole(@BindingParam("currentRuleItem") final RuleAssociation ruleAssoc) {		
		currentRulesList.remove(ruleAssoc);
		currentRoleItem.getRules().remove(ruleAssoc);
		itemsToUnlink.add(ruleAssoc);
		Clients.showNotification("Правило "+ruleAssoc.getRule().getComponentName()+" откреплено!", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
	}
	
	@Command
	public void changeAllowProperty(@BindingParam("ruleItem") final RuleAssociation ruleAssoc){
		allowProperty = (ruleAssoc.getAllow() != null) ? Boolean.valueOf(ruleAssoc.getAllow()) : true;				
		if(allowProperty){
			allowProperty = false;			
		} else {
			allowProperty = true;
		}				
		ruleAssoc.setAllow(allowProperty.toString());				
		DataSourceLoader.getInstance().updateRecord(ruleAssoc);
		Clients.showNotification("Правило '"+ruleAssoc.getRule().getComponentName()+"' "+(allowProperty ? "активно" : "неактивно")+"! ", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
	}
	
	private Boolean isContainsRuleAssociation(RuleAssociation ruleAssoc){
		Boolean result = false;
		List<RuleAssociation> matchRA = new ArrayList(DataSourceLoader.getInstance().fetchRecords("RuleAssociation", "where e.roleId="+ruleAssoc.getRoleId()+" and e.ruleId="+ruleAssoc.getRuleId()));
		if(!matchRA.isEmpty()){
			result = true;
		}
		return result;		
	}
	
	private void removeUnlinkItems() {
		for (Object entity : itemsToUnlink) {
			if(entity instanceof RuleAssociation){
				RuleAssociation ra = (RuleAssociation)entity;
				if(isContainsRuleAssociation(ra)){
					DataSourceLoader.getInstance().removeRecord(entity);
				}
			} else {
				DataSourceLoader.getInstance().removeRecord(entity);
			}
			
		}
	}
	
	@Command
	public void closeThis() {
		win.detach();
	}
}
