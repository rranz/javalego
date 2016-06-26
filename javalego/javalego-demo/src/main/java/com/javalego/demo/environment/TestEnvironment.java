package com.javalego.demo.environment;

import javax.ejb.EJB;

import com.javalego.data.DataProvider;
import com.javalego.security.SecurityServices;
import com.javalego.security.shiro.SecurityShiro;

/**
 * Entorno de pruebas.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TestEnvironment extends BaseEnvironment {

	// Seguridad
	private SecurityShiro security = new SecurityShiro("classpath:shiro.ini");

	@EJB
	private DataProvider dataProvider;

	@Override
	public String getName() {
		return "TEST";
	}

	@Override
	public DataProvider getDataProvider() {
		return dataProvider;
	}

	@Override
	public SecurityServices getSecurity() {
		return security;
	}

}
