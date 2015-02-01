package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.AttendantPerson;
import ru.dreamcloud.pharmatracker.model.MedicalExpert;
import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;

public class MedicalExpertTilePanelVM {
	
	/**************************************
	  Property currentMedicalExpert	 
	***************************************/
	private MedicalExpert currentMedicalExpert;	
	
	public MedicalExpert getCurrentMedicalExpert() {
		return currentMedicalExpert;
	}

	public void setCurrentMedicalExpert(MedicalExpert currentMedicalExpert) {
		this.currentMedicalExpert = currentMedicalExpert;
	}
	
	/**************************************
	  Property authService	 
	***************************************/
	private AuthenticationService authService;
	
	/**************************************
	  Property viewDocuments	 
	***************************************/
	private Boolean viewDocuments;
	
	public Boolean getViewDocuments() {
		return viewDocuments;
	}

	public void setViewDocuments(Boolean viewDocuments) {
		this.viewDocuments = viewDocuments;
	}
	
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
	  Property createPermission	 
	***************************************/
	private Boolean createPermission;	

	public Boolean getCreatePermission() {
		return createPermission;
	}

	public void setCreatePermission(Boolean createPermission) {
		this.createPermission = createPermission;
	}

	/**************************************
	  Property editPermission	 
	***************************************/
	private Boolean editPermission;	
	
	public Boolean getEditPermission() {
		return editPermission;
	}

	public void setEditPermission(Boolean editPermission) {
		this.editPermission = editPermission;
	}

	/**************************************
	  Property deletePermission	 
	***************************************/
	private Boolean deletePermission;	

	public Boolean getDeletePermission() {
		return deletePermission;
	}

	public void setDeletePermission(Boolean deletePermission) {
		this.deletePermission = deletePermission;
	}

	/**************************************
	  Methods	 
	***************************************/
	@Init
	public void init(@ExecutionArgParam("currentMedicalExpert") MedicalExpert medicalExpert) {
		authService = new AuthenticationService();
		createPermission = authService.checkAccessRights(authService.getCurrentProfile().getRole(),"CreateDisabled");
		editPermission = authService.checkAccessRights(authService.getCurrentProfile().getRole(),"EditDisabled");
		deletePermission = authService.checkAccessRights(authService.getCurrentProfile().getRole(),"DeleteDisabled");			
		viewDocuments = authService.checkAccessRights(authService.getCurrentProfile().getRole(), "ViewDocumentsDisabled");
		viewContactInfo = authService.checkAccessRights(authService.getCurrentProfile().getRole(), "ViewContactInfoDisabled");
		
		currentMedicalExpert = medicalExpert;
	}
	
    @Command
    @NotifyChange("currentMedicalExpert")
    public void editMedicalExpertItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("medicalExpertItem", currentMedicalExpert);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/medicalexpertwindow.zul", null, params);
        window.doModal();
    }
    
	@GlobalCommand
	@Command
	@NotifyChange("currentMedicalExpert")
	public void refreshMedicalExpertTilePanel(@BindingParam("medicalExpert") MedicalExpert medExpert){
		currentMedicalExpert = medExpert;
	}
    
}
