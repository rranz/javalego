package com.javalego.demo.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.javalego.application.AppContext;
import com.javalego.data.DataContext;
import com.javalego.demo.data.DemoPersistenceContext;
import com.javalego.demo.entities.Empresa;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoPersistenceContext.class, loader = AnnotationConfigContextLoader.class)
public class DemoTest {

	public static final Logger logger = Logger.getLogger(DemoTest.class);

	/**
	 * Ejemplo de carga del entorno en la clase de persistencia de Spring
	 * DemoPersistenceContext.
	 * 
	 * @throws Exception
	 */
	@Test
	public void environment() throws Exception {

		logger.info("Loaded environment " + AppContext.getCurrent().getEnvironmentName());

		// Obtener registros de entidades.
		List<Empresa> list = (List<Empresa>) DataContext.getProvider().findAll(Empresa.class);

		logger.info("Empresa: " + list.get(0).getNombre());

	}

}
