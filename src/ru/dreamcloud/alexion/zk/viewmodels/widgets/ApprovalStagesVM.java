package ru.dreamcloud.alexion.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.model.Resolution;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class ApprovalStagesVM {
	
	private static int MAX_BOOTSTRAP_COLUMNS = 12;

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
	 * Property attachedPatientHistory
	 ***************************************/
	
	private PatientHistory attachedPatientHistory;	
	
	public PatientHistory getAttachedPatientHistory() {
		return attachedPatientHistory;
	}

	public void setAttachedPatientHistory(PatientHistory attachedPatientHistory) {
		this.attachedPatientHistory = attachedPatientHistory;
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
		lgClassType = (MAX_BOOTSTRAP_COLUMNS/res) > 1 ? String.valueOf(MAX_BOOTSTRAP_COLUMNS/res) : "2";		
		mdClassType = String.valueOf(MAX_BOOTSTRAP_COLUMNS/4);
		xsClassType = String.valueOf(MAX_BOOTSTRAP_COLUMNS/2);
				
	}
	
	public List<PatientHistory> retrievePatientHistories(Resolution resolutionItem) {		
        return new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "e.resolution.resolutionId="+resolutionItem.getResolutionId()));   
    }
	
	@Command
	public void attachPatientHistory(@BindingParam("phItem")PatientHistory phItem) {
	    attachedPatientHistory = phItem;
	}
	
	@Command
	@NotifyChange("approvalStages")
	public void changeResolution(@BindingParam("currentResolutionItem")Resolution resItem) {
		attachedPatientHistory.setResolution(resItem);
		DataSourceLoader.getInstance().updateRecord(attachedPatientHistory);
	}
	
	@Command
	public void viewPatientHistory(@BindingParam("phItem")PatientHistory phItem) {
    	Sessions.getCurrent().setAttribute("currentPatientHistory", phItem);
    	Executions.sendRedirect("/detail/patienthistory");
	}
}
