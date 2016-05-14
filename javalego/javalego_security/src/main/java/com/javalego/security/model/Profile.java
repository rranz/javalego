package com.javalego.security.model;

import java.util.Locale;

/**
 * Modelo de datos del perfil de usuario.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface Profile {

	/**
	 * Idioma de ejecución de aplicación.
	 * @return
	 */
	Locale getLocale();
	
	/**
	 * Nombre del usuario (en aplicaciones web equivale al Principal).
	 * @return
	 */
	String getUser();

	/**
	 * País de ejecución de la aplicación.
	 * @return
	 */
	String getCountry();
	
}
