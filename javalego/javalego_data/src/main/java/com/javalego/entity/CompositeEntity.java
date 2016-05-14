package com.javalego.entity;

import java.util.List;

/**
 * Interface para definir objetos de entidad JPA que tienen clave compuesta por varios campos o propiedades..
 * 
 * @author ROBERTO RANZ
 */
public interface CompositeEntity extends Entity {

	/**
	 * Obtiene la lista de campos de la clave primaria.
	 * 
	 * @return
	 */
	public List<String> getCompositeKeys();

}
