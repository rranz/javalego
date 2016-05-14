package com.javalego.pdf.tables;

import java.awt.Color;

/**
 * Campo
 * @author ROBERTO RANZ
 */
public class Field {

	/**
	 * Nombre de referencia.
	 */
	private String name;
	
	/**
	 * Nombre de la propiedad del objeto de donde obtenemos el valor a incluir en la celda de la tabla. Si no existe este valor se
	 * entiende que es un array de valores y se obtendría el valor por su posición dentro del array.
	 */
	private String propertyName;
	
	/**
	 * Título a mostrar en la tabla como encabezado.
	 */
	private String title;
	
	private String description;
	
	private Color backgroundColor;
	
	private Color foreColor;

	private Color headerBackgroundColor;
	
	private Color headerForeColor;
	
	private String defaultValue;
	
	public Field(String title) {
		this.title = title;
	}
	
	public Field(String propertyName, String title) {
		this.propertyName = propertyName;
		this.title = title;
	}
	
	public Field(String propertyName, String title, String name) {
		this.propertyName = propertyName;
		this.title = title;
		this.name = name;
	}
	
	public Field(String propertyName, String title, String name, String defaultValue) {
		this.propertyName = propertyName;
		this.title = title;
		this.name = name;
		this.defaultValue = defaultValue;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getForeColor() {
		return foreColor;
	}

	public void setForeColor(Color foreColor) {
		this.foreColor = foreColor;
	}

	public Color getHeaderBackgroundColor() {
		return headerBackgroundColor;
	}

	public void setHeaderBackgroundColor(Color headerBackgroundColor) {
		this.headerBackgroundColor = headerBackgroundColor;
	}

	public Color getHeaderForeColor() {
		return headerForeColor;
	}

	public void setHeaderForeColor(Color headerForeColor) {
		this.headerForeColor = headerForeColor;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
