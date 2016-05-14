package com.javalego.ui.actions;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.editor.IEditorViewListener;

/**
 * Acción de un editor de beans vinculada al editar un registro.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IActionBeanEditor extends IAction {

	/**
	 * Ejecutar la acción enviando los datos de contexto que podemos obtener el editor de beans.
	 * @param listener
	 * @throws LocalizedException
	 */
	public abstract void execute(IEditorViewListener listener) throws LocalizedException;
	
}
