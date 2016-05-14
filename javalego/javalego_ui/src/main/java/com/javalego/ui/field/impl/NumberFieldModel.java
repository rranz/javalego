package com.javalego.ui.field.impl;

/**
 * Propiedad numérica que puede representa varias tipologias de información:
 * importe monetario, numérico o porcentaje.
 * 
 * @author ROBERTO RANZ
 */
public class NumberFieldModel extends AbstractNumberFieldModel {

	private static final long serialVersionUID = 3906588003174244111L;

	/**
	 * Representa un porcentaje %
	 */
	private boolean percent;

	/**
	 * Mostrar el símbolo monetario asociado a una divisa o a la moneda local.
	 */
	private boolean showSymbol = true;

	/**
	 * Define si un importe económico representa una moneda o divisa.
	 */
	private boolean currency;

	public NumberFieldModel() {
	}

	@Override
	public boolean isPercent() {
		return percent;
	}

	public void setPercent(boolean percent) {
		this.percent = percent;
	}

	public boolean isShowSymbol() {
		return showSymbol;
	}

	public void setShowSymbol(boolean showSymbol) {
		this.showSymbol = showSymbol;
	}

	public boolean isCurrency() {
		return currency;
	}

	public void setCurrency(boolean currency) {
		this.currency = currency;
	}

}
