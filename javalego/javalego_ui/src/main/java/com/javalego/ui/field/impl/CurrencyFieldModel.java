package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Valor de tipo moneda.
 * @author ROBERTO RANZ
 */
public class CurrencyFieldModel extends AbstractNumberFieldModel {

	private static final long serialVersionUID = -6359956307818904849L;

	/**
	 * Mostrar el símbolo monetario asociado a una divisa o a la moneda local.
	 */
	private boolean showSymbol = true;

	/**
	 * Campo que contiene la divisa asociada al campos monetario que servirá para configurar su renderizado con el símbolo monetario asociado a esta divisa.
	 */
	private String currencyFieldName;
	
	public CurrencyFieldModel() {};
	
	public CurrencyFieldModel(String name, Key title) {
		super(name, title);
	}
	
	public boolean isShowSymbol() {
		return showSymbol;
	}

	public void setShowSymbol(boolean showSymbol) {
		this.showSymbol = showSymbol;
	}

	public String getCurrencyFieldName() {
		return currencyFieldName;
	}

	public void setCurrencyFieldName(String currencyFieldName) {
		this.currencyFieldName = currencyFieldName;
	}

}
