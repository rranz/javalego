package com.javalego.store.items;

import java.util.Date;


/**
 * Miembro del equipo o comunidad (proyecto).
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IMember extends IBaseItem {
	
	/**
	 * Redes sociales.
	 * @return
	 */
	ISocial getSocial();
	
	/**
	 * Fecha de registro en la tienda.
	 * @return
	 */
	Date getRegistration();
	
	/**
	 * Imagen o foto representativa.
	 * @return
	 */
	byte[] getImage();
}
