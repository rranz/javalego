package com.javalego.security.exceptions;

import com.javalego.exception.LocalizedException;

/**
 * Cuenta bloqueada.
 * 
 * @author ROBERTO RANZ
 *
 */
public class LockedAccountException extends LocalizedException {

	private static final long serialVersionUID = 4459544772164810574L;

	public LockedAccountException(String message, Object... params) {
		super(message, params);
	}

}
