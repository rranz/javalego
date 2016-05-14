package com.javalego.ui.mvp.profile.impl;

import java.util.Locale;

import com.javalego.security.SecurityContext;
import com.javalego.security.model.Profile;
import com.javalego.ui.UIContext;

/**
 * Modelo básico de un perfil de usuario.
 * 
 * Los campos country, user y locale se obtienen de la sesión de usuario y no se
 * pueden modificar. Se mantienen los campos para evitar errores al usar estos
 * campos en el editor de los datos del perfil de usuario.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class AbstractProfile implements Profile {

	private String country;

	private String user;

	private String password;
	
	private Locale locale;

	public AbstractProfile() {
	}

	@Override
	public Locale getLocale() {

		if (locale != null) {
			return locale;
		}
		else {
			return UIContext.getUserSessionLocale() != null ? UIContext.getUserSessionLocale() : Locale.getDefault();
		}
	}

	@Override
	public String getUser() {

		if (user != null) {
			return user;
		}
		else {
			return SecurityContext.getCurrent() != null && SecurityContext.getCurrent().getServices().getPrincipal() != null ? SecurityContext.getCurrent().getServices().getPrincipal().toString() : null;
		}
	}

	@Override
	public String getCountry() {

		if (country != null) {
			return country;
		}
		else {
			String country = UIContext.getUserSessionLocale() != null ? UIContext.getUserSessionLocale().getCountry() : Locale.getDefault().getDisplayCountry();

			return "".equals(country) ? getLocale().toString() : country;
		}
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Inicializar los valores de usuario, locale y country actuales para tomar los valores de la sesión de usuario.
	 */
	public void init() {
		user = null;
		locale = null;
		country = null;
		password = null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
