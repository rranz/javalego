package com.javalego.security.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.javalego.exception.LocalizedException;
import com.javalego.security.impl.AbstractSecurityServices;
import com.javalego.security.model.IRole;

/**
 * Test de localización de imágenes e iconos usando las diferentes tipologías de
 * repositorios de iconos existentes para Vaadin.
 * 
 * @author ROBERTO RANZ
 *
 */
public class SecurityTest {

	public static final Logger logger = Logger.getLogger(SecurityTest.class);

	@Test
	public void test() {

		AbstractSecurityServices s = new AbstractSecurityServices() {

			@Override
			public void logout() {
				// TODO Auto-generated method stub

			}

			@Override
			public void login(String user, String password) throws LocalizedException {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isAuthenticated() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasRole(IRole role) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasRole(String role) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasAnyRole(IRole... roles) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasAnyRole(String... roles) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasAllRoles(IRole... roles) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasAllRoles(String... roles) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Object getPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		logger.info(s.isPermitted(EmpresasEditor.class));
	}

}
