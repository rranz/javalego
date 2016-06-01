package com.javalego.store.items.impl;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.javalego.entity.impl.EntityId;

@Entity
public class UserRole extends EntityId {
   
    @ManyToOne
    private User user;
    
    @ManyToOne
    private Role role;

    public UserRole() {}
    
	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}    
}
