package com.javalego.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.javalego.application.AppContext;
import com.javalego.environment.TestEnvironment;
import com.javalego.exception.LocalizedException;

/**
 * Servlet implementation class StartApplication
 */
@WebServlet(value={"/start"}, loadOnStartup=1)
public class StartApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(StartApplication.class);
	
    /**
     * Default constructor. 
     */
    public StartApplication() {
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		try {
			AppContext.getCurrent().load(new TestEnvironment());
		}
		catch (LocalizedException e) {
			logger.error(e.getLocalizedMessage());
		}	
		
		super.init(config);
	}

}
