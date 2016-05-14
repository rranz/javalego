package com.javalego.ui.mvp.editor.bean.impl;

import com.javalego.exception.LocalizedException;

/**
 * Observer para notificar las acciones CRUD del editor.
 * 
 * @author ROBERTO RANZ
 */
public interface BeanEditorObserver<T> {

	/**
	 * Realizar el commit de los datos editados
	 */
	void commit(T bean) throws LocalizedException;

	/**
	 * Descartar cambios
	 * 
	 * @throws LocalizedException
	 */
	void discard(T bean);

	/**
	 * Eliminar
	 * 
	 * @throws LocalizedException
	 */
	void remove(T bean) throws LocalizedException;
}
