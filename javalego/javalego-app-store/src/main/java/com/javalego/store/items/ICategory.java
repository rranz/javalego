package com.javalego.store.items;

import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.locale.LocaleStore;


/**
 * Categoría de componentes de la tienda
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ICategory {

	/**
	 * Tipo de organización de componentes de la tienda.
	 * @return
	 */
	Type getType();
	
	/**
	 * Clave
	 * @return
	 */
	LocaleStore getCode();
	
	/**
	 * Icono
	 * @return
	 */
	MenuIcons2 getIcon();
}
