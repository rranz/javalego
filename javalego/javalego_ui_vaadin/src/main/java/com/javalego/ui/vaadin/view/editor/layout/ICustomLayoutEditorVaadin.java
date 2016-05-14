package com.javalego.ui.vaadin.view.editor.layout;

import com.javalego.ui.mvp.editor.IEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;

/**
 * Layout personalizado que utiliza el modelo de datos a editar.
 * 
 * @author ROBERTO RANZ
 *
 * TODO: Falta implementarlo en Vaadin.
 *
 * @param <T>
 */
public interface ICustomLayoutEditorVaadin<T> extends ICustomLayoutEditor<T> {

	/**
	 * Obtiene el layout en base al modelo de datos de edición.
	 * @param model
	 * @return
	 */
	public abstract T getLayout(IEditorModel model);	
}
