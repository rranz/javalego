package com.javalego.ui.editor.data;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;

/**
 * Elemento básico a incluir en un editor.
 * 
 * Pueden ser campos, acciones, ...
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IItemEditor {

	/**
	 * Nombre de referencia.
	 * @return
	 */
	String getName();
	
	/**
	 * Título
	 * @return
	 */
	Key getTitle();

	/**
	 * Validación del valor asignable al item del editor.
	 * @param value
	 * @return
	 */
	List<LocalizedException> validate(Object value);

}
