package com.javalego.ui.mvp.profile;

import com.javalego.security.model.Profile;
import com.javalego.ui.patterns.IView;

/**
 * Vista de edición de los datos del perfil de usuario
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IProfileView extends IView {

	/**
	 * Listener Vista.
	 * @author ROBERTO RANZ
	 *
	 */
	public interface IPerfilViewListener {

		Profile getModel();
	}	
	
	public void setListener(IPerfilViewListener listener);
	
}
