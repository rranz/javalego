package com.javalego.ui.vaadin.component.button.css;

import com.javalego.model.keys.Colors;


/**
 * Hoja de estilos del componente MenuPanels
 * 
 * @author ROBERTO RANZ
 *
 */
public class MenuPanelsCss extends CssButtonCss {

	/**
	 * Nombre Clase css más guión.
	 */
	protected static final String MENUPANELS = "menupanels-";
	
	/**
	 * Css segundo nivel de panels o menús
	 * @param color 
	 * @return
	 */
	public static String getLevel2(Colors color) {
		if (color != null) {
			return MENUPANELS + "level2" + "-" + color.name().toLowerCase();
		}
		else
			return null;
	}

}
