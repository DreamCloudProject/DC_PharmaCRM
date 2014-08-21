package ru.dreamcloud.alexion.zk.viewmodels.tabs;

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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Address;
import ru.dreamcloud.alexion.model.Diagnosis;
import ru.dreamcloud.alexion.model.Patient;
import ru.dreamcloud.alexion.model.ContactInfo;
import ru.dreamcloud.alexion.model.PhoneNumber;
import ru.dreamcloud.alexion.model.PhoneType;
import ru.dreamcloud.alexion.model.Region;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class PatientWindowViewModel {

	@Wire("#PatientWindow")
	private Window win;
	
	/**************************************
	 * Property currentPatientItem
	 ***************************************/
	private Patient currentPatientItem;
	
	public Patient getCurrentPatientItem() {
		return currentPatientItem;
	}

	public void setCurrentPatientItem(Patient currentPatientItem) {
		this.currentPatientItem = currentPatientItem;
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
	 * Property diagnosesList
	 ***************************************/
	private List<Diagnosis> diagnosesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Diagnosis", null));	

	public List<Diagnosis> getDiagnosesList() {
		return diagnosesList;
	}

	public void setDiagnosesList(List<Diagnosis> diagnosesList) {
		this.diagnosesList = diagnosesList;
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
	 * Property itemsToRemove
	 ***************************************/
	
	private List<Object> itemsToRemove;
	
	/**************************************
	  Methods	 
	***************************************/

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("patientItem") Patient currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		itemsToRemove = new ArrayList<Object>();
		addressItem = new Address();
		phoneItem = new PhoneNumber();
		contactInfoItem = new ContactInfo();
		
		if (this.actionType.equals("NEW")) {
			currentPatientItem = new Patient();			
			currentPatientItem.setContactInfo(contactInfoItem);
			addressList = new ArrayList<Address>();
			phonesList = new ArrayList<PhoneNumber>();
		}

		if (this.actionType.equals("EDIT")) {
			currentPatientItem = currentItem;
			if(currentItem.getContactInfo() != null){
				contactInfoItem = currentItem.getContactInfo();
			}
			addressList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Address", "e.contactInfo.contactId="+contactInfoItem.getContactId()));
			phonesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PhoneNumber", "e.contactInfo.contactId="+contactInfoItem.getContactId()));
		}
	}
	
	@Command
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		clearAllRemovedItems();
		contactInfoItem.setAddressList(addressList);
		contactInfoItem.setPhonesList(phonesList);
		currentPatientItem.setContactInfo(contactInfoItem);
		if (actionType.equals("NEW")) {
			DataSourceLoader.getInstance().addRecord(currentPatientItem);
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}

		if (actionType.equals("EDIT")) {
			DataSourceLoader.getInstance().updateRecord(currentPatientItem);
			Clients.showNotification("Запись успешно сохранена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
		}		
		
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
