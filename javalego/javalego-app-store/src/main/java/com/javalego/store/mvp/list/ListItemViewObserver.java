package com.javalego.store.mvp.list;

import com.javalego.exception.LocalizedException;

/**
 * Observer para gestionar los eventos de mantenimiento de items de la vista
 * (edición, inserción, ...)
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ListItemViewObserver<T> {

	/**
	 * Editando un bean
	 * 
	 * @param bean
	 * @throws LocalizedException
	 */
	void editing(T bean);

	/**
	 * Insertando un bean
	 * 
	 * @param bean
	 * @throws LocalizedException
	 */
	void inserting(T bean);

	/**
	 * Bean grabado
	 * 
	 * @param bean
	 */
	void saved(T bean);

	/**
	 * Edición cancelada
	 * 
	 * @param bean
	 */
	void canceled(T bean);

}
