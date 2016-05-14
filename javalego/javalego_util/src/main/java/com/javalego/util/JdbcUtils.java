package com.javalego.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Clase de utilidad para ejecutar comandos SQL utilizando drivers JDBC en Java.
 * @author ROBERTO RANZ
 */
public class JdbcUtils {

	private static final Logger log = Logger.getLogger(JdbcUtils.class);

	// Conexión
	private Connection connection = null;

	/**
	 * Desconectar
	 * 
	 * @throws Exception
	 */
	public void disconnect() throws Exception {

		if (connection != null) {
			connection.close();
			log.info("OK: Conexión cerrada");
		}
	}

//	/**
//	 * Ejemplos
//	 */
//	public static void main(String args[]) {
//		//testMySQL();
//		//testOracle();
//	}

//	private static void testOracle() {
//		JdbcUtils jdbc = new JdbcUtils();
//		try {
//			jdbc.connect("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@host:1522:TESH", "user", "password");
//
//			CallableStatement callableStatement = null;
//
//			String insertStoreProc = buildProcedureCall("PACKAGE", "INSERT_DOCUMENT", 15);
//
//			callableStatement = jdbc.prepareCall(insertStoreProc);
//
//			callableStatement.setString(1, "");
//			callableStatement.setString(2, "");
//			callableStatement.setString(3, "");
//			InputStream bodyIn = callableStatement.getClass().getResourceAsStream("filename");
//			callableStatement.setBytes(9, getBytesFromStream(bodyIn));
//
//			callableStatement.setDate(10, new java.sql.Date(new Date().getTime()));
//			callableStatement.setDate(11, null);
//			callableStatement.setDate(12, null);
//
//			// callableStatement.setBinaryStream(10, bodyIn, (int) bodyIn...length());
//
//			callableStatement.registerOutParameter(13, java.sql.Types.NUMERIC);
//			callableStatement.registerOutParameter(14, java.sql.Types.VARCHAR);
//			callableStatement.registerOutParameter(15, java.sql.Types.NUMERIC);
//
//			callableStatement.executeQuery();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				jdbc.disconnect();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
//	private static void testMySQL() {
//		
//		JdbcUtils jdbc = new JdbcUtils();
//		
//		try {
//			jdbc.connect("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/gana", "root", "xxxxx");
//
//			jdbc.executeStatement("select *from usuarios");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				jdbc.disconnect();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}	
	
	/**
	 * Obtener llamada a un procedimiento almacenado.
	 * 
	 * @param procedure
	 * @return
	 * @throws SQLException
	 */
	public CallableStatement prepareCall(String procedure) throws SQLException {
		
		if (connection == null)
			log.error("Debe llamar al método connect() para establecer una conexión con la base de datos.");
		
		return connection.prepareCall(procedure);
	}
	
	/**
	 * Ejecutar sentencia SQL.
	 * @param statement
	 * @throws SQLException
	 */
	public void executeStatement(String statement) throws SQLException {
		
		Statement stmt = connection.createStatement();
		log.info("RESULT: " + stmt.executeUpdate(statement));		
	}

	/**
	 * Obtener una conexión Jdbc.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void connect(String driver, String url, String user, String password) throws SQLException, ClassNotFoundException {

		try {
			Class.forName(driver);

		} catch (ClassNotFoundException e) {

			log.info("Debe incluir el driver de Oracle ojdbc14.jar en la configuración del proyecto.");
			throw e;
		}
		log.info("OK: Oracle JDBC Driver Registrado!");

		try {
			connection = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {

			log.error("KO: Fallo de conexión. Ver log del error.", e);
			throw e;
		}

		if (connection != null) {
			log.info("OK: Conexión realizada");
		} else {
			log.info("KO: Fallo al realizar la conexión.");
		}

	}

	/**
	 * Fecha actual en formato SQL.
	 * 
	 * @return
	 */
	public java.sql.Date getCurrentDate() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

	/**
	 * Obtener un array de bytes del contenido del documento a insertar.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromStream(InputStream input) throws Exception {

		DataOutputStream out = null;
		DataInputStream dis = null;
		ByteArrayOutputStream fout = null;
		try {
			dis = new DataInputStream(input);
			fout = new java.io.ByteArrayOutputStream();
			out = new DataOutputStream(new BufferedOutputStream(fout));
			try {
				while (true) {
					out.writeByte(dis.readByte());
				}
			}
			// controlar el final del fichero.
			catch (EOFException ex) {
			} finally {
				out.flush();
				out.close();
			}
			return fout.toByteArray();
		} finally {
			if (dis != null)
				dis.close();
		}
	}

	/**
	 * Constructor de llamada a un procedimiento almacenado.
	 * 
	 * @param packageName
	 * @param procedureName
	 * @param paramCount
	 * @return
	 */
	public static String buildProcedureCall(String packageName, String procedureName, int paramCount) {
		
		StringBuffer sb = new StringBuffer("{call " + packageName + "." + procedureName + "(");
		for (int n = 1; n <= paramCount; n++) {
			sb.append("?");
			if (n < paramCount)
				sb.append(",");
		}
		return sb.append(")}").toString();
	}

	/**
	 * Cerrar recursos
	 * 
	 * @param rs
	 * @param s
	 * @param c
	 */
	public static void close(ResultSet rs, Statement s, Connection c) {
		
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}
		try {
			if (s != null)
				s.close();
		} catch (Exception e) {
		}
		try {
			if (c != null)
				c.close();
		} catch (Exception e) {
		}
	}

}
