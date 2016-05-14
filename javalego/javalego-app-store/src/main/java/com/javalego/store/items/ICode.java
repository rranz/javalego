package com.javalego.store.items;

import com.javalego.store.items.impl.Code;

/**
 * Acceso a los recursos relacionados con el código.
 * 
 * @see Code
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ICode {

	/**
	 * Repositorio de código (Github, Jira, ...) donde está alojado nuestro código.
	 * @return
	 */
	IRepository getRepository();
	
	/**
	 * Url de acceso al repositorio donde está alojado nuestro código.
	 * @return
	 */
	String getUrlRepository();
	
	/**
	 * Métricas de código (Ej.: SonarQube) url
	 * @return
	 */
	String getMetrics();
	
	/**
	 * Documentación url
	 * @return
	 */
	String getDoc();

	/**
	 * JavaDoc url
	 * @return
	 */
	String getJavadoc();

}
