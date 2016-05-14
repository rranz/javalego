package com.javalego.data;

import java.util.Collection;

import com.javalego.exception.LocalizedException;

/**
 * Proveedor de datos.
 * 
 * Interface que define los métodos básicos de acceso a datos,
 * independiente del proveedor o tecnología utilizada.
 * 
 * Las implementaciones de esta interface pueden ser Spring Data, JPA Hibernate,
 * JDBC, beans memory, ... o desarrollar nuestro propio código.
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface DataProvider<T> {

	/**
	 * Tipologías de proveedores de datos posibles actualmente que podemos usar
	 * para definir la tipología de provider. Esta tipología nos permitirá
	 * adaptar nuestro modelo de negocio al contexto de ejecución de acceso a la
	 * base de datos. Ej.: en Android con SQLite podremos adaptar la sentencia
	 * de un filtro para este tipo de base de datos en lugar de utilizar
	 * sentencias JPA más simples.
	 * 
	 * NOTA: El tipo Mock servirá para definir providers con datos para realizar
	 * pruebas en desarrollo.
	 */
	public enum Type {
		SQLite, JPA, Spring_Data, REST, Mock
	};

	/**
	 * Obtiene todos los objetos de la entidad pasada como parámetro.
	 * 
	 * @param entity
	 *            clase de entidad
	 * @return colección de entidades
	 * @throws LocalizedException
	 */
	Collection<? extends T> getList(Class<? extends T> entity) throws LocalizedException;

	/**
	 * Obtiene una colección de entidades
	 * 
	 * @param entity
	 *            clase de entidad
	 * @param where
	 *            condición (sentencia JPQL)
	 * @param order
	 *            ordenación (lista de campos separados por comas)
	 * @return colección de entidades
	 * @throws LocalizedException
	 */
	Collection<? extends T> getList(Class<? extends T> entity, String where, String order) throws LocalizedException;

	/**
	 * Obtiene de una colección de entidades.
	 * 
	 * @param entity
	 *            clase de entidad
	 * @param where
	 *            condición (sentencia JPQL)
	 * @return colección de entidades
	 * @throws LocalizedException
	 */
	Collection<? extends T> getList(Class<? extends T> entity, String where) throws LocalizedException;

	/**
	 * Obtiene una colección de entidades paginada.
	 * 
	 * @param entity
	 *            clase de entidad
	 * @param startIndex
	 *            posición inicial de recuperación de registros en la tabla
	 * @param count
	 *            número de registros a recuperar desde la posición inicial
	 * @param where
	 *            condición (sentencia JPQL)
	 * @param order
	 *            lista de campos separadas por comas
	 * @return colección de entidades
	 * @throws LocalizedException
	 */
	Collection<? extends T> getPagedList(Class<? extends T> entity, int startIndex, int count, String where, String order) throws LocalizedException;

	/**
	 * Obtener una colección de entidades a partir de la ejecución de un query
	 * definido en la entidad.
	 * 
	 * @param name
	 *            nombre de la consulta
	 * @return colección de entidades
	 */
	Collection<? extends T> getQuery(String name) throws LocalizedException;

	/**
	 * Cargar o inicializar el proveedor de datos.
	 */
	void load() throws LocalizedException;

	/**
	 * Obtener un valor de tipo Long a partir de una sentencia JPQL.
	 * 
	 * @param statement
	 *            sentencia JPQL
	 * @return
	 */
	Long getLong(String statement) throws LocalizedException;

	/**
	 * Obtener una entidad a partir de una sentencia JPQL.
	 * 
	 * @param statement
	 *            sentencia JPQL
	 * @return entidad
	 */
	T getObject(String statement) throws LocalizedException;

	/**
	 * Eliminar un bean o registro de entidad
	 */
	void delete(T bean) throws LocalizedException;

	/**
	 * Persistir registro
	 * 
	 * @return
	 */
	T save(T bean) throws LocalizedException;

	/**
	 * Buscar una entidad por su identificador único.
	 * 
	 * @param entity
	 *            clase de entidad
	 * @param id
	 *            Identificador único
	 * @return entidad
	 * @throws LocalizedException
	 */
	T getObject(Class<? extends T> entity, Long id) throws LocalizedException;

	/**
	 * Buscar una entidad mediante una condición.
	 * 
	 * @param entity
	 *            clase de entidad
	 * @param where
	 *            condición (sentencia JPQL)
	 * @return objeto de entidad
	 * @throws LocalizedException
	 */
	T getObject(Class<? extends T> entity, String where) throws LocalizedException;

	/**
	 * Obtener la lista de valores posibles que actualmente tiene un campo o
	 * propiedad de una entidad.
	 * 
	 * @param entity
	 *            clase de entidad
	 * @param fieldName
	 *            campo o propiedad de la entidad que deseamos obtener la lista
	 *            de valores únicos existentes actualmente.
	 * @param where
	 *            condición (sentencia JPQL)
	 * @param order
	 *            lista de campos separadas por comas
	 * @return colección de entidades
	 * @throws LocalizedException
	 */
	Collection<?> getFieldValues(Class<? extends T> entity, String fieldName, String where, String order) throws LocalizedException;

	/**
	 * Tipología de proveedor de datos (SQLite, JPA, Spring_Data, REST, Mock,
	 * ...)
	 * 
	 * @return
	 */
	Type getType();

}
