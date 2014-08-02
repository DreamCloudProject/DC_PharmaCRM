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

import ru.dreamcloud.alexion.model.Diagnosis;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class DiagnosisViewModel {

	/**************************************
	 * Property selected
	 ***************************************/
	private Diagnosis selected;

	public Diagnosis getSelected() {
		return selected;
	}

	public void setSelected(Diagnosis selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property diagnosisList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<Diagnosis> diagnosisList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Diagnosis", null));

	public ArrayList<Diagnosis> getDiagnosisList() {
		return diagnosisList;
	}

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!diagnosisList.isEmpty()) {
			selected = diagnosisList.get(0);
		}
	}
	
    @Command
    @NotifyChange("diagnosisList")
    public void addDiagnosisItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("diagnosisItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/diagnosiswindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("diagnosisList")
    public void editDiagnosisItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("diagnosisItem", selected);
    	params.put("actionType", "EDIT");
        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/diagnosiswindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("diagnosisList")
    public void removeDiagnosisItem() {
    	Messagebox.show("Вы уверены что хотите удалить эту запись?", "Удаление записи", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {			
			@Override
			public void onEvent(Event event) throws Exception {
				if (Messagebox.ON_YES.equals(event.getName())){
					final HashMap<String, Object> params = new HashMap<String, Object>();
					params.put("searchTerm", new String());
					DataSourceLoader.getInstance().removeRecord(selected, selected.getDiagnosisId());
					BindUtils.postGlobalCommand(null, null, "search", params);
					Clients.showNotification("Запись успешно удалена!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
				}
				
			}
		});
    }
    
    @GlobalCommand
    @Command
    @NotifyChange("diagnosisList")
    public void search(@BindingParam("searchTerm") String term) {
    	diagnosisList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Diagnosis", "e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
