package com.javalego.test.app;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.javalego.application.AppContext;
import com.javalego.application.Environment;
import com.javalego.application.EnvironmentImpl;
import com.javalego.exception.LocalizedException;

/**
 * Test de contexto de aplicación.
 * 
 * @author ROBERTO RANZ
 *
 */
public class AppTest
{

	@Before
	public void BeforeTest() throws LocalizedException
	{
	}

	public static final Logger logger = Logger.getLogger(AppTest.class);

	@Test
	public void environment() throws LocalizedException
	{

		Environment environment = new EnvironmentImpl("TEST");

		// Set services
		// environment.setDataProvider(IDataProvider);
		// environment.setRepositories(Collection<IRepositoryIcons>);
		// environment.setSecurity(ISecurity);
		// ...

		AppContext.getCurrent().load(environment);

		// Obtener los registros de una entidad.
		// List<IEntity>list = AppContext.getDataProvider().getList(IEntity);

		logger.info("Loaded environment " + AppContext.getCurrent().getName());
	}

}
