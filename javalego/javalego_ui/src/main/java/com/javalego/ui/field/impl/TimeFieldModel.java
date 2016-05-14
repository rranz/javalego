package com.javalego.ui.field.impl;

/**
 * Propiedad que representa una hora.
 * 
 * @author ROBERTO RANZ
 */
public class TimeFieldModel extends TextFieldModel {

	private static final long serialVersionUID = 3655706822845371516L;

	/**
	 * Referencia al campo hora inicial que el sistema validará con respecto a
	 * este campo que representará la hora final, comprobando que la hora final
	 * no sea inferior a la hora inicial.
	 */
	private String fieldTimeInitValidate;

	/**
	 * Valor mínimo. Formato HH:mm
	 */
	private String minValue;

	/**
	 * Valor máximo. Formato HH:mm
	 */
	private String maxValue;

	/**
	 * Establecer el tamaño de edición a 5 dígitos hh:mm
	 */
	public TimeFieldModel() {
		setColumns(5);
	}

	public String getFieldTimeInitValidate() {
		return fieldTimeInitValidate;
	}

	public void setFieldTimeInitValidate(String fieldTimeInitValidate) {
		this.fieldTimeInitValidate = fieldTimeInitValidate;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

}
