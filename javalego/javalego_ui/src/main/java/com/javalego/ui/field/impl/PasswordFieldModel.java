package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Password property
 * 
 * @author ROBERTO RANZ
 */
public class PasswordFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 8306946273515307068L;

	/**
	 * Necesita encriptación.
	 */
	private boolean encrypt = true;

	public PasswordFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param required
	 */
	public PasswordFieldModel(Key title, boolean required) {
		super(title);
		setRequired(required);
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
}
