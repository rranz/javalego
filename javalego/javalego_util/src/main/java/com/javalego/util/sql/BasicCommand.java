package com.javalego.util.sql;

/**
 * <p>
 * Comando SQL
 * </p>
 * <p>
 * Description: Clase que recoge todos los parámetros para construir una
 * sentencia SQL válida
 * </p>
 * 
 * @author Roberto Ranz
 * @version 1.0
 */
public abstract class BasicCommand {

	String where;
	String[] fieldNames;
	String tableName;
	String filter;

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}

	/**
	 * Asignación de nombres mediante un String.
	 * 
	 * @param fieldNames
	 */
	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames.split(",");
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	/**
	 * Devuelve una cadena con la lista de campos encadenados por el carácter ,.
	 * 
	 * @return
	 */
	public String getStringFieldNames() {
		String listFieldNames = "";

		for (int i = 0; i < fieldNames.length; i++) {
			listFieldNames += fieldNames[i] + (i < fieldNames.length - 1 ? "," : "");
		}
		return listFieldNames;
	}

	public void setTableName(String tablename) {
		this.tableName = tablename;
	}

	public String getTableName() {
		return tableName;
	}

}
