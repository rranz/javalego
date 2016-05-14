package com.javalego.store.mvp.home;

import com.javalego.ui.patterns.IView;

/**
 * Vista de la tienda JAVALEGO
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IStoreView extends IView {

	/**
	 * Listener Vista.
	 * @author ROBERTO RANZ
	 *
	 */
	public interface IStoreViewListener {

		/**
		 * Recargar aplicación para actualizar idioma de la sesión de usuario.
		 */
		void reload();

		/**
		 * Logout de la aplicación.
		 */
		void logout();

		/**
		 * Comprueba si el usuario de la sesión está autenticado en el sistema.
		 * @return
		 */
		boolean isAuthenticated();

	}	
	
	public void setListener(IStoreViewListener listener);
	
}
