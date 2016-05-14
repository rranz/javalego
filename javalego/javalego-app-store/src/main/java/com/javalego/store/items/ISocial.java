package com.javalego.store.items;

/**
 * Elemento básico de acceso a opciones de plataformas sociales, repositorios, ...
 * 
 * @see ResourcesUI
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ISocial {

	/**
	 * URL: Cuenta de twitter.
	 * @return
	 */
	String getTwitter();
	
	/**
	 * URL: Cuenta de linkedin
	 * @return
	 */
	String getLinkedin();
	
	/**
	 * URL: Blog (Wordpress ej.)
	 * @return
	 */
	String getBlog();
	
	/**
	 * URL: Cuenta de facebook
	 * @return
	 */
	String getFacebook();
	
	/**
	 * URL: Cuenta en Google+ 
	 * @return
	 */
	String getGooglePlus();

	/**
	 * Correo electrónico
	 * @return
	 */
	String getEmail();

	/**
	 * URL Web del autor o empresa
	 * @return
	 */
	String getWeb();
	
	/**
	 * URL: Foros
	 * @return
	 */
	String getForum();
	
}
