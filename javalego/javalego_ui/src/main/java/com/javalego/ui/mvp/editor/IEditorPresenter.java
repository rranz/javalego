package com.javalego.ui.mvp.editor;

import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter del editor de campos
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IEditorPresenter extends IPresenter {

	/**
	 * Obtiene el valor original del campo antes de la edición
	 * @param fieldName
	 * @return
	 */
	Object getOldValue(String fieldName);

	/**
	 * Obtiene el valor actual del campo editado.
	 * @param fieldName
	 * @return
	 */
	Object getValue(String fieldName);

	/**
	 * Descargar los cambios realizados.
	 * @param fieldName
	 */
	void discard(String fieldName);

}
