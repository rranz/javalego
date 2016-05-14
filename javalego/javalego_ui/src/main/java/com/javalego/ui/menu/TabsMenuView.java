package com.javalego.ui.menu;

import com.javalego.model.keys.Key;


/**
 * Vista basada en Tabs para la representación gráfica de los items del menú.
 * 
 * @author ROBERTO RANZ
 */
public interface TabsMenuView extends MenuView {
	
	/**
	 * Establecer la visibilidad de un tab
	 * @param item
	 * @param visible
	 */
	void setVisibleTab(IMenuItem item, boolean visible);

	/**
	 * Establecer la visibilidad de un tab
	 * @param item
	 * @param visible
	 */
	void setVisibleTab(Key item, boolean visible);

	/**
	 * Comprobar si un tab está visible.
	 * @param item
	 * @return
	 */
	boolean isVisibleTab(IMenuItem item);
	
	/**
	 * Comprobar si un tab está visible.
	 * @param item
	 * @return
	 */
	boolean isVisibleTab(Key item);

	/**
	 * Establecer la Enabled de un tab
	 * @param item
	 * @param enabled
	 */
	void setEnabledTab(IMenuItem item, boolean enabled);

	/**
	 * Establecer la Enabled de un tab
	 * @param item
	 * @param enabled
	 */
	void setEnabledTab(Key item, boolean enabled);

	/**
	 * Comprobar si un tab está Enabled.
	 * @param item
	 * @return
	 */
	boolean isEnabledTab(IMenuItem item);
	
	/**
	 * Comprobar si un tab está Enabled.
	 * @param item
	 * @return
	 */
	boolean isEnabledTab(Key item);
	
	/**
	 * Establecer Caption de un tab
	 * @param item
	 * @param caption
	 */
	void setCaptionTab(IMenuItem item, String caption);

	/**
	 * Establecer Caption de un tab
	 * @param item
	 * @param caption
	 */
	void setCaptionTab(Key item, String caption);	
}
