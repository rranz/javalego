package com.javalego.ui.field.impl;


/**
 * Código nacional de afiliación a la seguridad social.
 * 
 * @author ROBERTO RANZ
 */
public class NassFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 1434383053442860398L;

	/**
	 * Nombre del campo del país de donde obtenemos el formato para su
	 * validación.
	 */
	private String countryFieldName;

	/**
	 * Nombre de la propiedad que contiene el tipo de seguridad social que
	 * tenemos que validar para establecer el número máximo de dígitos y el tipo
	 * de validación.
	 */
	private String tipo_ss_fieldName;

	public NassFieldModel() {
		setOnlyNumbers(true);
	}

	public String getTipo_ss_fieldName() {
		return tipo_ss_fieldName;
	}

	public void setTipo_ss_fieldName(String tipo_ss_fieldName) {
		this.tipo_ss_fieldName = tipo_ss_fieldName;
	}

	public String getCountryFieldName() {
		return countryFieldName;
	}

	public void setCountryFieldName(String countryFieldName) {
		this.countryFieldName = countryFieldName;
	}

}
