package com.javalego.ui.mvp.editor.bean;

import com.javalego.ui.mvp.editor.IBaseEditorView;
import com.javalego.ui.mvp.editor.IEditorViewListener;

/**
 * Vista de menú básica de editor de campos
 * 
 * @author ROBERTO RANZ
 */
public interface IBeanEditorView<T> extends IBaseEditorView {
	
	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	public static interface BeanEditorViewListener<T> extends IEditorViewListener {

		IBeanEditorModel<T> getModel();
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	public void setListener(BeanEditorViewListener<T> listener);
}
