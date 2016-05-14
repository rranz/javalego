package com.javalego.ui.field.impl.state;

/**
 * Validación realizada mediante una expresión aplicada sobre el contexto de edición del registro.
 * @author ROBERTO RANZ
 */
public class ValidationExpression extends BasicValidation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Mensaje
	 */
	private String message;
	
	/**
	 * Expresión previa que debe de cumplirse para evaluar la expresión. Esta propiedad nos permite incluir sentencias que no requieran ejecutar, por ejemplo, sentencias sql
	 * si incluimos una evaluación previa que no tiene que realizar este tipo de operaciones de cálculo. Ej.: AccionPlanGrupo donde se evalúa si las horas son presenciales previamente
	 * a la búsqueda del número de alumnos finalizados.
	 */
	private String priorExpression;
	
	/**
	 * Expresión.
	 */
	private String expression;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String[] getMessages() {
		if (message != null)
			return new String[] {message};
		else
			return null;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getPriorExpression() {
		return priorExpression;
	}

	public void setPriorExpression(String priorExpression) {
		this.priorExpression = priorExpression;
	}

}
