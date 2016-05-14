package com.javalego.store.items;


/**
 * Proveedor de implementación del producto o proyecto.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IProvider extends IBaseItem {

	/**
	 * Dirección Web.
	 * @return
	 */
	String getUrl();
	
}
