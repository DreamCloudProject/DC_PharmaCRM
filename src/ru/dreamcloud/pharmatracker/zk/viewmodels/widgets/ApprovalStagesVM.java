package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.pharmatracker.model.PatientHistory;
import ru.dreamcloud.pharmatracker.model.PatientHistoryStatus;
import ru.dreamcloud.pharmatracker.model.Resolution;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class ApprovalStagesVM {
	
	private static int MAX_BOOTSTRAP_COLUMNS = 12;
	
	/**************************************
	  Property stageType	 
	***************************************/
	private String stageType;	
	
	public String getStageType() {
		return stageType;
	}

	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

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

	/**************************************
	 * Property approvalStages
	 ***************************************/
	private List<Resolution> approvalStages;

	public List<Resolution> getApprovalStages() {
		return approvalStages;
	}

	public void setApprovalStages(List<Resolution> approvalStages) {
		this.approvalStages = approvalStages;
	}
	
	/**************************************
	 * Property noStages
	 ***************************************/
	private List<PatientHistory> noStages;	

	public List<PatientHistory> getNoStages() {
		return noStages;
	}

	public void setNoStages(List<PatientHistory> noStages) {
		this.noStages = noStages;
	}

	/**************************************
	 * Property xsClassType
	 ***************************************/
	private String xsClassType;
	
	public String getXsClassType() {
		return xsClassType;
	}

	public void setXsClassType(String xsClassType) {
		this.xsClassType = xsClassType;
	}

	/**************************************
	 * Property mdClassType
	 ***************************************/
	private String mdClassType;

	public String getMdClassType() {
		return mdClassType;
	}

	public void setMdClassType(String mdClassType) {
		this.mdClassType = mdClassType;
	}
	
	/**************************************
	 * Property lgClassType
	 ***************************************/
	private String lgClassType;	

	public String getLgClassType() {
		return lgClassType;
	}

	public void setLgClassType(String lgClassType) {
		this.lgClassType = lgClassType;
	}
	
	/**************************************
	 * Property rows
	 ***************************************/
	private Integer rows;	

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init(@ExecutionArgParam("type") String type) {
		stageType = type;
		approvalStages = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Resolution", "where e.project.title LIKE '"+stageType+"'"));
		noStages = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.resolution is null and e.project.title LIKE '"+stageType+"' and e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".OPEN"));
		int res = (((Double)Math.ceil(approvalStages.size())).intValue() != 0) ? ((Double)Math.ceil(approvalStages.size())).intValue() : 1;
		lgClassType = (MAX_BOOTSTRAP_COLUMNS/res) >= 2 ? String.valueOf(MAX_BOOTSTRAP_COLUMNS/res) : "12";		
		mdClassType = String.valueOf(MAX_BOOTSTRAP_COLUMNS/4);
		xsClassType = String.valueOf(MAX_BOOTSTRAP_COLUMNS/2);
				
	}
	
	@GlobalCommand
	@Command
	@NotifyChange("approvalStages")
	public List<PatientHistory> retrievePatientHistories(@BindingParam("resolutionId")Integer resolutionId) {
		return new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.resolution.resolutionId="+resolutionId+" and e.project.title LIKE '"+stageType+"' and e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".OPEN"));
    }
	
	@Command
	@NotifyChange({"noStages","approvalStages"})
	public void changeResolution(@BindingParam("currentResolutionItem")Resolution resItem,
								 @BindingParam("targetComponent")Component phDragged) {		
		String[] phId = phDragged.getId().split("_");
		Object pk = Integer.valueOf(phId[1]);
		currentPatientHistory = (PatientHistory)DataSourceLoader.getInstance().getRecord(PatientHistory.class, pk);
		/*if(resItem == null){
			if(!noStages.contains(currentPatientHistory)){
				noStages.add(currentPatientHistory);
			}
		} else {
			if(noStages.contains(currentPatientHistory)){
				noStages.remove(currentPatientHistory);
			}
		}*/
		currentPatientHistory.setResolution(resItem);
		DataSourceLoader.getInstance().mergeRecord(currentPatientHistory);
		noStages = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.resolution is null and e.project.title LIKE '"+stageType+"' and e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".OPEN"));
	}
	
	@Command
	public void viewPatientHistory(@BindingParam("phItem")PatientHistory phItem) {
    	Sessions.getCurrent().setAttribute("currentPatientHistory", phItem);
    	Executions.sendRedirect(Labels.getLabel("pages.detail.patienthistory.URL"));
	}
}
