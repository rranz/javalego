package com.javalego.entity.impl;

import javax.persistence.MappedSuperclass;

import com.javalego.entity.Entity;

/**
 * Clase básica de entidad que implementa el campo id que es común para cualquier clase y tabla dentro de GANA.
 * 
 * @author ROBERTO RANZ
 */
@MappedSuperclass
public abstract class AbstractEntity implements Entity {

	
}
