package ru.dreamcloud.alexion.zk.viewmodels.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Address;
import ru.dreamcloud.alexion.model.AttendantPerson;
import ru.dreamcloud.alexion.model.ContactInfo;
import ru.dreamcloud.alexion.model.PhoneNumber;
import ru.dreamcloud.alexion.model.Region;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class AttPersonWindowViewModel {

	@Wire("#AttPersonWindow")
	private Window win;
	
	/**************************************
	 * Property currentAttPersonItem
	 ***************************************/
	private AttendantPerson currentAttPersonItem;
	
	public AttendantPerson getCurrentAttPersonItem() {
		return currentAttPersonItem;
	}

	public void setCurrentAttPersonItem(AttendantPerson currentAttPersonItem) {
		this.currentAttPersonItem = currentAttPersonItem;
	}
	
	/**************************************
	 * Property contactInfoItem
	 ***************************************/
	private ContactInfo contactInfoItem;	

	public ContactInfo getContactInfoItem() {
		return contactInfoItem;
	}

	public void setContactInfoItem(ContactInfo contactInfoItem) {
		this.contactInfoItem = contactInfoItem;
	}
	
	/**************************************
	 * Property addressItem
	 ***************************************/
	private Address addressItem;	
	
	public Address getAddressItem() {
		return addressItem;
	}

	public void setAddressItem(Address addressItem) {
		this.addressItem = addressItem;
	}
	
	/**************************************
	 * Property phoneItem
	 ***************************************/
	private PhoneNumber phoneItem;

	public PhoneNumber getPhoneItem() {
		return phoneItem;
	}

	public void setPhoneItem(PhoneNumber phoneItem) {
		this.phoneItem = phoneItem;
	}

	/**************************************
	 * Property regionsList
	 ***************************************/
	private List<Region> regionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Region", null));
	
	public List<Region> getRegionsList() {
		return regionsList;
	}

	public void setRegionsList(List<Region> regionsList) {
		this.regionsList = regionsList;
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

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("attPersonItem") AttendantPerson currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);

		if (this.actionType.equals("NEW")) {
			currentAttPersonItem = new AttendantPerson();
			contactInfoItem = new ContactInfo();
			currentAttPersonItem.setContactInfo(contactInfoItem);			
		}

		if (this.actionType.equals("EDIT")) {
			currentAttPersonItem = currentItem;			
			contactInfoItem = currentItem.getContactInfo();
		}		

	}
	
	@Command
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		
		if (actionType.equals("NEW")) {
			DataSourceLoader.getInstance().addRecord(currentAttPersonItem);
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			DataSourceLoader.getInstance().updateRecord(currentAttPersonItem, currentAttPersonItem.getAttPersonId());
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}		
		
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
    @Command
    public void addNewAddress() {
    	
    }
	
	@Command
	public void closeThis() {
		win.detach();
	}
}
