package com.javalego.ui.mvp.editor.detail;

import java.util.Collection;

import com.javalego.model.BaseModel;

/**
 * Definición de un relación maestro detalle entre objetos. Esta relación puede estar
 * asociada a objetos persistentes JPA o a una simple lista de objetos (Iterable) existentes como
 * propiedad en el objeto maestro u owner.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IDetailEditorModel<T> extends BaseModel {

	/**
	 * Lista de objeto a editar.
	 * @return
	 */
	Collection<T> getData();
	
	/**
	 * Clase de Bean requerida para crear los componentes visuales cuando getData() no devuelve información (size = 0 o null).
	 * @return
	 */
	Class<T> getBeanClass();

	/**
	 * Nuevo objeto
	 * @return
	 */
	Object getNewObject();

}
