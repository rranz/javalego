package com.javalego.demo.test;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.javalego.application.Environment;
import com.javalego.demo.data.DemoPersistenceContext;
import com.javalego.demo.ejb.ERPServices;
import com.javalego.security.SecurityServices;
import com.javalego.security.session.UserSession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoPersistenceContext.class, loader = AnnotationConfigContextLoader.class)
public class DemoTest
{
	public static final Logger logger = Logger.getLogger(DemoTest.class);

	@Autowired
	@Qualifier("dev")
	private Environment environment;
	
	@Autowired
	private SecurityServices security;
	
	@Autowired
	private UserSession userSession;
	
	@EJB
	private ERPServices erpServices;
	
	/**
	 * Ejemplo de carga del entorno en la clase de persistencia de Spring DemoPersistenceContext.
	 * 
	 * @throws Exception
	 */
	@Test
	public void environment() throws Exception
	{
		logger.info("Loaded environment " + environment.getName());

		logger.info("Security : " + security.hasRole("hol"));
		
		logger.info("User : " + userSession.getProfile());

		erpServices.populateDatabase();
	}
}
