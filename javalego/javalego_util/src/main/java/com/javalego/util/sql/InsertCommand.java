package com.javalego.util.sql;

/**
 * <p>
 * Sentencia SQL
 * </p>
 * <p>
 * Description: Clase que recoge todos los parámetros para construir una
 * sentencia Insert SQL válida
 * </p>
 * 
 * @author Roberto Ranz
 * @version 1.0
 */
public class InsertCommand extends BasicExecuteCommand {

	public InsertCommand() {
		setTypeUpdate(INSERT);
	}

}
