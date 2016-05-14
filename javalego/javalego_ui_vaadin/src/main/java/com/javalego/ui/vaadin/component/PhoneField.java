package com.javalego.ui.vaadin.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TextField;

/**
 * Número de teléfono
 * 
 * @author ROBERTO RANZ
 */
public class PhoneField extends TextField {

	private static final long serialVersionUID = -6745074114506402900L;

	public PhoneField() {
		init();
	}

	private void init() {
		setIcon(FontAwesome.PHONE);
	}

}
