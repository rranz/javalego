package com.javalego.demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.javalego.application.Environment;
import com.javalego.data.jpa.JpaProvider;
import com.javalego.data.spring.SpringPersistenceXmlContext;
import com.javalego.demo.ejb.ERPDataProvider;
import com.javalego.demo.ejb.ERPServices;
import com.javalego.security.SecurityServices;
import com.javalego.security.session.UserSession;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan({ "com.javalego.demo.environment" })
public class DemoPersistenceXmlContext extends SpringPersistenceXmlContext
{
	// Activar entorno de desarrollo.
	@Autowired
	@Qualifier("dev")
	private Environment environment;

	// Establecemos los beans de environment para poder usar autowired en las clases.

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
	public String getPersistenceUnit()
	{
		// Obtiene el valor application.properties
		return null; // "erp-unit";
	}

}