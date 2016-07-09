package com.javalego.erp;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.javalego.data.spring.SpringPersistenceContext;

/**
 * Contexto de aplicación Spring para ERP demo.
 * 
 * Acceso a datos: Spring Data.
 * 
 * @author ROBERTO RANZ
 *
 */
@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:application.properties")
public class ErpPersistenceContext extends SpringPersistenceContext {

	@Override
	protected void setJpaProperties(Properties properties) {

		properties.put(HIBERNATE_FORMAT_SQL, "true".equals(getProperty(HIBERNATE_FORMAT_SQL, false)));
		// Crear ddl con Hibernate
		String ddl = getProperty(HIBERNATE_DDL, false);
		if (!"".equals(ddl)) {
			properties.put(HIBERNATE_DDL, ddl);
		}
	}

	@Override
	protected void setDataSourceProperties(DriverManagerDataSource dataSource) {

		dataSource.setDriverClassName(getProperty(DB_DRIVER, true));
		dataSource.setUrl(getProperty(DB_URL, true));
		dataSource.setUsername(getProperty(DB_USERNAME, true));
		dataSource.setPassword(getProperty(DB_PASSWORD));
	}

	@Override
	protected JpaVendorAdapter getJpaVendorAdapter() {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql("true".equals(getProperty(DB_SHOW_SQL)));
		adapter.setGenerateDdl("true".equals(getProperty(DB_GENERATE_DDL)));
		adapter.setDatabasePlatform(getProperty(DB_DIALECT));
		return adapter;
	}

	@Override
	protected String getPackagesToScan() {
		return getProperty(PACKAGES_TO_SCAN);
	}

	@Override
	public String getPersistenceUnit()
	{
		return null;
	}

}
