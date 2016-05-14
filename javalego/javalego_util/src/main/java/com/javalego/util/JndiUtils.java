package com.javalego.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * Utilidades para el uso de JNDI.
 * @author ROBERTO RANZ
 */
public final class JndiUtils {

	private static final Logger logger = Logger.getLogger(JndiUtils.class); 
	
	private JndiUtils() {
	}

	private static InitialContext ctx;

	/**
	 * Inicializar context
	 * @return
	 */
	private static InitialContext getInitialContext() {
		if (ctx == null) {
			try {
				ctx = new InitialContext();
			} catch (NamingException e) {
				logger.fatal("It is not possible to create a new InitialContext.", e);
				throw new RuntimeException(e);
			}
		}
		return ctx;
	}

	/**
	 * Encontrar el nombre JNDI
	 * @param jndiName
	 * @return
	 * @throws NamingException
	 */
	public static Object jndiLookup(String jndiName) throws NamingException {
		final Object obj;
		try {
			obj = getInitialContext().lookup(jndiName);
			if (logger.isDebugEnabled()) logger.debug("Found JNDI name: " + jndiName);
		} catch (NamingException e) {
			logger.error("Cannot find JNDI name: " + jndiName);
			throw e;
		}
		return obj;
	}

}
