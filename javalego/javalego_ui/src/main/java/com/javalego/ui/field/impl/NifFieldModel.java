package com.javalego.ui.field.impl;

/**
 * Propiedad Nif.
 * 
 * @author ROBERTO RANZ
 */
public class NifFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 869106266925082788L;

	/**
	 * Asocia Nif a la tabla de personas y realiza la validación del Nif
	 * introducido y permite la edición o visualización de los datos de la
	 * persona.
	 */
	private boolean person = true;

	/**
	 * Path de búsqueda del objeto persona cuando estamos editando los datos de
	 * una persona desde un campo de una tabla relacionada. Ej.: Editar la
	 * persona desde los horarios de un formador. Ver caso en PSPL.xml
	 * descriptor.
	 */
	private String pathPerson;

	/**
	 * Permite la búsqueda dentro de la tabla de personas.
	 */
	private boolean findPerson = false;

	/**
	 * Buscar la persona en todas las entidades del dominio para localizar su
	 * uso.
	 */
	private boolean findAnyWhere = false;

	/**
	 * Validar el acceso a los datos de la persona cuando es de plantilla. True
	 * por defecto para evitar accesos no deseados.
	 */
	private boolean validateAccess = true;

	/**
	 * Permite la búsqueda dentro de la tabla de plantilla de la empresa.
	 */
	private boolean findCompanyStaff = false;

	/**
	 * Aviso que mostramos al usuario para que confirme la carga de los datos de
	 * persona cuando exista en la base de datos. Por defecto es igual a true.
	 */
	private boolean warning = true;

	/**
	 * Nombre del campo del país de donde obtenemos el formato para su
	 * validación.
	 */
	private String countryFieldName;

	private boolean validate = true;

	public NifFieldModel() {
	}

	public boolean isPerson() {
		return person;
	}

	public void setPerson(boolean person) {
		this.person = person;
	}

	public boolean isFindPerson() {
		return findPerson;
	}

	public void setFindPerson(boolean findPerson) {
		this.findPerson = findPerson;
	}

	public boolean isFindCompanyStaff() {
		return findCompanyStaff;
	}

	public void setFindCompanyStaff(boolean findCompanyStaff) {
		this.findCompanyStaff = findCompanyStaff;
	}

	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public boolean isValidateAccess() {
		return validateAccess;
	}

	public void setValidateAccess(boolean validateAccess) {
		this.validateAccess = validateAccess;
	}

	public String getPathPerson() {
		return pathPerson;
	}

	public void setPathPerson(String pathPerson) {
		this.pathPerson = pathPerson;
	}

	public String getCountryFieldName() {
		return countryFieldName;
	}

	public void setCountryFieldName(String countryFieldName) {
		this.countryFieldName = countryFieldName;
	}

	public boolean isFindAnyWhere() {
		return findAnyWhere;
	}

	public void setFindAnyWhere(boolean findAnyWhere) {
		this.findAnyWhere = findAnyWhere;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
}
