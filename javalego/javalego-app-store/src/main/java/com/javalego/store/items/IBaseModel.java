package com.javalego.store.items;

import java.io.Serializable;

import com.javalego.model.resources.locale.Languages;

/**
 * Datos básicos de un modelo dentro de la arquitectura de componentes.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBaseModel extends Serializable {

	/**
	 * Nombre de referencia
	 */
	String getName();

	/**
	 * Nombre de referencia
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * Título
	 * @param title Enumerado que contiene {@link Languages} para mostrar el título en el idioma del usuario.
	 */
	void setTitle(String title);

	/**
	 * Descripción
	 * @param description Enumerado que contiene {@link Languages} para mostrar la descripción en el idioma del usuario.
	 */
	void setDescription(String description);
	
	/**
	 * Título
	 * @param title Enumerado que contiene {@link Languages} para mostrar el título en el idioma del usuario.
	 */
	String getTitle();
	
	/**
	 * Descripción
	 * @param description Enumerado que contiene {@link Languages} para mostrar la descripción en el idioma del usuario.
	 */
	String getDescription();

	boolean hasDescription();
	
	boolean hasTitle();	
}
