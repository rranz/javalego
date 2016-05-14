package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.crypto.hash.Sha256Hash;

import com.javalego.security.model.IRole;
import com.javalego.security.model.IUser;
import com.javalego.security.services.UserServices;

/**
 * Miembros registrados en la plataforma como desarrolladores o empresas.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TestUserServices implements UserServices {

	/**
	 * Rol Admin
	 */
	private static final String ADMIN = "admin";

	/**
	 * Lista de todos los usuarios de la aplicación y sus roles asociados.
	 */
	private List<IUser> users = new ArrayList<IUser>();
	
	/**
	 * Lista de todos los roles de aplicaci�n.
	 */
	private List<IRole> roles;

	/**
	 * Constructor
	 */
	public TestUserServices() {
		init();
	}

	/**
	 * Cargar inicial de todos los datos de usuarios y roles.
	 */
	private void init() {
		getAllUsers();
		getAllRoles();
	}

	@Override
	public synchronized Collection<IUser> getAllUsers() {

		if (users == null || users.size() == 0) {

			users.add(new IUser() {

				@Override
				public String getName() {
					return "roberto";
				}

				@Override
				public String getPassword() {
					return new Sha256Hash("RobertoAbc12").toBase64();
				}

				@Override
				public List<IRole> getRoles() {
					return Arrays.asList(new IRole[] {findRole(ADMIN)});
				}
			});
			users.add(new IUser() {

				@Override
				public String getName() {
					return "rafa";
				}

				@Override
				public String getPassword() {
					return new Sha256Hash("Rafa12Abc").toBase64();
				}

				@Override
				public List<IRole> getRoles() {
					return Arrays.asList(new IRole[] {findRole(ADMIN)});
				}
			});
		}

		return users;
	}

	/**
	 * Localizar un rol por su nombre
	 * @param role
	 * @return
	 */
	private IRole findRole(String role) {
		
		for(IRole item : getAllRoles()) {
			if (item.getName().equals(role)) {
				return item; 
			}
		}
		return null;
	}
	
	/**
	 * Lista de todos los roles disponibles de la aplicación
	 * @return
	 */
	private synchronized List<IRole> getAllRoles() {
		
		if (roles == null) {
			
			roles = new ArrayList<IRole>();
			
			roles.add(new IRole() {

				@Override
				public Collection<String> getPermissions() {
					return null;
				}

				@Override
				public String getName() {
					return ADMIN;
				}

				@Override
				public String getDescription() {
					return null;
				}
			});
		}
		return roles;
	}
	
	@Override
	public IUser findUser(String username) {

		for (IUser user : getAllUsers()) {
			if (username.equals(user.getName())) {
				return user;
			}
		}

		return null;
	}

	@Override
	public void deleteUser(String username) {

		users.remove(findUser(username));
	}

	@Override
	public void updateUser(IUser user) {

	}

	@Override
	public String getCredentials(String username) {

		IUser user = findUser(username);

		return user != null ? user.getPassword() : null;
	}

	@Override
	public synchronized Collection<IRole> getRoles(String username) {

		IUser user = findUser(username);
		
		return user != null ? user.getRoles() : null;
	}

	@Override
	public void addUser(IUser user) {
		users.add(user);
	}

}
