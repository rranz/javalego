package com.javalego.ui.menu;

import java.io.Serializable;
import java.util.List;

import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;

/**
 * Modelo de item de menú.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IMenuItem extends Serializable {

	/**
	 * Nombre de referencia
	 * @return
	 */
	Key getName();

	/**
	 * Título descriptivo
	 * @return
	 */
	String getTitle();

	String getDescription();

	Icon getIcon();

	Colors getColor();

	boolean isEnabled();

	boolean isVisible();

	boolean isSeparator();

	boolean isCheckable();

	IMenuItem getParent();
	
	List<IMenuItem> getSubItems();

	void setParent(IMenuItem menuItem);
	
	/**
	 * Objeto asociado al menú.
	 * @return
	 */
	Object getData();
	
	/**
	 * Establecer el objeto del dato asociado al item de menú.
	 * @param data
	 */
	void setData(Object data);
	
	/**
	 * Establecer el título del item de menú.
	 * @param title
	 */
	IMenuItem setTitle(String title);

	/**
	 * Establecer el color de fondo del item
	 * @param color
	 */
	IMenuItem setColor(Colors color);

}
