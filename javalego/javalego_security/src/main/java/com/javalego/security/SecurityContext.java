package com.javalego.security;

import com.javalego.model.context.Context;
import com.javalego.security.services.UserServices;
import com.javalego.security.session.UserSession;

/**
 * Administrador de la seguridad de acceso a los recursos de nuestra aplicación.
 * <p>
 * Gestiona la autenticación de usuarios, obtiene los datos de la sesión de usuarios, gestiona los usuarios conectados y
 * acceso a recursos mediante roles y permisos.
 * <p>
 * Esta especificación debe utilizar algunos de los proveedores de seguridad existentes en la plataforma.
 * <p>
 * Actualmente, existe una implementación para Apache Shiro (proyecto javalego_security_shiro) que por su simplicidad y
 * potencial recomendamos.
 * <p>
 * Nota: integración Spring Security en desarrollo.
 * <p>
 * Puede desarrollar su propia tecnología de seguridad creando una clase que implemente la interface ISecurityContext e
 * integrarla en su aplicación estableciendo la variable current (setCurrent()) con una instancia de esta clase.
 * 
 * @author ROBERTO RANZ
 *
 */
public class SecurityContext implements Context
{

	/**
	 * Contexto de seguridad de la aplicación.
	 */
	private static SecurityContext current = new SecurityContext();

	/**
	 * Servicios de acceso al sistema (login, roles, ...)
	 */
	private SecurityServices securityServices;

	/**
	 * Servicios de gestión de usuarios de la aplicación
	 */
	private UserServices userServices;

	/**
	 * Datos de la sesión de usuario. En un entorno Web deberemos de obtenerlo de los datos de sesión de la request.
	 */
	private UserSession userSession;

	private SecurityContext()
	{
	}

	/**
	 * Obtiene la instancia de aplicación de administrador de la seguridad de acceso a la aplicación dependiendo del
	 * proveedor elegido (Shiro, Spring Security, JEE, ...)
	 * 
	 * @return
	 */
	public static SecurityContext getCurrent()
	{
		return current;
	}

	/**
	 * Establecer el contexto de seguridad que puede ser implementado para Shiro, Spring Security, JEE, ...
	 * implementando la interface ISecurityContext.
	 * 
	 * @param current
	 */
	public static synchronized void setCurrent(SecurityContext current)
	{
		SecurityContext.current = current;
	}

	/**
	 * Servicios de seguridad de acceso a la aplicación dependiendo del proveedor elegido (Shiro, Spring Security, JEE,
	 * ...)
	 * 
	 * @return
	 */
	public SecurityServices getServices()
	{
		return securityServices;
	}

	/**
	 * Servicios de seguridad de acceso a la aplicación dependiendo del proveedor elegido (Shiro, Spring Security, JEE,
	 * ...)
	 * 
	 * @param securityServices
	 */
	public void setServices(SecurityServices securityServices)
	{
		this.securityServices = securityServices;
	}

	/**
	 * Servicios de gestión de usuarios de la aplicación
	 */
	public UserServices getUserServices()
	{
		return userServices;
	}

	/**
	 * Servicios de gestión de usuarios de la aplicación
	 */
	public void setUserServices(UserServices userServices)
	{
		this.userServices = userServices;
	}

	/**
	 * Sesión de usuario para el contexto de ejecución de la aplicación.
	 * 
	 * @return
	 */
	public UserSession getUserSession()
	{
		return userSession;
	}

	/**
	 * Sesión de usuario para el contexto de ejecución de la aplicación.
	 * 
	 * @param userSession
	 */
	public void setUserSession(UserSession userSession)
	{
		this.userSession = userSession;
	}

}
