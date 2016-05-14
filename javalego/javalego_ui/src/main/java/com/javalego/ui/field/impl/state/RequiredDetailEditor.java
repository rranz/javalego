package com.javalego.ui.field.impl.state;

import java.util.ArrayList;

/**
 * Validación de campos requeridos
 * @author ROBERTO RANZ
 */
public class RequiredDetailEditor extends BasicValidation {

	private static final long serialVersionUID = 1L;

	/**
	 * Nombre de referencia al editor dentro del distribuidor de propiedades 
	 * @see SectionRows
	 */
	private String name;
	
	/**
	 * Lista de propiedades separadas por el carácter |.
	 */
	private String propertyNames;
	
	/**
	 * Lista de propiedades donde alguna tiene que contener un valor para evitar evitar el requerimiento.
	 */
	private String choicePropertyNames;
	
	/**
	 * Número mínimo de registros
	 */
	private int minRecords;
	
	/**
	 * Número máximo de registros.
	 */
	private int maxRecords;
	
	/**
	 * Condición que debe cumplirse para aplicar el requerimiento.
	 */
	private String conditionExpression;

	/**
	 * Mensajes
	 */
	private ArrayList<String> messages;
	
	public String getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(String propertyNames) {
		this.propertyNames = propertyNames;
	}

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	@Override
	public String[] getMessages() {
		return messages != null ? (String[])messages.toArray(new String[messages.size()]) : null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinRecords() {
		return minRecords;
	}

	public void setMinRecords(int minRecords) {
		this.minRecords = minRecords;
	}

	public int getMaxRecords() {
		return maxRecords;
	}

	public void setMaxRecords(int maxRecords) {
		this.maxRecords = maxRecords;
	}

	public String getChoicePropertyNames() {
		return choicePropertyNames;
	}

	public void setChoicePropertyNames(String choicePropertyNames) {
		this.choicePropertyNames = choicePropertyNames;
	}
	
	
}
