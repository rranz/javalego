package com.javalego.data.test;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.javalego.data.jpa.JpaProvider;
import com.javalego.data.spring.SpringPersistenceContext;

@Configuration
@EnableJpaRepositories("repositories")
@PropertySource("classpath:application.properties")
public class DemoPersistenceContext extends SpringPersistenceContext
{
	@Bean
	public JpaProvider dataProvider()
	{
		return new DemoDataProvider();
	}

	/**
	 * TransactionManager requerido para Spring JPA Repositories.
	 * 
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager()
	{
		return new DataSourceTransactionManager(dataSource());
	}

	@Override
	protected void setJpaProperties(Properties properties)
	{
	}

	@Override
	protected void setDataSourceProperties(DriverManagerDataSource dataSource)
	{
		dataSource.setDriverClassName(getProperty(DB_DRIVER, true));
		dataSource.setUrl(getProperty(DB_URL, true));
		dataSource.setUsername(getProperty(DB_USERNAME, true));
		dataSource.setPassword(getProperty(DB_PASSWORD));
	}

	@Override
	protected JpaVendorAdapter getJpaVendorAdapter()
	{
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql("true".equals(getProperty(DB_SHOW_SQL)));
		adapter.setGenerateDdl("true".equals(getProperty(DB_GENERATE_DDL)));
		adapter.setDatabasePlatform(getProperty(DB_DIALECT));
		return adapter;
	}

	@Override
	protected String getPackagesToScan()
	{
		return getProperty(PACKAGES_TO_SCAN);
	}

	@Override
	public String getPersistenceUnit()
	{
		return getProperty(PERSISTENCE_UNIT);
	}

}