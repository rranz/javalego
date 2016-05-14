package com.javalego.ui.mvp.editor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.editor.rules.IEditionRules;
import com.javalego.ui.patterns.IView;

/**
 * Vista de menú básica de editor de campos
 * 
 * @author ROBERTO RANZ
 */
public interface IBaseEditorView extends IView, IEditionRules {

	/**
	 * Aplicar cambios de los campos editados.
	 * 
	 * @return Lista de excepciones producidas en la vista.
	 * @throws LocalizedException
	 */
	List<LocalizedException> commit() throws LocalizedException;

	/**
	 * Cancelar los cambios realizados.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	void discard();

	/**
	 * Mostrar los errores producidos al realizar el commit.
	 * 
	 * @param exceptions
	 */
	public void showErrors(List<LocalizedException> exceptions);

	/**
	 * Obtener la lista de valores en edición.
	 * 
	 * @return
	 */
	public Collection<?> getValues();
	
	/**
	 * Obtener la lista de campos y sus valores en edición.
	 * 
	 * @return
	 */
	public Map<String, ?> getFieldValues();
	
	/**
	 * Descartar los cambios realizados en un campo.
	 * 
	 * @param fieldName
	 */
	public void discard(String fieldName);

	/**
	 * Validar los datos. Este método se puede usar si necesita realizar alguna
	 * tarea previa al commit que necesita la validación de cada campo editado.
	 * 
	 * @return
	 */
	public List<LocalizedException> validate();

}
