package com.javalego.ui.mvp.beans.view.list.adapter;

import com.javalego.model.keys.Icon;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;

/**
 * Adaptador de los datos del bean de una lista para ser mostrados en UI.
 * Html, Texto, imagen, ...
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface IListBeansViewAdapter<T> extends IFormatBeansView<T> {

	/**
	 * Obtener el texto Html del contenido del bean para mostrarse en
	 * componentes UI.
	 * 
	 * @param bean
	 * @return
	 */
	String toHtml(T bean);

	/**
	 * Propiedad del bean que contiene una imagen
	 * @return
	 */
	String getImageFieldName();

	/**
	 * Id sólo para android de la imagen por defecto.
	 * TODO: pendiente de eliminar de esta interface e integrar en Android exclusivamente.
	 * @return
	 */
	int getDefaultImageResource();
	
	/**
	 * Icono. Imagen basada en anotaciones que usa el repositorio de imágenes de la aplicación para buscar este enumerado.
	 * @return
	 */
	Icon getIcon(T bean);
}
