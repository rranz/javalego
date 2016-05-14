package com.javalego.ui.vaadin.component.numbers;

import com.javalego.ui.vaadin.component.TextFieldExt;
import com.javalego.ui.vaadin.component.formatter.BaseFieldFormatter;
import com.javalego.util.StringUtils;

/**
 * Editor básico de números.
 * @author Roberto
 *
 */
public abstract class BaseNumberField extends TextFieldExt {

	private static final long serialVersionUID = 1L;

	public abstract void setMaximunFractionDigits(int maximunFractionDigits);
	
	public abstract void setMinimumFractionDigits(int minimumFractionDigits);
	
	/**
	 * Establece comas y puntos para formatear los miles.
	 * @param groupingUsed
	 */
	public abstract void setGroupingUsed(boolean groupingUsed);

	@Override
  public Class<String> getType() {
      return String.class;
  }
	
	@Override
	public void setConvertedValue(Object newValue) {
		super.setValue(StringUtils.toString(newValue));
	}

	@SuppressWarnings("deprecation")
	public Object getParseValue() {
		try {
			return getFormatter().parse((String)super.getValue());
		} catch (Exception e) {
			return super.getValue();
		}
	}

	public abstract BaseFieldFormatter getFormatter();
	
}
