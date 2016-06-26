package com.javalego.application;

import org.apache.log4j.Logger;

import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.errors.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.locale.LocaleContext;
import com.javalego.locale.translator.Translator;
import com.javalego.model.context.Context;
import com.javalego.model.keys.Key;
import com.javalego.office.OfficeContext;
import com.javalego.security.SecurityContext;
import com.javalego.security.SecurityServices;
import com.javalego.security.session.UserSession;
import com.javalego.ui.UIContext;

/**
 * Contexto de aplicación que inicializa todos los servicios a partir de la configuración definida en un entorno de
 * ejecución.
 * 
 * <p>
 * <i>Contextos y servicios de aplicación disponibles:</i>
 * <p>
 * <b>Data:</b> {@link DataContext}
 * <p>
 * <b>Localización:</b> {@link LocaleContext}
 * <p>
 * <b>UI:</b> {@link UIContext}
 * <p>
 * <b>Security:</b> {@link SecurityContext}
 * 
 * <p>
 * Debe usar un entorno de ejecución {@link Environment} para cargar la configuración de contexto de aplicación.
 * 
 * <p>
 * Este contexto de aplicación es opcional, pudiendo usar de forma independiente los diferentes tipos de contextos de la
 * arquitectura (UI, Data, Locale, Security, ...)
 * 
 * @author ROBERTO RANZ
 */
public class AppContext implements Context
{

	public static final Logger logger = Logger.getLogger(AppContext.class);

	/**
	 * Entorno de ejecución cuya configuración se ha cargando en el contexto de aplicación.
	 */
	private Environment environment;

	/**
	 * Registra la excepción ocurrida en la inicialización del contexto de aplicación.
	 */
	private LocalizedException error;

	/**
	 * Nombre de referencia de la aplicación.
	 */
	private String name;

	/**
	 * Título de la aplicación.
	 */
	private Key title;

	/**
	 * Entorno cargado
	 */
	private boolean loaded;

	/**
	 * Contexto de aplicación actual.
	 */
	private static AppContext current = null;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	protected AppContext(String name, Key title)
	{
		current = this;
		this.name = name;
		this.title = title;
	}

	/**
	 * Constructor
	 */
	protected AppContext()
	{
		current = this;
	}

	/**
	 * Instancia actual del contexto de aplicación.
	 * 
	 * @return
	 */
	public synchronized static AppContext getCurrent()
	{
		if (current == null)
		{
			current = new AppContext(null, null);
		}
		return current;
	}

	/**
	 * Entorno cargado
	 * 
	 * @return
	 */
	public boolean isLoaded()
	{
		return loaded;
	}

	/**
	 * Establecer el actual contexto de aplicación.
	 * <p>
	 * Este método nos permitirá personalizar nuestra propia implementación para adecuarla a nuestros requerimientos
	 * funcionales.
	 * 
	 * @param current
	 */
	public static void setCurrent(AppContext current)
	{
		AppContext.current = current;
	}

	/**
	 * Contexto de interface de usuario UI.
	 * 
	 * @return
	 */
	public UIContext getUI()
	{
		return UIContext.getCurrent();
	}

	/**
	 * Contexto de acceso a datos.
	 * 
	 * @return
	 */
	public DataContext getData()
	{
		return DataContext.getCurrent();
	}

	/**
	 * Contexto de localización de aplicaciones.
	 * 
	 * @return
	 */
	public LocaleContext getLocale()
	{
		return LocaleContext.getCurrent();
	}

	/**
	 * Contexto de localización de aplicaciones.
	 * 
	 * @return
	 */
	public OfficeContext getOffice()
	{
		return OfficeContext.getCurrent();
	}

	/**
	 * Contexto de seguridad.
	 * 
	 * @return
	 */
	public SecurityContext getSecurity()
	{
		return SecurityContext.getCurrent();
	}

	/**
	 * Crear las instancias de los contextos definidos en el entorno.
	 * 
	 * @param environment
	 * @throws LocalizedException
	 */
	public void load(Environment environment) throws LocalizedException
	{

		String title = environment.getTitle() == null ? environment.getName()
			: LocaleContext.getText(environment.getTitle());

		// Establecer el título y nombre del entorno si no están definidos.
		if (this.title == null && environment.getTitle() != null)
		{
			this.title = environment.getTitle();
		}
		if (this.name == null)
		{
			this.name = environment.getName();
		}

		logger.debug("Loading runtime environment " + title + " ...");

		try
		{
			// Repositorios de iconos
			if (LocaleContext.getCurrent() != null)
			{
				LocaleContext.getCurrent().setRepositoriesIcons(environment.getRepositoriesIcons());
			}

			// Localización de aplicaciones
			// Establecer los servicios de traducción de textos
			Translator translator = environment.getTranslator();
			if (translator != null)
			{
				LocaleContext.setTranslator(translator);
			}

			// Acceso a datos
			if (DataContext.getCurrent() != null)
			{

				// Proveedor de datos
				DataProvider dataProvider = environment.getDataProvider();
				if (dataProvider != null)
				{
					dataProvider.init();
					DataContext.getCurrent().setProvider(dataProvider);
				}

				// Servicios de datos o negocio
				DataContext.getCurrent().setBusinessService(environment.getBusinessService());
			}

			// Security
			if (SecurityContext.getCurrent() != null)
			{

				// Servicios de seguridad
				SecurityServices security = environment.getSecurity();
				if (security != null)
				{
					SecurityContext.getCurrent().setServices(security);
				}

				// Sesión de usuario
				UserSession userSession = environment.getUserSession();
				if (userSession != null)
				{
					SecurityContext.getCurrent().setUserSession(userSession);
				}
			}

			logger.debug("Runtime environment " + title + " loaded.");

			this.environment = environment;
		}
		catch (RuntimeException | LocalizedException e)
		{

			error = new LocalizedException(UIContext.getText(CommonErrors.APPLICATION_ERROR, e));

			e.printStackTrace();

			logger.debug("ERROR LOADING RUNTIME ENVIRONMENT " + environment.getTitle());

			throw UIContext.getException(e, CommonErrors.APPLICATION_ERROR);
		}

		loaded = true;

	}

	/**
	 * Nombre de referencia del entorno de ejecución actual.
	 * 
	 * @return
	 */
	public String getEnvironmentName()
	{
		return environment != null ? environment.getName() : "(unknown)";
	}

	/**
	 * Título descriptivo del entorno de ejecución actual.
	 * 
	 * @return
	 */
	public Key getEnvironmentTitle()
	{
		return environment != null ? environment.getTitle() : null;
	}

	/**
	 * Error que se ha producido en la ejecución de la aplicación y que servirá para mostrar un mensaje al usuario.
	 * (Ej.: error conexión base de datos).
	 */
	public LocalizedException getError()
	{
		return error;
	}

	/**
	 * Registra la excepción ocurrida en la inicialización del contexto de aplicación.
	 * 
	 * @param error
	 */
	public void setError(LocalizedException error)
	{
		this.error = error;
	}

	/**
	 * Nombre de referencia de la aplicación
	 * 
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Título descriptivo de la aplicación
	 * 
	 * @return
	 */
	public Key getTitle()
	{
		return title;
	}

	/**
	 * Nombre del contexto de aplicación
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Título de contexto de aplicación
	 * 
	 * @param title
	 */
	public void setTitle(Key title)
	{
		this.title = title;
	}

}
