package com.javalego.store.items;

import java.util.Collection;

/**
 * Definición de las características comunes de cualquier producto tanto de
 * arquitectura como de negocio.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IProduct extends IBaseItem
{
	/**
	 * Categoría (presentación, negocio, módulo, componente UI, ...)
	 * @return
	 */
	ICategory getCategory();
	
	/**
	 * Proyecto al que pertenece este producto.
	 * @return
	 */
	IProject getProject();
	
	/**
	 * Licencia
	 * @return
	 */
	ILicense getLicense();	
	
	/**
	 * Demo
	 * @return
	 */
	IDemo getDemo();

	/**
	 * Imágenes
	 * @return
	 */
	Collection<IScreenShot> getScreenshots();

	/**
	 * Comentarios
	 * @return
	 */
	Collection<IComment> getComments();

}
