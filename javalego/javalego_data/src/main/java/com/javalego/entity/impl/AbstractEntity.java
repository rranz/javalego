package com.javalego.entity.impl;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.javalego.entity.Entity;

/**
 * Clase básica de entidad.
 * 
 * @author ROBERTO RANZ
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> implements Entity<T>
{
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof Entity))
		{
			return false;
		}
		if (getId() == null || ((Entity<?>) obj).getId() == null)
		{
			return false;
		}
		if (!getId().equals(((Entity<?>) obj).getId()))
		{
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return getId() == null ? super.hashCode() : getId().hashCode();
	}

}
