package com.javalego.ui.mvp.register;

import com.javalego.exception.LocalizedException;
import com.javalego.security.model.Profile;
import com.javalego.ui.patterns.IView;

/**
 * Vista de registro de usuarios
 * 
 * @author ROBERTO RANZ
 */
public interface IRegisterView extends IView {
	
	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	interface RegisterViewListener {
		
		/**
		 * Registrar usuario
		 * @param userName
		 * @param password
		 * @throws LocalizedException
		 */
		void register(String userName, String password) throws LocalizedException;

		/**
		 * Datos del perfil de usuario para su edición
		 * @return
		 */
		Profile getModel();

		/**
		 * Descartar registro
		 */
		void discard();
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	public void setListener(RegisterViewListener listener);

}
