package com.javalego.application;

import java.util.Collection;

import com.javalego.data.BusinessService;
import com.javalego.data.DataProvider;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.locale.translator.Translator;
import com.javalego.locale.translator.TranslatorCode;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.security.SecurityServices;
import com.javalego.security.services.UserServices;
import com.javalego.security.session.UserSession;

/**
 * Configuración de un entorno de ejecución.
 * 
 * <p>
 * El entorno de ejecución permite personalizar tanto sus datos básicos (nombre, título, ...) como los diferentes
 * contextos de servicios de aplicación como: localización, seguridad, acceso a datos, UI, ...
 * 
 * <p>
 * El entorno debe configurarse al inicio de la aplicación.
 * 
 * <p>
 * Los contextos se pueden usar individualmente. El entorno nos permite definir una configuración específica para
 * ejecutar nuestra aplicación (Ej.: entorno de pruebas, preproducción y producción).
 * 
 * @author ROBERTO RANZ
 *
 */
public interface Environment
{

	/**
	 * Nombre de referencia
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Título
	 * 
	 * @return
	 */
	public abstract Key getTitle();

	/**
	 * Descripción
	 * 
	 * @return
	 */
	public abstract Key getDescription();

	/**
	 * Lista de repositorios de iconos usados por la aplicación.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	public abstract Collection<RepositoryIcons<Icon>> getRepositoriesIcons() throws LocalizedException;

	/**
	 * Contexto de seguridad utilizado en nuestra aplicación.
	 * 
	 * @return
	 */
	public abstract SecurityServices getSecurity();

	/**
	 * Contexto de sesión de usuario utilizado en nuestra aplicación.
	 * 
	 * @return
	 */
	public abstract UserSession getUserSession();

	/**
	 * Traductor usado para la localización de textos. Por defecto, la arquitectura incluye el traductor de textos
	 * basado en anotaciones localizadas y enumerados. {@link TranslatorCode}
	 * 
	 * @return
	 */
	public abstract Translator getTranslator();

	/**
	 * Proveedor de datos utilizado.
	 * 
	 * @return
	 */
	public abstract DataProvider getDataProvider();

	/**
	 * Servicios negocio.
	 * 
	 * @return
	 */
	public abstract BusinessService getBusinessService();

	/**
	 * Gestión de usuarios de la aplicación. Esta información debe estar vinculado a tipo de seguridad definido para la
	 * aplicación.
	 * 
	 * @see SecurityServices
	 * @return
	 */
	public abstract UserServices getUserServices();

}
