package com.javalego.ui.mvp.beans.view;

import com.javalego.ui.patterns.IModel;

/**
 * Modelo de datos utilizado para la vista de representación de la lista de beans.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBeansViewModel<T> extends IModel {

	/**
	 * Clase de la vista de Beans manejada.
	 * @return
	 */
	Class<T> getBeanClass();	
	
}
