package ru.dreamcloud.alexion.zk.viewmodels.tabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.alexion.model.PatientHistoryStatus;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class PatientHistoryClosedViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private PatientHistory selected;

	public PatientHistory getSelected() {
		return selected;
	}

	public void setSelected(PatientHistory selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property patientHistoriesList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<PatientHistory> patientHistoriesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".CLOSED"));

	public ArrayList<PatientHistory> getPatientHistoriesList() {
		return patientHistoriesList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!patientHistoriesList.isEmpty()) {
			selected = patientHistoriesList.get(0);
		}
	}
	
    @Command
    @NotifyChange("patientHistoriesList")
    public void openPatientHistoryItem() {
    	if(!patientHistoriesList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите открыть историю пациента?", "Открыть историю пациента", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						params.put("searchTerm", new String());
						selected.setPatientHistoryStatus(PatientHistoryStatus.OPEN);
						DataSourceLoader.getInstance().mergeRecord(selected);
						BindUtils.postGlobalCommand(null, null, "search", params);
						Clients.showNotification("История пациента открыта!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
					}
					
				}
			});
    	}
    }
	
    
    @Command
    @NotifyChange("patientHistoriesList")
    public void removePatientHistoryItem() {
    	if(!patientHistoriesList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						params.put("searchTerm", new String());						
						DataSourceLoader.getInstance().removeRecord(selected);
						BindUtils.postGlobalCommand(null, null, "search", params);
						Clients.showNotification("Запись успешно удалена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
					}
					
				}
			});
    	}
    }
    
    @GlobalCommand
    @Command
    @NotifyChange("patientHistoriesList")
    public void search(@BindingParam("searchTerm") String term) {
    	patientHistoriesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("PatientHistory", "where (e.patient.lastname LIKE '%"+term+"%' and e.patient.firstname LIKE '%"+term+"%' and e.patient.middlename LIKE '%"+term+"%') and e.patientHistoryStatus="+PatientHistoryStatus.class.getName()+".CLOSED"));
    }

}
