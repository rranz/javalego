package com.javalego.entity;


/**
 * Interface básica para objetos de entidades JPA con clave numérica. 
 * @author ROBERTO RANZ
 */
public interface IdNumberEntity extends IdEntity {
		
	/**
	 * Obtener el objeto de la propiedad ID que presenta la clave primaria.
	 * @return
	 */
	@Override
	Long getId();
	
	/**
	 * Establecer el objeto de la propiedad ID que representa la clave primaria.
	 * @param object
	 */
	void setId(Long id);
	
}
