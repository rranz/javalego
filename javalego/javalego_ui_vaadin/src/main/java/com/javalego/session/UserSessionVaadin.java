package com.javalego.session;

import java.util.Locale;

import com.javalego.security.model.Profile;
import com.javalego.security.session.UserSession;
import com.javalego.ui.vaadin.component.util.ScreenSize;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;

/**
 * Datos de la sesión de usuario obtenidos del framework Vaadin.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class UserSessionVaadin implements UserSession {

	/**
	 * Perfil de usuario
	 */
	private Profile profile;

	/**
	 * Cálculo del tipo de tamaño de pantalla del dispositivo del usuario.
	 */
	private ScreenSize screenSize = new ScreenSize();

	@Override
	public Locale getLocale() {
		return VaadinSession.getCurrent() != null ? VaadinSession.getCurrent().getLocale() : Locale.getDefault();
	}

	@Override
	public String getLanguage() {
		return VaadinSession.getCurrent() != null ? VaadinSession.getCurrent().getLocale().getLanguage() : Locale.getDefault().getLanguage();
	}

	@Override
	public void setLocale(Locale locale) {
		if (VaadinSession.getCurrent() != null) {
			VaadinSession.getCurrent().setLocale(locale);
		}
	}

	@Override
	public void logout() {

		if (VaadinSession.getCurrent() != null && VaadinSession.getCurrent().getSession() != null) {
			VaadinSession.getCurrent().getSession().invalidate();
		}
		Page.getCurrent().reload();
	}

	@Override
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public Profile getProfile() {
		return profile;
	}

	@Override
	public boolean isSmallScreen() {
		return screenSize != null ? screenSize.getScreenSize() == ScreenSize.Type.SMALL : false;
	}

	@Override
	public boolean isMediumScreen() {
		return screenSize != null ? screenSize.getScreenSize() == ScreenSize.Type.MEDIUM : false;
	}

	@Override
	public boolean isLargeScreen() {
		return screenSize != null ? screenSize.getScreenSize() == ScreenSize.Type.LARGE : true;
	}

	public ScreenSize getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(ScreenSize screenSize) {
		this.screenSize = screenSize;
	}

}
