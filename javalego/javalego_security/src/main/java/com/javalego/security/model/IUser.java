package com.javalego.security.model;

import java.util.Collection;

/**
 * Simple class that represents any User domain entity in any application.
 *
 * <p>
 * Because this class performs its own Realm and Permission checks, and these can happen frequently enough in a
 * production application, it is highly recommended that the internal User {@link #getRoles} collection be cached in a
 * 2nd-level cache when using JPA and/or Hibernate. The hibernate xml configuration for this sample application does in
 * fact do this for your reference (see User.hbm.xml - the 'roles' declaration).
 * </p>
 */
public interface IUser
{

	/**
	 * User name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Password
	 * 
	 * @return
	 */
	String getPassword();

	/**
	 * List roles
	 * 
	 * @return
	 */
	Collection<IRole> getRoles();
}
