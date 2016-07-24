package com.javalego.demo.test;

import java.util.List;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.openejb.OpenEjbContainer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.demo.Logger;
import com.javalego.demo.entities.Department;
import com.javalego.demo.rest.CRUDServicesRest;
import com.javalego.exception.LocalizedException;

public class UserServiceTest
{
	private static Context context;

	// No cdi si usamos @Inject y además logger tampoco
	private static CRUDServicesRest services;

	// nota: las clases tienen que estar en src/main/java
	@Inject
	private Logger logger;
	
    @Before
    public void setUp() throws NamingException {
		Properties p = new Properties();

		// Acceso remoto a los servicios REST
		p.setProperty(OpenEjbContainer.OPENEJB_EMBEDDED_REMOTABLE, "true");

		p.put("openejb.configuration", "src/main/resources/META-INF/openejb.xml");

		// Reducir el ámbito de clases CDI del contenedor openejb para reducir el tiempo de localización. (por defecto
		// busca
		// en todas las clases del classpath).
		p.put("openejb.deployments.classpath.filter.descriptors", "true");
		p.put("openejb.exclude-include.order", "include-exclude"); // Defines the processing order
		p.put("openejb.deployments.classpath.include", ".*javalego.*"); // Include nothing
		p.put("openejb.descriptors.output", "true");

		context = EJBContainer.createEJBContainer(p).getContext();

		services = (CRUDServicesRest) context.lookup("java:global/javalego-demo/DepartmentServices");

		 //context = EJBContainer.createEJBContainer().getContext();
		
        context.bind("inject", this);

        System.out.println(logger);
    }
	
	
	@BeforeClass
	public static void start() throws Exception
	{
	}

	@AfterClass
	public static void close() throws NamingException
	{
		if (context != null)
		{
			context.close();
		}
	}

	@Test
	public void create() throws LocalizedException
	{

		
		//		Response r = services.save(new Department("SDDDDD"));
//		System.out.println(r.readEntity(Department.class));
//		r = services.find(1);
//		System.out.println(r.readEntity(Department.class));
		
		test();
	}

	private void test() throws LocalizedException
	{
		services.save(new Department("dp 1"));
		services.save(new Department("dp 2"));

		List<Department> list = services.findByProperty("name",  "dp 1"); //.readEntity(List.class);
		for (Department item : list)
		{
			System.out.println(item.getName() + " " + item.getId());
		}

		list.get(0).setName("CAMBIO");
		services.save(list.get(0));

		System.out.println(((Department) services.find(1)).getName());

		services.delete(1);
	}

}
