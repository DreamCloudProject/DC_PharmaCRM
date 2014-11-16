package ru.dreamcloud.alexion.zk.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ru.dreamcloud.util.jpa.DataSourceLoader;

public class SearchService {
	
	/**************************************
	 * Property allEntities
	 ***************************************/
	private List<Object> allEntities;

	public SearchService() {
		allEntities = DataSourceLoader.getInstance().getAllEntities(null);
	}

    private boolean isContainsInFieldsValues(Object object, String term) throws IllegalArgumentException, IllegalAccessException{
    	Class c = object.getClass();
		Field[] entityFields = c.getDeclaredFields();
		boolean result = false;
		for (Field field : entityFields) {
			Class fieldType = field.getType();
			field.setAccessible(true);
			if (fieldType.getName().equals(String.class.getName())) {										
				if((field.get(object) != null) 
					&& (field.get(object).toString().toLowerCase().indexOf(term.toLowerCase()) != -1)){
					result = true;					
				}
			}
			if((!result) && (field.get(object) != null) && (isDataSourceEntity(field.get(object)))){
				result = isContainsInFieldsValues(field.get(object),term);
			}
			if(result){
				break;
			}
		}
    	return result;
    }
    
    private boolean isDataSourceEntity(Object obj){
    	boolean result = false;
    	for (Object entity : allEntities) {
    		if((entity != null) && (obj != null)){
    			if(entity.getClass().getName().equals(obj.getClass().getName())){
    				result = true;
    				break;
    			}
    		}
		}
    	return result;
    }
    
    public List<Object> retrieveResultsList(List<?> searchCollection, String term) {    	
    	List<Object> resultsList = new ArrayList<Object>();
    	boolean isContains;
    	try {
	    	for (Object obj : searchCollection) {
	    		isContains = false;
	        	Class c = obj.getClass();
	    		Field[] phFields = c.getDeclaredFields();	    		
	    		for (Field field : phFields) {
	    			field.setAccessible(true);
	    			if(isDataSourceEntity(field.get(obj))){
	    				if((!isContains) && (field.get(obj) != null)){	    					
	    	    			isContains = isContainsInFieldsValues(field.get(obj), term);
	    	    		}
	    			}
    				if((!isContains) && (field.get(obj) != null) && (field.getType().getName().equals(List.class.getName()))){
    					List<Object> listItems = new ArrayList((List)field.get(obj));
						for (Object item : listItems) {
							if(!isContains) {
								isContains = isContainsInFieldsValues(item, term);
							}
						}
					}    				
    				if(isContains){
    					break;
    				}
	    		}
	    		
	    		if(isContains){
	    			resultsList.add(obj);
	    		}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return resultsList;
    }
}
