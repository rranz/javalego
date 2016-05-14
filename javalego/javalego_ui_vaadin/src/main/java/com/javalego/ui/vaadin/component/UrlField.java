package com.javalego.ui.vaadin.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TextField;

/**
 * Número de teléfono
 * 
 * @author ROBERTO RANZ
 */
public class UrlField extends TextField {

	private static final long serialVersionUID = 6835465723973754045L;

	public UrlField() {
		init();
	}

	private void init() {
		setIcon(FontAwesome.CLOUD);
	}

}
