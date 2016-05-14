package com.javalego.store.items;

import java.util.Date;

/**
 * Noticias 
 * @author ROBERTO RANZ
 *
 */
public interface INews extends IBaseItem {

	/**
	 * Fecha de la noticia.
	 * @return
	 */
	Date getDate();
	
}
