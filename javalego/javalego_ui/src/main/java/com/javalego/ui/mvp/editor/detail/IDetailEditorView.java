package com.javalego.ui.mvp.editor.detail;

import java.util.Collection;

import com.javalego.ui.patterns.IView;

/**
 * Vista del editor detalle.
 * 
 * @author ROBERTO RANZ
 */
public interface IDetailEditorView extends IView {
	
	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	public static interface DetailEditorViewListener {
		
		/**
		 * Modelo de datos a editar.
		 * @return
		 */
		Collection<?> getData();

		/**
		 * Clase de Bean requerida para crear los componentes visuales cuando getData() no devuelve información (size = 0 o null).
		 * @return
		 */
		Class<?> getBeanClass();

		/**
		 * Obtener un nuevo objeto para añadir.
		 * @return
		 */
		Object getNewObject();

	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	public void setListener(DetailEditorViewListener listener);
	
}
