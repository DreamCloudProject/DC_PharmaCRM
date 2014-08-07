package ru.dreamcloud.alexion.zk.viewmodels.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import ru.dreamcloud.alexion.model.Address;
import ru.dreamcloud.alexion.model.District;
import ru.dreamcloud.alexion.model.Region;
import ru.dreamcloud.alexion.utils.DataSourceLoader;
import ru.dreamcloud.alexion.zk.integration.FiasBasicDataLoader;

public class DistrictViewModel {
	
	/**************************************
	 * Property fiasBasicDataLoader
	 ***************************************/
	
	private FiasBasicDataLoader fiasBasicDataLoader;

	/**************************************
	 * Property selected
	 ***************************************/
	private District selected;

	public District getSelected() {
		return selected;
	}

	public void setSelected(District selected) {
		this.selected = selected;
	}

	/**************************************
	 * Property districtsList
	 ***************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<District> districtsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("District", null));

	public ArrayList<District> getDistrictsList() {
		return districtsList;
	}
	
	/**************************************
	 * Property regionsList
	 ***************************************/
	private List<Region> regionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Region", null));
	
	public List<Region> getRegionsList() {
		return regionsList;
	}

	public void setRegionsList(List<Region> regionsList) {
		this.regionsList = regionsList;
	}


	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		if (!districtsList.isEmpty()) {
			selected = districtsList.get(0);
		}
	}
	
    @Command
    @NotifyChange("districtsList")
    public void addDistrictItem() {
    	final HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("districtItem", null);
    	params.put("actionType", "NEW");
    	Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/districtwindow.zul", null, params);
        window.doModal();
    }
    
    @Command
    @NotifyChange("districtsList")
    public void editDistrictItem() {
    	if(!districtsList.isEmpty()) {
	    	final HashMap<String, Object> params = new HashMap<String, Object>();
	    	params.put("districtItem", selected);
	    	params.put("actionType", "EDIT");
	        Window window = (Window)Executions.createComponents("/WEB-INF/zk/windows/districtwindow.zul", null, params);
	        window.doModal();
    	}
    }
    
    @Command
    @NotifyChange("districtsList")
    public void removeDistrictItem() {
    	if(!districtsList.isEmpty()) {
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
    
	@Command
	@NotifyChange("regionsList")
	public void synchronizeRegionsList() {
		fiasBasicDataLoader = new FiasBasicDataLoader();
		List<String> regions = fiasBasicDataLoader.getRussianRegions();				
		for (String regTitle : regions) {
			if(!isContainsRegion(regTitle)){
				Region newRegion = new Region();
				newRegion.setTitle(regTitle);
				newRegion.setDescription(regTitle);
				//System.out.println(region.getTitle() + ": " + regTitle);
				DataSourceLoader.getInstance().addRecord(newRegion);
			}
		}
		regionsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Region", null));
		Clients.showNotification("Список регионов синхронизирован!", Clients.NOTIFICATION_TYPE_INFO, null, "top_center" ,4100);
	}
	
	private boolean isContainsRegion(String regionTitle) {		
		for (Region region : regionsList) {
			if(region.getTitle().equals(regionTitle)){
				return true;
			}
		}
		return false;
	}
    
    @GlobalCommand
    @Command
    @NotifyChange("districtsList")
    public void search(@BindingParam("searchTerm") String term) {
    	districtsList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("District", "e.title LIKE '%"+term+"%' or e.description LIKE '%"+term+"%'"));
    }

}
