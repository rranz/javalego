package com.javalego.entity.impl;

import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.javalego.entity.CompositeEntity;
import com.javalego.util.EntityUtil;

/**
 * Implementación de la interface ICompositeBaseObject para entidades de clave compuesta por varios campos o propiedades.
 * 
 * @author ROBERTO RANZ
 */
@MappedSuperclass
public abstract class AbstractCompositeEntity extends AbstractEntity implements CompositeEntity {

	/**
	 * Lista de campos que componen la clave primaria. Por defecto, es ID.
	 */
	@Transient
	private List<String> compositeKeys;

	@Override
	public List<String> getCompositeKeys() {

		if (compositeKeys == null) {
			compositeKeys = EntityUtil.getCompositeKeys(this);
		}
		return compositeKeys;
	}

	public void setCompositeKeys(List<String> compositeKeys) {
		this.compositeKeys = compositeKeys;
	}

}
