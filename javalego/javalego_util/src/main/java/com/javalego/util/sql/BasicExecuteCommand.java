package com.javalego.util.sql;

import java.sql.Connection;

/**
 * <p>Sentencia SQL </p>
 * <p>Description: Clase que recoge todos los parámetros para construir una sentencia Insert SQL válida </p>
 * @author Roberto Ranz
 * @version 1.0
 */
public abstract class BasicExecuteCommand extends BasicCommand {

  /**
   * Tipologia de ejecución de la regla de negocio.
   */
  public static String
      UPDATE = "UPDATE",
      INSERT = "INSERT",
      REMOVE = "DELETE";

  Object[] values;
  
  String typeUpdate;

  /**
	 * Ejecutar la sentencia SQL de actualización.
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public boolean execute(Connection connection) throws Exception {
		return connection.prepareStatement(getStatement()).execute();
	}
	
  public void setValues(Object[] values) {
    this.values = values;
  }

  /**
   * Constructura de la setencia SQL.
   * @return
   */
  public String getStatement() {
    
  	String SQL = "";
    String wherefilter = "";
    
    // Insert
    if (typeUpdate.equals(INSERT)){
    	
    	String listValues = "";
      
    	for(int i = 0; i < values.length; i++)
        listValues += getValueToString(values[i]).toString() + (i < values.length-1 ? "," : "");
    	
    	SQL += "INSERT INTO " + tableName + "\n";
      SQL += "(" + getStringFieldNames()  + ") VALUES(" + listValues + ")";
    }
    
    // Update
    else if (typeUpdate.equals(UPDATE)){
      
    	SQL += "UPDATE " + tableName + " SET \n";
      for(int i = 0; i < fieldNames.length; i++){
        SQL += fieldNames[i] + " = " + getValueToString(values[i]).toString() + (i < fieldNames.length-1 ? "," : "");
      }
      SQL = SQL.substring(0, SQL.length()-1);
    }
    
    // Delete
    else if (typeUpdate.equals(REMOVE)){
      SQL += "DELETE FROM " + tableName + "\n";
    }

    if (where != null && !where.equals(""))
      wherefilter = where;
    if (filter != null && !filter.equals(""))
      if (where == null || where.equals(""))
        wherefilter += "(" + filter + ")";
      else
        wherefilter += "(" + wherefilter + " AND (" + filter + ")";

    if (!wherefilter.equals(""))
      SQL += "WHERE " + wherefilter + " \n";
    return SQL;
  }
  
  private String getValueToString(Object value){
  	if (value instanceof String){
  		if (value.toString().indexOf("'") > -1)
  			return "'" + value.toString().replaceAll("'","''") + "'";
  		else
  			return "'" + value + "'";
  	}
  	else
  		return value.toString();
  }
  
	public String getTypeUpdate() {
		return typeUpdate;
	}
	public void setTypeUpdate(String typeUpdate) {
		this.typeUpdate = typeUpdate;
	}
	
}
