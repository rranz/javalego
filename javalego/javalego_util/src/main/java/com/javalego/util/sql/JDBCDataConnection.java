package com.javalego.util.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.naming.InitialContext;

/**
 * Implementación de DataConnection para interaccionar con una base de datos
 * local (ODBC).
 * 
 * @author ROBERTO RANZ
 */
public class JDBCDataConnection extends DataConnection {

	private static final long serialVersionUID = 1L;

	/**
	 * Conexión a la base de datos.
	 */
	private Connection connection;
	
	/**
	 * This is a mutex used to control access to the conn object. Note that I
	 * create a new String object explicitly. This is done so that a single
	 * mutex is unique for each JDBCDataConnection instance.
	 */
	private final Object connMutex = new String("Connection_Mutex");
	
	/**
	 * If used, defines the JNDI context from which to get a connection to the
	 * data base
	 */
	private String jndiContext;
	
	/**
	 * When using the DriverManager to connect to the database, this specifies
	 * the URL to use to make that connection. URL's are specific to the JDBC
	 * vendor.
	 */
	private String url;
	
	/**
	 * When using the DriverManager to connect to the database, this specifies
	 * the user name to log in with.
	 */
	private String userName;
	
	/**
	 * When using the DriverManager to connect to the database, this specifies
	 * the password to log in with.
	 */
	private String password;
	
	/**
	 * When using the DriverManager to connect to the database, this specifies
	 * any additional properties to use when connecting.
	 */
	private Properties properties;

	/**
	 * Driver de conexión.
	 */
	private String driver;

	/**
	 * Create a new DatabaseDataStoreConnection. Be sure to set the JDBC
	 * connection properties (user name, password, connection method, etc) prior
	 * to connecting this object.
	 */
	public JDBCDataConnection() {
	}

	static public void main(String[] args) {
		try {

			JDBCDataConnection c = new JDBCDataConnection();

			// Connection con = c.MSAccessConnect("c:/pruebas.mdb");

			InsertCommand i = new InsertCommand();
			i.setTableName("BANCOS");
			i.setFieldNames(new String[] { "BANCO", "NOMBRE" });
			i.setValues(new Object[] { "PEPE", "222222222" });
			i.execute(c.getConnection());

			SelectCommand t = new SelectCommand("BANCOS");
			ResultSet rs = t.getResultSet(c.getConnection());
			while (rs.next()) {
				String banco = rs.getString("banco");
				String nombre = rs.getString("nombre");
				System.out.println(banco + ", " + nombre);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Crea una conexión en base al driver MSAccess.
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param passwd
	 */
	public Connection MSAccessConnect(String url) throws Exception {
		driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		loadDriver(driver);
		this.setUrl("jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + url);
		connect();
		return connection;
	}

	/**
	 * Crea una conexión en base al driver MSAccess.
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param passwd
	 */
	public void MSAccessConnect(String url, String userName, String password) throws Exception {
		driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		this.userName = userName;
		this.password = password;
		loadDriver(driver);
		this.setUrl("jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + url);
		connect();
	}

	/**
	 * Crea una nueva JDBCDataConnection e inicializa la conexión a la base de
	 * datos.
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param passwd
	 */
	public JDBCDataConnection(String driver, String url, String user, String passwd) throws Exception {
		loadDriver(driver);
		this.setUrl(url);
		this.setUserName(user);
		this.setPassword(passwd);
		connectByDriverManager();
	}

	/**
	 * Crea una nueva JDBCDataConnection e inicializa la conexión a la base de
	 * datos.
	 * 
	 * @param driver
	 * @param url
	 */
	public JDBCDataConnection(String driver, String url) throws Exception {
		loadDriver(driver);
		this.setUrl(url);
	}

	/**
	 * Cargar el Driver.
	 * 
	 * @param driver
	 * @throws Exception
	 */
	private void loadDriver(String driver) throws Exception {
		try {
			Class.forName(driver);
		}
		catch (Exception e) {
			throw new Exception("El driver no puede ser cargado.\n" + e.getMessage());
		}
		this.driver = driver;
	}

	/**
	 * Crea una nueva JDBCDataConnection e inicializa la conexión a la base de
	 * datos.
	 * 
	 * @param driver
	 * @param url
	 * @param props
	 */
	public JDBCDataConnection(String driver, String url, Properties props) throws Exception {
		loadDriver(driver);
		this.setUrl(url);
		this.setProperties(props);
	}

	/**
	 * Crea una nueva JDBCDataConnection e inicializa la conexión a la base de
	 * datos. database using the given params.
	 * 
	 * @param jndiContext
	 * @param user
	 * @param passwd
	 */
	public JDBCDataConnection(String jndiContext, String user, String passwd) throws Exception {
		this.jndiContext = jndiContext;
		this.setUserName(user);
		this.setPassword(passwd);
		loadDriver(driver);
	}

	/**
	 * @return the JDBC connection url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            set the JDBC connection url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the user name used to connect to the database
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @params userName used to connect to the database
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password used to connect to the database
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password used to connect to the database
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return JDBC connection properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            miscellaneous JDBC properties to use when connecting to the
	 *            database via the JDBC driver
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Connect to the database. This method attempts to connect via jndiContext
	 * first, if possible. If not, then it tries to connect by using the
	 * DriverManager.
	 */
	@Override
	protected void connect() throws Exception {
		// if the jndiContext is not null, then try to get the DataSource to use
		// from jndi
		if (jndiContext != null) {
			try {
				connectByJNDI();
			}
			catch (Exception e) {
				try {
					connectByDriverManager();
				}
				catch (Exception ex) {
					throw new Exception("Failed to connect to the database", e);
				}
			}
		}
		else {
			try {
				connectByDriverManager();
			}
			catch (Exception ex) {
				throw new Exception("Failed to connect to the database", ex);
			}
		}
	}

	/**
	 * Attempts to get a JDBC Connection from a JNDI javax.sql.DataSource, using
	 * that connection for interacting with the database.
	 * 
	 * @throws Exception
	 */
	private void connectByJNDI() throws Exception {
		InitialContext ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(jndiContext);
		synchronized (connMutex) {
			connection = ds.getConnection(getUserName(), getPassword());
			connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		}
	}

	/**
	 * Attempts to get a JDBC Connection from a DriverManager. If properties is
	 * not null, it tries to connect with those properties. If that fails, it
	 * then attempts to connect with a user name and password. If that fails, it
	 * attempts to connect without any credentials at all.
	 * <p>
	 * If, on the other hand, properties is null, it first attempts to connect
	 * with a username and password. Failing that, it tries to connect without
	 * any credentials at all.
	 * 
	 * @throws Exception
	 */
	private void connectByDriverManager() throws Exception {
		synchronized (connMutex) {
			if (getProperties() != null) {
				try {
					connection = DriverManager.getConnection(getUrl(), getProperties());
					connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				}
				catch (Exception e) {
					try {
						connection = DriverManager.getConnection(getUrl(), getUserName(), getPassword());
						connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					}
					catch (Exception ex) {
						connection = DriverManager.getConnection(getUrl());
						connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					}
				}
			}
			else {
				try {
					connection = DriverManager.getConnection(getUrl(), getUserName(), getPassword());
				}
				catch (Exception e) {
					e.printStackTrace();
					// try to connect without using the userName and password
					connection = DriverManager.getConnection(getUrl());
				}
			}
		}
	}

	/**
	 * Disconnects from the database and causes all of the attached DataModels
	 * to flush their contents.
	 */
	@Override
	protected void disconnect() throws Exception {
		synchronized (connMutex) {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public ResultSet executeQuery(PreparedStatement ps) {
		synchronized (connMutex) {
			if (connection != null) {
				try {
					return ps.executeQuery();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public ResultSet executeQuery(SelectCommand selectCommand) {
		synchronized (connMutex) {
			if (connection != null) {
				try {
					return connection.prepareStatement(selectCommand.getStatement()).executeQuery();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public int executeUpdate(PreparedStatement ps) {
		synchronized (connMutex) {
			if (connection != null) {
				try {
					return ps.executeUpdate();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public PreparedStatement prepareStatement(String sql) throws Exception {
		synchronized (connMutex) {
			if (connection != null) {
				return connection.prepareStatement(sql);
			}
		}
		return null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Obtener la relación de tablas de una base de datos a la que está asociada
	 * la conexión.
	 */
	public ResultSet getMatadataTables() throws Exception {
		DatabaseMetaData dbmd = connection.getMetaData();
		// Specify the type of object; in this case we want tables
		String[] types = { "TABLE" };
		/* ResultSet resultSet = */return dbmd.getTables(null, null, "%", types);

		// Get the table names
		// while (resultSet.next()) {
		// // Get the table name
		// String tableName = resultSet.getString(3);
		// // Get the table's catalog and schema names (if any)
		// String tableCatalog = resultSet.getString(1);
		// String tableSchema = resultSet.getString(2);
		// }
	}
}
