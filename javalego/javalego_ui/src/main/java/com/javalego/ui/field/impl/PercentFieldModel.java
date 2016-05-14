package com.javalego.ui.field.impl;


/**
 * Valor de tipo porcentaje.
 * 
 * @author ROBERTO RANZ
 */
public class PercentFieldModel extends AbstractNumberFieldModel {

	private static final long serialVersionUID = 9216554623346164003L;

	/**
	 * Rango de 0 a 100 (mínimo y máximo).
	 */
	private boolean range100 = true;

	public boolean isRange100() {
		return range100;
	}

	public void setRange100(boolean range100) {
		this.range100 = range100;
	}

}
