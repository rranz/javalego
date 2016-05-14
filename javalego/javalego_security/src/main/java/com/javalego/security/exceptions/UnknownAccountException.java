package com.javalego.security.exceptions;

import com.javalego.exception.LocalizedException;

/**
 * Cuenta desconocida.
 * 
 * @author ROBERTO RANZ
 *
 */
public class UnknownAccountException extends LocalizedException {

	private static final long serialVersionUID = -4653044584986495626L;

	public UnknownAccountException(String message, Object... params) {
		super(message, params);
	}

}
