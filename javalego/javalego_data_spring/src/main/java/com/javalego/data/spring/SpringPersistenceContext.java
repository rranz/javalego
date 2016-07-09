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
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import com.javalego.data.DataProvider;
import com.javalego.data.spring.jpa.GenericDaoJpa;
import com.javalego.exception.LocalizedException;

/**
 * Configuración básica de Spring Data JPA basada en anotaciones (no requiere applicationContext.xml).
 * <p>
 * Al extender esta clase, debe implementar los métodos de definición de propiedades JPA (como driver, package.to.scan y
 * datasource) y el JpaVendorAdapter.
 * <p>
 * Esta clase incluye, de forma opcional, métodos de obtención de propiedades a partir de ficheros de propiedades que
 * podemos definir usando la anotación @PropertySource("classpath:application.properties").
 * <p>
 * Ejemplo de un archivo de configuración:
 * <p>
 * 
 * <pre>
 * db.dialect=org.hibernate.dialect.H2Dialect
 * db.driver=org.h2.Driver
 * db.url=jdbc:h2:mem:datajpa
 * db.username=sa
 * db.password=
 * 
 * db.show_sql=true
 * db.generate_ddl=true
 * 
 * package.to.scan=
 * 
 * hibernate.format_sql=true
 * hibernate.hbm2ddl.auto=update|create|create-drop|validate (valores posibles)
 * </pre>
 * 
 * @author ROBERTO RANZ
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public abstract class SpringPersistenceContext
{
	public static final String PERSISTENCE_UNIT = "persistence-unit";

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

	@Resource
	protected Environment environment;

	@Bean
	DataProvider jpaDao()
	{
		return new GenericDaoJpa();
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
	 * Definición de las propiedades del datasource para realizar la conexión a la base de datos.
	 * 
	 * @return
	 */
	protected abstract void setDataSourceProperties(DriverManagerDataSource dataSource);

	@Bean
	public JpaVendorAdapter jpaVendorAdapter()
	{
		return getJpaVendorAdapter();
	}

	/**
	 * Obtener el valor de una propiedad del archivo de configuración definido en la anotación @PropertyResource
	 *
	 * @param key
	 *            clave propiedad
	 * @return
	 * @throws LocalizedException
	 */
	protected String getProperty(String key)
	{
		return getProperty(key, false);
	}

	/**
	 * Obtener el valor de una propiedad del archivo de configuración definido en la anotación @PropertyResource
	 * 
	 * @param key
	 *            clave propiedad
	 * @param required
	 *            valor requerido
	 * @return
	 * @throws LocalizedException
	 */
	protected String getProperty(String key, boolean required)
	{
		if (environment == null)
		{
			return null;
		}

		String value = environment.getProperty(key);

		if (required && value == null)
		{
			logger.error("Config datasource: key '" + key + "' is null. Required value.");
			return null;
		}
		return value == null ? "" : value;
	}

	@Bean
	public MessageSource messageSource()
	{
		return new ResourceBundleMessageSource();
	}

	// Beans para configurar sin archivo persistence.xml

	@Bean
	public DataSource dataSource()
	{
		logger.info("Loading config properties ...");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		setDataSourceProperties(dataSource);

		logger.info("JPA CONNECTION: url=" + dataSource.getUrl() + " user=" + dataSource.getUsername());

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
		JpaVendorAdapter jpaVendorAdapter)
	{
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setJpaVendorAdapter(jpaVendorAdapter);

		// Obtener el valor de la unidad de persistencia, en caso de existir múltiples definiciones.
		String persistenceUnit = getPersistenceUnit();
		if (persistenceUnit == null || "".equals(persistenceUnit.trim()))
		{
			persistenceUnit = environment.getProperty(PERSISTENCE_UNIT);
		}

		if (persistenceUnit != null && !"".equals(persistenceUnit.trim()))
		{
			em.setPersistenceUnitName(persistenceUnit);
		}

		// Propiedades jpa
		Properties properties = new Properties();
		setJpaProperties(properties);
		em.setJpaProperties(properties);
		em.setPackagesToScan(getPackages());

		return em;
	}

	/**
	 * Lista de packages de entities a escanear.
	 * 
	 * @param values
	 * @return
	 */
	private String[] getPackages()
	{
		String ps = getPackagesToScan();

		// Buscar el valor de la propiedad en las variables de entorno,
		// posiblemente inyectadas desde una fichero de propidades
		// @PropertySource.
		if (ps == null)
		{
			ps = environment.getProperty(PACKAGES_TO_SCAN);
		}
		if (ps != null)
		{
			ps = StringUtils.trimAllWhitespace(ps);
		}
		return ps != null ? ps.split("\\,") : null;
	}

	/**
	 * Definir el unidad de persistencia en el caso de tener varias definidas en el archivos persistence.xml. En caso
	 * contrario, devolver null.
	 * 
	 * @return
	 */
	public abstract String getPersistenceUnit();
}
