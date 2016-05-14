package com.javalego.ui.vaadin.component.formatter;

import java.text.NumberFormat;

import com.javalego.util.StringUtils;
import com.vaadin.data.Property;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;

/**
 * Formatter Currency
 * @author ROBERTO RANZ
 */
public class CurrencyFieldFormatter extends BaseFieldFormatter {

	private static final long serialVersionUID = -8208563638157172688L;

	private NumberFormat df;

	private AbstractField<?> component;
	
	/**
	 * Constructor
	 * @param propertyDataSource
	 * @param component
	 */
	public CurrencyFieldFormatter(Property<?> propertyDataSource, AbstractField<?> component) {
		
		super(propertyDataSource);
		this.component = component;
	}

	@Override
	public Object parse(String formattedValue) throws Exception {
		try {
			if (isDefaultNull() && "".equals(formattedValue))
				return null;			
			
			Double value = Double.parseDouble(formattedValue.replaceAll("\\.", "").replaceAll("\\,", "\\.").replaceAll(getFormat().getCurrency().getSymbol(), "").trim().replaceAll(" ", ""));
			
			component.setComponentError(null);
			
			return value;
		}
		catch(Exception e) {
			if (!"".equals(formattedValue) && noErrorIsNull) {
				component.setComponentError(new UserError("Value" + "  '" + formattedValue + "'  " + "incorrect" + "."));
			}
			return 0;
		}
	}

	@Override
	public String format(Object value) {
		if (value == null) 
			return "";
		else
			return getFormat().format(StringUtils.toDouble(value));
	}

	@Override
	public NumberFormat getFormat() {
		if (df == null) {
			df =  NumberFormat.getCurrencyInstance(); //UserSessionContext.getLocale());
			df.setMaximumFractionDigits(2);
		}
		return df;
	}

	/**
	 * Establecer el número máximo de dígitos decimales.
	 * @param digits
	 */
	public void setMaximumFractionDigits(int digits) {
		getFormat().setMaximumFractionDigits(digits);
	}
	
	@Override
	public Class<?> getType() {
		return String.class;
	}

	@Override
	public void setGroupingUsed(boolean groupingUsed) {
		getFormat().setGroupingUsed(groupingUsed);
	}
}
