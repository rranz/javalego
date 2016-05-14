package com.javalego.store.model.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.crypto.hash.Sha256Hash;

import com.javalego.security.model.IRole;
import com.javalego.security.model.IUser;
import com.javalego.security.services.UserServices;
import com.javalego.store.items.impl.Role;
import com.javalego.store.items.impl.User;

/**
 * Generación de usuarios y roles para los miembros de la plataforma (ficticios).
 * 
 * @author ROBERTO RANZ
 *
 */
public class MockUserServices implements UserServices {

	/**
	 * Rol Admin
	 */
	private static final String ADMIN = "admin";

	/**
	 * Lista de todos los usuarios de la aplicación y sus roles asociados.
	 */
	private List<IUser> users = new ArrayList<IUser>();
	
	/**
	 * Lista de todos los roles de aplicación.
	 */
	private List<IRole> roles;

	/**
	 * Constructor
	 */
	public MockUserServices() {
		init();
	}

	/**
	 * Cargar inicial de todos los datos de usuarios y roles.
	 */
	private void init() {
		getAllUsers();
	}

	@Override
	public synchronized Collection<IUser> getAllUsers() {

		if (users == null || users.size() == 0) {

			users.add(new User("roberto", new Sha256Hash("RobertoAbc12").toBase64(), Arrays.asList(new IRole[] {findRole(ADMIN)})));
			users.add(new User("rafa", new Sha256Hash("Rafa12Abc").toBase64(), Arrays.asList(new IRole[] {findRole(ADMIN)})));
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
	public synchronized List<IRole> getAllRoles() {
		
		if (roles == null) {
			
			roles = new ArrayList<IRole>();
			
			roles.add(new Role(ADMIN));
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
