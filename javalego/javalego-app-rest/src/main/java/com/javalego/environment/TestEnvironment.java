package com.javalego.environment;

import java.util.Collection;

import com.javalego.application.Environment;
import com.javalego.data.DataProvider;
import com.javalego.data.BusinessService;
import com.javalego.data.jpa.SpringDataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.locale.translator.Translator;
import com.javalego.model.JavaLegoApplicationContext;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.security.SecurityServices;
import com.javalego.security.services.UserServices;
import com.javalego.security.session.UserSession;
import com.javalego.security.shiro.SecurityShiro;
import com.javalego.session.UserSessionVaadin;

/**
 * Entorno de pruebas.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TestEnvironment implements Environment {

	@Override
	public Key getDescription() {
		return null;
	}

	@Override
	public String getName() {
		return "TEST";
	}

	@Override
	public Key getTitle() {
		return null;
	}

	@Override
	public Collection<RepositoryIcons<Icon>> getRepositoriesIcons() throws LocalizedException {
		return null;
	}

	@Override
	public SecurityServices getSecurity() {
		return new SecurityShiro("classpath:shiro.ini");
	}

	@Override
	public UserSession getUserSession() {
		return new UserSessionVaadin();
	}

	@Override
	public DataProvider<Entity> getDataProvider() {
		return new SpringDataProvider(JavaLegoApplicationContext.class);
	}

	@Override
	public UserServices getUserServices() {
		return null;
	}

	@Override
	public Translator getTranslator() {
		return null;
	}

	@Override
	public BusinessService getBusinessService() {
		return null;
	}

}
