package com.javalego.demo.data;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.javalego.application.AppContext;
import com.javalego.application.Environment;
import com.javalego.data.spring.SpringPersistenceContext;
import com.javalego.exception.LocalizedException;

@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:application.properties")
@ComponentScan({ "com.javalego.demo" })
public class DemoPersistenceContext extends SpringPersistenceContext
	implements ApplicationListener<ContextRefreshedEvent>
{

	// Activar entorno de desarrollo.
	@Autowired
	@Qualifier("dev")
	private Environment environment;

	private static final Logger logger = Logger.getLogger(DemoPersistenceContext.class);

	/**
	 * Cargar entorno de ejecución de la aplicación.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
	{
		try
		{
			AppContext.getCurrent().load(environment);
		}
		catch (LocalizedException e)
		{
			logger.error(e.getLocalizedMessage());
		}

	}

	@Override
	protected void setJpaProperties(Properties properties)
	{
		properties.put(HIBERNATE_FORMAT_SQL, "true".equals(getProperty(HIBERNATE_FORMAT_SQL, false)));
		// Crear ddl con Hibernate
		String ddl = getProperty(HIBERNATE_DDL, false);
		if (!"".equals(ddl))
		{
			properties.put(HIBERNATE_DDL, ddl);
		}
	}

	@Override
	protected void setDataSourceProperties(DriverManagerDataSource dataSource)
	{
		dataSource.setDriverClassName(getProperty(DB_DRIVER, true));
		dataSource.setUrl(getProperty(DB_URL, true));
		dataSource.setUsername(getProperty(DB_USERNAME, true));
		dataSource.setPassword(getProperty(DB_PASSWORD));
	}

	@Override
	protected JpaVendorAdapter getJpaVendorAdapter()
	{
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql("true".equals(getProperty(DB_SHOW_SQL)));
		adapter.setGenerateDdl("true".equals(getProperty(DB_GENERATE_DDL)));
		adapter.setDatabasePlatform(getProperty(DB_DIALECT));
		return adapter;
	}

	@Override
	protected String getPackagesToScan()
	{
		return getProperty(PACKAGES_TO_SCAN);
	}

}