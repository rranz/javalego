package com.javalego.store.items;

import java.util.Date;

/**
 * Comentario o valoración de un proyecto, producto, ...
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IComment extends IAuthor, IBaseItem
{
	/**
	 * Número de estrellas de puntuación (hasta 5).
	 * @return
	 */
	int getRating();
	
	/**
	 * Comentario
	 * @return
	 */
	String getComment();
	
	/**
	 * Fecha
	 * @return
	 */
	Date getDate();
}
