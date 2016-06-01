package com.javalego.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.Order;

import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;

/**
 * Proveedor de datos.
 * 
 * Interface que define los métodos básicos de acceso a datos, independiente del
 * proveedor o tecnología utilizada.
 * 
 * Las implementaciones de esta interface pueden ser Spring Data, EclipseLink,
 * JPA Hibernate, JDBC, mocks, ... o desarrollar nuestro propio código.
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface DataProvider {

	/*
	 * A "copy" of the Hibernate's API as this doesn't exist in JPA.
	 */
	public enum MatchMode {
		START, END, EXACT, ANYWHERE
	}

	/**
	 * Tipologías de proveedores de datos.
	 * <p>
	 * Esta tipología nos permitirá adaptar nuestro modelo de negocio al
	 * contexto de ejecución de acceso a la base de datos. Ej.: en Android con
	 * SQLite podremos adaptar la sentencia de un filtro para este tipo de base
	 * de datos en lugar de utilizar sentencias JPA más simples.
	 * <p>
	 * NOTA: El tipo Mock servirá para definir proveedores de datos para
	 * realizar pruebas unitarias.
	 */
	public enum Type {
		SQLite, JPA, Spring_Data, REST, Mock
	};

	/**
	 * Saves an entity.
	 * 
	 * @param entity
	 * @return newly created id for the entity.
	 */
	<T extends Entity<PK>, PK extends Serializable> PK save(T entity);

	/**
	 * Marges objects with the same identifier within a session into a newly
	 * created object.
	 * 
	 * @param entity
	 * @return a newly created instance merged.
	 */
	<T extends Entity<PK>, PK extends Serializable> T merge(T entity);

	/**
	 * Deletes tne entity.
	 * 
	 * @param clazz
	 * @param id
	 * @throws NotFoundException
	 *             if the id does not exist.
	 */
	<T extends Entity<PK>, PK extends Serializable> void delete(T entity) throws LocalizedException;

	/**
	 * Find an entity by its identifier.
	 * 
	 * @param clazz
	 * @param id
	 * @return entity
	 */
	<T extends Entity<?>> T find(Class<T> clazz, Serializable id);

	/**
	 * Finds an entity by one of its properties.
	 * 
	 * 
	 * @param clazz
	 *            the entity class.
	 * @param propertyName
	 *            the property name.
	 * @param value
	 *            the value by which to find.
	 * @return list
	 */
	<T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value);

	/**
	 * Finds entities by a String property specifying a MatchMode. This search
	 * is case insensitive.
	 * 
	 * @param clazz
	 *            the entity class.
	 * @param propertyName
	 *            the property name.
	 * @param value
	 *            the value to check against.
	 * @param matchMode
	 *            the match mode: EXACT, START, END, ANYWHERE.
	 * @return list
	 */
	<T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value, MatchMode matchMode);

	/**
	 * Finds all objects of an entity class.
	 * 
	 * @param clazz
	 *            the entity class.
	 * @return list
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz);

	/**
	 * Finds all objects of an entity class.
	 * 
	 * @param clazz
	 *            the entity class.
	 * @param where
	 *            condition HQL filter entities. Ej.: 'name like '%BA%' and
	 *            number < 20'
	 * @return list
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz, String where);

	/**
	 * Finds all objects of an entity class.
	 * 
	 * @param clazz
	 *            the entity class.
	 * @param where
	 *            condition HQL filter entities. Ej.: 'name like '%BA%' and
	 *            number < 20'
	 * @param order
	 *            order HQL entities. Ej.: 'name, date desc'
	 * @return list
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz, String where, String order);

	/**
	 * Finds all objects of a class by the specified order.
	 * 
	 * @param clazz
	 *            the entity class.
	 * @param order
	 *            the order: ASC or DESC.
	 * @param propertiesOrder
	 *            the properties on which to apply the ordering.
	 * 
	 * @return list
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder);

	/**
	 * Paged list entities
	 * 
	 * @param clazz
	 *            the entity class.
	 * @param startIndex
	 *            init index
	 * @param count
	 *            count objects
	 * @param where
	 *            condition HQL
	 * @param order
	 *            order HQL
	 * @return list
	 * @throws LocalizedException
	 */
	<T extends Entity<?>> List<T> pagedList(Class<T> clazz, int startIndex, int count, String where, String order) throws LocalizedException;

	/**
	 * Get property values.
	 * 
	 * @param clazz
	 *            entity class
	 * @param propertyName
	 *            property name
	 * @param where
	 *            condition HQL
	 * @param order
	 *            order HQL
	 * @return list values
	 */
	List<?> fieldValues(Class<?> clazz, String propertyName, String where, String order);

	/**
	 * Tipología de proveedor de datos (SQLite, JPA, Spring_Data, REST, Mock,
	 * ...)
	 * 
	 * @return
	 */
	Type getType();

	/**
	 * Cargar o inicializar el proveedor de datos.
	 */
	void load();

	/**
	 * Count records
	 * 
	 * @param clazz
	 *            entity class
	 * @param where
	 *            condition HQL
	 * @return
	 */
	Long count(Class<?> clazz, String where);

}