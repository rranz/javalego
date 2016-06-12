package com.javalego.entity.impl;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Entidad que incluye el campo id autogenerado que es usado de forma estándar en muchas aplicaciones JPA, reutilizando
 * su código.
 * 
 * @author ROBERTO RANZ
 */
@MappedSuperclass
public abstract class EntityId extends AbstractEntity<Long>
{
	/**
	 * Identificador de registro secuencial.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public void setId(Long id)
	{
		this.id = id;
	}

}
