package com.javalego.ui.vaadin.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TextField;

/**
 * Campo Email.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class EmailField extends TextField {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public EmailField() {
		initialize();
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 */
	public EmailField(String caption) {

		this.setCaption(caption);
		initialize();
	}

	private void initialize() {
		setIcon(FontAwesome.ENVELOPE);
	}

}
