package com.javalego.ui.mvp.editor.services;

import com.javalego.exception.LocalizedException;

/**
 * Gestión de Eventos del editor para controlar el ciclo de vida de la edición de datos. 
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBeanEditorEvents<T> {

	/**
	 * Evento antes del borrado del bean
	 * @throws LocalizedException
	 */
	void beforeDeleteEvent(T bean) throws LocalizedException;

	/**
	 * Evento antes grabar el bean
	 * @throws LocalizedException
	 */
	void beforeSaveEvent(T bean) throws LocalizedException;
	
}
