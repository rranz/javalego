package com.javalego.store.items;


/**
 * Elemento básico de autor o creador del componente. Se crea para identificar
 * los tipos de items que tienen el campo author que nos permitirá identificar
 * si el usuario tiene acceso de edición a dicho item.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IAuthor {

	/**
	 * Autor
	 * @return
	 */
	IMember getAuthor();
}
