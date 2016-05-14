package com.javalego.ui.field.impl;


/**
 * Propiedad que representa un Cif.
 * @author ROBERTO RANZ
 */
public class CifFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 4784234208109265589L;

	/**
	 * Posibilidad de crear la empresa si el cif introducido no existe en la tabla de empresas. Por defecto = true pero sería conveniente poner a false cuando se intente crea una empresa desde un módulo como Empresas del Grupo donde no sólo hay que crear la empresa sino que, además, hay que dar de alta el cliente , quedando el proceso a medias.
	 */
	private boolean create = true;
	
	/**
	 * Nombre del campo del país de donde obtenemos el formato para su validación.
	 */
	private String countryFieldName;
	
	private boolean validate = true;
	
	/**
	 * Edición en sólo lectura de los datos de la empresa.
	 */
	private boolean readOnlyEdit;
	
	public CifFieldModel() {
		setUppercase(true);
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public String getCountryFieldName() {
		return countryFieldName;
	}

	public void setCountryFieldName(String countryFieldName) {
		this.countryFieldName = countryFieldName;
	}

	public boolean isReadOnlyEdit() {
		return readOnlyEdit;
	}

	public void setReadOnlyEdit(boolean readOnlyEdit) {
		this.readOnlyEdit = readOnlyEdit;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
}
