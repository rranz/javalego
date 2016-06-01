package com.javalego.application;

import java.util.Collection;

import com.javalego.data.BusinessService;
import com.javalego.data.DataProvider;
import com.javalego.exception.LocalizedException;
import com.javalego.icons.RepositoryIcons;
import com.javalego.locale.translator.Translator;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.security.SecurityServices;
import com.javalego.security.services.UserServices;
import com.javalego.security.session.UserSession;

/**
 * Configuración de un entorno de ejecución básico que implementa la interface
 * Environment y que puede ser reutilizado en la mayoría de aplicaciones.
 * 
 * <p>
 * El entorno de ejecución permite personalizar tanto sus datos básicos (nombre,
 * título, ...) como los diferentes contextos de servicios de aplicación como:
 * localización, seguridad, acceso a datos, UI, ...
 * 
 * <p>
 * El entorno debe configurarse al inicio de la aplicación.
 * 
 * <p>
 * Los contextos se pueden usar individualmente. El entorno nos permite definir
 * una configuración específica para ejecutar nuestra aplicación (Ej.: entorno
 * de pruebas, preproducción y producción).
 * 
 * @author ROBERTO RANZ
 *
 */
public class EnvironmentImpl implements Environment {

	private UserServices userServices;

	private DataProvider dataProvider;

	private BusinessService businessService;

	private UserSession userSession;

	private SecurityServices security;

	private Collection<RepositoryIcons<Icon>> repositoriesIcons;

	private Key description;

	private Key title;

	private String name;

	private Translator translator;

	/**
	 * Constructor
	 */
	public EnvironmentImpl() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Nombre clave
	 */
	public EnvironmentImpl(String name) {
		this.name = name;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Nombre clave
	 * @param title
	 *            título
	 */
	public EnvironmentImpl(String name, Key title) {
		this(name);
		this.title = title;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Nombre clave
	 * @param title
	 *            título
	 * @param name
	 *            descripción
	 */
	public EnvironmentImpl(String name, Key title, Key description) {
		this(name, title);
		this.description = description;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Key getTitle() {
		return title;
	}

	@Override
	public Key getDescription() {
		return description;
	}

	@Override
	public Collection<RepositoryIcons<Icon>> getRepositoriesIcons() {
		return repositoriesIcons;
	}

	@Override
	public SecurityServices getSecurity() {
		return security;
	}

	@Override
	public UserSession getUserSession() {
		return userSession;
	}

	@Override
	public DataProvider getDataProvider() {
		return dataProvider;
	}

	@Override
	public UserServices getUserServices() {
		return userServices;
	}

	/**
	 * Lista de repositorios de iconos usados por la aplicación.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	public EnvironmentImpl setRepositoriesIcons(Collection<RepositoryIcons<Icon>> repositories) {
		this.repositoriesIcons = repositories;
		return this;
	}

	/**
	 * Gestión de usuarios de la aplicación. Esta información debe estar
	 * vinculado a tipo de seguridad definido para la aplicación.
	 * 
	 * @see SecurityServices
	 * @return
	 */
	public EnvironmentImpl setUserServices(UserServices userServices) {
		this.userServices = userServices;
		return this;
	}

	/**
	 * Proveedor de datos utilizado.
	 * 
	 * @return
	 */
	public EnvironmentImpl setDataProvider(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
		return this;
	}

	/**
	 * Contexto de sesión de usuario utilizado en nuestra aplicación.
	 * 
	 * @return
	 */
	public EnvironmentImpl setUserSession(UserSession userSession) {
		this.userSession = userSession;
		return this;
	}

	/**
	 * Contexto de seguridad utilizado en nuestra aplicación.
	 * 
	 * @return
	 */
	public EnvironmentImpl setSecurity(SecurityServices security) {
		this.security = security;
		return this;
	}

	/**
	 * Descripción
	 * 
	 * @return
	 */
	public EnvironmentImpl setDescription(Key description) {
		this.description = description;
		return this;
	}

	/**
	 * Título
	 * 
	 * @return
	 */
	public EnvironmentImpl setTitle(Key title) {
		this.title = title;
		return this;
	}

	/**
	 * Nombre de referencia
	 * 
	 * @return
	 */
	public EnvironmentImpl setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Translator getTranslator() {
		return translator;
	}

	public EnvironmentImpl setTranslator(Translator translator) {
		this.translator = translator;
		return this;
	}

	@Override
	public BusinessService getBusinessService() {
		return businessService;
	}

	public EnvironmentImpl setBusinessService(BusinessService businessServices) {
		this.businessService = businessServices;
		return this;
	}

}
