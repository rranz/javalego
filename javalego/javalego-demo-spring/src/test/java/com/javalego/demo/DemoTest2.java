package com.javalego.demo;

import java.util.List;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.javalego.application.AppContext;
import com.javalego.data.jpa.JpaProvider;
import com.javalego.demo.data.DemoPersistenceContext;
import com.javalego.demo.ejb.ERPServices;
import com.javalego.demo.entities.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoPersistenceContext.class, loader = AnnotationConfigContextLoader.class)
public class DemoTest2 {

	public static final Logger logger = Logger.getLogger(DemoTest2.class);	
	
	@EJB
	private ERPServices erpServices;

	@EJB
	private JpaProvider dataProvider;
	
	/**
	 * Ejemplo de carga del entorno en la prueba unitaria.
	 * 
	 * @throws Exception
	 */
	@Test
	public void environment() throws Exception {

		logger.info("Loaded environment " + AppContext.getCurrent().getEnvironmentName());

		Department department = new Department();
		department.setName("Development 2");
		dataProvider.save(department);
		
		// Obtener registros de entidades.
		List<Department>list = dataProvider.findAll(Department.class);
		
		logger.info("Number Department: " + list.size());
		
	}

}
