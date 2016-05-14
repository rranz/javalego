package com.javalego.entity.impl;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.javalego.entity.IdNumberEntity;

/**
 * Implementación de la interface IIdNumberObject. Esta es la clase utilizada
 * principalmente por la arquitectura GANA para sus módulos.
 * 
 * @author ROBERTO RANZ
 */
@MappedSuperclass
public abstract class IdNumberEntityImpl extends IdEntityImp implements IdNumberEntity {

	/**
	 * Identificador de registro secuencial. Común para todas las entidades.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Override
	public String getIdName() {
		return DEFAULT_ID;
	}

	/**
	 * Campo Id para las búsquedas JPA que utiliza el token this que será
	 * convertido al nombre o alias de la clase sobre la que se realiza la
	 * consulta JQL.
	 */
	@Override
	public String getThisIdName() {
		return THIS + "." + getIdName();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setId(Object id) {
		this.id = (Long) id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {

			return true;

		}

		if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {

			return false;

		}

		IdNumberEntityImpl that = (IdNumberEntityImpl) obj;

		return this.id.equals(that.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */

	@Override
	public int hashCode() {

		return id == null ? 0 : id.hashCode();

	}
}
