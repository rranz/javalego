package com.javalego.ui.menu;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.patterns.IModel;

/**
 * Modelo de datos del menú.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IMenuModel extends IModel {

	/**
	 * Obtener la lista de subItems de un Item de menú.
	 * @param menuItem
	 * @return
	 */
	List<IMenuItem> getSubItems(IMenuItem menuItem);

	/**
	 * Items principales 1er nivel.
	 * @return
	 * @throws LocalizedException 
	 */
	List<IMenuItem> getMainItems() throws LocalizedException;

	/**
	 * Item que representa la cabecera del menú o título.
	 * @return
	 */
	IMenuItem getHeader();

}
