package com.javalego.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javalego.data.jpa.HibernateApplicationContext;

/**
 * Definimos el fichero de propiedades para el cloud bluemix
 * 
 * @author ROBERTO RANZ
 *
 */
@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:bluemix-application.properties")
public class BluemixStoreApplicationContext extends HibernateApplicationContext {

}
