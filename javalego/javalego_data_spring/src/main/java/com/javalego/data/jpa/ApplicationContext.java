package com.javalego.data.jpa;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import com.javalego.exception.LocalizedException;

/**
 * Configuración básica de Spring basada en anotaciones.
 * 
 * <p>
 * Gestiona su configuración a partir de datos definidos en un fichero de
 * propiedades. Incluir anotación:
 * 
 * @PropertySource("classpath:application.properties").
 * 
 * @author ROBERTO RANZ
 * 
 */
@Configuration
public abstract class ApplicationContext {

	public static final Logger logger = Logger.getLogger(ApplicationContext.class);

	@Resource
	protected Environment environment;

	/**
	 * Value property
	 * 
	 * @param key
	 * @return
	 * @throws LocalizedException
	 */
	protected String getValue(String key, boolean required) {

		if (environment == null) {
			return null;
		}

		String value = environment.getProperty(key);

		if (required && value == null) {
			logger.error("Config datasource: key '" + key + "' is null. Required value.");
			return null;
		}
		return value == null ? "" : value;
	}

	@Bean
	public MessageSource messageSource() {
		return new ResourceBundleMessageSource();
	}
	
	public Environment getEnvironment() {
		return environment;
	}

}
