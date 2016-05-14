package com.javalego.ui.mvp.login;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.patterns.IView;

/**
 * Vista de login de usuario
 * 
 * @author ROBERTO RANZ
 */
public interface ILoginView extends IView {
	
	/**
	 * Fijar los datos de usuario y password iniciales.
	 * @param user
	 * @param password
	 */
	public void setData(String user, String password);

	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	interface LoginViewListener {
		
		/**
		 * Validación de usuario
		 * @param user
		 * @param password
		 * @throws LocalizedException
		 */
		void login(String user, String password) throws LocalizedException;

		/**
		 * Incluir opción de registrarse
		 * @return
		 */
		boolean hasRegister();

		/**
		 * Registrar usuario
		 * @return
		 */
		void register() throws LocalizedException;
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	public void setListener(LoginViewListener listener);

}
