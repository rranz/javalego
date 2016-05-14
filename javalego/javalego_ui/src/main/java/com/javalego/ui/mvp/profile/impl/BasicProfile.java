package com.javalego.ui.mvp.profile.impl;

import java.util.List;

import com.javalego.security.model.IRole;
import com.javalego.security.model.IUser;

/**
 * Implementación de un perfil básico de usuario. Podremos especializar este
 * perfil en nuestra aplicación implementando nuestro propio perfile a partir de
 * la interface IProfile del proyecto de seguridad.
 * 
 * @author ROBERTO RANZ
 *
 */
public class BasicProfile extends AbstractProfile implements IUser {

	private String firstname;

	private String lastname;

	private String email;

	private String web;

	private String phone;

	private byte[] photo;

	/**
	 * Constructor
	 */
	public BasicProfile() {}
	
	/**
	 * Constructor
	 * @param user Nombre de usuario
	 * @param firstName
	 * @param lastName
	 */
	public BasicProfile(String user, String firstName, String lastName) {
		setUser(user);
		this.firstname = firstName;
		this.lastname = lastName;
	}
	
	public String firstname() {
		return firstname;
	}

	public String lastname() {
		return lastname;
	}

	public String email() {
		return email;
	}

	public String phone() {
		return phone;
	}

	public byte[] photo() {
		return photo;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Override
	public String getName() {
		return getUser();
	}

	@Override
	public List<IRole> getRoles() {
		return null;
	}

}
