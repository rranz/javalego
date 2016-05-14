package com.javalego.store.items.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.javalego.entity.impl.IdNumberEntityImpl;
import com.javalego.security.model.IRole;
import com.javalego.security.model.IUser;

@Entity
public class User extends IdNumberEntityImpl implements IUser {

	private String name;

	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<UserRole> userRoles;

	public User() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param password
	 */
	public User(String name, String password) {
		this(name, password, null);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param password
	 * @param roles
	 */
	public User(String name, String password, Collection<IRole> roles) {
		this.name = name;
		this.password = password;

		if (roles != null) {
			userRoles = new ArrayList<UserRole>();
			for (IRole role : roles) {
				userRoles.add(new UserRole(this, (Role) role));
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<IRole> getRoles() {
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Collection<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

}
