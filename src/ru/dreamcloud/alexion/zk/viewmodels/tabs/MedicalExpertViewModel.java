package ru.dreamcloud.alexion.zk.viewmodels.tabs;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.MedicalExpert;
import ru.dreamcloud.alexion.model.PatientHistory;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class MedicalExpertViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private MedicalExpert selected;

	public MedicalExpert getSelected() {
		return selected;
	}

	public void setSelected(MedicalExpert selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property attPersonsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<MedicalExpert> medicalExpertsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("MedicalExpert", null));

	public ArrayList<MedicalExpert> getMedicalExpertsList() {
		return medicalExpertsList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!medicalExpertsList.isEmpty()) {
			selected = medicalExpertsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("medicalExpertsList")
    public void addMedicalExpertItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("medicalExpertItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/medicalexpertwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("medicalExpertsList")
    public void editMedicalExpertItem() {
    	if(!medicalExpertsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("medicalExpertItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/medicalexpertwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("medicalExpertsList")
    public void removeMedicalExpertItem() {
    	if(!medicalExpertsList.isEmpty()) {
	    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event event) throws Exception {
					if (Messagebox.ON_YES.equals(event.getName())){
						final HashMap<String, Object> params = new HashMap<String, Object>();
						params.put("searchTerm", new String());
						for (PatientHistory ph : selected.getPatientHistories()) {
							ph.setMedicalExpert(null);
							DataSourceLoader.getInstance().updateRecord(ph);
						}
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
    @NotifyChange("medicalExpertsList")
    public void search(@BindingParam("searchTerm") String term) {
    	medicalExpertsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("MedicalExpert", "where e.firstname LIKE '%"+term+"%' or e.middlename LIKE '%"+term+"%' or e.lastname LIKE '%"+term+"%'"));
    }

}
