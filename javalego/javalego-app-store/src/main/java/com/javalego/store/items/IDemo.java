package com.javalego.store.items;

/**
 * Datos para acceder a demos y su código fuente.
 *  
 * @author ROBERTO RANZ
 *
 */
public interface IDemo {

	/**
	 * Url de la demo del producto
	 * @return
	 */
	String getUrl();
	
	/**
	 * Código o nombre de la vista (View Vaadin) que se usará para la demo.
	 * @return
	 */
	String getView();
	
	/**
	 * Url que muestra un ejemplo del código para construir un producto. Actualmente, utilizamos
	 * páginas html que utilizan la librería js syntaxhighlighter pero sería conveniente utilizar un addons de vaadin como componente e integrarlo en la misma página).
	 * @return
	 */
	String getUrlCode();	
}
