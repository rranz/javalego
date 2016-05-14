package com.javalego.data.provider;

/**
 * Replicar la lista de métodos e Spring Data para compatibilidad los repositorios creados.
 * 
 * También servirá para JPA y datos en memoria o dummys o que vengan de servicios web, ... rest,..
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IRepository<T> {

	Iterable<T> findAll();
	
}
