package com.javalego.ui.mvp.finder;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.patterns.IView;

/**
 * Vista de búsqueda de objetos seleccionables.
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface IFinderView<T> extends IView {

	public interface IFinderViewListener<T> {

		/**
		 * Establecer objeto dentro de su contexto de edición. (Ej.: edición de
		 * un campo con una acción que usa esta vista para seleccionar un valor
		 * y establecerlo en la edición).
		 * 
		 * @param value
		 * @throws LocalizedException
		 */
		void setValue(T value) throws LocalizedException;

		/**
		 * Lista de objetos
		 * 
		 * @return
		 * @throws LocalizedException
		 */
		Collection<T> getObjects() throws LocalizedException;

		/**
		 * Campos a visualizar
		 * 
		 * @return
		 */
		String[] getProperties();

		/**
		 * Títulos de los campos del bean visualizados
		 * 
		 * @return
		 */
		String[] getHeaders();
		
		/**
		 * Título de búsqueda
		 * @return
		 */
		Key getTitle();
	}

	void setListener(IFinderViewListener<T> listener);

	/**
	 * Mostrar buscador
	 */
	void show();
}
