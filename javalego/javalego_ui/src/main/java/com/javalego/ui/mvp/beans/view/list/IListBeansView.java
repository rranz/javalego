package com.javalego.ui.mvp.beans.view.list;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.IBeansView;

/**
 * Vista de beans simple basada en una lista.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public interface IListBeansView<T> extends IBeansView<T> {

	public interface ListBeansViewListener<T> extends IListBeansViewModel<T> {
		
		/**
		 * Editar bean
		 * @param bean
		 * @throws LocalizedException 
		 */
		void editBean(T bean) throws LocalizedException;
		
		/**
		 * Aplicar un filtro de selección
		 * @param statement
		 */
		void applyFilter(String statement) throws LocalizedException;

		/**
		 * Eliminar el filtro actual.
		 */
		void removeCurrentFilter() throws LocalizedException;		
	}
	
	void setListener(ListBeansViewListener<T> listener);
	
}
