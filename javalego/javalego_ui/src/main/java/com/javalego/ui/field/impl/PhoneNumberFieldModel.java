package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Número de teléfono
 * 
 * @author ROBERTO RANZ
 */
public class PhoneNumberFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 3876819622615723113L;

	/**
	 * Nombre del campo del país de donde obtenemos el formato para su
	 * validación.
	 */
	private String countryFieldName;

	public PhoneNumberFieldModel() {
		init();
	}

	public PhoneNumberFieldModel(Key title) {
		super(title);
		init();
	}
	
	public PhoneNumberFieldModel(String name, Key title) {
		super(name, title);
		init();
	}

	public PhoneNumberFieldModel(String name, Key title, Key description) {
		super(name, title, description);
		init();
	}

	private void init() {
	}

	public String getCountryFieldName() {
		return countryFieldName;
	}

	public void setCountryFieldName(String countryFieldName) {
		this.countryFieldName = countryFieldName;
	}

}
