package com.javalego.ui.vaadin.component.button.css;

import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Hoja de estilos del componente CssButton
 * 
 * @author ROBERTO RANZ
 *
 */
public class CssButtonCss implements ICssButtonCss {

	/**
	 * Nombre Clase css más guión.
	 */
	protected String CSS = "cssbutton-";
	
	@Override
	public String getIconText() {
		return CSS + "icon-text";
	}

	@Override
	public String getText() {
		return CSS + "text";
	}

	@Override
	public String getLayout() {
		return CSS + "layout";
	}

	@Override
	public String getColor(String color) {
		return CSS + color.toLowerCase();
	}

	@Override
	public String getSelected(String color) {
		return CSS + (color != null ? color + "-" : "") + "selected";
	}

	@Override
	public String getShadow() {
		return CssVaadin.getShadowRounded();
	}

	@Override
	public String getIcon() {
		return CSS + "icon";
	}

	@Override
	public String getIconSmall() {
		return CSS + "icon-small";
	}

	@Override
	public String getDisabled() {
		return CSS + "disabled";
	}

	@Override
	public String getBorder() {
		return CSS + "border";
	}

	@Override
	public String getSmall() {
		return CSS + "small";
	}

	@Override
	public String getLabelColored() {
		return ValoTheme.LABEL_COLORED;
	}

	@Override
	public String getLabelLight() {
		return ValoTheme.LABEL_LIGHT;
	}

	@Override
	public String getLabelBold() {
		return ValoTheme.LABEL_BOLD;
	}

	@Override
	public String getSelectedBackground() {
		return CSS + "selected-background";
	}
	
	@Override
	public String getIcDrawer() {
		return CSS + "ic-drawer";
	}

}
