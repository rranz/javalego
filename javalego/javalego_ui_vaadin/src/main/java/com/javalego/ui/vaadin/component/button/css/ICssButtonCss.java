package com.javalego.ui.vaadin.component.button.css;

import com.javalego.ui.vaadin.css.ICss;

/**
 * Hojas de estilos para el componente CssButton
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ICssButtonCss extends ICss {

	/**
	 * Button con icono y texto
	 * @return
	 */
	String getIconText();

	/**
	 * Button solo texto
	 * @return
	 */
	String getText();

	/**
	 * Layout del button
	 * @return
	 */
	String getLayout();

	/**
	 * Reducir el padding aplicado por defecto.
	 * @return
	 */
	String getSmall();

	/**
	 * Color de fondo
	 * @param color
	 * @return
	 */
	String getColor(String color);

	/**
	 * Seleccionado
	 * @return
	 */
	String getSelected(String color);
	
	/**
	 * Seleccionado con cambio de color de fondo.
	 * @return
	 */
	String getSelectedBackground();

	/**
	 * Color (sólo para theme Valo)
	 * @return
	 */
	String getLabelColored();

	/**
	 * Color (sólo para theme Valo)
	 * @return
	 */
	String getLabelLight();
	
	/**
	 * Bold (sólo para theme Valo)
	 * @return
	 */
	String getLabelBold();
	
	/**
	 * Aplicar sombra
	 * @return
	 */
	String getShadow();

	/**
	 * Estilo aplicado a la imagen o icono.
	 * @return
	 */
	String getIcon();

	/**
	 * Estilo aplicado a la imagen o icono. Padding small. Ej.: para iconos en cajas de texto.
	 * @return
	 */
	String getIconSmall();

	/**
	 * Estilo aplicado cuando se no está habilitada la opción de click de botón.
	 * @return
	 */
	String getDisabled();
	
	/**
	 * Aplicar border al estilo actual
	 * @return
	 */
	String getBorder();

	/**
	 * Adaptar button para icono IC Drawer estilo Android que elimina el color de background en hover.
	 * @return
	 */
	String getIcDrawer();
}
