package com.javalego.store.items;

import java.util.Collection;

import com.javalego.store.items.impl.Version;

/**
 * Proyecto
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IProject extends IAuthor, IBaseItem
{
	/**
	 * Tipo de proyecto: arquitectura o negocio.
	 * @return
	 */
	Type getType();
	
	/**
	 * Versión actual
	 * @return
	 */
	Version getCurrentVersion();
	
	/**
	 * Versiones
	 * @return
	 */
	//Collection<ProjectVersion> getVersions();	
	
	/**
	 * Lista de proyectos que tiene asociados.
	 * Un producto puede tener vinculados varios proyectos: Ej.: RRHH tiene múltiples proyectos.
	 * Accordion tiene 2 proyectos vinculados.
	 * @return
	 */
	Collection<IProduct> getProducts();

	/**
	 * Licencia
	 * @return
	 */
	ILicense getLicense();
	
	/**
	 * Proveedores soportados del producto o proyecto.
	 * @return
	 */
	Collection<IProvider> getProviders();
	
	/**
	 * Nombres de los proveedores soportados
	 * @return
	 */
	String getProviderNames();
	
	/**
	 * Accesos a redes sociales
	 * @return
	 */
	ISocial getSocial();
	
	/**
	 * Acceso a los recursos del código
	 * @return
	 */
	ICode getCode();
	
	/**
	 * Entorno de ejecución
	 * @return
	 */
	String getEnvironment();
	
	/**
	 * Demo
	 * @return
	 */
	IDemo getDemo();

	/**
	 * Comentarios sobre los proveedores o tecnologías usadas.
	 * @return
	 */
	String getComment_providers();
	
	/**
	 * Comentarios
	 * @return
	 */
	Collection<IComment> getComments();
}
