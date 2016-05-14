package com.javalego.ui.vaadin.component;

import com.vaadin.ui.PasswordField;

/**
 * Extensión PasswordField para incluir nuevas funcionalidad y formatos
 * visuales.
 * 
 * @author ROBERTO RANZ
 */
public class PasswordFieldExt extends PasswordField {

	private static final long serialVersionUID = 5461750021540999891L;

	public PasswordFieldExt() {
	}
	
	public PasswordFieldExt(String caption) {
		super(caption);
	}

	public String getText() {

		return getValue() != null ? getValue().toString() : "";
	}
}
