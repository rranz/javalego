package com.javalego.store.items.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;

/**
 * Desarrollador de la comunidad.
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
public class Developer extends Member {

	private static final long serialVersionUID = 3549079320871141715L;

	private String firstName;
	
	private String lastName;
	
	private String email;

	private String web;

	private String phone;

	private byte[] photo;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Social social = new Social();
		
	public Developer() {}
	
	/**
	 * Constructor
	 * @param name Campo clave o principal
	 * @param firstName
	 * @param lastName
	 * @param registration
	 */
	public Developer(String name, String firstName, String lastName, Date registration) {
		super(name, registration);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toHtml() {
		return "<strong>" + firstName + " " + lastName + "</strong>";
	}	

	@Override
	public String toString() {
		return firstName + " " + lastName + " (" + UIContext.getText(LocaleStore.DEVELOPER) + ")";
	}	

	@Override
	public String toTile() {
		return "<strong>" + firstName + " " + lastName + "</strong><br><i>" + UIContext.getText(LocaleStore.DEVELOPER) + "</i>";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Override
	public Social getSocial() {
		return social;
	}

	public void setSocial(Social social) {
		this.social = social;
	}

}
