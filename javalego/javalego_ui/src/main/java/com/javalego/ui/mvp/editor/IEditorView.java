package com.javalego.ui.mvp.editor;


/**
 * Vista de menú básica de editor de campos
 * 
 * @author ROBERTO RANZ
 */
public interface IEditorView extends IBaseEditorView {
	
	/**
	 * Edición de campos: Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	public static interface EditorViewListener extends IEditorViewListener {
		
		/**
		 * Modelo de datos a editar.
		 * @return
		 */
		IEditorModel getModel();

	}
	
	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	public void setListener(EditorViewListener listener);
	
}
