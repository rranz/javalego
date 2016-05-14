package com.javalego.ui.actions;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansEditorViewListener;

/**
 * Acción de un editor de beans vinculada al browser de registros.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IActionBeansEditor extends IAction {

	/**
	 * Ejecutar la acción enviando los datos de contexto que podemos obtener el editor de beans.
	 * @param listener
	 * @throws LocalizedException
	 */
	public abstract void execute(@SuppressWarnings("rawtypes") BeansEditorViewListener listener) throws LocalizedException;
	
}
