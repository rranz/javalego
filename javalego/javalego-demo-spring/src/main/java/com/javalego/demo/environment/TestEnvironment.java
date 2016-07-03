package com.javalego.demo.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import com.javalego.data.DataProvider;
import com.javalego.data.spring.SpringDataProvider;
import com.javalego.demo.data.DemoPersistenceContext2;
import com.javalego.security.SecurityServices;
import com.javalego.security.shiro.SecurityShiro;

/**
 * Entorno de pruebas.
 * 
 * @author ROBERTO RANZ
 *
 */
@Component("test")
public class TestEnvironment extends BaseEnvironment
{

	// Seguridad
	private SecurityShiro security = new SecurityShiro("classpath:shiro.ini");

	@Autowired
	private GenericApplicationContext context;

	@Override
	public String getName()
	{
		return "TEST";
	}

	@Override
	public synchronized DataProvider getDataProvider()
	{
		return context != null ? new SpringDataProvider(context)
			: new SpringDataProvider(DemoPersistenceContext2.class);
	}

	@Override
	public SecurityServices getSecurity()
	{
		return security;
	}

}
