package com.javalego.ui.mvp.beans.view.paged;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.beans.view.IBeansView;

/**
 * Vista de beans paginados.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public interface IPagedBeansView<T> extends IBeansView<T> {

	public interface PagedBeansViewListener<T> extends IPagedBeansModel<T> {
		
		/**
		 * Cargar la primera página
		 * @throws LocalizedException
		 */
		void firstPage() throws LocalizedException;

		/**
		 * Cargar la siguiente página
		 * @throws LocalizedException
		 */
		void nextPage() throws LocalizedException;
		
		/**
		 * Cargar la última página
		 * @throws LocalizedException
		 */
		void lastPage() throws LocalizedException;
		
		/**
		 * Cargar la página anterior
		 * @throws LocalizedException
		 */
		void priorPage() throws LocalizedException;

		/**
		 * Recargar página actual
		 * @throws LocalizedException
		 */
		void refreshPage() throws LocalizedException;
		
		/**
		 * Cargar un número de página
		 * @param number
		 * @throws LocalizedException 
		 */
		void loadPage(int number) throws LocalizedException;
		
		/**
		 * Obtiene la lista del modelo de campos a mostrar. 
		 * Depende de la tipología de modelo asociada al presenter se utilizará este método.
		 * @return
		 */
		Collection<FieldModel> getFieldsModel();

		/**
		 * Obtener la descripción del bean que queremos mostrar en los componentes UI.
		 * Depende de la tipología de modelo asociada al presenter se utilizará este método.
		 * @param bean
		 * @return
		 */
		String toHtml(T bean);

		/**
		 * Ordenar beans
		 * @param propertyId
		 * @param ascending
		 * @throws LocalizedException 
		 */
		void goSort(Object[] propertyId, boolean[] ascending) throws LocalizedException;

		/**
		 * Aplicar un filtro de selección
		 * @param statement
		 */
		void applyFilter(String statement) throws LocalizedException;

		/**
		 * Eliminar el filtro actual.
		 */
		void removeCurrentFilter() throws LocalizedException;
		
		/**
		 * Editar bean
		 * @param bean
		 * @throws LocalizedException 
		 */
		void editBean(T bean) throws LocalizedException;

	}
	
	public void setListener(PagedBeansViewListener<T> listener);

}
