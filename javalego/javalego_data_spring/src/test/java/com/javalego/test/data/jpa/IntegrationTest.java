package com.javalego.test.data.jpa;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base class for integration tests.
 * 
 * 
 * @author Oliver Gierke
 */
// @RunWith(SpringJUnit4ClassRunner.class)

@Transactional
public class IntegrationTest {

	@Autowired
	DataSource dataSource;

	/**
	 * 
	 * Populates the configured {@link DataSource} with data from
	 * {@code data.sql}.
	 * 
	 * 
	 * @throws SQLException
	 */

	// @Before
	public void populateDatabase() throws SQLException {

		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

		populator.addScript(new ClassPathResource("data.sql"));

		Connection connection = null;

		try {
			connection = DataSourceUtils.getConnection(dataSource);
			populator.populate(connection);
		}
		finally {

			if (connection != null) {
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
		}

	}
}
