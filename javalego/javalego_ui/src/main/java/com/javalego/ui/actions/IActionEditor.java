package com.javalego.ui.actions;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.IEditorViewListener;

/**
 * Acción utilizada en un editor. Se puede vincular a un campo en edición como relaciones o acciones específicas.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IActionEditor extends IAction {

	/**
	 * Ejecución de una acción de editor enviando los datos de contexto que podemos obtener el editor de bean o datos.
	 * @param listener
	 * @throws LocalizedException
	 */
	void execute(IEditorViewListener listener, IItemEditor dataEditor) throws LocalizedException;
	
}
