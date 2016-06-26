package com.javalego.security;

import com.javalego.exception.LocalizedException;
import com.javalego.model.context.Services;
import com.javalego.model.pattern.Adapter;
import com.javalego.security.model.IRole;

/**
 * Servicios de seguridad para la autenticación de usuarios y validación de acceso a recursos por rol.
 * <p>
 * Actualmente, puede utilizar el adaptador de Apache Shiro que por su simplicidad y potencial recomendamos.
 * <p>
 * Puede desarrollar su propia tecnología de seguridad creando una clase que implemente la interface SecurityServices e
 * integrarla en su aplicación estableciendo la variable current (setCurrent()) con una instancia de esta clase.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface SecurityServices extends Services, Adapter
{
	/**
	 * Login al sistema definiendo un token de seguridad básico: usuario y contraseña.
	 * 
	 * @param user
	 * @param password
	 * @throws LocalizedException
	 */
	void login(String user, String password) throws LocalizedException;

	/**
	 * Cerrar la sesión de usuario.
	 */
	void logout();

	/**
	 * Verificar que el usuario tiene asignado el rol pasado como parámetro
	 * 
	 * @param role
	 * @return
	 */
	boolean hasRole(String role);

	/**
	 * Verificar que el usuario tiene asignado el rol pasado como parámetro
	 * 
	 * @param role
	 * @return
	 */
	boolean hasRole(IRole role);

	/**
	 * Comprueba si el usuario tiene algunos de los roles pasados como parámetro
	 * 
	 * @param roles
	 * @return
	 */
	boolean hasAnyRole(String... roles);

	/**
	 * Comprueba si el usuario tiene algunos de los roles pasados como parámetro
	 * 
	 * @param roles
	 * @return
	 */
	boolean hasAnyRole(IRole... roles);

	/**
	 * Comprueba si el usuario tiene todos los roles pasados como parámetro
	 * 
	 * @param roles
	 * @return
	 */
	boolean hasAllRoles(String... roles);

	/**
	 * Comprueba si el usuario tiene todo los roles pasados como parámetro
	 * 
	 * @param roles
	 * @return
	 */
	boolean hasAllRoles(IRole... roles);

	/**
	 * Verifica el usuario de sesión está autenticadado.
	 * 
	 * @return
	 */
	boolean isAuthenticated();

	/**
	 * Obtiene el principal de la sesión de usuario.
	 * 
	 * @return
	 */
	Object getPrincipal();

	/**
	 * Comprueba si el usuario tiene permisos de acceso al objeto o recurso.
	 * 
	 * @param object
	 * @return
	 */
	boolean isPermitted(Object object);

}
