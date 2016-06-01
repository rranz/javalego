package com.javalego.entity;

import java.io.Serializable;

/**
 * Entidad del modelo de dominio.
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface Entity<T extends Serializable> {

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
	T getId();

	/**
	 * Establecer el valor de la clave de entidad.
	 */
	void setId(T value);
}
