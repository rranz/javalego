package com.javalego.data.spring;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.javalego.data.DataProvider;
import com.javalego.data.spring.jpa.GenericDaoJpa;

/**
 * Configuración básica de Spring Data basada en anotaciones (no requiere applicationContext.xml) para
 * utilizar el acceso a base de datos mediante JPA. 
 * <p>
 * Define el datasource, entity manager y transaction manager usando
 * los archivos estándar persistence.xml y permite definir la únidad de persistencia, en caso de definir varias en dicho
 * archivo.
 * 
 * @author ROBERTO RANZ
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public abstract class SpringPersistenceXmlContext
{
	public static final String PERSISTENCE_UNIT = "persistence-unit";

	@Resource
	protected Environment environment;

	@Bean
	DataProvider jpaDao()
	{
		return new GenericDaoJpa();
	}

	@Bean
	public PlatformTransactionManager transactionManager()
	{
		JpaTransactionManager jtManager = new JpaTransactionManager(
			getEntityManagerFactoryBean().getObject());
		return jtManager;
	}

	@Bean
	public LocalEntityManagerFactoryBean getEntityManagerFactoryBean()
	{
		LocalEntityManagerFactoryBean em = new LocalEntityManagerFactoryBean();
		
		String persistenceUnit = getPersistenceUnit();
		
		if (persistenceUnit == null || "".equals(persistenceUnit.trim())) {
			persistenceUnit = environment.getProperty(PERSISTENCE_UNIT);
		}
		
		if (persistenceUnit != null && !"".equals(persistenceUnit.trim()))
		{
			em.setPersistenceUnitName(persistenceUnit);
		}
		 
		return em;
	}

	/**
	 * Definir el unidad de persistencia en el caso de tener varias definidas en el archivos persistence.xml. En caso
	 * contrario, devolver null.
	 * 
	 * @return
	 */
	public abstract String getPersistenceUnit();

}
