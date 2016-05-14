package com.javalego.ui.vaadin.component.search;


/**
 * Interface para realizar búsquedas en listas de valores.
 * 
 * @author ROBERTO RANZ
 */
public interface ISearch {

	/**
	 * Buscar un objeto dentro de una lista mediante un texto.
	 * @param text
	 * @return
	 */
	void find(String text);
	
	/**
	 * Permite definir las búsquedas que representan un filtro de selección de objetos dentro de la lista.
	 * @return
	 */
	boolean isFilter();
	
}
