package com.javalego.demo.environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import com.javalego.data.DataProvider;
import com.javalego.data.jpa.SpringDataProvider;
import com.javalego.entity.Entity;
import com.javalego.security.SecurityServices;
import com.javalego.security.shiro.DefaultShiroRealm;
import com.javalego.security.shiro.SecurityShiro;

/**
 * Entorno de desarrollo.
 * 
 * @author ROBERTO RANZ
 *
 */
@Component("dev")
public class DevelopmentEnvironment extends BaseEnvironment {

	private SecurityShiro security;
	
	@Autowired
	private GenericApplicationContext context;
	
	private boolean database = true;

	@Override
	public String getName() {
		return "DEV";
	}

	/**
	 * No requiere autenticación en modo desarrollo
	 */
	@Override
	public synchronized SecurityServices getSecurity() {

		if (security == null) {

			// Seguridad simulada para pruebas
			security = new SecurityShiro(new DefaultShiroRealm(getUserServices())) {
				@Override
				public boolean isAuthenticated() {
					return true;
				}

				@Override
				public Object getPrincipal() {
					return "testuser";
				}

				@Override
				public boolean hasRole(String role) {
					return true;
				}
			};
		}
		return security;
	}

	@Override
	public synchronized DataProvider<Entity> getDataProvider() {
		return database ? new SpringDataProvider(context) : null;
	}

}
