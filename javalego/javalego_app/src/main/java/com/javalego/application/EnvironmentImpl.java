package com.javalego.application;

import java.util.Collection;

import com.javalego.data.DataProvider;
import com.javalego.data.BusinessService;
import com.javalego.entity.Entity;
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

	private DataProvider<Entity> dataProvider;

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
	public DataProvider<Entity> getDataProvider() {
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
	public void setRepositoriesIcons(Collection<RepositoryIcons<Icon>> repositories) {
		this.repositoriesIcons = repositories;
	}

	/**
	 * Gestión de usuarios de la aplicación. Esta información debe estar
	 * vinculado a tipo de seguridad definido para la aplicación.
	 * 
	 * @see SecurityServices
	 * @return
	 */
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	/**
	 * Proveedor de datos utilizado.
	 * 
	 * @return
	 */
	public void setDataProvider(DataProvider<Entity> dataProvider) {
		this.dataProvider = dataProvider;
	}

	/**
	 * Contexto de sesión de usuario utilizado en nuestra aplicación.
	 * 
	 * @return
	 */
	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}

	/**
	 * Contexto de seguridad utilizado en nuestra aplicación.
	 * 
	 * @return
	 */
	public void setSecurity(SecurityServices security) {
		this.security = security;
	}

	/**
	 * Descripción
	 * 
	 * @return
	 */
	public void setDescription(Key description) {
		this.description = description;
	}

	/**
	 * Título
	 * 
	 * @return
	 */
	public void setTitle(Key title) {
		this.title = title;
	}

	/**
	 * Nombre de referencia
	 * 
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Translator getTranslator() {
		return translator;
	}

	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	@Override
	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessServices) {
		this.businessService = businessServices;
	}

}
