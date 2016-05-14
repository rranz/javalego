package com.javalego.store.model.impl;

import java.util.Collection;

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

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Collection<IUser> getAllUsers() throws LocalizedException {

		return (Collection<IUser>) DataContext.getProvider().getList(User.class);
	}

	/**
	 * Lista de todos los roles disponibles de la aplicación
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private synchronized Collection<IRole> getAllRoles() throws LocalizedException {

		return (Collection<IRole>) DataContext.getProvider().getList(Role.class);
	}

	@Override
	public IUser findUser(String username) throws LocalizedException {

		return (IUser) DataContext.getProvider().getObject(User.class, "name = '" + username + "'");
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
