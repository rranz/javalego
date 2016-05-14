package com.javalego.security.services;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.context.Services;
import com.javalego.security.model.IRole;
import com.javalego.security.model.IUser;

/**
 * A service interface for accessing and modifying user data in the system.
 */
public interface UserServices extends Services {

	/**
	 * Get all user
	 * @return
	 * @throws LocalizedException
	 */
	Collection<IUser> getAllUsers() throws LocalizedException;

	/**
	 * Get user
	 * @param username
	 * @return
	 * @throws LocalizedException
	 */
	IUser findUser(String username) throws LocalizedException;

	/**
	 * Delete user
	 * @param username
	 * @throws LocalizedException
	 */
	void deleteUser(String username) throws LocalizedException;

	/**
	 * Update user
	 * @param user
	 * @throws LocalizedException
	 */
	void updateUser(IUser user) throws LocalizedException;
	
	/**
	 * Add new user
	 * @param user
	 * @throws LocalizedException
	 */
	void addUser(IUser user) throws LocalizedException;

	/**
	 * User credentials
	 * @param username 
	 * @return
	 * @throws LocalizedException 
	 */
	String getCredentials(String username) throws LocalizedException;

	/**
	 * Get user roles
	 * @param username
	 * @return
	 * @throws LocalizedException 
	 */
	Collection<IRole> getRoles(String username) throws LocalizedException;
}
