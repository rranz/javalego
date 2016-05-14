package com.javalego.ui.mvp.beans.view.list;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.IBeansViewModel;


/**
 * Modelo de beans paginados. Ej.: ver su contenido en formato Html para incluir en vistas UI.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IListBeansViewModel<T> extends IBeansViewModel<T> {

	/**
	 * Obtiene la lista de Beans a mostrar en la vista.
	 * @return
	 * @throws LocalizedException
	 */
	Collection<T> getBeans(String filter, String order) throws LocalizedException;

}
