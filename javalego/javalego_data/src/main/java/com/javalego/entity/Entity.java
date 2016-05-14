package com.javalego.entity;

/**
 * Entidad del modelo de dominio.
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface Entity {

	/**
	 * Texto de referencia a la tabla principal dentro de una consulta JQL (JPA)
	 * y que el sistema convertirá al alias. Se incluye el punto porque es el
	 * uso habitual de esta variable. this.
	 */
	String THIS_FULL = "this.";

	/**
	 * Texto de referencia a la tabla principal dentro de una consulta JQL (JPA)
	 * y que el sistema convertirá al alias. Se incluye el punto porque es el
	 * uso habitual de esta variable.
	 */
	String THIS = "this";

	/**
	 * Campo temporal de representa una clave primaria que es utilizada en por
	 * la clases relacionadas con la cache de los editores para establecer una
	 * relación entre los objetos master y objetos detail que nos permite
	 * localizar los objetos master por este código secuencial y temporal.
	 */
	static final String TMP_ID = "tmpID";

	/**
	 * Obtener el nombre del campo Id del objeto que se utiliza por defecto.
	 * 
	 * @return
	 */
	static final String DEFAULT_ID = "id";

	/**
	 * Obtener el valor de la clave de entidad.
	 * 
	 * @return
	 */
	Object getId();

	/**
	 * Establecer el valor de la clave de entidad.
	 */
	void setId(Object value);
}
