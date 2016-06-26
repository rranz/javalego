package com.javalego.security.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.javalego.security.SecurityServices;
import com.javalego.security.annotation.DenyAll;
import com.javalego.security.annotation.PermitAll;
import com.javalego.security.annotation.RolesAllowed;
import com.javalego.security.permission.CustomPermission;
import com.javalego.security.permission.Permission;

/**
 * Clase abstract que implementa métodos reutilizables por las clases que implementen la interface ISecurityContext.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class AbstractSecurityServices implements SecurityServices
{

	private static final Logger logger = Logger.getLogger(AbstractSecurityServices.class);

	private HashMap<Class<?>, Permission> objects = new HashMap<Class<?>, Permission>();

	@Override
	public boolean isPermitted(Object object)
	{
		if (object == null)
		{
			return false;
		}

		// Comprobar roles definidos a nivel de clase.
		RolesAllowed roles = null;
		if (object instanceof Class<?>)
		{
			roles = ((Class<?>) object).getAnnotation(RolesAllowed.class);
		}
		else
		{
			roles = object.getClass().getAnnotation(RolesAllowed.class);
		}
		if (roles != null && hasAnyRole(roles.value()))
		{
			return true;
		}

		// Comprobar denegación de acceso
		DenyAll denyAll = null;
		if (object instanceof Class<?>)
		{
			denyAll = ((Class<?>) object).getAnnotation(DenyAll.class);
		}
		else
		{
			denyAll = object.getClass().getAnnotation(DenyAll.class);
		}
		if (denyAll != null)
		{
			return false;
		}

		// Comprobar tiene todos los permisos
		PermitAll permitAll = null;
		if (object instanceof Class<?>)
		{
			permitAll = ((Class<?>) object).getAnnotation(PermitAll.class);
		}
		else
		{
			permitAll = object.getClass().getAnnotation(PermitAll.class);
		}
		if (permitAll != null)
		{
			return true;
		}

		// Permisos personalizados con una clase
		CustomPermission p = null;

		if (object instanceof Class<?>)
		{
			p = (CustomPermission) ((Class<?>) object).getAnnotation(CustomPermission.class);
		}
		else
		{
			p = object.getClass().getAnnotation(CustomPermission.class);
		}

		if (p != null)
		{
			try
			{
				if (objects.containsKey(p.type()))
				{
					return objects.get(p.type()).isPermitted();
				}
				else
				{
					Permission instance = p.type().newInstance();

					objects.put(p.type(), instance);

					return p.type().newInstance().isPermitted();
				}
			}
			catch (InstantiationException e)
			{
				logger.error("SECURITY CONTEXT: ERROR NEW INSTANCE " + object.getClass().getCanonicalName());
			}
			catch (IllegalAccessException e)
			{
				logger.error("SECURITY CONTEXT: ERROR NEW INSTANCE " + object.getClass().getCanonicalName());
			}
		}

		return false;
	}

}
