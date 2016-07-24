package com.javalego.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.Order;
import javax.ws.rs.NotFoundException;

import com.javalego.entity.Entity;
import com.javalego.model.pattern.Adapter;

/**
 * Proveedor de datos.
 * <p>
 * Interface que define los métodos básicos de acceso a datos, independiente del proveedor y tecnología utilizada.
 * <p>
 * Los adaptadores de esta interface pueden ser: Spring Data, EclipseLink, JPA Hibernate, JDBC, mocks, ... o
 * desarrollar nuestro propio código.
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface DataProvider extends Adapter
{

	/*
	 * Tipo de modo de búsqueda.
	 */
	public enum MatchMode
	{
		START,
		END,
		EXACT,
		ANYWHERE
	}

	/**
	 * Tipologías de proveedores de datos.
	 * <p>
	 * Esta tipología nos permitirá adaptar nuestro modelo de negocio al contexto de ejecución de acceso a la base de
	 * datos. Ej.: en Android con SQLite podremos adaptar la sentencia de un filtro para este tipo de base de datos en
	 * lugar de utilizar sentencias JPA más simples.
	 * <p>
	 * NOTA: El tipo Mock servirá para definir proveedores de datos para realizar pruebas unitarias.
	 */
	public enum Type
	{
		SQLite,
		JPA,
		Spring_Data,
		REST,
		Mock
	};
	
	/**
	 * Actualiza o inserta una entidad
	 * 
	 * @param entity
	 *            objeto entidad
	 * @throws excepción
	 * @return Entidad actualizada
	 */
	<T extends Entity<?>> T save(T entity) throws DataProviderException;

	/**
	 * Mezclar datos de objetos de entidad con el mismo identificador creando un nuevo objeto.
	 * 
	 * @param entity
	 *            objeto entidad
	 * @throws excepción
	 * @return crear una nueva instancia de entidad mezclando sus datos
	 */
	<T extends Entity<?>> T merge(T entity) throws DataProviderException;

	/**
	 * Elimina una entidad
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param id
	 *            identificador
	 * @throws excepción
	 * @throws NotFoundException
	 *             Si el identificador de entidad no existe
	 */
	<T extends Entity<?>> T delete(T entity) throws DataProviderException;

	/**
	 * Encontrar una entidad por su identificador.
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param id
	 *            valor clave entidad
	 * @throws excepción
	 * @return entity entidad
	 */
	<T extends Entity<?>> T find(Class<T> entityClass, Serializable id) throws DataProviderException;

	/**
	 * Encuentra entidades por una de sus propiedades.
	 * 
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param propertyName
	 *            nombre de la propiedad
	 * @param value
	 *            valor de búsqueda de la propiedad
	 * @throws excepción
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) throws DataProviderException;

	/**
	 * Encuentra entidades por el valor de una de sus propiedades especificando un modo de búsqueda. La búsqueda no
	 * distingue entre mayúsculas y minúsculas.
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param propertyName
	 *            Nombre de la propiedad
	 * @param value
	 *            Valor de búsqueda.
	 * @param matchMode
	 *            modo de búsqueda: EXACT, START, END, ANYWHERE.
	 * @throws excepción
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findByProperty(Class<T> entityClass, String propertyName, String value,
		MatchMode matchMode) throws DataProviderException;

	/**
	 * Encontrar todos los objetos de la clase entidad
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @throws excepción
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> entityClass) throws DataProviderException;

	/**
	 * Encontrar todos los objetos de la clase entidad
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param where
	 *            condición HQL para filtrar entidades. Ej.: 'name like '%BA%' and number < 20'
	 * @throws excepción
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> entityClass, String where) throws DataProviderException;

	/**
	 * Encontrar todos los objetos de la clase entidad
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param where
	 *            condición HQL para filtrar entidades. Ej.: 'name like '%BA%' and number < 20'
	 * @param order
	 *            sentencia de ordenación HQL. Ej.: 'name, date desc'
	 * @throws excepción
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> entityClass, String where, String order) throws DataProviderException;

	/**
	 * Encontrar todos los objetos de la clase entidad para un order específico.
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param order
	 *            ordenación: ASC or DESC.
	 * @param propertiesOrder
	 *            propiedades a aplicar la ordenación.
	 * @throws excepción
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> entityClass, Order order, String... propertiesOrder) throws DataProviderException;

	/**
	 * Lista de entidades paginada
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param startIndex
	 *            índice de comienzo
	 * @param count
	 *            número de objetos a recuperar
	 * @param where
	 *            sentencia de condición HQL.
	 * @param order
	 *            sentencia de ordenación HQL. Ej.: 'name, date desc'
	 * @return list lista de objetos de entidad
	 * @throws excepción
	 */
	<T extends Entity<?>> List<T> pagedList(Class<T> entityClass, int startIndex, int count, String where, String order)
		throws DataProviderException;

	/**
	 * Obtiene los valores de una propiedad.
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param propertyName
	 *            Nombre de la propiedad
	 * @param where
	 *            sentencia de condición HQL.
	 * @param order
	 *            sentencia de ordenación HQL. Ej.: 'name, date desc'
	 * @throws excepción
	 * @return list values
	 */
	List<?> propertyValues(Class<?> entityClass, String propertyName, String where, String order) throws DataProviderException;

	/**
	 * SQLite, JPA, Spring_Data, REST, Mock, ...
	 * 
	 * @return
	 */
	Type getType();

	/**
	 * Inicialización del proveedor de datos.
	 */
	void init();

	/**
	 * Número de registros de una entidad
	 * 
	 * @param entityClass
	 *            clase entidad
	 * @param where
	 *            sentencia de condición HQL.
	 * @throws excepción
	 * @return
	 */
	Long count(Class<?> entityClass, String where) throws DataProviderException;

}