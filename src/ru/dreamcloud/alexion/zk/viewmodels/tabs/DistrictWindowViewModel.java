package ru.dreamcloud.alexion.zk.viewmodels.tabs;

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

import ru.dreamcloud.alexion.model.District;
import ru.dreamcloud.alexion.model.Region;
import ru.dreamcloud.persistence.jpa.DataSourceLoader;

public class DistrictWindowViewModel {

	@Wire("#DistrictWindow")
	private Window win;
	
	/**************************************
	 * Property currentDistrictItem
	 ***************************************/
	private District currentDistrictItem;
	
	public District getCurrentDistrictItem() {
		return currentDistrictItem;
	}

	public void setCurrentDistrictItem(District currentDistrictItem) {
		this.currentDistrictItem = currentDistrictItem;
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
	 * Property regionItem
	 ***************************************/
	private Region regionItem;
	
	public Region getRegionItem() {
		return regionItem;
	}

	public void setRegionItem(Region regionItem) {
		this.regionItem = regionItem;
	}
	
	/**************************************
	 * Property regionItem
	 ***************************************/
	private Region newRegionItem;	
	
	public Region getNewRegionItem() {
		return newRegionItem;
	}

	public void setNewRegionItem(Region newRegionItem) {
		this.newRegionItem = newRegionItem;
	}

	/**************************************
	 * Property currentRegionsList
	 ***************************************/
	private List<Region> currentRegionsList;
	
	public List<Region> getCurrentRegionsList() {
		return currentRegionsList;
	}

	public void setCurrentRegionsList(List<Region> currentRegionsList) {
		this.currentRegionsList = currentRegionsList;
	}
	
	/**************************************
	 * Property allRegionsList
	 ***************************************/
	
	private List<Region> allRegionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Region", null));	
	
	public List<Region> getAllRegionsList() {
		return allRegionsList;
	}

	/**************************************
	 * Property regionsToUnlink
	 ***************************************/
	
	private List<Object> regionsToUnlink;

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("districtItem") District currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		regionItem = new Region();
		newRegionItem  = new Region();
		regionsToUnlink = new ArrayList<Object>();
		
		if (this.actionType.equals("NEW")) {
			currentDistrictItem = new District();
			currentRegionsList = new ArrayList<Region>();
		}

		if (this.actionType.equals("EDIT")) {
			currentDistrictItem = currentItem;
			currentRegionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Region", "e.district.districtId="+currentDistrictItem.getDistrictId()));
		}

	}
	
	@Command
	@NotifyChange("currentDistrictItem")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		approveUnlinkRegions();
		currentDistrictItem.setRegions(currentRegionsList);
		
		if (actionType.equals("NEW")) {
			DataSourceLoader.getInstance().addRecord(currentDistrictItem);
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			DataSourceLoader.getInstance().updateRecord(currentDistrictItem);
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}		
		
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
    @Command
    @NotifyChange({"currentRegionsList","currentDistrictItem","allRegionsList","regionItem"})
    public void addNewRegion() {    	
    	if(allRegionsList.contains(regionItem)){
	    	if(!currentRegionsList.contains(regionItem)){
				regionItem.setDistrict(currentDistrictItem);
				currentRegionsList.add(regionItem);
				Clients.showNotification("Регион '"+regionItem.getTitle()+"' прикреплен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
	    	} else {
	    		Clients.showNotification("Данный регион уже прикриплен!", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
	    	}
    	} else {
	    	Messagebox.show("Текущего региона нет в базе данных. Хотите ли вы его добавить?", "Новый регион", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override				
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						BindUtils.postGlobalCommand(null, null, "createNewRegion", null);											
					}
				}
	    	});
	    	
    	}
    }
    
    @GlobalCommand
    @Command
    @NotifyChange({"currentRegionsList","currentDistrictItem","allRegionsList","newRegionItem"})
    public void createNewRegion(){
		if(newRegionItem.getDescription().isEmpty()){
			newRegionItem.setDescription(currentDistrictItem.getTitle());
		}
		newRegionItem.setDistrict(currentDistrictItem);
		currentRegionsList.add(newRegionItem);		
		Clients.showNotification("Регион '"+newRegionItem.getTitle()+"' прикреплен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		newRegionItem = new Region();
    }
    

    @Command
    @NotifyChange({"currentRegionsList","currentDistrictItem","allRegionsList","regionItem"})
    public void removeRegion(){
    	Messagebox.show("Вы хотите удалить '"+regionItem.getTitle()+"' из базы данных?", "Удалить регион", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
			@Override				
			public void onEvent(Event event) throws Exception {
				if (Messagebox.ON_YES.equals(event.getName())){
					BindUtils.postGlobalCommand(null, null, "removeRegionFromDatabase", null);											
				}
			}
    	});
    }
    
    @GlobalCommand
    @Command
    @NotifyChange({"currentRegionsList","currentDistrictItem","allRegionsList","regionItem"})
    public void removeRegionFromDatabase(){
    	unlinkRegion(regionItem);
    	approveUnlinkRegions();
    	DataSourceLoader.getInstance().removeRecord(regionItem);    	    	
    	Clients.showNotification("Регион '"+regionItem.getTitle()+"' удален из базы данных! ", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    }
	
	@Command
	@NotifyChange({"currentRegionsList","currentDistrictItem","allRegionsList","regionItem"})
	public void unlinkRegion(@BindingParam("regionItem") final Region regItem) {
		regItem.setDistrict(null);
    	if(regItem.getRegionId() != null){
    		regionsToUnlink.add(regItem);	
    	}	
		currentRegionsList.remove(regItem);
		Clients.showNotification("Регион "+regItem.getTitle()+" откреплен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
	}
	
	@NotifyChange({"currentRegionsList","currentDistrictItem","allRegionsList","regionItem"})
	private void approveUnlinkRegions(){		
		for (Object entityObj : regionsToUnlink) {
			DataSourceLoader.getInstance().updateRecord(entityObj);
		}		
	}
	
	@Command
	public void closeThis() {
		win.detach();
	}
}
