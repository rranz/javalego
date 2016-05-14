package com.javalego.model;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javalego.data.jpa.HibernateApplicationContext;

@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:application.properties")
public class JavaLegoApplicationContext extends HibernateApplicationContext {

}
