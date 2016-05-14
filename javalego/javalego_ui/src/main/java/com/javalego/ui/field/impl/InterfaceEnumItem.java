package com.javalego.ui.field.impl;

import java.io.Serializable;

/**
 * Elemento de interface con otros sistemas que vinculamos a un item de una
 * propiedad enumerada.
 * 
 * @author ROBERTO RANZ
 */
public class InterfaceEnumItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Nombre de la interface.
	 */
	private String name;

	/**
	 * Valor
	 */
	private Object value;

	/**
	 * Descripción ampliada.
	 */
	private String description;

	public InterfaceEnumItem(String name, String value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}

	public InterfaceEnumItem() {
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name != null && value != null ? name + " = " + value : "InterfaceEnumItem class";
	}
}
