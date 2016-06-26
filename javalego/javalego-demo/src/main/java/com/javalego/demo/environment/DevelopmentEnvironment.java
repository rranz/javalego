package com.javalego.demo.environment;

import javax.inject.Inject;

import com.javalego.data.DataProvider;
import com.javalego.security.SecurityServices;
import com.javalego.security.shiro.DefaultShiroRealm;
import com.javalego.security.shiro.SecurityShiro;

/**
 * Entorno de desarrollo.
 * 
 * @author ROBERTO RANZ
 *
 */
public class DevelopmentEnvironment extends BaseEnvironment
{
	private SecurityShiro security;

	@Inject
	private DataProvider dataProvider;

	private boolean database = true;

	@Override
	public String getName()
	{
		return "DEV";
	}

	/**
	 * No requiere autenticación en modo desarrollo
	 */
	@Override
	public synchronized SecurityServices getSecurity()
	{

		if (security == null)
		{

			// Seguridad simulada para pruebas
			security = new SecurityShiro(new DefaultShiroRealm(getUserServices()))
			{
				@Override
				public boolean isAuthenticated()
				{
					return true;
				}

				@Override
				public Object getPrincipal()
				{
					return "testuser";
				}

				@Override
				public boolean hasRole(String role)
				{
					return true;
				}
			};
		}
		return security;
	}

	@Override
	public synchronized DataProvider getDataProvider()
	{
		if (!database)
		{
			return null;
		}
		return dataProvider;
	}

}
