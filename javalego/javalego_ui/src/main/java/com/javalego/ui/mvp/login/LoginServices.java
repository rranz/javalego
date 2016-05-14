package com.javalego.ui.mvp.login;

import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
import com.javalego.security.exceptions.IncorrectCredentialsException;
import com.javalego.ui.UIContext;

/**
 * Servicios de login
 * 
 * @author ROBERTO RANZ
 *
 */
public class LoginServices implements ILoginServices {

	@Override
	public void login(String user, String password) throws LocalizedException {

		// Eliminar caracteres para evitar injección sql.
		user = user.replace("'", "").replace("\"", "");
		password = password.replace("'", "").replace("\"", "");

		// Ejecutar Login en el sistema de seguridad de GANA
		try {
			if (SecurityContext.getCurrent().getServices() != null) {
				SecurityContext.getCurrent().getServices().login(user, password);
			}
		}
		catch(LocalizedException e) {
			throw UIContext.adapterSecurityException(e);
		}
	}

	@Override
	public boolean exist(String user) {
		
		user = user.replace("'", "").replace("\"", "");

		// Ejecutar Login en el sistema de seguridad de GANA
		try {
			if (SecurityContext.getCurrent().getServices() != null) {
				SecurityContext.getCurrent().getServices().login(user, "");
			}
			return false;
		}
		catch(LocalizedException e) {
			return e instanceof IncorrectCredentialsException;
		}
	}	

}
