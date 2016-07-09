package com.javalego.demo.data;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.javalego.application.Environment;
import com.javalego.data.jpa.JpaProvider;
import com.javalego.data.spring.SpringPersistenceContext;
import com.javalego.demo.ejb.ERPDataProvider;
import com.javalego.demo.ejb.ERPServices;
import com.javalego.security.SecurityServices;
import com.javalego.security.session.UserSession;

@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:application.properties")
@ComponentScan({ "com.javalego.demo.environment" })
public class DemoPersistenceContext extends SpringPersistenceContext
{
	// Activar entorno de desarrollo.
	@Autowired
	@Qualifier("dev")
	private Environment environment;

	@Bean
	public ERPServices erpServices()
	{
		return new ERPServices();
	}

	@Bean
	public JpaProvider dataProvider()
	{
		return new ERPDataProvider();
	}

	@Bean
	public SecurityServices securityServices()
	{
		return environment.getSecurity();
	}

	@Bean
	public UserSession userSession()
	{
		return environment.getUserSession();
	}

	@Override
	protected void setJpaProperties(Properties properties)
	{
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

	@Override
	public String getPersistenceUnit()
	{
		return getProperty(PERSISTENCE_UNIT);
	}

}