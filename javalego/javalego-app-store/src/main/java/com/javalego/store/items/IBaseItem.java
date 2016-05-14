package com.javalego.store.items;

import com.javalego.model.keys.Icon;

/**
 * Elemento básico de la plataforma.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBaseItem extends IBaseModel {

	/**
	 * Imagen representativa o icono.
	 * @return
	 */
	Icon getIcon();
	
	/**
	 * Título de sus características que será utilizado como presentación.
	 * @return
	 */
	String toHtml();

	/**
	 * Contenido HTML incluido en la vista de tipo tile para mostrar una lista de productos, proyecto o miembros.
	 * @return
	 */
	String toTile();
	
}
