package ru.dreamcloud.pharmatracker.zk.viewmodels.widgets;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Init;

import ru.dreamcloud.pharmatracker.model.Project;
import ru.dreamcloud.util.jpa.DataSourceLoader;

public class ProjectTabsVM {
	
	/**************************************
	 * Property projectsList
	 ***************************************/
	private Object[] projectsList;

	public Object[] getProjectsList() {
		return projectsList;
	}
	
	

	/**************************************
	 * Methods
	 ***************************************/
	@Init
	public void init() {
		List<Project> projects = new ArrayList(DataSourceLoader.getInstance().fetchRecords("Project", null));
		projectsList = projects.toArray();		
		
	}

}
