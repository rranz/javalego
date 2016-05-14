package com.javalego.data.jpa;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuración básica de Spring Data (JPA Hibernate) basada en anotaciones (no
 * requiere applicationContext.xml) para utilizar el acceso a base de datos
 * mediante JPA. Define el datasource, entity manager y transaction manager.
 * 
 * El archivo application.properties contiene configuración de acceso a la base
 * de datos mediante los drivers Jdbc (H2, MySql y PostgreSQL) y la definición
 * de los packages de las clases de entidad Jpa que utilizaremos en nuestro
 * proyecto.
 * 
 * Puede utilizar directamente esta clase pero necesitará el fichero de
 * propiedades que debe existir en su classpath application.properties.
 * 
 * Incluir anotación: @PropertySource("classpath:application.properties")
 * 
 * Ej. propiedades que necesitamos definir:
 * 
 * <pre>
 * #Database Configuration
 * db.driver=org.h2.Driver
 * #db.driver=com.mysql.jdbc.Driver
 * #db.driver=org.postgresql.Driver
 * db.url=jdbc:h2:mem:datajpa
 * #db.url=jdbc:mysql://localhost:3306/datajpa
 * #db.url=jdbc:postgresql://localhost/datajpa
 * db.username=sa
 * db.password=
 * 
 * #Hibernate Configuration
 * db.dialect=org.hibernate.dialect.H2Dialect
 * #db.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
 * #db.dialect=org.hibernate.dialect.PostgreSQLDialect
 * hibernate.format_sql=true
 * db.show_sql=true
 * db.generate_ddl=true
 * #(opcional) hibernate.hbm2ddl.auto=update|create|create-drop|validate (valores posibles)
 * 
 * #EntityManager
 * #Declares the base package of the entity classes (character , several packages).
 * entitymanager.packages.to.scan=com.javalego.jpa.entities
 * 
 * </pre>
 * 
 * @author ROBERTO RANZ
 * 
 */
@Configuration
@EnableTransactionManagement
// @EnableJpaRepositories
abstract public class HibernateApplicationContext extends ApplicationContext {

	private static final String DB_DRIVER = "db.driver";
	private static final String DB_DIALECT = "db.dialect";
	private static final String DB_URL = "db.url";
	private static final String DB_SHOW_SQL = "db.show_sql";
	private static final String DB_GENERATE_DDL = "db.generate_ddl";
	private static final String DB_USERNAME = "db.username";
	private static final String DB_PASSWORD = "db.password";

	private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String HIBERNATE_DDL = "hibernate.hbm2ddl.auto";

	private static final String PACKAGES_TO_SCAN = "packages.to.scan";

	public static final Logger logger = Logger.getLogger(HibernateApplicationContext.class);

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		logger.info("Loading config properties ...");

		dataSource.setDriverClassName(getValue(DB_DRIVER, true));
		dataSource.setUrl(getValue(DB_URL, true));
		dataSource.setUsername(getValue(DB_USERNAME, true));
		dataSource.setPassword(getValue(DB_PASSWORD, false));

		logger.info("JPA CONNECTION: url=" + dataSource.getUrl() + " user=" + dataSource.getUsername());

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(dataSource);
		em.setJpaVendorAdapter(jpaVendorAdapter);

		em.setPackagesToScan(environment.getRequiredProperty(PACKAGES_TO_SCAN).split("\\,"));

		// Propiedades Hibernate
		em.getJpaPropertyMap().put("hibernate.format_sql", "true".equals(getValue(HIBERNATE_FORMAT_SQL, false)));

		// Crear ddl con Hibernate
		String ddl = getValue(HIBERNATE_DDL, false);
		if (!"".equals(ddl)) {
			em.getJpaPropertyMap().put(HIBERNATE_DDL, ddl);
		}

		return em;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

		adapter.setShowSql("true".equals(getValue(DB_SHOW_SQL, false)));

		adapter.setGenerateDdl("true".equals(getValue(DB_GENERATE_DDL, false)));

		adapter.setDatabasePlatform(getValue(DB_DIALECT, false));

		return adapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

}
