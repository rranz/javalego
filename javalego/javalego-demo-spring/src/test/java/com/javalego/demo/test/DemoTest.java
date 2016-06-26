package com.javalego.demo.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.javalego.application.AppContext;
import com.javalego.data.DataContext;
import com.javalego.demo.data.DemoPersistenceContext;
import com.javalego.demo.ejb.MoviesServices;
import com.javalego.demo.entities.Empresa;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoPersistenceContext.class, loader = AnnotationConfigContextLoader.class)
public class DemoTest
{
	public static final Logger logger = Logger.getLogger(DemoTest.class);

	@Autowired
	private MoviesServices services;
	
	/**
	 * Ejemplo de carga del entorno en la clase de persistencia de Spring DemoPersistenceContext.
	 * 
	 * @throws Exception
	 */
	@Test
	public void environment() throws Exception
	{
		logger.info("Loaded environment " + AppContext.getCurrent().getEnvironmentName());

		// Obtener registros de entidades.
		List<Empresa> list = (List<Empresa>) DataContext.getProvider().findAll(Empresa.class);

		Empresa e = new Empresa();
		e.setNombre("EMPRESA EJB");
		e.setCif("CIFEJB");
		e.setRazon_social("RAZON SOCIAL EJB");

		Empresa e2 = new Empresa();
		e2.setNombre("EMPRESA EJB 2");
		e2.setCif("CIFEJB 2");
		e2.setRazon_social("RAZON SOCIAL EJB 2");

		services.addMovies(new Empresa[] {e, e2});
		
		
		//DataContext.getProvider().save(e);
		
		logger.info("Empresa: " + list.get(0).getNombre());
	}
}
