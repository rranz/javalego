package com.javalego.demo;

import java.util.List;

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
import com.javalego.data.jpa.JpaProvider;
import com.javalego.demo.data.DemoPersistenceXmlContext;
import com.javalego.demo.ejb.ERPServices;
import com.javalego.demo.entities.Employee;
import com.javalego.security.SecurityServices;
import com.javalego.security.session.UserSession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoPersistenceXmlContext.class, loader = AnnotationConfigContextLoader.class)
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

	@EJB
	private JpaProvider dataProvider;

	/**
	 * Ejemplo de carga del entorno en la clase de persistencia de Spring DemoPersistenceContext.
	 * 
	 * @throws Exception
	 */
	@Test
	public void populated() throws Exception
	{
		logger.info("Loaded environment " + environment.getName());

		logger.info("Security : " + security.hasRole("ADMIN"));

		logger.info("User : " + userSession.getProfile());

		erpServices.populateDatabase();

		List<Employee> list = dataProvider.findAll(Employee.class);

		for (Employee item : list)
		{
			logger.info("Employee: " + item.getEname());
		}
	}
}
