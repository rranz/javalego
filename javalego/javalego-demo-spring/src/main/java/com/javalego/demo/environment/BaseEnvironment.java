package com.javalego.demo.environment;

import java.util.Collection;

import com.javalego.application.Environment;
import com.javalego.data.BusinessService;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.locale.translator.Translator;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.security.services.UserServices;
import com.javalego.security.session.UserSession;
import com.javalego.session.UserSessionVaadin;

/**
 * Entorno de pruebas.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class BaseEnvironment implements Environment {

	// Sesión de usuario
	private UserSessionVaadin userSession = new UserSessionVaadin();

	// Repositorio de imágenes
	public BaseEnvironment() {
	}

	@Override
	public Key getTitle() {
		return null;
	}

	@Override
	public Key getDescription() {
		return null;
	}

	@Override
	public synchronized Collection<RepositoryIcons<Icon>> getRepositoriesIcons() throws LocalizedException {
		return null;
	}

	@Override
	public UserSession getUserSession() {
		return userSession;
	}

	@Override
	public Translator getTranslator() {
		return null;
	}

	@Override
	public BusinessService getBusinessService() {
		return null;
	}

	@Override
	public UserServices getUserServices() {
		return null;
	}	
}
