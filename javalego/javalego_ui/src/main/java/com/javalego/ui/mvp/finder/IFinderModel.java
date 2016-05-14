package com.javalego.ui.mvp.finder;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;

/**
 * Model de búsqueda de objetos seleccionables.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IFinderModel<T> {

	/**
	 * Objeto seleccionado.
	 * @param value
	 * @throws LocalizedException
	 */
	void setValue(T value) throws LocalizedException;
	
	/**
	 * Lista de objetos seleccionables.
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
