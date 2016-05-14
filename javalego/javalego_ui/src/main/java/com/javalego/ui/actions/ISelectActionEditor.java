package com.javalego.ui.actions;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.editor.data.IValueDataEditor;
import com.javalego.ui.mvp.editor.IEditorViewListener;

/**
 * Definición de una acción ejecutada dentro del contexto de un editor.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ISelectActionEditor extends IActionEditor {

	/**
	 * Campo del editor del que se podrá seleccionar y validar un valor.
	 * @return
	 */
	IDataFieldModel getFieldModel();

	/**
	 * Realizar la validación del nuevo valor seleccionado.
	 * Este método es ejecutado por los editores cuando se intenta modificar el valor del campo al que está asociada la acción.
	 * 
	 * Nota: Este método se ejecuta después del cambio de valor (Ej. Vaadin).
	 * 
	 * @param listener
	 * @param dataEditor
	 * @param oldValue
	 * @param newValue
	 * @throws LocalizedException
	 */
	void validate(IEditorViewListener listener, IValueDataEditor dataEditor, Object oldValue, Object newValue) throws LocalizedException;
	
	/**
	 * Listener para actualizar los campos del bean cuando se produce su modificiación y después de
	 * pasar la validación (ver validate() método de esta interface).
	 * 
	 * Nota: Este método se ejecuta antes que la validación (Ej. Vaadin).
	 * 
	 * @param listener
	 * @param dataEditor
	 * @param currentValue
	 * @throws LocalizedException 
	 */
	void valueChangeEvent(IEditorViewListener listener, IValueDataEditor dataEditor, Object currentValue) throws LocalizedException;	
}
