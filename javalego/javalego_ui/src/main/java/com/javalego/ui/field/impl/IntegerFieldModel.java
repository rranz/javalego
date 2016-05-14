package com.javalego.ui.field.impl;


/**
 * Valor de tipo entero.
 * 
 * @author ROBERTO RANZ
 */
public class IntegerFieldModel extends AbstractNumberFieldModel {

	private static final long serialVersionUID = 5059996933299971153L;

	/**
	 * Definición de un campo secuencial basado en una serie de campos que
	 * servirán de condición para obtener el número secuencial más alta
	 * existente en la tabla. Ej.: En Grupos añadir campo orden que buscará el
	 * cliente.plan.accion para obtener el número más alto.
	 */
	private String secuencialFieldNames;

	public String getSecuencialFieldNames() {
		return secuencialFieldNames;
	}

	public void setSecuencialFieldNames(String secuencialFieldNames) {
		this.secuencialFieldNames = secuencialFieldNames;
	}

	public IntegerFieldModel() {
	}

}
