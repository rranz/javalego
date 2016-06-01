package com.javalego.erp.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.javalego.application.Environment;
import com.javalego.data.BusinessService;
import com.javalego.data.DataProvider;
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
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;

/**
 * Entorno de TEST Spring para instanciar los servicios de la aplicación ERP
 * Demo
 *
 */
public class TestEnvironmentSpring implements Environment {

	@Autowired
	private SecurityServices securityServices;

	@Autowired
	private UserSession userSession;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private UserServices userServices;

	@Autowired
	private DataProvider dataProvider;

	@Autowired
	private Translator translator;

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
	 * Servicios de seguridad implementados con la librería <a
	 * href="http://shiro.apache.org/">Apache Shiro</a>
	 */
	@Override
	public synchronized SecurityServices getSecurity() {
		return securityServices;
	}

	/**
	 * Datos de la sesión de usuarios obtenidos del framework <a
	 * href="https://vaadin.com/">Vaadin</a>.
	 */
	@Override
	public synchronized UserSession getUserSession() {
		return userSession;
	}

	/**
	 * Servicios de acceso a datos implementados con <a
	 * href="http://projects.spring.io/spring-data/">Spring Data</a>
	 */
	@Override
	public synchronized DataProvider getDataProvider() {
		return dataProvider;
	}

	/**
	 * Servicios de gestión de usuarios de acceso a la aplicación. CRUD
	 */
	@Override
	public UserServices getUserServices() {
		return userServices;
	}

	/**
	 * Traductor usado para la localización de textos. Por defecto, traductor
	 * basado en código java {@link TranslatorCode}: anotaciones y enumerados.
	 */
	@Override
	public Translator getTranslator() {
		return translator;
	}

	/**
	 * Servicios de negocio de la aplicación ERP.
	 */
	@Override
	public BusinessService getBusinessService() {
		return businessService;
	}

	/**
	 * Lista de repositorios de iconos usados por la aplicación para la capa de
	 * presentación.
	 */
	@Override
	public synchronized Collection<RepositoryIcons<Icon>> getRepositoriesIcons() throws LocalizedException {

		// Cargar una lista de imágenes mediante enumerados y un directorio de
		// ficheros de imágenes.
		// Los nombres de los ficheros deben de coincidir con la lista de
		// enumerados.
		ResourceIconsVaadin rep = ResourceIconsVaadin.getCurrent();
		rep.load(Icons.class, "/icons");

		List<RepositoryIcons<Icon>> repositories = new ArrayList<RepositoryIcons<Icon>>();
		repositories.add(rep);

		return repositories;
	}

}
