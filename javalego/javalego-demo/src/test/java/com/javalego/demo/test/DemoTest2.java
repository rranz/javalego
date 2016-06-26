package com.javalego.demo.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.application.AppContext;
import com.javalego.demo.environment.TestEnvironment;

public class DemoTest2 {

	public static final Logger logger = Logger.getLogger(DemoTest2.class);	
	
	/**
	 * Ejemplo de carga del entorno en la prueba unitaria.
	 * 
	 * @throws Exception
	 */
	@Test
	public void environment() throws Exception {

		AppContext.getCurrent().load(new TestEnvironment());
		
		logger.info("Loaded environment " + AppContext.getCurrent().getEnvironmentName());
	}

}
