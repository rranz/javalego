package com.javalego.security.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.javalego.exception.LocalizedException;
import com.javalego.security.model.IRole;
import com.javalego.security.services.UserServices;

/**
 * Custom Realm Shiro JAVALEGO
 * 
 * Utiliza la interface de servicios de acceso al modelo de datos básico de
 * usuarios, roles y permisos definido en JAVALEGO para gestionar la seguridad
 * básica independiente de la librería utilizada (Shiro, Spring Security, ...)
 */
public class DefaultShiroRealm extends AuthorizingRealm {

	/**
	 * Servicios de acceso a la información de usuario.
	 */
	private UserServices userServices;

	/**
	 * Constructor
	 * 
	 * @param userService
	 *            Servicios de acceso a la información de usuario para validar
	 *            usuario, credenciales y acceso a sus roles y permisos.
	 */
	public DefaultShiroRealm(UserServices userService) {

		this.userServices = userService;

		setName("JavaLegoRealm"); // This name must match the name in the User
								// class's getPrincipals() method

		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);

		setCredentialsMatcher(matcher);

		// Se incluye cache para evitar la recuperación de datos de usuario y
		// roles por cada comprobación de permisos y accesos de roles.
		// TODO pensar en un entorno real con posibles actualizaciones de
		// usuarios, roles y permisos de forma dinámica. Ver cómo recargar los
		// nuevos datos.
		// Utilizar @Cache(usage= CacheConcurrencyStrategy.READ_WRITE) en
		// entidades JPA por ejemplo y quitar esta cache para ver cómo actúa.
		setCacheManager(new org.apache.shiro.cache.MemoryConstrainedCacheManager());
		setCachingEnabled(true);

		matcher.setStoredCredentialsHexEncoded(false);
	}

	/**
	 * Simulates a call to an underlying data store - in a 'real' application,
	 * this call would communicate with an underlying data store via an EIS API
	 * (JDBC, JPA, Hibernate, etc).
	 * <p/>
	 * Note that when implementing your own realm, there is no need to check
	 * against a password (or other credentials) in this method. The
	 * {@link org.apache.shiro.realm.AuthenticatingRealm AuthenticatingRealm}
	 * superclass will do that automatically via the use of a configured
	 * {@link org.apache.shiro.authc.credential.CredentialsMatcher
	 * CredentialsMatcher} (see this example's corresponding {@code shiro.ini}
	 * file to see a configured credentials matcher).
	 * <p/>
	 * All that is required is that the account information include directly the
	 * credentials found in the EIS.
	 * 
	 * @param username
	 *            the username for the account data to retrieve
	 * @return the Account information corresponding to the specified username:
	 * @throws LocalizedException 
	 */
	protected SimpleAccount getAccount(String username) throws LocalizedException {

		// Verificar si existe el usuario.
		String credentials = userServices.getCredentials(username);

		// Si no existe, mostrar usuario no válido.
		if (credentials == null) {
			return null;
		}

		// Crear una cuenta de usuario donde se validará su credencial.
		SimpleAccount account = new SimpleAccount(username, credentials, getName());

		// Añadir roles y permisos a la cuenta si se autentica correctamente.
		addRoles(account);

		return account;
	}

	/**
	 * Incluir los roles de un usuario a la cuenta. Obtiene la lista de roles y
	 * permisos de la cuenta de usuario y los convierte en propiedades válidas
	 * para Shiro, obteniendo la funcionalidad requerida para nuestra aplicación
	 * y podremos usar las anotaciones @RequiredPermissions para definir acceso
	 * a los recursos de aplicación.
	 * 
	 * @param account
	 * @throws LocalizedException 
	 */
	private void addRoles(SimpleAccount account) throws LocalizedException {

		Collection<IRole> roles = userServices.getRoles(account.getPrincipals().toString());
		if (roles != null) {
			
			for (IRole role : roles) {
				
				// Añadir el rol a la cuenta
				account.addRole(role.getName());
				
				// Añadir los permisos de cada rol
				Collection<String> permissions = role.getPermissions();
				
				if (permissions != null) {
					account.addStringPermissions(role.getPermissions());
				}
			}
		}
	}

	// /**
	// * Obtener los roles de la cuenta de usuario.
	// * @param principal
	// * @return
	// */
	// public List<IRole> getRoles(String principal) {
	//
	// return Arrays.asList(new IRole[] {new IRole() {
	// @Override
	// public Collection<String> getPermissions() {
	// return null;
	// }
	//
	// @Override
	// public String getName() {
	// return "admin";
	// }
	//
	// @Override
	// public String getDescription() {
	// return "Administrador del sistema";
	// }
	// }});
	// }

	// /**
	// * Obtener los permisos de la cuenta de usuario.
	// * @param account
	// */
	// private void getPermissions(SimpleAccount account) {
	// //most applications would assign permissions to Roles instead of users
	// directly because this is much more
	// //flexible (it is easier to configure roles and then change role-to-user
	// assignments than it is to maintain
	// // permissions for each user).
	// // But these next lines assign permissions directly to this trivial
	// account object just for simulation's sake:
	// new Permission() {
	//
	// @Override
	// public boolean implies(Permission p) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	// };
	// account.addStringPermission("read"); //this user is allowed to 'edit'
	// _any_ blogEntry
	// account.addStringPermission("write"); //this user is allowed to 'edit'
	// _any_ blogEntry
	// account.addStringPermission("remove"); //this user is allowed to 'edit'
	// _any_ blogEntry
	// account.addStringPermission("blogEntry:edit"); //this user is allowed to
	// 'edit' _any_ blogEntry
	// //fine-grained instance level permission:
	// account.addStringPermission("printer:print:laserjet2000"); //allowed to
	// 'print' to the 'printer' identified
	// //by the id 'laserjet2000'
	// }

	// /**
	// * Obtener la credenciales del usuario.
	// * @param username
	// * @return
	// */
	// private String getCredentials(String username) {
	// return "47ehxf8NSp1hmFUmdMCw6z7dVGFOtABJXu9J+SzuLPA=";
	// }

	// /**
	// * Validar el usuario
	// * @param username
	// * @return
	// */
	// private boolean validUser(String username) {
	// return "guest".equals(username) || "roberto".equals(username);
	// }

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// we can safely cast to a UsernamePasswordToken here, because this
		// class 'supports' UsernamePasswordToken
		// objects. See the Realm.supports() method if your application will use
		// a different type of token.
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		try {
			return getAccount(upToken.getUsername());
		}
		catch (LocalizedException e) {
			throw new AuthenticationException(e);
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// get the principal this realm cares about:
		String username = (String) getAvailablePrincipal(principals);

		// call the underlying EIS for the account data:
		try {
			return getAccount(username);
		}
		catch (LocalizedException e) {
			throw new AuthenticationException(e);
		}
	}

}
