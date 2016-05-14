package com.javalego.security.exceptions;

import com.javalego.exception.LocalizedException;

/**
 * Credenciales incorrectas.
 * 
 * @author ROBERTO RANZ
 */
public class IncorrectCredentialsException extends LocalizedException {

	private static final long serialVersionUID = 1504279238507653632L;

	public IncorrectCredentialsException(String message, Object... params) {
		super(message, params);
	}

}
