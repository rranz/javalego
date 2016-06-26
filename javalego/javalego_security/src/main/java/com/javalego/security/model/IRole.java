package com.javalego.security.model;

import java.util.Collection;

/**
 * Model object that represents a security role.
 */
public interface IRole
{
	/**
	 * Role name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Description role
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * Permissions role
	 * 
	 * @return
	 */
	Collection<String> getPermissions();

}
