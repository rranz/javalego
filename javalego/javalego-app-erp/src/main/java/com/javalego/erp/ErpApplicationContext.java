package com.javalego.erp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javalego.data.jpa.HibernateApplicationContext;

/**
 * Contexto de aplicación Spring para ERP demo.
 * 
 * Acceso a datos: Spring Data.
 * 
 * @author ROBERTO RANZ
 *
 */
@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:application.properties")
public class ErpApplicationContext extends HibernateApplicationContext {

}
