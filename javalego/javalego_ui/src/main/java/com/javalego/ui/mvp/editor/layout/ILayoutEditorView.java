package com.javalego.ui.mvp.editor.layout;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.patterns.IView;

/**
 * Vista de menú básica
 * 
 * @author ROBERTO RANZ
 */
public interface ILayoutEditorView extends IView {
	
	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	public static interface LayoutEditorViewListener {
		
		/**
		 * Datos del layout a editar
		 * @return
		 */
		List<IItemEditor> getDataEditor();
	
		/**
		 * Lista de otros layouts hijos. Recursividad.
		 * @return
		 */
		List<ILayoutEditorModel> getChildren();
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	public void setListener(LayoutEditorViewListener listener);

	/**
	 * Lista de datos del layout.
	 * @return
	 */
	public List<IItemEditor> getDataEditor();
	
	/**
	 * Cargar los campos del layout y sus layouts hijos.
	 * @param listener
	 * @throws LocalizedException 
	 */
	public void loadFields(IEditorViewListener listener) throws LocalizedException;

}
