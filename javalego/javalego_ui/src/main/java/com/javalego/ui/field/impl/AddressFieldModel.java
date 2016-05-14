package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Propiedad que representa un domicilio cuya información está compuesta
 * por varios campos: país, provincia, ciudad, calle, cp, piso y número.
 * @author ROBERTO RANZ
 *
 */
public class AddressFieldModel extends AbstractFieldModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5523540515115046466L;

	/**
	 * Campo: País
	 */
	private String country;
	
	/**
	 * País por defecto.
	 */
	private String defaultCountry;
	
	/**
	 * Campo: Nombre de la provincia o estado
	 */
	private String state;

	/**
	 * Campo: Población cuando no se indica el código de ciudad o poblacion.
	 */
	private String population;

	/**
	 * Tipo de domicilio (calle / avenida, etc.)
	 */
	private String street_type;
	
	/**
	 * Campo de comunidad autónoma o región.
	 */
	private String region;
	
	/**
	 * Campo: Calle
	 */
	private String street;
	
	/**
	 * Campo alternativo de dirección con un formato diferente al estándar: domicilio, localidad y provincia.
	 */
	private String address2FieldName;
	
	/**
	 * Campo: Piso
	 */
	private String streetFloor;
	
	/**
	 * Campo: Número piso.
	 */
	private String streetNumber;

	/**
	 * Código postal
	 */
	private String codPostal;
	
	public AddressFieldModel() {
	}

	/**
	 * Constructor
	 * @param name
	 * @param title
	 */
	public AddressFieldModel(String name, Key title) {
		super(name, title);
	}

	/**
	 * Constructor
	 * @param name
	 * @param title
	 * @param description
	 */
	public AddressFieldModel(String name, Key title, Key description) {
		super(name, title);
	}	
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetFloor() {
		return streetFloor;
	}

	public void setStreetFloor(String streetFloor) {
		this.streetFloor = streetFloor;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getStreet_type() {
		return street_type;
	}

	public void setStreet_type(String street_type) {
		this.street_type = street_type;
	}

	public String getDefaultCountry() {
		return defaultCountry;
	}

	public void setDefaultCountry(String defaultCountry) {
		this.defaultCountry = defaultCountry;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress2FieldName() {
		return address2FieldName;
	}

	public void setAddress2FieldName(String address2FieldName) {
		this.address2FieldName = address2FieldName;
	}

}
