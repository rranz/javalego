package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Propiedad de tipo fecha.
 * 
 * @author ROBERTO RANZ
 */
public class DateFieldModel extends AbstractFieldModel {

	private static final long serialVersionUID = 3197889808892285226L;

	/**
	 * Valor mínimo. Formato yyyyMMdd
	 */
	private String minValue;

	/**
	 * Valor máximo. Formato yyyyMMdd
	 */
	private String maxValue;

	/**
	 * Referencia al campo que representa la fecha DESDE dentro de un período.
	 * El sistema validará la fecha de este propiedad como HASTA y comprobará
	 * que no sea inferior a la fecha DESDE.
	 */
	private String fieldDateInitValidate;

	/**
	 * Incluir un botón para establecer la fecha actual.
	 */
	private boolean todayButton;

	/**
	 * Incluye la hora en la fecha. El campo en base de datos tiene que ser de
	 * tipo TIMESTAMP para recoger el valor en este formato.
	 */
	private boolean timestamp;

	public DateFieldModel() {
		init();
	}

	/**
	 * Constructor
	 * @param name 
	 */
	public DateFieldModel(Key name) {
		super(name);
		init();
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public DateFieldModel(String name, Key title) {
		super(name, title);
		init();
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 */
	public DateFieldModel(String name, Key title, Key description) {
		super(name, title);
		init();
	}

	private void init() {
		setAlignment(ALING_CENTER);
	}
	
	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getFieldDateInitValidate() {
		return fieldDateInitValidate;
	}

	public void setFieldDateInitValidate(String fieldDateInitValidate) {
		this.fieldDateInitValidate = fieldDateInitValidate;
	}

	public boolean isTodayButton() {
		return todayButton;
	}

	public void setTodayButton(boolean todayButton) {
		this.todayButton = todayButton;
	}

	public boolean isTimestamp() {
		return timestamp;
	}

	public void setTimestamp(boolean timestamp) {
		this.timestamp = timestamp;
	}
}
