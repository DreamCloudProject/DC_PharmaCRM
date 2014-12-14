package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

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

import ru.dreamcloud.pharmatracker.model.authentication.CommonRole;
import ru.dreamcloud.pharmatracker.model.authentication.CommonRule;
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
	 * Property newRuleTitle
	 ***************************************/
	private String newRuleTitle;	

	public String getNewRuleTitle() {
		return newRuleTitle;
	}

	public void setNewRuleTitle(String newRuleTitle) {
		this.newRuleTitle = newRuleTitle;
	}

	/**************************************
	 * Property currentRulesList
	 ***************************************/
	private List<CommonRule> currentRulesList;
	
	public List<CommonRule> getCurrentRulesList() {
		return currentRulesList;
	}

	public void setCurrentRulesList(List<CommonRule> currentRulesList) {
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
		
		if (this.actionType.equals("NEW")) {
			currentRoleItem = new CommonRole();
			currentRulesList = new ArrayList<CommonRule>();			
		}

		if (this.actionType.equals("EDIT")) {
			currentRoleItem = currentItem;
			List<Integer> args = new ArrayList<Integer>();
			args.add(currentRoleItem.getRoleId());
			currentRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonRule", "where e.roles IN :args", args));			
		}
		currentRoleItem.setRules(currentRulesList);
	}
	
	@Command
	@NotifyChange("currentRoleItem")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		boolean presence = false;
		List<CommonRole> allRolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRole", null));
		for (CommonRole commonRole : allRolesList) {
			if(commonRole.getTitle().equals(currentRoleItem.getTitle())){
				presence = true;
				break;
			}
		}
		if(presence){
			Clients.showNotification("Роль с таким названием уже есть! Переименуйте и повторите попытку...", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);
		} else {
			DataSourceLoader.getInstance().mergeRecord(currentRoleItem);
		}
		
		
		if (actionType.equals("NEW")) {			
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {			
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}		
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
    public void addNewRule() {
    	boolean presence = false;
    	for (CommonRule rule : allRulesList) {
			if(rule.getComponentName().equals(newRuleTitle)){
				presence = true;				
				break;
			}
		}
    	
    	if(presence){
    		Clients.showNotification("Правило с таким названием уже есть в базе данных!", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
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
    	CommonRule newRuleItem = new CommonRule();
    	newRuleItem.setComponentName(newRuleTitle);
    	newRuleItem.setRoles(new ArrayList<CommonRole>());
    	newRuleItem.getRoles().add(currentRoleItem);		
    	//currentRoleItem.getRules().add(newRuleItem);    	
    	//currentRulesList = currentRoleItem.getRules();    	
    	DataSourceLoader.getInstance().mergeRecord(newRuleItem);
    	allRulesList.add(newRuleItem);
		Clients.showNotification("Правило '"+newRuleItem.getComponentName()+"' добавлено!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    }
    

    @Command
    @NotifyChange({"currentRulesList","currentRoleItem","allRulesList","currentRuleItem"})
    public void removeRule(@BindingParam("currentRuleItem") final CommonRule rule){    	
		List<Integer> args = new ArrayList<Integer>();
		args.add(rule.getRuleId());		
		List<CommonRole> rolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonRole", "where e.rules IN :args", args));
		if(!rolesList.isEmpty()){
			String activeRoles = new String();
			for (int i = 0; i < rolesList.size(); i++) {
				if(i == (rolesList.size()-1)){
					activeRoles += rolesList.get(i).getTitle();
				} else {
					activeRoles += rolesList.get(i).getTitle() + ", ";
				}
			}
			Clients.showNotification("Правило '"+rule.getComponentName()+"' используется у "+activeRoles+"!", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);			
		} else {
			DataSourceLoader.getInstance().removeRecord(rule);
			allRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRule", null));
			Clients.showNotification("Правило '"+rule.getComponentName()+"' удален из базы данных! ", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}    	    	
    }
	
	@Command
	@NotifyChange("currentRoleItem")
	public void changeAllowProperty(@BindingParam("ruleItem") CommonRule rule){
		List<Integer> args;
		if(isContainsRuleAssociation(rule)){
			args = new ArrayList<Integer>();
			args.add(currentRoleItem.getRoleId());
			currentRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonRule", "where e.roles IN :args", args));
			currentRoleItem.setRules(new ArrayList<CommonRule>());
			for (CommonRule matchRule : currentRulesList) {
				if(!matchRule.getComponentName().equals(rule.getComponentName())){
					currentRoleItem.getRules().add(matchRule);
				} else {
					rule = matchRule;
				}
			}	
			
			args = new ArrayList<Integer>();
			args.add(rule.getRuleId());
			List<CommonRole> ruleRolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonRole", "where e.rules IN :args", args));
			rule.setRoles(new ArrayList<CommonRole>());
			for (CommonRole matchRole : ruleRolesList) {
				if(!matchRole.getTitle().equals(currentRoleItem.getTitle())){					
					rule.getRoles().add(matchRole);
				}
			}			
		} else {
			allRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRule", null));
			for (CommonRule matchRule : allRulesList) {
				if(matchRule.getComponentName().equals(rule.getComponentName())){
					rule = matchRule;
					break;
				}
			}	
			currentRoleItem.getRules().add(rule);
			rule.getRoles().add(currentRoleItem);			
		}

		DataSourceLoader.getInstance().mergeRecord(rule);
		
		Clients.showNotification("Правило '"+rule.getComponentName()+"' "+(currentRoleItem.getRules().contains(rule) ? "активно" : "неактивно")+"! ", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
	}
	
	public Boolean isContainsRuleAssociation(CommonRule rule){
		Boolean result = false;
		List<Integer> args = new ArrayList<Integer>();
		args.add(currentRoleItem.getRoleId());
		List<CommonRule> matchRules = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonRule", "where e.roles IN :args", args));
		for (CommonRule commonRule : matchRules) {
			if(commonRule.getComponentName().equals(rule.getComponentName())){
				result = true;
				break;
			}
		}		
		return result;
	}
	
	@Command
	public void closeThis() {
		win.detach();
	}
}
