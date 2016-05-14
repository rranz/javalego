package com.javalego.ui.mvp.login;

import com.javalego.exception.LocalizedException;

/**
 * Servicios de login
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ILoginServices {

	/**
	 * Validar usuario y contraseña.
	 * 
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public void login(String user, String password) throws LocalizedException;

	/**
	 * Validar la existencia de un usuario
	 * 
	 * @param user
	 * @return
	 */
	public boolean exist(String user);
}
