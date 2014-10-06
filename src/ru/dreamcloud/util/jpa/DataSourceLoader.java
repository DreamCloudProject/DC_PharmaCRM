package ru.dreamcloud.util.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

import org.eclipse.persistence.internal.jpa.EntityManagerFactoryImpl;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;

public class DataSourceLoader {
	private static final String PERSISTENCE_UNIT_NAME = "DCPharmaCRMPU";
	private static EntityManagerFactoryImpl factory = (EntityManagerFactoryImpl)Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	// private static EntityManagerImpl em = (EntityManagerImpl)factory.createEntityManager();
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private static DataSourceLoader instance;

	public static synchronized DataSourceLoader getInstance() {
		if (instance == null) {
			instance = new DataSourceLoader();
		}
		return instance;
	}

	private DataSourceLoader() {
	}

	public List<?> fetchRecords(String entity, String where) {
		where = where != null ? where : "";
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		List<?> entityList = null;
		try {
			Query q = em.createQuery("select e from " + entity + " e " + where);
			entityList = q.getResultList();
		} finally {
			em.close();
		}
		return entityList;
	}

	public Object getRecord(Class<?> entityClass, Object pk) {
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		Object entityObject = null;
		try {
			entityObject = em.find(entityClass, pk);
		} finally {
			em.close();
		}
		return entityObject;
	}

	public void addRecord(Object entity) {
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.merge(entity);
				t.commit();				
			} finally {
				if (t.isActive())
					t.rollback();
			}
		} finally {
			em.close();
		}
	}

	public void updateRecord(Object entity, Object pk) {
		Class<?> entityClass = entity.getClass();
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		Object entityObject = em.find(entityClass, pk);
		try {
			EntityTransaction t = em.getTransaction();
			try {
				for (Field f : entityClass.getDeclaredFields()) {
					if (!Modifier.isStatic(f.getModifiers())) {
						f.setAccessible(true);
						// System.out.println(f.getName() + ": " + f.get(entityObject) + " = " + f.get(entity));
						if (f.get(entity) != null) {
							f.set(entityObject, f.get(entity));
						}
					}
				}
				t.begin();
				em.merge(entityObject);
				t.commit();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (t.isActive())
					t.rollback();
			}
		} finally {
			em.close();
		}
	}

	public void updateRecord(Object entity) {
		Class<?> entityClass = entity.getClass();
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		Object pk = emf.getPersistenceUnitUtil().getIdentifier(entity);
		Object entityObject = em.find(entityClass, pk);
		try {
			EntityTransaction t = em.getTransaction();
			try {
				for (Field f : entityClass.getDeclaredFields()) {
					if (!Modifier.isStatic(f.getModifiers())) {
						f.setAccessible(true);
						// System.out.println(f.getName() + ": " + f.get(entityObject) + " = " + f.get(entity));
						if (f.get(entity) != null) {
							f.set(entityObject, f.get(entity));
						}
					}
				}
				t.begin();
				em.merge(entityObject);
				t.commit();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (t.isActive())
					t.rollback();
			}
		} finally {
			em.close();
		}
	}

	public void removeRecord(Object entity, Object pk) {
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		Object entityObject = em.find(entity.getClass(), pk);
		try {
			EntityTransaction t = em.getTransaction();
			try {				
				t.begin();
				em.remove(entityObject);
				t.commit();
			} finally {
				if (t.isActive())
					t.rollback();
			}
		} finally {
			em.close();
		}
	}

	public void removeRecord(Object entity) {
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		Object pk = factory.getPersistenceUnitUtil().getIdentifier(entity);
		// System.out.println(pk.toString());
		Object entityObject = em.find(entity.getClass(), pk);
		try {
			EntityTransaction t = em.getTransaction();
			try {				
				t.begin();
				em.remove(entityObject);
				t.commit();
			} finally {
				if (t.isActive())
					t.rollback();
			}
		} finally {
			em.close();
		}
	}

	public List<Object> getAllEntities(String where) {
		EntityManagerFactoryImpl emf = factory;
		EntityManagerImpl em = (EntityManagerImpl)emf.createEntityManager();
		List<Object> allEntities = new ArrayList<Object>();
		try {
			for (EntityType<?> entity : em.getMetamodel().getEntities()) {
				final String className = entity.getName();
				allEntities.addAll(fetchRecords(className, where));
			}
		} finally {
			em.close();
		}
		return allEntities;
	}

}
