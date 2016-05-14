package com.javalego.util.sql;

import java.util.*;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * <p>
 * Libería de utilidades
 * </p>
 * <p>
 * Description: Funciones de ámbito general relacionadas con Bases de Datos
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Roberto Ranz
 * </p>
 * 
 * @author Roberto Ranz
 * @version 1.0
 */
public class DBUtil {

	private DBUtil() {
	}

	/**
	 * Este método devuelve el nombre de un tipo JDBC.
	 * 
	 * @param jdbcType
	 *            Tipo de campo JDBC.
	 * @return Devuelve null si jdbcType no es reconocido.
	 */
	public static String getJdbcTypeName(int jdbcType) {
		// Usa reflexión para poblar un mapa de valores int y nombres
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		// Obtiene todos los campos de java.sql.Types
		Field[] fields = java.sql.Types.class.getFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				String name = fields[i].getName();
				Integer value = (Integer) fields[i].get(null);
				map.put(value, name);
			}
			catch (IllegalAccessException e) {
			}
		}
		return map.get(new Integer(jdbcType));
	}

	/**
	 * Devuelve el nombre del tipo de campo utilizado en el tipo de Base de
	 * Datos de la conexión actual
	 * 
	 * @param metadata
	 * @param Type
	 * @return String
	 * @throws Exception
	 */
	public static String getJdbcTypeName(DatabaseMetaData metadata, int Type) throws Exception {
		String typeName = "";
		if (metadata != null) {
			ResultSet types = metadata.getTypeInfo();
			while (types.next()) {
				if (types.getInt("DATA_TYPE") == Type) {
					typeName = types.getString("TYPE_NAME");
					break;
				}
			}
		}
		if (typeName.equals("") && Type == Types.BOOLEAN)
			return "VARCHAR";
		else if (typeName.equals("") && Type == Types.BLOB)
			return "BLOB";
		else
			return typeName;
	}

	/**
	 * Devuelve el nombre del tipo de campo utilizado en el tipo de Base de
	 * Datos de la conexión actual
	 * 
	 * @param Type
	 * @return String
	 */
	public static String getTypeName(int Type) {
		String typeName = "";
		switch (Type) {
		case Types.VARCHAR: {
			typeName = "VARCHAR";
			break;
		}
		case Types.DATE: {
			typeName = "DATE";
			break;
		}
		case Types.BLOB: {
			typeName = "BLOB";
			break;
		}
		case Types.BOOLEAN: {
			typeName = "BOOLEAN";
			break;
		}
		case Types.INTEGER: {
			typeName = "INTEGER";
			break;
		}
		case Types.FLOAT: {
			typeName = "FLOAT";
			break;
		}
		case Types.NUMERIC: {
			typeName = "NUMERIC";
			break;
		}
		case Types.LONGVARCHAR: {
			typeName = "MEMO";
			break;
		}
		case Types.BINARY: {
			typeName = "BLOB";
			break;
		}
		}
		;
		return typeName;
	}

	/**
	 * Comprueba si el tipo de dato es numérico.
	 */
	static public boolean isNumber(int dataType) {
		return dataType == Types.BIGINT || dataType == Types.BIT || dataType == Types.DECIMAL || dataType == Types.DOUBLE || dataType == Types.FLOAT || dataType == Types.INTEGER
				|| dataType == Types.NUMERIC || dataType == Types.REAL || dataType == Types.SMALLINT || dataType == Types.TINYINT;
	}

}
