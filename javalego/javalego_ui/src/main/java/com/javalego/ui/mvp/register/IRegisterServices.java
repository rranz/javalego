package com.javalego.ui.mvp.register;

import com.javalego.exception.LocalizedException;
import com.javalego.security.model.Profile;
import com.javalego.ui.patterns.IService;

/**
 * Servicios de registro de usuarios
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IRegisterServices extends IService {

	/**
	 * Registrar usuario con los datos de nombre y contraseña.
	 * 
	 * @param userName
	 * @param password
	 * @throws LocalizedException
	 */
	void register(String userName, String password) throws LocalizedException;

	/**
	 * Datos del perfil de usuario
	 * 
	 * @return
	 */
	Profile getModel();
}
