package com.javalego.demo.test;

import java.util.List;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.apache.openejb.OpenEjbContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.demo.entities.Department;
import com.javalego.demo.rest.CRUDServicesRest;
import com.javalego.demo.rest.department.DepartmentServicesRest;
import com.javalego.exception.LocalizedException;

public class UserServiceTest
{
	private static Context context;

	private static DepartmentServicesRest services;

	private static CRUDServicesRest ds;

	@BeforeClass
	public static void start() throws Exception
	{
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

		// Obtener una instancia de los servicios REST CRUD.
		services = (DepartmentServicesRest) context.lookup("java:global/javalego-demo/DepartmentServicesRestImpl");

		ds = (CRUDServicesRest) context.lookup("java:global/javalego-demo/DepartmentServices");
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
		Response r = ds.save(new Department("SDDDDD"));
		
		System.out.println(r.readEntity(Department.class));
		
		r = ds.find(1);
		System.out.println(r.readEntity(Department.class));
		
		//test();
	}

	@SuppressWarnings("unchecked")
	private void test() throws LocalizedException
	{
		services.create("dp 1");
		services.create("dp 2");

		List<Department> list = services.list().readEntity(List.class);
		for (Department item : list)
		{
			System.out.println(item.getName() + " " + item.getId());
		}

		list.get(0).setName("CAMBIO");
		services.update(list.get(0));

		System.out.println(((Department) services.find(1).readEntity(Department.class)).getName());

		services.delete(1);
	}

}
