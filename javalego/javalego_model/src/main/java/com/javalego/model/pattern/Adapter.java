package com.javalego.model.pattern;

/**
 * Interface básica de un adaptador usado para implementar el código de adaptación de las interfaces de nuestros
 * servicios con diferentes API,s o frameworks.
 * <p>
 * Ej.: Adapters para acceso a datos (interface DataProvider) como Spring Data, Hibernate, EclipseLink, ...
 * <p>
 * Adapters para implementar servicios de seguridad en Shiro, Spring Security, ...
 * <p>
 * Adapters para implementar servicios frontend como AngularJS, JSF, Spring MVC, Vaadin, Android, ...
 * 
 * @author ROBERTO RANZ
 *
 */
public interface Adapter
{
	/**
	 * Nombre clave de la tecnología (framework, api, ...) del adaptador.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Título de la tecnología (framework, api, ...) del adaptador.
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * Anotaciones sobre la adaptación realizada para integrar la tecnología usada por este adaptador.
	 * 
	 * @return
	 */
	String getDescription();

}
