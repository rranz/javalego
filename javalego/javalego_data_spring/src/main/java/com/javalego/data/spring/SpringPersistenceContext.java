package com.javalego.data.spring;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.javalego.exception.LocalizedException;

/**
 * Configuración básica de Spring Data (JPA Hibernate) basada en anotaciones (no
 * requiere applicationContext.xml) para utilizar el acceso a base de datos
 * mediante JPA. Define el datasource, entity manager y transaction manager.
 * <p>
 * El archivo application.properties contiene configuración de acceso a la base
 * de datos mediante los drivers Jdbc (H2, MySql y PostgreSQL) y la definición
 * de los packages de las clases de entidad Jpa que utilizaremos en nuestro
 * proyecto.
 * <p>
 * Puede utilizar directamente esta clase pero necesitará el fichero de
 * propiedades que debe existir en su classpath application.properties.
 * <p>
 * Incluir anotación: @PropertySource("classpath:application.properties")
 * <p>
 * Ej. propiedades que necesitamos definir:
 * <p>
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
 * </pre>
 * 
 * @author ROBERTO RANZ
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
abstract public class SpringPersistenceContext {

	// Propiedades de configuración del datasource y jpa.
	public static final String DB_DRIVER = "db.driver";
	public static final String DB_DIALECT = "db.dialect";
	public static final String DB_URL = "db.url";
	public static final String DB_SHOW_SQL = "db.show_sql";
	public static final String DB_GENERATE_DDL = "db.generate_ddl";
	public static final String DB_USERNAME = "db.username";
	public static final String DB_PASSWORD = "db.password";
	public static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	public static final String HIBERNATE_DDL = "hibernate.hbm2ddl.auto";
	public static final String PACKAGES_TO_SCAN = "packages.to.scan";

	public static final Logger logger = Logger.getLogger(SpringPersistenceContext.class);

	@Bean
	public DataSource dataSource() {

		logger.info("Loading config properties ...");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		setDataSourceProperties(dataSource);

		logger.info("JPA CONNECTION: url=" + dataSource.getUrl() + " user=" + dataSource.getUsername());

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(dataSource);
		em.setJpaVendorAdapter(jpaVendorAdapter);

		// Lista de packages de entities a escanear. Si no se define esta
		// información, hay que definir las entidades en el fichero de recursos
		// /META-INF/persistence.xml.
		String ps = getPackagesToScan();
		
		// Buscar el valor de la propiedad en las variables de entorno,
		// posiblemente inyectadas desde una fichero de propidades
		// @PropertySource.
		if (ps == null) {
			ps = environment.getProperty(PACKAGES_TO_SCAN);
		}
		if (ps != null) {
			em.setPackagesToScan(ps.split("\\,"));
		}

		Properties properties = new Properties();
		em.setJpaProperties(properties);

		// Propiedades Hibernate
		// em.getJpaPropertyMap().put("hibernate.format_sql",
		// "true".equals(getValue(HIBERNATE_FORMAT_SQL, false)));

		// Crear ddl con Hibernate
		// String ddl = getValue(HIBERNATE_DDL, false);
		// if (!"".equals(ddl)) {
		// em.getJpaPropertyMap().put(HIBERNATE_DDL, ddl);
		// }

		return em;
	}

	/**
	 * Configuración JPA
	 * 
	 * @return
	 */
	protected abstract void setJpaProperties(Properties properties);

	/**
	 * Vendor JPA Adapter
	 * 
	 * @return
	 */
	protected abstract JpaVendorAdapter getJpaVendorAdapter();

	/**
	 * Obtener la lista de paquetes de entidades jpa.
	 * 
	 * @return
	 */
	protected abstract String getPackagesToScan();

	/**
	 * Definición de las propiedades del datasource para realizar la conexión a
	 * la base de datos.
	 * 
	 * @return
	 */
	protected abstract void setDataSourceProperties(DriverManagerDataSource dataSource);

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return getJpaVendorAdapter();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}

	@Resource
	protected Environment environment;

	/**
	 * Obtener el valor de una propiedad del archivo de configuración definido
	 * en la anotación @PropertyResource
	 *
	 * @param key
	 *            clave propiedad
	 * @return
	 * @throws LocalizedException
	 */
	protected String getProperty(String key) {
		return getProperty(key, false);
	}

	/**
	 * Obtener el valor de una propiedad del archivo de configuración definido
	 * en la anotación @PropertyResource
	 * 
	 * @param key
	 *            clave propiedad
	 * @param required
	 *            valor requerido
	 * @return
	 * @throws LocalizedException
	 */
	protected String getProperty(String key, boolean required) {

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

}
