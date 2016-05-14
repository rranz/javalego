package com.javalego.msoffice;

import com.jacob.com.Dispatch;

/**
 * Elemento básico de una aplicación MS-Office (Tables, Document, Bookmarks, etc.).
 * @author ROBERTO RANZ
 */
public abstract class BasicElementApp {

	/**
	 * Puntero al elemento de la aplicación.
	 */
	protected Dispatch pointer;
	
	/**
	 * Nombre del elemento en la aplicación.
	 * @return
	 */
	protected abstract String getInternalName();
	
	/**
	 * Instanciar puntero al elemento de la aplicación.
	 * @param pointer
	 */
	protected abstract void setPointer() throws Exception;

	public Dispatch getPointer() throws Exception {
		if (pointer == null)
			setPointer();
		return pointer;
	}
	
}
