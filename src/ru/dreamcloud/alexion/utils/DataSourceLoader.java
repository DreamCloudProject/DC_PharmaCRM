package ru.dreamcloud.alexion.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

public class DataSourceLoader {
	private static final String PERSISTENCE_UNIT_NAME = "DCPharmaCRMPU";
	private static EntityManagerFactoryImpl factory = (EntityManagerFactoryImpl)Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManagerImpl em = (EntityManagerImpl)factory.createEntityManager();
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
    private static DataSourceLoader instance;
    
    public static synchronized DataSourceLoader getInstance() {
        if (instance == null) {
            instance = new DataSourceLoader();
        }
        return instance;
    }
    
    private DataSourceLoader(){    	
    }

	public List<?> fetchRecords(String entity, String where) {
		where = where != null ? " where " + where : "";
		Query q = em.createQuery("select e from " + entity + " e " + where);
		List<?> entityList = q.getResultList();
		return entityList;

	}
	
	public Object getRecord(Class<?> entityClass, Object pk) {
		Object entityObject = em.find(entityClass, pk);
		return entityObject;
	}

	public void addRecord(Object entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.refresh(entity);
	}

	public void updateRecord(Object entity, Object pk) {
		Class<?> entityClass = entity.getClass();
		Object entityObject = em.find(entityClass, pk);
		try {
			for (Field f : entityClass.getDeclaredFields()) {
				if (!Modifier.isStatic(f.getModifiers())) {
					f.setAccessible(true);
					//System.out.println(f.getName() + ": " + f.get(entityObject) + " = " + f.get(entity));
					if (f.get(entity) != null) {
						f.set(entityObject, f.get(entity));
					}	
				}			
			}
			em.getTransaction().begin();
			em.persist(entityObject);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateRecord(Object entity) {
		Class<?> entityClass = entity.getClass();
		Object pk = factory.getPersistenceUnitUtil().getIdentifier(entity);
		Object entityObject = em.find(entityClass, pk);
		try {
			for (Field f : entityClass.getDeclaredFields()) {
				if (!Modifier.isStatic(f.getModifiers())) {
					f.setAccessible(true);
					//System.out.println(f.getName() + ": " + f.get(entityObject) + " = " + f.get(entity));
					if (f.get(entity) != null) {
						f.set(entityObject, f.get(entity));
					}	
				}			
			}
			em.getTransaction().begin();
			em.persist(entityObject);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeRecord(Object entity, Object pk) {
		Object entityObject = em.find(entity.getClass(), pk);
		em.getTransaction().begin();
		em.remove(entityObject);
		em.getTransaction().commit();
	}
	
	public void removeRecord(Object entity) {
		Object pk = factory.getPersistenceUnitUtil().getIdentifier(entity);
		System.out.println(pk.toString());
		Object entityObject = em.find(entity.getClass(), pk);
		em.getTransaction().begin();
		em.remove(entityObject);
		em.getTransaction().commit();
	}
	
	public List<Object> getAllEntities(String where) {
		List<Object> allEntities = new ArrayList<Object>();
		for (EntityType<?> entity : em.getMetamodel().getEntities()) {
		    final String className = entity.getName();
		    allEntities.addAll(fetchRecords(className, where));		    		    
		}
		return allEntities;
	}

}
