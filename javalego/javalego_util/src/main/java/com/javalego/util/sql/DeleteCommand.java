package com.javalego.util.sql;

/**
 * <p>
 * Sentencia SQL
 * </p>
 * <p>
 * Description: Clase que recoge todos los parámetros para construir una
 * sentencia Delete SQL válida
 * </p>
 * 
 * @author Roberto Ranz
 * @version 1.0
 */
public class DeleteCommand extends BasicExecuteCommand {

	public DeleteCommand() {
		setTypeUpdate(REMOVE);
	}

}
