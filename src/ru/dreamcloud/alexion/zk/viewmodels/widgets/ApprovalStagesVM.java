package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.model.Resolution;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class ApprovalStagesVM {
	
	private static int MAX_BOOTSTRAP_COLUMNS = 12;
	
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
	private List<Resolution> approvalStages = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Resolution", null));

	public List<Resolution> getApprovalStages() {
		return approvalStages;
	}

	public void setApprovalStages(List<Resolution> approvalStages) {
		this.approvalStages = approvalStages;
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
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		int res = ((Double)Math.ceil(approvalStages.size())).intValue();		
		lgClassType = (MAX_BOOTSTRAP_COLUMNS/res) >= 2 ? String.valueOf(MAX_BOOTSTRAP_COLUMNS/res) : "12";		
		mdClassType = String.valueOf(MAX_BOOTSTRAP_COLUMNS/4);
		xsClassType = String.valueOf(MAX_BOOTSTRAP_COLUMNS/2);
				
	}
	
	@GlobalCommand
	@Command
	@NotifyChange("approvalStages")
	public List<PatientHistory> retrievePatientHistories(@BindingParam("resolutionItem")Resolution resolutionItem) {		
        return new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.resolution.resolutionId="+resolutionItem.getResolutionId()));   
    }
	
	@Command
	@NotifyChange("approvalStages")
	public void changeResolution(@BindingParam("currentResolutionItem")Resolution resItem,
								 @BindingParam("targetComponent")Component phDragged) {
		Object pk = Integer.valueOf(phDragged.getId());
		currentPatientHistory = (PatientHistory)DataSourceLoader.getInstance().getRecord(PatientHistory.class, pk);
		currentPatientHistory.setResolution(resItem);
		DataSourceLoader.getInstance().updateRecord(currentPatientHistory);
	}
	
	@Command
	public void viewPatientHistory(@BindingParam("phItem")PatientHistory phItem) {
    	Sessions.getCurrent().setAttribute("currentPatientHistory", phItem);
    	Executions.sendRedirect(Labels.getLabel("pages.detail.patienthistory.URL"));
	}
}
