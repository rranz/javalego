package com.javalego.security.session;

import java.util.Locale;

import com.javalego.model.context.Services;
import com.javalego.security.model.Profile;

/**
 * Datos de la sesión de usuario.
 * <p>
 * Deberemos de implementar nuestra propia clase para obtener esta información.
 * <p>
 * Si estamos usando algún framework Web como Vaadin o Spring MVC, deberemos de localizar el API que nos facilite esta
 * información.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface UserSession extends Services
{
	/**
	 * Gets the default locale for this session.
	 * 
	 * By default this is the preferred locale of the user using the session. In most cases it is read from the browser
	 * defaults.
	 * 
	 * @return the locale of this session.
	 */
	Locale getLocale();

	/**
	 * Sets the default locale for this session.
	 * 
	 * @param locale
	 */
	void setLocale(Locale locale);

	/**
	 * Obtener el código de idioma de locale. Sirver para abreviar el código.
	 * 
	 * @return
	 */
	String getLanguage();

	/**
	 * Logout del usuario en la aplicación.
	 */
	void logout();

	/**
	 * Establecer el perfil de usuario de la sesión
	 * 
	 * @param profile
	 */
	void setProfile(Profile profile);

	/**
	 * Perfil de la sesión de usuario
	 * 
	 * @return
	 */
	Profile getProfile();

	/**
	 * El dispositivo del usuario tiene una pantalla de tipo pequeño. Normalmente, menor a 600px
	 * 
	 * @return
	 */
	boolean isSmallScreen();

	/**
	 * El dispositivo del usuario tiene una pantalla de tipo medio. Normalmente, entre 601px y 1050px
	 * 
	 * @return
	 */
	boolean isMediumScreen();

	/**
	 * El dispositivo del usuario tiene una pantalla grande. Normalmente, mayor a 1050px
	 * 
	 * @return
	 */
	boolean isLargeScreen();

}
