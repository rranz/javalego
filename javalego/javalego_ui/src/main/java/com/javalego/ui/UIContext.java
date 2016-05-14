package com.javalego.ui;

import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.locale.LocaleContext;
import com.javalego.model.BaseModel;
import com.javalego.model.context.Context;
import com.javalego.model.keys.ErrorKey;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleSecurity;
import com.javalego.security.SecurityContext;
import com.javalego.security.exceptions.IncorrectCredentialsException;
import com.javalego.security.exceptions.LockedAccountException;
import com.javalego.security.exceptions.UnknownAccountException;
import com.javalego.ui.icons.RepositoryIconsUI;

/**
 * Contexto de interface de usuario
 * <p>
 * Ofrece métodos de utilidad globales a la aplicación que simplifican el
 * desarrollo.
 * </p>
 * <p>
 * Ej.: obtener la traducción de un texto utilizando el valor locale de la
 * sesión de usuario.
 * </p>
 * 
 * @author roberto
 * 
 */
public class UIContext implements Context {

	//private static final Logger logger = Logger.getLogger(UIContext.class);

	private static final UIContext current = new UIContext();

	/**
	 * Flag de carga de iconos finalizada
	 */
	//private boolean loadedIcons;

	/**
	 * País por defecto.
	 */
	private String country;

	protected UIContext() {
	}

	public static UIContext getCurrent() {
		return current;
	}

	/**
	 * Obtiene una excepción cuyo texto se obtiene de un enumerado (Key) que
	 * define dicho texto en varios idiomas.
	 * 
	 * @param exception
	 *            Excepción
	 * @param key
	 *            Texto de la excepción multiidioma
	 * @param params
	 *            Lista de parámetros a incluir en el texto (Ej.: Campo {0} y
	 *            campo {1} sin valor).
	 * @return
	 */
	public static LocalizedException getException(Exception exception, ErrorKey key, String... params) {

		return new LocalizedException(exception, getUserSessionLocale(), key, params);
	}

	/**
	 * Obtiene una excepción cuyo texto se obtiene de un enumerado (Key) que
	 * define dicho texto en varios idiomas.
	 * 
	 * @param key
	 *            Texto de la excepción multiidioma
	 * @param params
	 *            Lista de parámetros a incluir en el texto (Ej.: Campo {0} y
	 *            campo {1} sin valor).
	 * @return
	 */
	public static LocalizedException getException(ErrorKey key, String... params) {

		return new LocalizedException(null, getUserSessionLocale(), key, params);
	}

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo value
	 * de la enumeración Locale.
	 * 
	 * @param value
	 * @return
	 */
	public static String getText(Key value, Object... params) {

		if (LocaleContext.getCurrent() != null) {
			return LocaleContext.getText(value, getUserSessionLocale(), params);
		}
		else {
			return value.name();
		}
	}

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo
	 * description de la enumeración Locale.
	 * 
	 * @param value
	 * @return
	 */
	public static String getDescription(Key value, Object... params) {

		if (LocaleContext.getCurrent() != null) {
			return LocaleContext.getDescription(value, getUserSessionLocale(), params);
		}
		else {
			return value.name();
		}
	}

	/**
	 * Obtiene la traducción del título de un objeto BaseModel utilizando el
	 * valor locale de la sesión de usuario.
	 * 
	 * @param value
	 * @return
	 */
	public static String getTitle(BaseModel model, Object... params) {

		if (LocaleContext.getCurrent() != null) {
			return LocaleContext.getTitle(model, getUserSessionLocale(), params);
		}
		else {
			return model.toString();
		}
	}

	/**
	 * Obtiene la traducción de la descripción de un objeto BaseModel utilizando
	 * el valor locale de la sesión de usuario.
	 * 
	 * @param value
	 * @return
	 */
	public static String getDescription(BaseModel model, Object... params) {

		if (LocaleContext.getCurrent() != null) {
			return LocaleContext.getDescription(model, getUserSessionLocale(), params);
		}
		else {
			return model.toString();
		}
	}

	/**
	 * Obtiene el idioma de la sesión de usuario.
	 * 
	 * @param value
	 * @return
	 */
	public static Locale getUserSessionLocale() {

		return hasUserSession() ? SecurityContext.getCurrent().getUserSession().getLocale() : (LocaleContext.getCurrent() != null ? LocaleContext.getDefaultLocale() : Locale.getDefault());
	}
//
//	/**
//	 * Cargar los iconos estándar de editores UI.
//	 * 
//	 * @throws LocalizedException
//	 */
//	private void loadEditorIcons() throws LocalizedException {
//
//		if (!loadedIcons && LocaleContext.hasRepositoriesIcons()) {
//
//			for (RepositoryIcons<?> item : LocaleContext.getCurrent().getRepositoriesIcons()) {
//
//				if (item instanceof ResourceRepositoryIcons) {
//
//					((ResourceRepositoryIcons<?>) item).load(IconEditor.class, "/icons_editor");
//
//					loadedIcons = true;
//				}
//			}
//		}
//
//	}

	/**
	 * Cambiar el idioma de la sesión de usuario.
	 * 
	 * @param locale
	 */
	public static void setUserSessionLocale(Locale locale) {

		if (hasUserSession()) {
			SecurityContext.getCurrent().getUserSession().setLocale(locale);
		}
	}

	/**
	 * Adaptar el mensaje de error al idioma actual.
	 * 
	 * @param exception
	 */
	public static LocalizedException adapterSecurityException(LocalizedException exception) {

		if (exception instanceof IncorrectCredentialsException) {
			return new LocalizedException(getText(LocaleSecurity.INCORRECT_CREDENTIALS), exception.getParameters());
		}
		else if (exception instanceof LockedAccountException) {
			return new LocalizedException(getText(LocaleSecurity.LOCKED_ACCOUNT), exception.getParameters());
		}
		else if (exception instanceof UnknownAccountException) {
			return new LocalizedException(getText(LocaleSecurity.UNKNOWN_ACCOUNT), exception.getParameters());
		}

		return exception;
	}

	/**
	 * Obtener el código del país de la sesión de usuario o del sistema
	 * operativo.
	 * 
	 * @return
	 */
	public String getCountry() {

		if (country != null) {
			return country;
		}

		// Buscar el país de la sesión de usuario.
		else if (hasUserSession()) {
			return SecurityContext.getCurrent().getUserSession().getLocale().getCountry();
		}
		// Obtener el país de las variables de entorno java.
		else {
			String code = System.getProperty("user.country");
			return "".equals(code) || code == null ? null : code;
		}
	}

	/**
	 * Comprueba si existe un servicios de obtención de datos de la sesión de
	 * usuario.
	 * 
	 * @return
	 */
	private static boolean hasUserSession() {
		return SecurityContext.getCurrent() != null && SecurityContext.getCurrent().getUserSession() != null;
	}

	/**
	 * Establecer el país por defecto para la aplicación. En entornos como
	 * Android se obtiene de los recursos Ej.:
	 * context.getResources().getConfiguration().locale.getCountry()
	 * 
	 * @param code
	 */
	public void setCountry(String code) {
		country = code;
	}

	/**
	 * Logout del usuario en la aplicación. 1. Realiza el logout de la seguridad
	 * activa de la aplicación {@link SecurityContext} 2. Recarga la aplicación
	 * para volver a solicitar la página de inicio.
	 */
	public static void logout() {

		if (SecurityContext.getCurrent() != null && SecurityContext.getCurrent().getServices() != null) {
			SecurityContext.getCurrent().getServices().logout();
		}

		if (hasUserSession()) {
			SecurityContext.getCurrent().getUserSession().logout();
		}

	}

	/**
	 * Obtener el componente UI a partir de la clave de un icono registrado en
	 * los repositorios de imágenes.
	 * 
	 * @param icon
	 * @return
	 * @throws LocalizedException
	 */
	public static Object getComponent(Icon icon) throws LocalizedException {

		return getCurrent().getIconComponent(icon);
	}

	/**
	 * Obtener el componente UI a partir de la clave de un icono registrado en
	 * los repositorios de imágenes.
	 * 
	 * @param icon
	 * @return
	 * @throws LocalizedException
	 */
	public Object getIconComponent(Icon icon) throws LocalizedException {

		return getIconComponent(icon, null);
	}

	/**
	 * Obtener el componente UI a partir de la clave de un icono registrado en
	 * los repositorios de imágenes.
	 * 
	 * @param icon
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getIconComponent(Icon icon, Locale locale) throws LocalizedException {

		RepositoryIcons<?> item = LocaleContext.findRepositoryIcons(icon, locale);
		if (item != null) {
			if (item instanceof RepositoryIconsUI) {
				return ((RepositoryIconsUI) item).getComponent(icon);
			}
		}
		return null;
	}



}
