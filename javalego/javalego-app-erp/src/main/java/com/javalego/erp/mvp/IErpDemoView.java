package com.javalego.erp.mvp;

import com.javalego.ui.patterns.IView;

/**
 * Vista de ERP Demo
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IErpDemoView extends IView {

	/**
	 * Listener Vista.
	 * @author ROBERTO RANZ
	 *
	 */
	public interface IErpDemoViewListener {

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
	
	public void setListener(IErpDemoViewListener listener);
	
}
