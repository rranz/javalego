package com.javalego.ui.vaadin.view.editor.beans.list;

import com.javalego.exception.LocalizedException;
import com.vaadin.ui.AbstractComponent;

/**
 * Personalización del contenido del componente asociado a cada bean de una
 * vista de tipo lista de beans.
 * 
 * @author ROBERTO RANZ
 */
public interface ICustomBeanView<T> {

	/**
	 * Obtener el componente personalizado para el bean pasado como
	 * parámetro.
	 * 
	 * @param bean
	 *            Bean asociado a cada item.
	 * @throws LocalizedException 
	 */
	AbstractComponent getComponent(T bean) throws LocalizedException;

}
