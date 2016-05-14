package com.javalego.store.ui;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.javalego.application.EnviromentFactory;
import com.javalego.application.Environment;
import com.javalego.exception.LocalizedException;

/**
 * Inicializar los servicios de la aplicación usando un contexto de aplicación
 * de la arquitectura.
 * 
 * Servlet implementation class InitApp
 */
@WebServlet(urlPatterns = { "/initapp" }, loadOnStartup = 1)
public class InitApp extends HttpServlet {

	private static final String APPLICATION_PROPERTIES = "application.properties";

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(InitApp.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitApp() {
		super();

	}

	/**
	 * Instanciamos el entorno en base a la configuración definida en el archivo
	 * de application.properties que incluye además la configuración de Spring
	 * Data.
	 * <p>
	 * NOTA: En com.javalego.store.environment.resources se ubican los
	 * diferentes archivos de configuración para los diferentes entornos.
	 * <p>
	 * Se han definido diferentes profiles en Maven para cada entorno de
	 * ejecución.
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		try {
			Environment environment = EnviromentFactory.getEnvironment(APPLICATION_PROPERTIES);

			if (environment != null) {
				StoreAppContext.getCurrent().load(environment);
			}
			else {
				logger.error("Environment not located in " + APPLICATION_PROPERTIES);
			}
		}
		catch (LocalizedException e) {
			logger.error(e.getLocalizedMessage());
		}
		super.init(config);
	}
}
