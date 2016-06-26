package com.javalego.demo.test;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.application.AppContext;
import com.javalego.demo.ejb.ERPServices;

public class DemoTest
{
	public static final Logger logger = Logger.getLogger(DemoTest.class);

	@EJB
	private ERPServices services;
	
	/**
	 * Ejemplo de carga del entorno en la clase de persistencia de Spring DemoPersistenceContext.
	 * 
	 * @throws Exception
	 */
	@Test
	public void environment() throws Exception
	{
		logger.info("Loaded environment " + AppContext.getCurrent().getEnvironmentName());
	}
}
