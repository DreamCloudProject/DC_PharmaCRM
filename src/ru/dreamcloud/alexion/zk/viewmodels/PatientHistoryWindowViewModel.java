package ru.dreamcloud.alexion.zk.viewmodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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

import ru.dreamcloud.alexion.model.AttendantPerson;
import ru.dreamcloud.alexion.model.Diagnosis;
import ru.dreamcloud.alexion.model.MedicalExpert;
import ru.dreamcloud.alexion.model.Nurse;
import ru.dreamcloud.alexion.model.Patient;
import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.model.PatientHistoryStatus;
import ru.dreamcloud.alexion.model.Project;
import ru.dreamcloud.alexion.model.Resolution;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class PatientHistoryWindowViewModel {

	@Wire("#PatientHistoryWindow")
	private Window win;
	
	/**************************************
	 * Property currentPatientHistory
	 ***************************************/
	private PatientHistory currentPatientHistory;	
	
	public PatientHistory getCurrentPatientHistory() {
		return currentPatientHistory;
	}

	public void setCurrentPatientHistory(PatientHistory currentPatientHistory) {
		this.currentPatientHistory = currentPatientHistory;
	}

	/*-------------------------------------------------------------------------------------------------------------------------*/
	/**************************************
	 * Property allResolutionsList
	 ***************************************/
	
	private List<Resolution> allResolutionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Resolution", null));
	
	public List<Resolution> getAllResolutionsList() {
		return allResolutionsList;
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------*/
	/**************************************
	 * Property patientItem
	 ***************************************/
	private Patient patientItem;	
	
	public Patient getPatientItem() {
		return patientItem;
	}

	public void setPatientItem(Patient patientItem) {
		this.patientItem = patientItem;
	}

	/**************************************
	 * Property newPatientFullname
	 ***************************************/
	private String newPatientFullname;	
	
	public String getNewPatientFullname() {
		return newPatientFullname;
	}

	public void setNewPatientFullname(String newPatientFullname) {
		this.newPatientFullname = newPatientFullname;
	}
	
	/**************************************
	 * Property currentPatientsList
	 ***************************************/
	
	private List<Patient> currentPatientsList;
	
	public List<Patient> getCurrentPatientsList() {
		return currentPatientsList;
	}

	/**************************************
	 * Property allPatientsList
	 ***************************************/
	
	private List<Patient> allPatientsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Patient", null));
	
	public List<Patient> getAllPatientsList() {
		return allPatientsList;
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
	 * Property currentAttPersonsList
	 ***************************************/
	
	private List<AttendantPerson> currentAttPersonsList;	

	public List<AttendantPerson> getCurrentAttPersonsList() {
		return currentAttPersonsList;
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
	 * Property currentMedicalExpertsList
	 ***************************************/
	
	private List<MedicalExpert> currentMedicalExpertsList;		

	public List<MedicalExpert> getCurrentMedicalExpertsList() {
		return currentMedicalExpertsList;
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
	 * Property currentNursesList
	 ***************************************/
	private List<Nurse> currentNursesList;	

	public List<Nurse> getCurrentNursesList() {
		return currentNursesList;
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
	 * Property projectItem
	 ***************************************/
	private Project projectItem;	

	public Project getProjectItem() {
		return projectItem;
	}

	public void setProjectItem(Project projectItem) {
		this.projectItem = projectItem;
	}

	/**************************************
	 * Property allProjectsList
	 ***************************************/
	
	private List<Project> allProjectsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Project", null));
	
	public List<Project> getAllProjectsList() {
		return allProjectsList;
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------*/

	private PatientHistoryStatus patientHistoryStatus;
	
	public PatientHistoryStatus getPatientHistoryStatus() {
		return patientHistoryStatus;
	}

	public void setPatientHistoryStatus(PatientHistoryStatus patientHistoryStatus) {
		this.patientHistoryStatus = patientHistoryStatus;
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------*/
	/**************************************
	 * Property itemsToUnlink
	 ***************************************/
	
	private List<Object> itemsToUnlink;
	
	/*-------------------------------------------------------------------------------------------------------------------------*/
	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		currentPatientHistory = new PatientHistory();
		patientItem = new Patient();		
		itemsToUnlink = new ArrayList<Object>();
	}
	
	@Command
	@NotifyChange("currentPatientHistory")
	public void save() {
		if((currentPatientHistory.getAttperson() != null)
			||(currentPatientHistory.getMedicalExpert() != null)
			||(currentPatientHistory.getNurse() != null)
			||(currentPatientHistory.getPatient() != null)){
			final HashMap<String, Object> params = new HashMap<String, Object>();				
			currentPatientHistory.setPatientHistoryStatus(PatientHistoryStatus.OPEN);
			params.put("resolutionItem", currentPatientHistory.getResolution());				
			DataSourceLoader.getInstance().mergeRecord(currentPatientHistory);		
			Clients.showNotification("Запись успешно добавлена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);		
			BindUtils.postGlobalCommand(null, null, "retrievePatientHistories", params);
			win.detach();
		} else {
			Clients.showNotification("Необходимо заполнить все обызательные поля!", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center" ,4100);			
		}
	}
	
	@Command
	@NotifyChange({"currentPatientHistory","currentPatientsList","allPatientsList","patientItem"})
	public void addNewPatient(){
    	if(allPatientsList.contains(patientItem)){
    		currentPatientHistory.setPatient(patientItem);
    		currentPatientsList = new ArrayList<Patient>();
    		currentPatientsList.add(patientItem);
			Clients.showNotification("Пациент с именем "+patientItem.getLastname()+" "+patientItem.getFirstname()+" "+patientItem.getMiddlename()+" добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    	} else {
	    	Messagebox.show("Пациента с таким именем нет в базе данных. Хотите ли вы его добавить?", "Новый пациент", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override				
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						BindUtils.postGlobalCommand(null, null, "createNewPatient", null);											
					}
				}
	    	});	    	
    	}		
	}
	
    @GlobalCommand
    @Command
    @NotifyChange({"currentPatientHistory","currentPatientsList","allPatientsList","patientItem"})
    public void createNewPatient(){
    	String[] fullname = newPatientFullname.split(" ");   	
    	patientItem = new Patient();    	
    	patientItem.setLastname(fullname[0]);
    	if(fullname.length > 1){
    		patientItem.setFirstname(fullname[1]);
    	}
    	if(fullname.length > 2){
    		patientItem.setMiddlename(fullname[2]);
    	}
    	currentPatientHistory.setPatient(patientItem);
    	currentPatientsList = new ArrayList<Patient>();
		currentPatientsList.add(patientItem);		
		Clients.showNotification("Пациент с именем "+patientItem.getLastname()+" "+patientItem.getFirstname()+" "+patientItem.getMiddlename()+" добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);    	
    }
    
	@Command
	@NotifyChange({"currentPatientHistory","currentAttPersonsList","allAttPersonsList","attPersonItem"})
	public void addNewAttPerson(){
    	if(allAttPersonsList.contains(attPersonItem)){
    		currentPatientHistory.setAttperson(attPersonItem);    		
    		currentAttPersonsList = new ArrayList<AttendantPerson>();
    		currentAttPersonsList.add(attPersonItem);
			Clients.showNotification("Сопровождающий с именем "+attPersonItem.getLastname()+" "+attPersonItem.getFirstname()+" "+attPersonItem.getMiddlename()+" добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    	} else {
	    	Messagebox.show("Сопровождающего с таким именем нет в базе данных. Хотите ли вы его добавить?", "Новый сопровождающий", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override				
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						BindUtils.postGlobalCommand(null, null, "createNewAttPerson", null);											
					}
				}
	    	});	    	
    	}		
	}
	
    @GlobalCommand
    @Command
	@NotifyChange({"currentPatientHistory","currentAttPersonsList","allAttPersonsList","attPersonItem"})
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
    	currentPatientHistory.setAttperson(attPersonItem);
		currentAttPersonsList = new ArrayList<AttendantPerson>();
		currentAttPersonsList.add(attPersonItem);
		Clients.showNotification("Сопровождающий с именем "+attPersonItem.getLastname()+" "+attPersonItem.getFirstname()+" "+attPersonItem.getMiddlename()+" добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);    	
    }
    
	@Command
	@NotifyChange({"currentPatientHistory","currentNursesList","allNursesList","nurseItem"})
	public void addNewNurse(){
    	if(allNursesList.contains(nurseItem)){
    		currentPatientHistory.setNurse(nurseItem);	
    		currentNursesList = new ArrayList<Nurse>();
    		currentNursesList.add(nurseItem);
			Clients.showNotification("Мед. сестры с именем "+nurseItem.getLastname()+" "+nurseItem.getFirstname()+" "+nurseItem.getMiddlename()+" добавлена! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    	} else {
	    	Messagebox.show("Мед. сестры с таким именем нет в базе данных. Хотите ли вы добавить?", "Новая мед. сестра", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override				
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						BindUtils.postGlobalCommand(null, null, "createNewNurse", null);											
					}
				}
	    	});	    	
    	}		
	}
	
    @GlobalCommand
    @Command
	@NotifyChange({"currentPatientHistory","currentNursesList","allNursesList","nurseItem"})
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
    	currentPatientHistory.setNurse(nurseItem);
		currentNursesList = new ArrayList<Nurse>();
		currentNursesList.add(nurseItem);
		Clients.showNotification("Мед. сестра с именем "+nurseItem.getLastname()+" "+nurseItem.getFirstname()+" "+nurseItem.getMiddlename()+" добавлена! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);    	
    }
    
	@Command
	@NotifyChange({"currentPatientHistory","currentMedicalExpertsList","allMedicalExpertsList","medicalExpertItem"})
	public void addNewMedicalExpert(){
    	if(allMedicalExpertsList.contains(medicalExpertItem)){
    		currentPatientHistory.setMedicalExpert(medicalExpertItem);	
    		currentMedicalExpertsList = new ArrayList<MedicalExpert>();
    		currentMedicalExpertsList.add(medicalExpertItem);
			Clients.showNotification("Мед. эксперта с именем "+medicalExpertItem.getLastname()+" "+medicalExpertItem.getFirstname()+" "+medicalExpertItem.getMiddlename()+" добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
    	} else {
	    	Messagebox.show("Мед. эксперта с таким именем нет в базе данных. Хотите ли вы добавить?", "Новый мед. эксперт", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override				
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						BindUtils.postGlobalCommand(null, null, "createNewMedicalExpert", null);											
					}
				}
	    	});	    	
    	}		
	}
	
    @GlobalCommand
    @Command
	@NotifyChange({"currentPatientHistory","currentMedicalExpertsList","allMedicalExpertsList","medicalExpertItem"})
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
    	currentPatientHistory.setMedicalExpert(medicalExpertItem);
    	currentMedicalExpertsList = new ArrayList<MedicalExpert>();
    	currentMedicalExpertsList.add(medicalExpertItem);
    	Clients.showNotification("Мед. эксперта с именем "+medicalExpertItem.getLastname()+" "+medicalExpertItem.getFirstname()+" "+medicalExpertItem.getMiddlename()+" добавлен! Для сохранения изменений нажмите кнопку 'Сохранить'.", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);    	
    }
    
	@Command
	public void closeThis() {
		win.detach();
	}

}
