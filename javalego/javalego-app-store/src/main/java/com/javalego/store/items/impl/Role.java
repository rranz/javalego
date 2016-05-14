package com.javalego.store.items.impl;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.javalego.entity.impl.IdNumberEntityImpl;
import com.javalego.security.model.IRole;

@Entity
public class Role extends IdNumberEntityImpl implements IRole {

	private String description;

	@ElementCollection
	private Collection<String> permissions;

	@NotNull
	private String name;
	
	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Collection<String> getPermissions() {
		return permissions;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPermissions(Collection<String> permissions) {
		this.permissions = permissions;
	}

	public void setName(String name) {
		this.name = name;
	}

}
