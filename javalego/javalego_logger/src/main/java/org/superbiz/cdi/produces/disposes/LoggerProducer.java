package org.superbiz.cdi.produces.disposes;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instanciar un logger mediante CDI y sin especificar la clase.
 * <p>
 * Ej.:
 * 
 * <pre>
 * &#64;Inject
 * Logger logger;
 * </pre>
 * 
 * @author ROBERTO
 *
 */
public class LoggerProducer
{
	@Produces
	public Logger loggerProducer(InjectionPoint ip)
	{
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}
}