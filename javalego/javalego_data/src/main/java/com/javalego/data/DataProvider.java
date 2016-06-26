package com.javalego.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.Order;

import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
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
	 * @return Entidad actualizada
	 */
	<T extends Entity<?>> T save(T entity);

	/**
	 * Mezclar datos de objetos de entidad con el mismo identificador creando un nuevo objeto.
	 * 
	 * @param entity
	 *            objeto entidad
	 * @return crear una nueva instancia de entidad mezclando sus datos
	 */
	<T extends Entity<?>> T merge(T entity);

	/**
	 * Elimina una entidad
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param id
	 *            identificador
	 * @throws NotFoundException
	 *             Si el identificador de entidad no existe
	 */
	<T extends Entity<?>> T delete(T entity) throws LocalizedException;

	/**
	 * Encontrar una entidad por su identificador.
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param id
	 *            valor clave entidad
	 * @return entity entidad
	 */
	<T extends Entity<?>> T find(Class<T> clazz, Serializable id);

	/**
	 * Encuentra entidades por una de sus propiedades.
	 * 
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param propertyName
	 *            nombre de la propiedad
	 * @param value
	 *            valor de búsqueda de la propiedad
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value);

	/**
	 * Encuentra entidades por el valor de una de sus propiedades especificando un modo de búsqueda. La búsqueda no
	 * distingue entre mayúsculas y minúsculas.
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param propertyName
	 *            Nombre de la propiedad
	 * @param value
	 *            Valor de búsqueda.
	 * @param matchMode
	 *            modo de búsqueda: EXACT, START, END, ANYWHERE.
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value,
		MatchMode matchMode);

	/**
	 * Encontrar todos los objetos de la clase entidad
	 * 
	 * @param clazz
	 *            clase entidad
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz);

	/**
	 * Encontrar todos los objetos de la clase entidad
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param where
	 *            condición HQL para filtrar entidades. Ej.: 'name like '%BA%' and number < 20'
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz, String where);

	/**
	 * Encontrar todos los objetos de la clase entidad
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param where
	 *            condición HQL para filtrar entidades. Ej.: 'name like '%BA%' and number < 20'
	 * @param order
	 *            sentencia de ordenación HQL. Ej.: 'name, date desc'
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz, String where, String order);

	/**
	 * Encontrar todos los objetos de la clase entidad para un order específico.
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param order
	 *            ordenación: ASC or DESC.
	 * @param propertiesOrder
	 *            propiedades a aplicar la ordenación.
	 * 
	 * @return list lista de objetos de entidad
	 */
	<T extends Entity<?>> List<T> findAll(Class<T> clazz, Order order, String... propertiesOrder);

	/**
	 * Lista de entidades paginada
	 * 
	 * @param clazz
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
	 * @throws LocalizedException
	 */
	<T extends Entity<?>> List<T> pagedList(Class<T> clazz, int startIndex, int count, String where, String order)
		throws LocalizedException;

	/**
	 * Obtiene los valores de una propiedad.
	 * 
	 * @param clazz
	 *            clase entidad
	 * @param propertyName
	 *            Nombre de la propiedad
	 * @param where
	 *            sentencia de condición HQL.
	 * @param order
	 *            sentencia de ordenación HQL. Ej.: 'name, date desc'
	 * @return list values
	 */
	List<?> propertyValues(Class<?> clazz, String propertyName, String where, String order);

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
	 * @param clazz
	 *            clase entidad
	 * @param where
	 *            sentencia de condición HQL.
	 * @return
	 */
	Long count(Class<?> clazz, String where);

}