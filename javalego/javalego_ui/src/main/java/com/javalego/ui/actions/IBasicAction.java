package com.javalego.ui.actions;

import com.javalego.exception.LocalizedException;

/**
 * Acción básica que ejecuta una acción que no necesita datos de contexto.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBasicAction extends IAction {

	/**
	 * Ejecución de una acción.
	 * @param listener
	 * @throws LocalizedException
	 */
	public abstract void execute() throws LocalizedException;
	
}
