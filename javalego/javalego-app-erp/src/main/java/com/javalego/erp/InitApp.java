package com.javalego.erp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.javalego.erp.environment.DevelopmentEnvironment;
import com.javalego.exception.LocalizedException;

/**
 * Inicializar los servicios de la aplicación usando un contexto de aplicación
 * de la arquitectura.
 * 
 * Servlet implementation class InitApp
 */
@WebServlet(urlPatterns = { "/initapp" }, loadOnStartup = 1)
public class InitApp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(InitApp.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitApp() {
		super();

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			ErpAppContext.getCurrent().load(new DevelopmentEnvironment());
		}
		catch (LocalizedException e) {
			logger.error(e.getLocalizedMessage());
		}
		super.init(config);
	}
}
