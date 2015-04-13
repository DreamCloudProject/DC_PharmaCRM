package ru.dreamcloud.pharmatracker.zk.viewmodels;

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

import ru.dreamcloud.pharmatracker.model.AttendantPerson;
import ru.dreamcloud.pharmatracker.model.Doctor;
import ru.dreamcloud.pharmatracker.model.MedicalExpert;
import ru.dreamcloud.pharmatracker.model.Nurse;
import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;
import ru.dreamcloud.pharmatracker.model.authentication.RoleAccessLevel;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class PatientHistoryWindowAdditionalVM {
	
	@Wire("#PatientHistoryWindowAdditional")
	private Window win;
	
	/**************************************
	  Property patientHistory 
	***************************************/
	private PatientHistory patientHistory;	

	public PatientHistory getPatientHistory() {
		return patientHistory;
	}

	public void setPatientHistory(PatientHistory patientHistory) {
		this.patientHistory = patientHistory;
	}
	/*-------------------------------------------------------------------------------------------------------------------------*/
	/**************************************
	 * Property attPersonItem
	 ***************************************/
	private AttendantPerson attPersonItem;	
	
	public AttendantPerson getAttPersonItem() {
		return attPersonItem;
	}

	public void setAttPersonItem(AttendantPerson attPersonItem) {
		this.attPersonItem = attPersonItem;
	}

	/**************************************
	 * Property newAttPersonItemFullname
	 ***************************************/
	private String newAttPersonItemFullname;
		
	public String getNewAttPersonItemFullname() {
		return newAttPersonItemFullname;
	}

	public void setNewAttPersonItemFullname(String newAttPersonItemFullname) {
		this.newAttPersonItemFullname = newAttPersonItemFullname;
	}
	
	/**************************************
	 * Property allAttPersonsList
	 ***************************************/
	
	private List<AttendantPerson> allAttPersonsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("AttendantPerson", null));
	
	public List<AttendantPerson> getAllAttPersonsList() {
		return allAttPersonsList;
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------*/
	/**************************************
	 * Property medicalExpertItem
	 ***************************************/
	private MedicalExpert medicalExpertItem;	
	
	public MedicalExpert getMedicalExpertItem() {
		return medicalExpertItem;
	}

	public void setMedicalExpertItem(MedicalExpert medicalExpertItem) {
		this.medicalExpertItem = medicalExpertItem;
	}

	/**************************************
	 * Property newMedicalExpertItemFullname
	 ***************************************/
	private String newMedicalExpertItemFullname;	
	
	public String getNewMedicalExpertItemFullname() {
		return newMedicalExpertItemFullname;
	}

	public void setNewMedicalExpertItemFullname(String newMedicalExpertItemFullname) {
		this.newMedicalExpertItemFullname = newMedicalExpertItemFullname;
	}

	/**************************************
	 * Property allMedicalExpertsList
	 ***************************************/
	
	private List<MedicalExpert> allMedicalExpertsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("MedicalExpert", null));
	
	public List<MedicalExpert> getAllMedicalExpertsList() {
		return allMedicalExpertsList;
	}
	/*-------------------------------------------------------------------------------------------------------------------------*/
	
	/**************************************
	 * Property nurseItem
	 ***************************************/
	private Nurse nurseItem;	
	
	public Nurse getNurseItem() {
		return nurseItem;
	}

	public void setNurseItem(Nurse nurseItem) {
		this.nurseItem = nurseItem;
	}

	/**************************************
	 * Property newNurseItemFullname
	 ***************************************/
	private String newNurseItemFullname;	
	
	public String getNewNurseItemFullname() {
		return newNurseItemFullname;
	}

	public void setNewNurseItemFullname(String newNurseItemFullname) {
		this.newNurseItemFullname = newNurseItemFullname;
	}
	
	/**************************************
	 * Property allNursesList
	 ***************************************/
	
	private List<Nurse> allNursesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Nurse", null));
	
	public List<Nurse> getAllNursesList() {
		return allNursesList;
	}	
	
	/*-------------------------------------------------------------------------------------------------------------------------*/
	
	/**************************************
	 * Property doctorItem
	 ***************************************/
	private Doctor doctorItem;	
	
	public Doctor getDoctorItem() {
		return doctorItem;
	}

	public void setDoctorItem(Doctor doctorItem) {
		this.doctorItem = doctorItem;
	}

	/**************************************
	 * Property newDoctorItemFullname
	 ***************************************/
	private String newDoctorItemFullname;	
	
	public String getNewDoctorItemFullname() {
		return newDoctorItemFullname;
	}

	public void setNewDoctorItemFullname(String newDoctorItemFullname) {
		this.newDoctorItemFullname = newDoctorItemFullname;
	}

	/**************************************
	 * Property allDoctorsList
	 ***************************************/
	
	private List<Doctor> allDoctorsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Doctor", null));
	
	public List<Doctor> getAllDoctorsList() {
		return allDoctorsList;
	}
	/*-------------------------------------------------------------------------------------------------------------------------*/
	
	/**************************************
	 * Property masterDoctorItem
	 ***************************************/
	private Doctor masterDoctorItem;	
	
	public Doctor getMasterDoctorItem() {
		return masterDoctorItem;
	}

	public void setMasterDoctorItem(Doctor masterDoctorItem) {
		this.masterDoctorItem = masterDoctorItem;
	}
	
	/**************************************
	 * Property newMasterDoctorItemFullname
	 ***************************************/
	private String newMasterDoctorItemFullname;	
	
	public String getNewMasterDoctorItemFullname() {
		return newMasterDoctorItemFullname;
	}

	public void setNewMasterDoctorItemFullname(String newMasterDoctorItemFullname) {
		this.newMasterDoctorItemFullname = newMasterDoctorItemFullname;
	}

	/*-------------------------------------------------------------------------------------------------------------------------*/	
	
	/**************************************
	 * Property allLawyersList
	 ***************************************/
	
	private List<CommonUserInfo> allLawyersList;
	
	public List<CommonUserInfo> getAllLawyersList() {
		return allLawyersList;
	}
	
	/**************************************
	 * Property allCuratorsList
	 ***************************************/
	
	private List<CommonUserInfo> allCuratorsList;
	
	public List<CommonUserInfo> getAllCuratorsList() {
		return allCuratorsList;
	}
	
	/**************************************
	 * Methods
	 ***************************************/

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, 
					 @ExecutionArgParam("patientHistoryItem") PatientHistory currentItem) {
		Selectors.wireComponents(view, this, false);
		List<RoleAccessLevel> args = new ArrayList<RoleAccessLevel>();
		args.add(RoleAccessLevel.EMPLOYEE);
		allAttPersonsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("AttendantPerson", null));
		allMedicalExpertsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("MedicalExpert", null));
		allCuratorsList = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonUserInfo", "where e.role.roleAccessLevel IN :args", args));
		allLawyersList = new ArrayList(DataSourceLoader.getInstance().fetchRecordsWithArrays("CommonUserInfo", "where e.role.roleAccessLevel IN :args", args));
		PatientHistory ph = (PatientHistory)DataSourceLoader.getInstance().getRecord(PatientHistory.class, currentItem.getPatientHistoriesId());
		patientHistory = ph;
		if(patientHistory.getAttperson() != null){			
			attPersonItem = (AttendantPerson)DataSourceLoader.getInstance().getRecord(AttendantPerson.class, patientHistory.getAttperson().getAttPersonId());
			newAttPersonItemFullname = attPersonItem.getFullname();
		}
		if(patientHistory.getNurse() != null){
			nurseItem = (Nurse)DataSourceLoader.getInstance().getRecord(Nurse.class, patientHistory.getNurse().getNurseId());			
			newNurseItemFullname = nurseItem.getFullname();
		}
		if(patientHistory.getMedicalExpert() != null){
			medicalExpertItem = (MedicalExpert)DataSourceLoader.getInstance().getRecord(MedicalExpert.class, patientHistory.getMedicalExpert().getMedicalExpertId());			
			newMedicalExpertItemFullname = medicalExpertItem.getFullname();
		}
		if(patientHistory.getDoctor() != null){
			doctorItem = (Doctor)DataSourceLoader.getInstance().getRecord(Doctor.class, patientHistory.getDoctor().getDoctorId());			
			newDoctorItemFullname = doctorItem.getFullname();
		}
		if(patientHistory.getMasterDoctor() != null){
			masterDoctorItem = (Doctor)DataSourceLoader.getInstance().getRecord(Doctor.class, patientHistory.getMasterDoctor().getDoctorId());			
			newMasterDoctorItemFullname = masterDoctorItem.getFullname();
		}		
	}
	
	@Command
	@NotifyChange("patientHistory")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();	
		HashMap<String, Object> paramsToRefresh;
		
		if(patientHistory.getAttperson() != null){
			if(!newAttPersonItemFullname.equals(patientHistory.getAttperson().getFullname())){
				addNewAttPerson();
			}			
		} else {
			if(newAttPersonItemFullname != null){
				addNewAttPerson();
			}
		}
		
		if(patientHistory.getNurse() != null){
			if(!newNurseItemFullname.equals(patientHistory.getNurse().getFullname())){
				addNewNurse();
			}
		} else {
			if(newNurseItemFullname != null){
				addNewNurse();
			}		
		}		
		
		if(patientHistory.getMedicalExpert() != null){
			if(!newMedicalExpertItemFullname.equals(patientHistory.getMedicalExpert().getFullname())){
				addNewMedicalExpert();
			}
		} else {
			if(newMedicalExpertItemFullname != null){
				addNewMedicalExpert();
			}
		}
		
		if(patientHistory.getDoctor() != null){
			if(!newDoctorItemFullname.equals(patientHistory.getDoctor().getFullname())){
				addNewDoctor();
			}
		} else {
			if(newDoctorItemFullname != null){
				addNewDoctor();
			}
		}
		
		if(patientHistory.getMasterDoctor() != null){
			if(!newMasterDoctorItemFullname.equals(patientHistory.getMasterDoctor().getFullname())){
				addNewMasterDoctor();
			}
		} else {
			if(newMasterDoctorItemFullname != null){
				addNewMasterDoctor();
			}
		}
		
		DataSourceLoader.getInstance().mergeRecord(patientHistory);
		
		PatientHistory phItemToSend = (PatientHistory)DataSourceLoader.getInstance().getRecord(PatientHistory.class, patientHistory.getPatientHistoriesId());
		
		paramsToRefresh = new HashMap<String, Object>();
		paramsToRefresh.put("attPerson", phItemToSend.getAttperson());
		BindUtils.postGlobalCommand(null, null, "refreshAttPersonTilePanel", paramsToRefresh);
		
		paramsToRefresh = new HashMap<String, Object>();
		paramsToRefresh.put("nurse", phItemToSend.getNurse());		
		BindUtils.postGlobalCommand(null, null, "refreshNurseTilePanel", paramsToRefresh);
		
		paramsToRefresh = new HashMap<String, Object>();
		paramsToRefresh.put("medicalExpert", phItemToSend.getMedicalExpert());		
		BindUtils.postGlobalCommand(null, null, "refreshMedicalExpertTilePanel", paramsToRefresh);
		
		paramsToRefresh = new HashMap<String, Object>();
		paramsToRefresh.put("doctor", phItemToSend.getDoctor());		
		BindUtils.postGlobalCommand(null, null, "refreshDoctorTilePanel", paramsToRefresh);
		
		paramsToRefresh = new HashMap<String, Object>();
		paramsToRefresh.put("doctor", phItemToSend.getMasterDoctor());		
		BindUtils.postGlobalCommand(null, null, "refreshMasterDoctorTilePanel", paramsToRefresh);
		
		params.put("patientHistory", phItemToSend);		
		BindUtils.postGlobalCommand(null, null, "refreshPatientHistory", params);
		BindUtils.postGlobalCommand(null, null, "refreshPatientHistoryPage", params);
		
		Clients.showNotification("История пациента отредактирована!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);	
		win.detach();
	}
	
	@NotifyChange("patientHistory")
	public void addNewAttPerson(){
    	if(allAttPersonsList.contains(attPersonItem)){
    		patientHistory.setAttperson(attPersonItem);			
    	} else {
    		createNewAttPerson();        		    	
    	}		
	}

	@NotifyChange("patientHistory")
    public void createNewAttPerson(){    	
    	String[] fullname = newAttPersonItemFullname.split(" ");   	
    	attPersonItem = new AttendantPerson();    	
    	attPersonItem.setLastname(fullname[0]);
    	if(fullname.length > 1){
    		attPersonItem.setFirstname(fullname[1]);
    	}
    	if(fullname.length > 2){
    		attPersonItem.setMiddlename(fullname[2]);
    	}
    	patientHistory.setAttperson(attPersonItem);		
    }
	
	@NotifyChange("patientHistory")
	public void addNewNurse(){
    	if(allNursesList.contains(nurseItem)){
    		patientHistory.setNurse(nurseItem);			
    	} else {
    		createNewNurse();        		    	
    	}		
	}

	@NotifyChange("patientHistory")
    public void createNewNurse(){    	
    	String[] fullname = newNurseItemFullname.split(" ");   	
    	nurseItem = new Nurse();    	
    	nurseItem.setLastname(fullname[0]);
    	if(fullname.length > 1){
    		nurseItem.setFirstname(fullname[1]);
    	}
    	if(fullname.length > 2){
    		nurseItem.setMiddlename(fullname[2]);
    	}
    	patientHistory.setNurse(nurseItem);		
    }
	
	@NotifyChange("patientHistory")
	public void addNewMedicalExpert(){
    	if(allMedicalExpertsList.contains(medicalExpertItem)){
    		patientHistory.setMedicalExpert(medicalExpertItem);    		
    	} else {
    		createNewMedicalExpert();
    	}		
	}
	
	@NotifyChange("patientHistory")
    public void createNewMedicalExpert(){    	
    	String[] fullname = newMedicalExpertItemFullname.split(" ");   	
    	medicalExpertItem = new MedicalExpert(); 	
    	medicalExpertItem.setLastname(fullname[0]);
    	if(fullname.length > 1){
    		medicalExpertItem.setFirstname(fullname[1]);
    	}
    	if(fullname.length > 2){
    		medicalExpertItem.setMiddlename(fullname[2]);
    	}
    	patientHistory.setMedicalExpert(medicalExpertItem);
    }
	
	@NotifyChange("patientHistory")
	public void addNewDoctor(){
    	if(allDoctorsList.contains(doctorItem)){
    		patientHistory.setDoctor(doctorItem);    		
    	} else {
    		createNewDoctor();
    	}		
	}
	
	@NotifyChange("patientHistory")
    public void createNewDoctor(){    	
    	String[] fullname = newDoctorItemFullname.split(" ");   	
    	doctorItem = new Doctor(); 	
    	doctorItem.setLastname(fullname[0]);
    	if(fullname.length > 1){
    		doctorItem.setFirstname(fullname[1]);
    	}
    	if(fullname.length > 2){
    		doctorItem.setMiddlename(fullname[2]);
    	}
    	patientHistory.setDoctor(doctorItem);
    }
	
	@NotifyChange("patientHistory")
	public void addNewMasterDoctor(){
    	if(allDoctorsList.contains(masterDoctorItem)){
    		patientHistory.setMasterDoctor(masterDoctorItem);    		
    	} else {
    		createNewMasterDoctor();
    	}		
	}
	
	@NotifyChange("patientHistory")
    public void createNewMasterDoctor(){    	
    	String[] fullname = newMasterDoctorItemFullname.split(" ");   	
    	masterDoctorItem = new Doctor(); 	
    	masterDoctorItem.setLastname(fullname[0]);
    	if(fullname.length > 1){
    		masterDoctorItem.setFirstname(fullname[1]);
    	}
    	if(fullname.length > 2){
    		masterDoctorItem.setMiddlename(fullname[2]);
    	}
    	patientHistory.setMasterDoctor(masterDoctorItem);
    }
	
	@Command
	@NotifyChange({"patientHistory", "newAttPersonItemFullname", "attPersonItem"})
	public void removeAttPerson() {
		attPersonItem = null;
		newAttPersonItemFullname = null;
		patientHistory.setAttperson(null);
	}
	
	@Command
	@NotifyChange({"patientHistory", "newNurseItemFullname", "nurseItem"})
	public void removeNurse() {
		nurseItem = null;
		newNurseItemFullname = null;
		patientHistory.setNurse(null);
	}
	
	@Command
	@NotifyChange({"patientHistory", "newMedicalExpertItemFullname", "medicalExpertItem"})
	public void removeMedicalExpert() {
		medicalExpertItem = null;
		newMedicalExpertItemFullname = null;
		patientHistory.setMedicalExpert(null);
	}
	
	@Command
	@NotifyChange({"patientHistory", "newDoctorItemFullname", "doctorItem"})
	public void removeDoctor() {
		doctorItem = null;
		newDoctorItemFullname = null;
		patientHistory.setDoctor(null);
	}
	
	@Command
	@NotifyChange({"patientHistory", "newMasterDoctorItemFullname", "masterDoctorItem"})
	public void removeMasterDoctor() {
		masterDoctorItem = null;
		newMasterDoctorItemFullname = null;
		patientHistory.setMasterDoctor(null);
	}
	
	@Command
	@NotifyChange("patientHistory")
	public void removeCurator() {
		patientHistory.setCurator(null);
	}
	
	@Command
	@NotifyChange("patientHistory")
	public void removeLawyer() {
		patientHistory.setLawyer(null);
	}
	
	@Command
	public void closeThis() {
		win.detach();
	}

}
