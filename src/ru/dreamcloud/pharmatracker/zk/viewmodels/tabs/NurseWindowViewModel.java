package ru.dreamcloud.pharmatracker.zk.viewmodels.tabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.Address;
import ru.dreamcloud.pharmatracker.model.ContactInfo;
import ru.dreamcloud.pharmatracker.model.Nurse;
import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.PhoneNumber;
import ru.dreamcloud.pharmatracker.model.PhoneType;
import ru.dreamcloud.pharmatracker.model.Region;
import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class NurseWindowViewModel {

	@Wire("#NurseWindow")
	private Window win;
	
	/**************************************
	 * Property currentNurseItem
	 ***************************************/
	private Nurse currentNurseItem;
	
	public Nurse getCurrentNurseItem() {
		return currentNurseItem;
	}

	public void setCurrentNurseItem(Nurse currentNurseItem) {
		this.currentNurseItem = currentNurseItem;
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
	 * Property addressList
	 ***************************************/
	private List<Address> addressList;	
	
	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
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
	 * Property phoneItem
	 ***************************************/
	private List<PhoneNumber> phonesList;
	
	public List<PhoneNumber> getPhonesList() {
		return phonesList;
	}

	public void setPhonesList(List<PhoneNumber> phonesList) {
		this.phonesList = phonesList;
	}

	/**************************************
	 * Property phoneTypes
	 ***************************************/	
	private List<PhoneType> phoneTypes = Arrays.asList(PhoneType.values());	

	public List<PhoneType> getPhoneTypes() {
		return phoneTypes;
	}

	public void setPhoneTypes(List<PhoneType> phoneTypes) {
		this.phoneTypes = phoneTypes;
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
	
	/**************************************
	  Property authService	 
	***************************************/
	private AuthenticationService authService;
	
	/**************************************
	  Property viewContactInfo	 
	***************************************/
	private Boolean viewContactInfo;	

	public Boolean getViewContactInfo() {
		return viewContactInfo;
	}

	public void setViewContactInfo(Boolean viewContactInfo) {
		this.viewContactInfo = viewContactInfo;
	}
	
	/**************************************
	 * Property itemsToRemove
	 ***************************************/
	
	private List<Object> itemsToRemove;
	
	/**************************************
	  Methods	 
	***************************************/

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("nurseItem") Nurse currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		itemsToRemove = new ArrayList<Object>();
		addressItem = new Address();
		phoneItem = new PhoneNumber();
		contactInfoItem = new ContactInfo();
		
		authService = new AuthenticationService();
		viewContactInfo = authService.checkAccessRights(authService.getCurrentProfile().getRole(), "ViewContactInfoDisabled");
		
		if (this.actionType.equals("NEW")) {
			currentNurseItem = new Nurse();			
			currentNurseItem.setContactInfo(contactInfoItem);
			addressList = new ArrayList<Address>();
			phonesList = new ArrayList<PhoneNumber>();
		}

		if (this.actionType.equals("EDIT")) {
			currentNurseItem = currentItem;
			if(currentItem.getContactInfo() != null){
				contactInfoItem = currentItem.getContactInfo();
			}
			addressList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Address", "where e.contactInfo.contactId="+contactInfoItem.getContactId()));
			phonesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PhoneNumber", "where e.contactInfo.contactId="+contactInfoItem.getContactId()));
		}
	}
	
	@Command
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		HashMap<String, Object> paramsToRefresh;
		
		clearAllRemovedItems();
		contactInfoItem.setAddressList(addressList);
		contactInfoItem.setPhonesList(phonesList);
		currentNurseItem.setContactInfo(contactInfoItem);
		DataSourceLoader.getInstance().mergeRecord(currentNurseItem);
		if (actionType.equals("NEW")) {			
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {			
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}
		
		paramsToRefresh = new HashMap<String, Object>();
		paramsToRefresh.put("nurse", currentNurseItem);
		BindUtils.postGlobalCommand(null, null, "refreshNurseTilePanel", paramsToRefresh);
		
		paramsToRefresh = new HashMap<String, Object>();
		PatientHistory ph = (PatientHistory)Sessions.getCurrent().getAttribute("currentPatientHistory");
		PatientHistory phItemToSend = (PatientHistory)DataSourceLoader.getInstance().getRecord(PatientHistory.class, ph.getPatientHistoriesId());
		phItemToSend.setNurse(currentNurseItem);
		paramsToRefresh.put("patientHistory", phItemToSend);		
		BindUtils.postGlobalCommand(null, null, "refreshPatientHistory", paramsToRefresh);
		BindUtils.postGlobalCommand(null, null, "refreshPatientHistoryPage", paramsToRefresh);
		
		params.put("searchTerm", new String());
		BindUtils.postGlobalCommand(null, null, "search", params);
		win.detach();
	}
	
    @Command
    @NotifyChange({"addressList","addressItem"})
    public void addNewAddress() {
    	addressItem.setContactInfo(contactInfoItem);
    	addressList.add(addressItem);
    	addressItem = new Address();
    	Clients.showNotification("Адрес добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    }
    
    @Command
    @NotifyChange({"addressList","addressItem"})
    public void removeAddress(@BindingParam("addressItem") final Address adrItem) {
    	if(adrItem.getAddressId() != 0){
    		itemsToRemove.add(adrItem);
    	}
    	addressList.remove(adrItem);
    	Clients.showNotification("Адрес удален! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
    }
    
    @Command
    @NotifyChange({"phonesList","phoneItem"})
    public void addNewPhoneNumber() {
    	phoneItem.setContactInfo(contactInfoItem);
    	phonesList.add(phoneItem);
    	phoneItem = new PhoneNumber();
    	Clients.showNotification("Номер телефона добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    }
    

    @Command
    @NotifyChange({"phonesList","phoneItem"})
    public void removePhoneNumber(@BindingParam("phoneItem") final PhoneNumber phnItem) {
    	if(phnItem.getPhoneId() != 0){
    		itemsToRemove.add(phnItem);
    	}
    	phonesList.remove(phnItem);
    	Clients.showNotification("Номер телефона удален! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_WARNING, null, "top_center" ,4100);
    }
	
	@Command
	public void closeThis() {
		win.detach();
	}
	
	private void clearAllRemovedItems(){		
		for (Object entityObj : itemsToRemove) {
			DataSourceLoader.getInstance().removeRecord(entityObj);
		}		
	}
}
