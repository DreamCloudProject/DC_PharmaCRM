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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.authentication.CommonUserInfo;
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
	
	/**************************************
	 * Property allLawyersList
	 ***************************************/
	
	private List<CommonUserInfo> allLawyersList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", null));
	
	public List<CommonUserInfo> getAllLawyersList() {
		return allLawyersList;
	}
	
	/**************************************
	 * Property allCuratorsList
	 ***************************************/
	
	private List<CommonUserInfo> allCuratorsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("CommonUserInfo", null));
	
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
		PatientHistory ph = (PatientHistory)DataSourceLoader.getInstance().getRecord(PatientHistory.class, currentItem.getPatientHistoriesId());
		patientHistory = ph;
	}
	
	@Command
	@NotifyChange("patientHistory")
	public void save() {
		final HashMap<String, Object> params = new HashMap<String, Object>();				
		DataSourceLoader.getInstance().mergeRecord(patientHistory);
		params.put("patientHistory", patientHistory);
		BindUtils.postGlobalCommand(null, null, "refreshPatientHistory", params);
		Clients.showNotification("История пациента отредактирована!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);	
		win.detach();
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
