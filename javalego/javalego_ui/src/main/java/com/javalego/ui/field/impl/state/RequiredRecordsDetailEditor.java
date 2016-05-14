package com.javalego.ui.field.impl.state;

/**
 * Validación del número de registros
 * 
 * @author ROBERTO RANZ
 */
public class RequiredRecordsDetailEditor extends BasicValidation {

	private static final long serialVersionUID = 1L;

	/**
	 * Nombre de referencia al editor dentro del distribuidor de propiedades
	 * 
	 * @see SectionRows
	 */
	private String name;

	/**
	 * Mensaje
	 */
	private String message;

	/**
	 * Condición que debe cumplirse para aplicar el requerimiento.
	 */
	private String conditionExpression;

	/**
	 * Condición que debe cumplirse para contar registros.
	 */
	private String countConditionExpression;

	@Override
	public String[] getMessages() {
		return new String[] { message };
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCountConditionExpression() {
		return countConditionExpression;
	}

	public void setCountConditionExpression(String countConditionExpression) {
		this.countConditionExpression = countConditionExpression;
	}

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

}
