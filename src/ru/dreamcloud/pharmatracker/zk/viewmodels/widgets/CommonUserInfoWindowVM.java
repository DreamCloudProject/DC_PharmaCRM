package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.security.NoSuchAlgorithmException;
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

import ru.dreamcloud.pharmatracker.model.Address;
import ru.dreamcloud.pharmatracker.model.ContactInfo;
import ru.dreamcloud.pharmatracker.model.PhoneNumber;
import ru.dreamcloud.pharmatracker.model.PhoneType;
import ru.dreamcloud.pharmatracker.model.Project;
import ru.dreamcloud.pharmatracker.model.Region;
import ru.dreamcloud.pharmatracker.model.authentication.CommonRole;
import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;
import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class CommonUserInfoWindowVM {
	
	@Wire("#CommonUserInfoWindow")
	private Window win;
	
	/**************************************
	  Property authService	 
	***************************************/
	private AuthenticationService authService;
	
	/**************************************
	 * Property currentUserInfoItem
	 ***************************************/
	private CommonUserInfo currentUserInfoItem;

	public CommonUserInfo getCurrentUserInfoItem() {
		return currentUserInfoItem;
	}

	public void setCurrentUserInfoItem(CommonUserInfo currentUserInfoItem) {
		this.currentUserInfoItem = currentUserInfoItem;
	}
	
	/**************************************
	 * Property password
	 ***************************************/
	private String password;	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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
	 * Property itemsToRemove
	 ***************************************/
	
	private List<Object> itemsToRemove;
	
	/**************************************
	 * Property allRolesList
	 ***************************************/
	
	private List<CommonRole> allRolesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonRole", null));
	
	public List<CommonRole> getAllRolesList() {
		return allRolesList;
	}
	
	/**************************************
	 * Property allUsers
	 ***************************************/
	
	private List<CommonUserInfo> allUsers = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", null));
	
	public List<CommonUserInfo> getAllUsers() {
		return allUsers;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("commonUserInfoItem") CommonUserInfo currentItem,
					 @ExecutionArgParam("actionType") String currentAction) {
		Selectors.wireComponents(view, this, false);
		setActionType(currentAction);
		currentRoleItem = new CommonRole();
		itemsToRemove = new ArrayList<Object>();
		addressItem = new Address();
		phoneItem = new PhoneNumber();
		contactInfoItem = new ContactInfo();
		authService = new AuthenticationService();
		
		if (this.actionType.equals("NEW")) {
			currentUserInfoItem = new CommonUserInfo();
			currentUserInfoItem.setContactInfo(contactInfoItem);
			addressList = new ArrayList<Address>();
			phonesList = new ArrayList<PhoneNumber>();
		}

		if (this.actionType.equals("EDIT")) {
			currentUserInfoItem = currentItem;
			password = currentItem.getPassword();
			if(currentItem.getContactInfo() != null){
				contactInfoItem = currentItem.getContactInfo();
			}
			addressList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Address", "where e.contactInfo.contactId="+contactInfoItem.getContactId()));
			phonesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PhoneNumber", "where e.contactInfo.contactId="+contactInfoItem.getContactId()));
		}		
	}
	
	@Command
	@NotifyChange("currentUserInfoItem")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchTerm", new String());
		clearAllRemovedItems();
		List<CommonUserInfo> matchUsers = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", "where e.login='"+currentUserInfoItem.getLogin()+"'"));
		contactInfoItem.setAddressList(addressList);
		contactInfoItem.setPhonesList(phonesList);
		currentUserInfoItem.setContactInfo(contactInfoItem);
		try {
			if( (currentUserInfoItem.getPassword() == null) || (!currentUserInfoItem.getPassword().equals(password)) ){
				currentUserInfoItem.setPassword(authService.hashToMd5(password));
			}			
			DataSourceLoader.getInstance().mergeRecord(currentUserInfoItem);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		if (actionType.equals("NEW")) {
			if(matchUsers.isEmpty()){
				Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);				
			} else {
				Clients.showNotification("Пользователь с таким логином уже существует! Смените логин и попробуйте снова...", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);
			}			
		}

		if (actionType.equals("EDIT")) {			
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
