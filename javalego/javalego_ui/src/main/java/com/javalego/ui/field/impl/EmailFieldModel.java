package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Propiedad que representa una dirección de correo electrónico.
 * @author ROBERTO RANZ
 */
public class EmailFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 4851926174817212244L;

	public EmailFieldModel() {
		init();
	}

	public EmailFieldModel(Key title) {
		super(title);
		init();
	}

	public EmailFieldModel(Key title, boolean required) {
		super(title);
		setRequired(required);
		init();
	}

	public EmailFieldModel(String name, Key title) {
		super(name, title);
		init();
	}

	public EmailFieldModel(String name, Key title, Key description) {
		super(name, title, description);
		init();
	}

	private void init() {
		setLowercase(true);
	}	
}
