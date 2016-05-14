package com.javalego.ui.field.impl.state;

import java.util.ArrayList;

/**
 * Validación de campos requeridos
 * 
 * @author ROBERTO RANZ
 */
public class RequiredValidation extends BasicValidation {

	private static final long serialVersionUID = 1L;

	/**
	 * Lista de propiedades separadas por el carácter |.
	 */
	private String propertyNames;

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
		return messages != null ? (String[]) messages.toArray(new String[messages.size()]) : null;
	}

}
