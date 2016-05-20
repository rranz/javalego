package com.javalego.demo.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import com.javalego.data.DataProvider;
import com.javalego.data.jpa.SpringDataProvider;
import com.javalego.entity.Entity;
import com.javalego.security.SecurityServices;
import com.javalego.security.shiro.SecurityShiro;

/**
 * Entorno de pruebas.
 * 
 * @author ROBERTO RANZ
 *
 */
@Component("test")
public class TestEnvironment extends BaseEnvironment {

	// Seguridad
	private SecurityShiro security = new SecurityShiro("classpath:shiro.ini");
	
	@Autowired
	private GenericApplicationContext context;
	
	@Override
	public String getName() {
		return "TEST";
	}

	@Override
	public synchronized DataProvider<Entity> getDataProvider() {
		return new SpringDataProvider(context);
	}

	@Override
	public SecurityServices getSecurity() {
		return security;
	}

}
