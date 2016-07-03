package com.javalego.security.shiro;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import com.javalego.exception.LocalizedException;
import com.javalego.security.impl.AbstractSecurityServices;
import com.javalego.security.model.IRole;

/**
 * Servicios de Seguridad implementados con Shiro
 * 
 * @author ROBERTO RANZ
 * 
 */
public class SecurityShiro extends AbstractSecurityServices {

	/**
	 * Path del archivo shiro.ini de configuración.
	 */
	private String shiroini;

	private SecurityManager securityManager;

	private static final transient Logger logger = Logger.getLogger(SecurityShiro.class);

	/**
	 * Constructor
	 * 
	 * @param shiroini
	 *            fichero de configuración de Shiro.
	 */
	public SecurityShiro(String shiroini) {
		this.shiroini = shiroini;
		init(null);
	}

	/**
	 * Constructor
	 * 
	 * @param realm
	 *            Realm utilizado.
	 */
	public SecurityShiro(Realm realm) {
		init(new DefaultSecurityManager(realm));
	}

	/**
	 * Constructor
	 * 
	 * @param securityManager
	 */
	public SecurityShiro(SecurityManager securityManager) {
		init(securityManager);
	}

	/**
	 * Inicializar Shiro
	 */
	private void init(SecurityManager securityManager) {

		this.securityManager = securityManager;

		// The easiest way to create a Shiro SecurityManager with configured
		// realms, users, roles and permissions is to use the simple INI config.
		// We'll do that by using a factory that can ingest a .ini file and
		// return a SecurityManager instance:

		// Use the shiro.ini file at the root of the classpath
		// (file: and url: prefixes load from files and urls respectively):
		if (shiroini != null) {
			Factory<SecurityManager> factory = new IniSecurityManagerFactory(shiroini);

			// for this simple example quickstart, make the SecurityManager
			// accessible as a JVM singleton. Most applications wouldn't do this
			// and instead rely on their container configuration or web.xml for
			// webapps. That is outside the scope of this simple quickstart, so
			// we'll just do the bare minimum so you can continue to get a feel
			// for things.
			SecurityUtils.setSecurityManager(factory.getInstance());
		}
		else {
			// for this simple example quickstart, make the SecurityManager
			// accessible as a JVM singleton. Most applications wouldn't do this
			// and instead rely on their container configuration or web.xml for
			// webapps. That is outside the scope of this simple quickstart, so
			// we'll just do the bare minimum so you can continue to get a feel
			// for things.
			SecurityUtils.setSecurityManager(securityManager);
		}
	}

	@Override
	public void login(String user, String password) throws LocalizedException {

		// get the currently executing user:
		Subject currentUser = SecurityUtils.getSubject();

		// Actualizar el realm del security manager web establecido con el
		// filtro de Shiro en el web.xml con el real del securitymanager de
		// contexto.
		if (securityManager != SecurityUtils.getSecurityManager() && securityManager instanceof DefaultSecurityManager && SecurityUtils.getSecurityManager() instanceof DefaultWebSecurityManager) {
			((DefaultWebSecurityManager) SecurityUtils.getSecurityManager()).setRealm(((DefaultSecurityManager) securityManager).getRealms().iterator().next());
			securityManager = null;
		}

		// Lista de usuarios. (solo los nombres).
		// org.apache.shiro.SecurityUtils.getSubject().getPrincipals();

		// let's login the current user so we can check against roles and
		// permissions:
		if (!currentUser.isAuthenticated()) {

			UsernamePasswordToken token = new UsernamePasswordToken(user, password);

			token.setRememberMe(true);

			try {
				currentUser.login(token);

				logger.debug("Login '" + user + "' " + (getCurrentUser().isAuthenticated() ? "OK" : "KO"));

				// ModularRealmAuthorizer a = (ModularRealmAuthorizer)
				// ((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getAuthorizer();

				//
				// info =
				// ((AuthenticatingRealm)((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getRealms().iterator().next()).getAuthenticationInfo(token);
				// AuthenticationInfo info =
				// ((AuthenticatingRealm)((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getRealms().toArray()[0]).getAuthenticationInfo(token);
				// ((Account)info).getRoles();
				// System.out.println(info);

				// Se elimina porque la sesión expiraría.
				// currentUser.logout();
			}
			catch (UnknownAccountException uae) {
				String message = "There is no user with username of " + token.getPrincipal();
				logger.info(message);
				throw new com.javalego.security.exceptions.UnknownAccountException(message, token.getPrincipal());
			}
			catch (IncorrectCredentialsException ice) {
				String message = "Password for account " + token.getPrincipal() + " was incorrect!";
				logger.info(message);
				throw new com.javalego.security.exceptions.IncorrectCredentialsException(message, token.getPrincipal());
			}
			catch (LockedAccountException lae) {
				String message = "The account for username " + token.getPrincipal() + " is locked.  " + "Please contact your administrator to unlock it.";
				logger.info(message);
				throw new com.javalego.security.exceptions.LockedAccountException(message, token.getPrincipal());
			}
			catch (AuthenticationException ae) {
				String message = "AuthenticationException " + ae.getMessage();
				logger.info(message);
				throw new LocalizedException(message);
			}
			catch (RuntimeException ae) {
				String message = "AuthenticationException " + ae.getMessage();
				logger.info(message);
				throw new LocalizedException(message);
			}
		}
	}

	@Override
	public boolean hasRole(IRole role) {
		return role != null ? getCurrentUser().hasRole(role.getName()) : false;
	}

	@Override
	public boolean hasRole(String role) {
		return role != null ? getCurrentUser().hasRole(role) : false;
	}

	@Override
	public boolean isAuthenticated() {
		return getCurrentUser().isAuthenticated();
	}

	private Subject getCurrentUser() {
		return SecurityUtils.getSubject();
	}

	@Override
	public Object getPrincipal() {
		return SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * Comprobar si el usuario tiene los permisos para usar al objeto.
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public boolean isPermitted(Object object) {

		// Si no está autenticado, false
		if (object == null || !getCurrentUser().isAuthenticated()) {
			return false;
		}

		// Validar permisos con api Shiro wilchards o comodines Ej.:
		// "printer:name"
		if (object instanceof String) {
			return SecurityUtils.getSubject().isPermitted(object.toString());
		}

		boolean permitted = false;
		// 1. Verificar si tiene permisos definidos con anotaciones estándar
		// JAVALEGO. Ej.: @CustomPermission, @HasRole, ...
		if (!super.isPermitted(object)) {
			return false;
		}
		else {
			permitted = true;
		}

		// 2. Buscar la anotación que define el recurso asociado al objeto que
		// tendrá que tener vinculado alguno de los roles del subject.
		RequiresPermissions permissions = object.getClass().getAnnotation(RequiresPermissions.class);
		if (permissions != null) {
			for (String permission : permissions.value()) {
				if (permissions.logical() == Logical.OR && getCurrentUser().isPermitted(permission)) {
					return true;
				}
				else if (permissions.logical() == Logical.AND && !getCurrentUser().isPermitted(permission)) {
					return false;
				}
			}
		}

		// 3. Si se definen los roles que tienen acceso al recurso mediante la
		// anotación RequiresRoles, validar con el usuario para poder dar
		// permiso de acceso al recurso.
		boolean checkRoles = true;
		RequiresRoles roles = object.getClass().getAnnotation(RequiresRoles.class);
		if (roles != null) {
			try {
				for (String role : roles.value()) {
					if (role != null) {
						for (String item : role.split("\\,")) {
							if (getCurrentUser().hasRole(item)) {
								checkRoles = true;
								break;
							}
						}
					}
				}
			}
			catch (AuthorizationException e) {
				checkRoles = false;
			}
		}

		return permissions != null && permissions.logical() == Logical.AND && checkRoles ? true : permitted;
	}

	@Override
	public void logout() {
		if (getCurrentUser() != null) {
			getCurrentUser().logout();
		}
	}

	@Override
	public boolean hasAnyRole(String... roles) {

		if (roles != null) {
			for (String item : roles) {
				if (getCurrentUser().hasRole(item)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasAnyRole(IRole... roles) {

		if (roles != null) {
			for (IRole item : roles) {
				if (hasRole(item)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasAllRoles(String... roles) {

		if (roles != null) {
			return getCurrentUser().hasAllRoles(Arrays.asList(roles));
		}
		return false;
	}

	@Override
	public boolean hasAllRoles(IRole... roles) {

		if (roles != null) {
			for (IRole item : roles) {
				if (!hasRole(item)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getName()
	{
		return "SHIRO";
	}

	@Override
	public String getTitle()
	{
		return "Shiro Security Library";
	}

	@Override
	public String getDescription()
	{
		return null;
	}

	// /**
	// * Obtener la lista de roles del Realm principal.
	// * @return
	// */
	// public Collection<String> getRoles() {
	//
	// if (info != null) {
	// return ((Account)info).getRoles();
	// }
	//
	// // Realm realm =
	// ((DefaultSecurityManager)SecurityUtils.getSecurityManager()).getRealms().iterator().next();
	// //
	// // if (realm instanceof MyRealm) {
	// // return
	// ((MyRealm)realm).getRoles(getCurrentUser().getPrincipal().toString());
	// // }
	// else {
	// return null;
	// }
	// }
}
