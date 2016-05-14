package com.javalego.model;

import java.io.Serializable;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;

/**
 * Modelo de datos básico.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface BaseModel extends Serializable {

	/**
	 * Nombre de referencia
	 */
	String getName();

	/**
	 * Título
	 * 
	 * @param title
	 *            Enumerado que contiene {@link Languages} para mostrar el
	 *            título en el idioma del usuario.
	 */
	Key getTitle();

	/**
	 * Descripción
	 * 
	 * @param description
	 *            Enumerado que contiene {@link Languages} para mostrar la
	 *            descripción en el idioma del usuario.
	 */
	Key getDescription();

	/**
	 * Nombre de referencia
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * Título
	 * 
	 * @param title
	 *            Enumerado que contiene {@link Languages} para mostrar el
	 *            título en el idioma del usuario.
	 */
	void setTitle(Key title);

	/**
	 * Descripción
	 * 
	 * @param description
	 *            Enumerado que contiene {@link Languages} para mostrar la
	 *            descripción en el idioma del usuario.
	 */
	void setDescription(Key description);

	/**
	 * Comprueba si existe una descripción.
	 * 
	 * @return
	 */
	boolean hasDescription();

	/**
	 * Comprueba si tiene definido un título
	 * 
	 * @return
	 */
	boolean hasTitle();

}
