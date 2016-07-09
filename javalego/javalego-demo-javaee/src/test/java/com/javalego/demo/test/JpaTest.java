package com.javalego.demo.test;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.javalego.demo.ejb.ERPServices;

/**
 * Pruebas unitarias del adaptador JPA para la persistencia de datos.
 * 
 * @author ROBERTO
 *
 */
public class JpaTest
{
	private static Context context;

	private static EJBContainer container;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		final Properties p = new Properties();

		// Configuración del Driver (2 opciones)

		// 1. Configurar datasource con propiedades
		// p.put("mysql", "new://Resource?type=DataSource");
		// p.put("movieDatabase.JdbcDriver", "com.mysql.jdbc.Driver");
		// p.put("movieDatabase.JdbcUrl", "jdbc:mysql://localhost:3306/javalego?createDatabaseIfNotExist=true");
		// p.put("movieDatabase.Username", "root");

		// 2. Usar un fichero de configuración donde podremos incluir cualquier configuración (datasource, ...) de open
		// ejb container.
		
		p.put("openejb.configuration", "src/main/resources/META-INF/openejb.xml");

		// Reducir el ámbito de clases CDI del contenedor openejb para reducir el tiempo de localización. (por defecto busca
		// en todas las clases del classpath).
		p.put("openejb.deployments.classpath.filter.descriptors", "true");
		p.put("openejb.exclude-include.order", "include-exclude"); // Defines the processing order
		p.put("openejb.deployments.classpath.include", ".*javalego.*"); // Include nothing
		p.put("openejb.descriptors.output", "true");

		// p.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.openejb.client.LocalInitialContextFactory");

		container = EJBContainer.createEJBContainer(p);
		context = container.getContext();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		container.close();
	}

	@Test
	public void test() throws Exception
	{
		// 'javalego-demo-javaee' es el nombre del proyecto para localizar al servicio en el contenedor openejb.
		ERPServices movies = (ERPServices) context.lookup("java:global/javalego-demo/ERPServices");
		movies.populateDatabase();
	}

}
