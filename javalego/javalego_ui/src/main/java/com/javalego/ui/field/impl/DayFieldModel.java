package com.javalego.ui.field.impl;

/**
 * Propiedad cuyo valor representa un día y sus propiedades se configurarán para
 * este tipo de valor en lugar de utilizar un campo integerField.
 * 
 * @author ROBERTO RANZ
 */
public class DayFieldModel extends IntegerFieldModel {

	private static final long serialVersionUID = -5231028205718384867L;

	/**
	 * Mes asociado para realizar la validación del día con respecto al mes y
	 * año.
	 */
	private String monthFieldName;

	/**
	 * Año asociado para realizar la validación del día con respecto al mes y
	 * año.
	 */
	private String yearFieldName;

	public DayFieldModel() {
		size = 2;
		minValue = 0.0;
		maxValue = 31.0;
		setZeroAsBlank(true);
		setGroupingUsed(false);
	}

	public String getMonthFieldName() {
		return monthFieldName;
	}

	public void setMonthFieldName(String monthFieldName) {
		this.monthFieldName = monthFieldName;
	}

	public String getYearFieldName() {
		return yearFieldName;
	}

	public void setYearFieldName(String yearFieldName) {
		this.yearFieldName = yearFieldName;
	}

}
