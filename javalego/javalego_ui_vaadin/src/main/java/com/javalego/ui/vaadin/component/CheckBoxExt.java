package com.javalego.ui.vaadin.component;

import com.vaadin.ui.CheckBox;

/**
 * Extensión del componente CheckBox para añadir funcionalidades adiciones 
 * 
 * @author ROBERTO RANZ
 */
public class CheckBoxExt extends CheckBox {

	private static final long serialVersionUID = 1L;

	public CheckBoxExt(String caption) {
		super(caption);
	}

	public CheckBoxExt() {
	}

	/**
	 * Valor boolean
	 * 
	 * @return
	 */
	public boolean getBoolean() {
		return getValue() != null ? (Boolean) getValue() : false;
	}

	/**
	 * Obtener el texto de un valor boolen (true o false)
	 * 
	 * @return
	 */
	public String getTextBoolean() {
		return getValue() != null && (Boolean) getValue() ? "true" : "false";
	}
}
