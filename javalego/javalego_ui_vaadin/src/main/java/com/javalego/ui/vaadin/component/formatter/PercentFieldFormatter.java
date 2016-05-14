package com.javalego.ui.vaadin.component.formatter;

import java.text.NumberFormat;

import com.javalego.util.StringUtils;
import com.vaadin.data.Property;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;

/**
 * Formatter Percent
 * @author ROBERTO RANZ
 */
public class PercentFieldFormatter extends BaseFieldFormatter {

	private static final long serialVersionUID = -3533714896527611214L;

	private NumberFormat df;

	private AbstractField<?> component;
	
	/**
	 * Constructor
	 * @param propertyDataSource
	 * @param component
	 */
	public PercentFieldFormatter(Property<?> propertyDataSource, AbstractField<?> component) {
		super(propertyDataSource);
		this.component = component;
		getFormat().setMaximumFractionDigits(2);
	}

	@Override
	public Object parse(String formattedValue) throws Exception {
		try {
			if (isDefaultNull() && "".equals(formattedValue))
				return null;			
			
			Double value = Double.parseDouble(formattedValue.replaceAll("\\.", "").replaceAll("\\,", "\\.").replaceAll("%", "").trim().replaceAll(" ", ""));

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
		
		return getFormat().format(StringUtils.toDouble(value) / 100.0);
	}
	
	@Override
	public NumberFormat getFormat() {
		if (df == null) {
			df =  NumberFormat.getPercentInstance();
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
