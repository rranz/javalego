package com.javalego.ui.vaadin.css;

import com.vaadin.ui.themes.ValoTheme;

/**
 * Definición de las hojas de estilo de uso general utilizadas por los componentes de Vaadin.
 * 
 * @author ROBERTO RANZ
 *
 */
public class CssVaadin {

	private CssVaadin() {}
	
	/**
	 * Incluye un sombreado en un componente y un borde redondeado.
	 * @return
	 */
	public static String getShadowRounded() {
		return "layout-shadow-rounded";
	}
	
	/**
	 * Fijar el color de fondo de los campos requeridos.
	 * @return
	 */
	public static String getRequired() {
		return "colored"; // "required-color";
	}
	
	/**
	 * Redondear las esquinas del layout.
	 * @return
	 */
	public static String getRounded() {
		return "layout-rounded";
	}
	
	/**
	 * Incluye un sombreado en un componente.
	 * @return
	 */
	public static String getShadow() {
		return "layout-shadow";
	}
	
	public static String getFontNormal() {
		return "font-normal";
	}
	
	public static String getFontBold() {
		return "font-bold";
	}
	
	public static String getFontLarge() {
		return "font-large";
	}
	
	public static String getFontMedium() {
		return "font-medium";
	}
	
	public static String getFontSmall() {
		return "font-small";
	}
	
	public static String getPaddingClear() {
		return "padding-clear";
	}
	
	/**
	 * Incluir un borde redondeado a un componente. Color silver.
	 * @return
	 */
	public static String getBorderRounded() {
		return "layout-border-rounded";
	}
	
	public static String getTile() {
		return "tile";
	}

	/**
	 * Establecer el valor margin a 4 pixels.
	 * @return
	 */
	public static String getMargin4() {
		return "margin4";
	}

	/**
	 * Establecer el valor margin a 4 pixels.
	 * @return
	 */
	public static String getMargin10() {
		return "margin10";
	}

	/**
	 * Establecer el border.
	 * @return
	 */
	public static String getBorder() {
		return "layout-border";
	}
	
	/**
	 * Establecer sombra en bottom.
	 * @return
	 */
	public static String getShadowBottom() {
		return "layout-bottom-shadow";
	}
	
	/**
	 * Establecer el border derecho.
	 * @return
	 */
	public static String getBorderRight() {
		return "layout-border-right";
	}
	
	/**
	 * Establecer estilo scroll para el componente. (Incluido en tema Valo solamente 7.3 Vaadin version).
	 * @return
	 */
	public static String getScrollable() {
		return "v-scrollable";
	}
	
	public static String getColored() {
		return ValoTheme.LABEL_COLORED;
	}

	public static String getLight() {
		return ValoTheme.LABEL_LIGHT;
	}	
	
}
