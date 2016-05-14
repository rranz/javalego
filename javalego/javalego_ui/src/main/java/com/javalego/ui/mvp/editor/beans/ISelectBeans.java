package com.javalego.ui.mvp.editor.beans;

import java.util.Collection;

/**
 * Interface de selección de beans.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ISelectBeans<T>
{
	/**
	 * Beans seleccionados.
	 * @return
	 */
	Collection<T> getSelectedBeans();

	/**
	 * Seleccionar beans
	 * @return
	 */
	void setSelectedBeans(Collection<T> beans);

	/**
	 * Bean actualmente seleccionado
	 * @return
	 */
	T getCurrentBean();

	/**
	 * Eliminar seleccón de objetos.
	 */
	void removeSelectedBeans();	
}
