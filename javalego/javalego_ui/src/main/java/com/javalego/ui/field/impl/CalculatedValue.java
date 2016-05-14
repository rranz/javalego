package com.javalego.ui.field.impl;

import java.io.Serializable;

import com.javalego.util.SystemUtils;
import com.javalego.util.StringUtils;

/**
 * Definición de una expresión para obtener un valor calculado de un campo.
 * @see AbstractFieldModel
 * @author ROBERTO RANZ
 */
public class CalculatedValue implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Contextos en los cuales se puede realizar el cálculo de expresiones del valor del campo.
	 */
	private static final String 
		CALCULATE_EXPRESSION_CONTEXT_INSERTING = "inserting", 
		CALCULATE_EXPRESSION_CONTEXT_EDITING = "editing", 
		CALCULATE_EXPRESSION_CONTEXT_ALL = "all";
	
	/**
	 * Definición de una expresión de cálculo para obtener el valor del campo antes de grabarlo
	 */
	private String expression;
	
	/**
	 * Eliminar caracteres no deseados al establecer el valor calculado.
	 */
	private String removeCharacters;
	
	/**
	 * Cálculo dinámico para aquellos campos con expresión de cálculo.
	 */
	private boolean dynamic = true;
	
	/**
	 * Describe brevemente la expresión de cálculo definida. Esto ayudará a su comprensión tanto al usuario como al que está viendo el descriptor.
	 */
	private String description;
	
	/**
	 * Contexto de ejecución del cálculo. Valores posibles: inserting, editing, all
	 */
	private String context = CALCULATE_EXPRESSION_CONTEXT_ALL;
	
	/**
	 * Lista de campos, separados por el carácter |, que deben de modificarse para activar el cálculo.
	 */
	private String requiredChangeFieldNames;
	
	public CalculatedValue(String expression) {
		this.expression = expression;
	}
	
	public CalculatedValue() {};
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Contexto de inserción para realizar el cálculo del valor
	 * @return
	 */
	public boolean isCalculateContextInserting() {
		return context.equals(CALCULATE_EXPRESSION_CONTEXT_INSERTING) || context.equals(CALCULATE_EXPRESSION_CONTEXT_ALL);
	}
	
	/**
	 * Contexto de edición para realizar el cálculo del valor
	 * @return
	 */
	public boolean isCalculateContextEditing() {
		return context.equals(CALCULATE_EXPRESSION_CONTEXT_EDITING) || context.equals(CALCULATE_EXPRESSION_CONTEXT_ALL);
	}
	
	/**
	 * Contexto cualquiera para realizar el cálculo del valor
	 * @return
	 */
	public boolean isCalculateContextAll() {
		return context.equals(CALCULATE_EXPRESSION_CONTEXT_ALL);
	}

	/**
	 * Comprobar si se debe calcular la propiedad por el contexto de edición (inserción o edición)
	 * @param inserting
	 * @return
	 */
	public boolean allowCalculate(boolean inserting) {
		if (isCalculateContextAll())
			return true;
		else if (inserting) 
			return isCalculateContextAll() || isCalculateContextInserting();
		else 
			return isCalculateContextAll() || isCalculateContextEditing();
	}

	/**
	 * Comprueba si tiene expresión de cálculo.
	 * @return
	 */
	public boolean isCalculated(){
		return !StringUtils.isEmpty(expression);
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public String getRequiredChangeFieldNames() {
		return requiredChangeFieldNames;
	}

	public void setRequiredChangeFieldNames(String requiredChangeFieldNames) {
		this.requiredChangeFieldNames = requiredChangeFieldNames;
	}

	public String getRemoveCharacters() {
		return removeCharacters;
	}

	public void setRemoveCharacters(String removeCharacters) {
		this.removeCharacters = removeCharacters;
	}

	/**
	 * Eliminar caracteres no deseados.
	 * @param value
	 * @return
	 */
	public String removeCharacters(String value) {
		
		if (removeCharacters != null) {
			for(char c : removeCharacters.toCharArray()) {
				try {
					value = SystemUtils.removeAll(value, c);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	@Override
	public String toString() {
		return "Valor calculado mediante expresión";  
	}
}
