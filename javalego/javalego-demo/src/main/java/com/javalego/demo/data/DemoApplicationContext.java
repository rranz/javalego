package com.javalego.demo.data;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javalego.application.AppContext;
import com.javalego.application.Environment;
import com.javalego.data.jpa.HibernateApplicationContext;
import com.javalego.exception.LocalizedException;

@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:application.properties")
@ComponentScan({ "com.javalego.demo" })
public class DemoApplicationContext extends HibernateApplicationContext implements ApplicationListener<ContextRefreshedEvent> {

	// Activar entorno de desarrollo.
	@Autowired
	@Qualifier("dev")
	private Environment environment;

	private static final Logger logger = Logger.getLogger(DemoApplicationContext.class);

	/**
	 * Cargar entorno de ejecución de la aplicación.
	 */
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		try {
			AppContext.getCurrent().load(environment);
		}
		catch (LocalizedException e) {
			logger.error("Error al cargar contexto de aplicación. " + e.getLocalizedMessage());
		}

	}
}