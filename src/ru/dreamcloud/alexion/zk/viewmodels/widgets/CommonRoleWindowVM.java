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
import ru.dreamcloud.authentication.persistence.jpa.CommonRoleRule;
import ru.dreamcloud.persistence.jpa.DataSourceLoader;

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
	private CommonRoleRule currentRuleItem;
	
	public CommonRoleRule getCurrentRuleItem() {
		return currentRuleItem;
	}

	public void setCurrentRuleItem(CommonRoleRule currentRuleItem) {
		this.currentRuleItem = currentRuleItem;
	}
	
	/**************************************
	 * Property newRuleItem
	 ***************************************/
	private CommonRoleRule newRuleItem;	
	
	public CommonRoleRule getNewRuleItem() {
		return newRuleItem;
	}

	public void setNewRuleItem(CommonRoleRule newRuleItem) {
		this.newRuleItem = newRuleItem;
	}

	/**************************************
	 * Property currentRulesList
	 ***************************************/
	private List<CommonRoleRule> currentRulesList;
	
	public List<CommonRoleRule> getCurrentRulesList() {
		return currentRulesList;
	}

	public void setCurrentRulesList(List<CommonRoleRule> currentRulesList) {
		this.currentRulesList = currentRulesList;
	}
	
	/**************************************
	 * Property allRulesList
	 ***************************************/
	
	private List<CommonRoleRule> allRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRoleRule", null));	
	
	public List<CommonRoleRule> getAllRulesList() {
		return allRulesList;
	}

	/**************************************
	 * Property currentRulesToUnlink
	 ***************************************/
	
	private List<Object> currentRulesToUnlink;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("commonRoleItem") CommonRole currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		currentRuleItem = new CommonRoleRule();
		newRuleItem  = new CommonRoleRule();
		currentRulesToUnlink = new ArrayList<Object>();
		
		if (this.actionType.equals("NEW")) {
			currentRoleItem = new CommonRole();
			currentRulesList = new ArrayList<CommonRoleRule>();
		}

		if (this.actionType.equals("EDIT")) {
			currentRoleItem = currentItem;
			currentRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRoleRule", "e.role.roleId="+currentRoleItem.getRoleId()));
		}

	}
	
	@Command
	@NotifyChange("currentRoleItem")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		approveUnlinkRules();
		currentRoleItem.setRules(currentRulesList);
		
		if (actionType.equals("NEW")) {
			DataSourceLoader.getInstance().addRecord(currentRoleItem);
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			DataSourceLoader.getInstance().updateRecord(currentRoleItem);
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}		
		
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
    public void addNewRule() {    	
    	if(allRulesList.contains(currentRuleItem)){
	    	if(!currentRulesList.contains(currentRuleItem)){
				currentRuleItem.setRole(currentRoleItem);
				currentRulesList.add(currentRuleItem);
				Clients.showNotification("Правило '"+currentRuleItem.getComponentName()+"' прикреплено! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
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
		newRuleItem.setRole(currentRoleItem);
		newRuleItem.setAllow("true");
		currentRulesList.add(newRuleItem);		
		Clients.showNotification("Правило '"+newRuleItem.getComponentName()+"' прикреплено! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		newRuleItem = new CommonRoleRule();
    }
    

    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
    public void removeRule(){
    	Messagebox.show("Вы хотите удалить правило '"+currentRuleItem.getComponentName()+"' из базы данных?", "Удалить регион", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
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
    	unlinkRule(currentRuleItem);
    	approveUnlinkRules();
    	DataSourceLoader.getInstance().removeRecord(currentRuleItem);    	    	
    	Clients.showNotification("Правило '"+currentRuleItem.getComponentName()+"' удален из базы данных! ", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    }
	
	@Command
	@NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
	public void unlinkRule(@BindingParam("currentRuleItem") final CommonRoleRule ruleItem) {
		ruleItem.setRole(null);
    	if(ruleItem.getRuleId() != 0){
    		currentRulesToUnlink.add(ruleItem);	
    	}	
		currentRulesList.remove(ruleItem);
		Clients.showNotification("Правило "+ruleItem.getComponentName()+" откреплено! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
	}
	
	@Command
	public void changeAllowProperty(@BindingParam("ruleItem") final CommonRoleRule ruleItem){
		
		if(ruleItem.getAllow() != null){
			allowProperty = Boolean.valueOf(ruleItem.getAllow());			
		} else {
			allowProperty = true;
		}
		
		if(allowProperty){
			allowProperty = false;			
		} else {
			allowProperty = true;
		}
		
		ruleItem.setAllow(allowProperty.toString());
		DataSourceLoader.getInstance().updateRecord(ruleItem);
		Clients.showNotification("Правило '"+ruleItem.getComponentName()+"' "+(allowProperty ? "активно" : "неактивно")+"! ", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
	}
	
	@NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
	private void approveUnlinkRules(){		
		for (Object entityObj : currentRulesToUnlink) {
			DataSourceLoader.getInstance().updateRecord(entityObj);
		}		
	}
	
	@Command
	public void closeThis() {
		win.detach();
	}
}
