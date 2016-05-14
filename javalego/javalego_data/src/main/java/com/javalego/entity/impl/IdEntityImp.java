package com.javalego.entity.impl;

import javax.persistence.MappedSuperclass;

import com.javalego.entity.IdEntity;

/**
 * Implementación de la interface IIdObject. Campos con valor de clave indefinida, principalmente String.
 * 
 * NOTA: Los objetos con claves Id no incrementales que se permita su edición, hay que tener en cuenta dejar en sólo lectura la edición de esta campo clave cuando se modifique porque si persistimos el
 * objeto JPA no localizará la clave y se DUPLICARÁ el registro, teniendo ahora dos objetos con distinta clave.
 * 
 * TODO: Falta implementar en GANA la generación de componentes de sólo lectura asociados a estos campos clave no incrementales y editables.
 * 
 * @author ROBERTO RANZ
 */
@MappedSuperclass
public abstract class IdEntityImp extends AbstractEntity implements IdEntity {

	/**
	 * Campo Id para las búsquedas JPA que utiliza el token this que será convertido al nombre o alias de la clase sobre la que se realiza la consulta JQL.
	 */
	@Override
	public String getThisIdName() {
		return THIS + "." + getIdName();
	}

}
