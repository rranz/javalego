package com.javalego.store.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.data.DataContext;
import com.javalego.exception.LocalizedException;
import com.javalego.security.model.IRole;
import com.javalego.security.model.IUser;
import com.javalego.security.services.UserServices;
import com.javalego.store.items.impl.Role;
import com.javalego.store.items.impl.User;

/**
 * Miembros registrados en la plataforma como desarrolladores o empresas.
 * 
 * @author ROBERTO RANZ
 *
 */
public class UserServicesImpl implements UserServices {

	/**
	 * Constructor
	 */
	public UserServicesImpl() {
	}

	@Override
	public synchronized Collection<IUser> getAllUsers() throws LocalizedException {

		List<User> list = DataContext.getProvider().findAll(User.class);
		
		List<IUser> users = new ArrayList<IUser>();
		users.addAll(list);
		
		return users;
	}

	/**
	 * Lista de todos los roles disponibles de la aplicación
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings({ "unused" })
	private synchronized Collection<IRole> getAllRoles() throws LocalizedException {

		List<Role> list = DataContext.getProvider().findAll(Role.class);

		List<IRole> roles = new ArrayList<IRole>();
		roles.addAll(list);
		
		return roles;
	}

	@Override
	public IUser findUser(String username) throws LocalizedException {

		return (IUser) DataContext.getProvider().find(User.class, "name = '" + username + "'");
	}

	@Override
	public void deleteUser(String username) throws LocalizedException {

		DataContext.getProvider().delete((User) findUser(username));
	}

	@Override
	public void updateUser(IUser user) throws LocalizedException {

		DataContext.getProvider().save((User) user);
	}

	@Override
	public String getCredentials(String username) throws LocalizedException {

		IUser user = findUser(username);

		return user != null ? user.getPassword() : null;
	}

	@Override
	public synchronized Collection<IRole> getRoles(String username) throws LocalizedException {

		IUser user = findUser(username);

		return user != null ? user.getRoles() : null;
	}

	@Override
	public void addUser(IUser user) throws LocalizedException {

		DataContext.getProvider().save((User) user);
	}

}
