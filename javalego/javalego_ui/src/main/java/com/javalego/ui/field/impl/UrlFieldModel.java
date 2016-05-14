package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Propiedad que representa una url.
 * 
 * @author ROBERTO RANZ
 */
public class UrlFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 1854774253198504543L;

	public UrlFieldModel() {
		init();
	}

	public UrlFieldModel(String name, Key title) {
		super(name, title);
		init();
	}

	public UrlFieldModel(String name, Key title, Key description) {
		super(name, title, description);
		init();
	}

	private void init() {
	}

}
