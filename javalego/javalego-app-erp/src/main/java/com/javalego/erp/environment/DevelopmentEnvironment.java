package com.javalego.erp.environment;

import com.javalego.security.SecurityServices;
import com.javalego.security.shiro.SecurityShiro;

/**
 * Entorno de desarrollo.
 * 
 * @author ROBERTO RANZ
 *
 */
public class DevelopmentEnvironment extends TestEnvironment {

	private SecurityShiro security;

	/**
	 * No requiere autenticación en modo desarrollo
	 */
	@Override
	public synchronized SecurityServices getSecurity() {
		if (security == null) {
			security = new SecurityShiro("classpath:shiro.ini") {
				@Override
				public boolean isAuthenticated() {
					return true;
				}
				
				@Override
				public boolean isPermitted(Object object) {
					return true;
				}
			};
		}
		return security;
	}

}
