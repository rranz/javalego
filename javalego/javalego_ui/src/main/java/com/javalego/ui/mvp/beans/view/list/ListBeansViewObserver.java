package com.javalego.ui.mvp.beans.view.list;

import com.javalego.exception.LocalizedException;

/**
 * Observer al que se notifican la edición de un bean o el cambio en su
 * visualización (filtros, ...)
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ListBeansViewObserver<T> {

	/**
	 * Editar bean
	 * 
	 * @param bean
	 * @throws LocalizedException
	 */
	void editBean(T bean) throws LocalizedException;

}
