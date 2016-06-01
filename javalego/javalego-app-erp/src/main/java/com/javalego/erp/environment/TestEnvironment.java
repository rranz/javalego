package com.javalego.erp.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.application.Environment;
import com.javalego.data.BusinessService;
import com.javalego.data.DataProvider;
import com.javalego.data.spring.SpringDataProvider;
import com.javalego.erp.ErpPersistenceContext;
import com.javalego.erp.data.ErpDataServices;
import com.javalego.erp.model.Icons;
import com.javalego.erp.model.Texts;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.locale.translator.Translator;
import com.javalego.locale.translator.TranslatorCode;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.security.SecurityServices;
import com.javalego.security.services.UserServices;
import com.javalego.security.session.UserSession;
import com.javalego.security.shiro.SecurityShiro;
import com.javalego.session.UserSessionVaadin;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;

/**
 * Entorno de TEST Aplicación ERP Demo
 *
 */
public class TestEnvironment implements Environment {

	@Override
	public String getName() {
		return Texts.TEST.name();
	}

	@Override
	public Key getTitle() {
		return Texts.TEST;
	}

	@Override
	public Key getDescription() {
		return Texts.TEST;
	}

	/**
	 * Lista de repositorio de iconos usados por la aplicación para la capa de
	 * presentación basada en Vaadin.
	 */
	@Override
	public synchronized Collection<RepositoryIcons<Icon>> getRepositoriesIcons() throws LocalizedException {

		ResourceIconsVaadin rep = ResourceIconsVaadin.getCurrent();
		rep.load(Icons.class, "/icons");
		rep.load(IconEditor.class, "/icons_editor");
		
		List<RepositoryIcons<Icon>> repositories = new ArrayList<RepositoryIcons<Icon>>();
		repositories.add(rep);

		return repositories;
	}

	/**
	 * Servicios de seguridad implementados con la librería <a
	 * href="http://shiro.apache.org/">Apache Shiro</a>
	 */
	@Override
	public synchronized SecurityServices getSecurity() {
		return new SecurityShiro("classpath:shiro.ini");
	}

	/**
	 * Datos de la sesión de usuarios obtenidos del framework <a
	 * href="https://vaadin.com/">Vaadin</a>.
	 */
	@Override
	public synchronized UserSession getUserSession() {
		return new UserSessionVaadin();
	}

	/**
	 * Servicios de acceso a datos implementados con <a
	 * href="http://projects.spring.io/spring-data/">Spring Data</a>
	 */
	@Override
	public synchronized DataProvider getDataProvider() {
		return new SpringDataProvider(ErpPersistenceContext.class);
	}

	/**
	 * Servicios de gestión de usuarios de acceso a la aplicación. CRUD
	 */
	@Override
	public UserServices getUserServices() {
		return null;
	}

	/**
	 * Traductor usado para la localización de textos. Por defecto, traductor
	 * basado en código java {@link TranslatorCode}: anotaciones y enumerados.
	 */
	@Override
	public Translator getTranslator() {
		return null;
	}

	/**
	 * Servicios de negocio de la aplicación ERP.
	 */
	@Override
	public BusinessService getBusinessService() {
		return new ErpDataServices();
	}

}
