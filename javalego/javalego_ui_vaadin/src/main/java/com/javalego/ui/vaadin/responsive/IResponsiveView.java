package com.javalego.ui.vaadin.responsive;

import com.javalego.exception.LocalizedException;

/**
 * Interface que define métodos que deben implementar nuestas vistas para hacer
 * nuestra aplicación responsive adaptadas a cualquier dispositivo.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IResponsiveView {

	/**
	 * Vista web
	 * 
	 * @return
	 */
	void loadWeb() throws LocalizedException;

	/**
	 * Vista mobile
	 * 
	 * @return
	 */
	void loadMobile() throws LocalizedException;

}
