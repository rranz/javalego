package com.javalego.util.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

/**
 * <p>
 * Sentencia SQL
 * </p>
 * <p>
 * Description: Clase que recoge todos los parámetros para construir una
 * sentencia SQL válida
 * </p>
 * 
 * @author Roberto Ranz
 * @version 1.0
 */
public class SelectCommand extends BasicExecuteCommand {
	private String order;
	private String group;
	private String having;
	private String filter;

	private LinkedList<String> joins = new LinkedList<String>();
	private String fieldsJoins;

	public SelectCommand() {
	}

	public SelectCommand(String tableName) {
		this.tableName = tableName;
	}

	public SelectCommand(String tableName, String where) {
		this.tableName = tableName;
		this.where = where;
	}

	public SelectCommand(String tableName, String where, String order) {
		this.tableName = tableName;
		this.where = where;
		this.order = order;
	}

	/**
	 * Añadir sentencia para unir dos tablas.
	 * 
	 * @param join
	 */
	public void addJoin(String join) {
		// no incluir esta relación si ya existe una igual, ya que de lo
		// contrario
		// el gestor de base de datos provocaría un error de enlace duplicado
		for (int i = 0; i < joins.size(); i++)
			if (joins.get(i).toUpperCase().equals(join.toUpperCase()))
				return;
		joins.add(join);
	}

	public LinkedList<String> getJoins() {
		return joins;
	}

	/**
	 * SQL: where
	 * 
	 * @return
	 */
	@Override
	public String getWhere() {
		return where;
	}

	@Override
	public void setWhere(String where) {
		this.where = where;
	}

	@Override
	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * SQL: Order By
	 * 
	 * @return
	 */
	public String getOrder() {
		return order;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * SQL: Group By
	 * 
	 * @return
	 */
	public String getGroup() {
		return group;
	}

	public void setHaving(String having) {
		this.having = having;
	}

	/***
	 * SQL: Having
	 * 
	 * @return
	 */
	public String getHaving() {
		return having;
	}

	/**
	 * Tabla principal de la setencia SQL.
	 * 
	 * @param tablename
	 */
	@Override
	public void setTableName(String tablename) {
		this.tableName = tablename;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	/**
	 * Obtener un ResultSet con la consulta definida.
	 * 
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public ResultSet getResultSet(Connection connection) throws Exception {
		return connection.prepareStatement(getStatement()).executeQuery();
	}

	/**
	 * Componer la sentencia SQL mediante las propiedades definidas en esta
	 * clase.
	 * 
	 * @return
	 */
	@Override
	public String getStatement() {
		String SQL = "";
		String wherefilter = "";
		SQL += "select " + (fieldNames == null ? "*" : getStringFieldNames()) + " " + (fieldsJoins != null && !fieldsJoins.equals("") ? "," + fieldsJoins : "") + " \n";
		SQL += "from " + tableName + " \n";
		Iterator<String> iterator = joins.iterator();
		while (iterator.hasNext()) {
			SQL += iterator.next() + " \n";
		}
		if (where != null && !where.equals(""))
			wherefilter = where;
		if (filter != null && !filter.equals(""))
			wherefilter += "(" + wherefilter + " and (" + filter + ")";

		if (!wherefilter.equals(""))
			SQL += "where " + wherefilter + " \n";
		if (order != null && !order.equals(""))
			SQL += "order by " + order + " \n";
		if (group != null && !group.equals(""))
			SQL += "group by " + group + " \n";
		if (having != null && !having.equals(""))
			SQL += "having " + having + " \n";
		// System.out.println(SQL);
		return SQL;
	}

	public void setFieldsJoins(String fieldsJoins) {
		this.fieldsJoins = fieldsJoins;
	}

	public String getFieldsJoins() {
		return fieldsJoins;
	}
}
