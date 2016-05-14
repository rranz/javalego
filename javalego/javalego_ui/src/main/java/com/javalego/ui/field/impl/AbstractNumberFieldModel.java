package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Propiedad numérica que puede representa varias tipologias de información: importe monetario, numérico o porcentaje.
 * @author ROBERTO RANZ
 */
public abstract class AbstractNumberFieldModel extends AbstractFieldModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 772544170575681591L;

	public static final int MAX_FRACTION_DIGITS = 2;

	/**
	 * Valor mínimo permitido
	 */
	protected Double minValue;

	/**
	 * Valor máximo permitido.
	 */
	protected Double maxValue;

	/**
	 * Usar puntos para agrupar por miles.
	 */
	private boolean groupingUsed = true;

	/**
	 * Número mínimo de parte fraccionada.
	 */
	private int minimumFractionDigits;

	/**
	 * Número máximo de dígitos decimales.
	 */
	private int maximumFractionDigits = MAX_FRACTION_DIGITS;
	
	/**
	 * Mostrar un espacio en blanco cuando el valor del campo sea igual a cero. Sólo en modo rejilla, no en la edición de un registro.
	 */
	private boolean zeroAsBlank;
	
	public AbstractNumberFieldModel(){
	}
	
	public AbstractNumberFieldModel(String name, Key title) {
		super(name, title);
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public boolean isGroupingUsed() {
		return groupingUsed;
	}

	public void setGroupingUsed(boolean groupingUsed) {
		this.groupingUsed = groupingUsed;
	}

	public boolean isZeroAsBlank() {
		return zeroAsBlank;
	}

	public void setZeroAsBlank(boolean zeroAsBlank) {
		this.zeroAsBlank = zeroAsBlank;
	}

	public int getMinimumFractionDigits() {
		return minimumFractionDigits;
	}

	public void setMinimumFractionDigits(int minimumFractionDigits) {
		this.minimumFractionDigits = minimumFractionDigits;
	}

	public int getMaximumFractionDigits() {
		return maximumFractionDigits;
	}

	public void setMaximumFractionDigits(int maximumFractionDigits) {
		this.maximumFractionDigits = maximumFractionDigits;
	}

	public boolean isPercent() {
		// TODO Auto-generated method stub
		return false;
	}

}
