package com.javalego.ui.mvp.beans.view.format;

/**
 * Formato utilizado para representar los datos de un bean.
 * 
 * Dependiendo de la naturaleza de la vista se definirán unas tipologías de
 * formato específicas: lista de beans, table de beans, ...
 * 
 * @author ROBERTO RANZ
 * 
 */
public interface IFormatBeansView<T> {

	/**
	 * Texto representativo del bean que queremos mostrar en la vista.
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * Comprueba si el texto de contenido del bean que se muestra en la lista
	 * contiene el valor pasado como parámetro. Este método se usa para realizar
	 * filtros de selección.
	 * 
	 * @param contains
	 * @return
	 */
	boolean contains(T bean, String contains);
}
