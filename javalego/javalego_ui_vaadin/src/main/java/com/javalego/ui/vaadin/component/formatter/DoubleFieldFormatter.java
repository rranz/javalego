package com.javalego.ui.vaadin.component.formatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.javalego.ui.field.impl.AbstractNumberFieldModel;
import com.javalego.util.StringUtils;
import com.vaadin.data.Property;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;

/**
 * Formatter de valores tipo Double
 * @author ROBERTO RANZ
 */
public class DoubleFieldFormatter extends BaseFieldFormatter {

	private static final long serialVersionUID = -6236796266524146415L;

	private NumberFormat df;

	private AbstractField<?> component;

	public DoubleFieldFormatter(Property<?> propertyDataSource, AbstractField<?> component) {
		super(propertyDataSource);
		this.component = component;
	}

	@Override
	public Object parse(String formattedValue) throws Exception {
		
		try {
			if (isDefaultNull() && "".equals(formattedValue))
				return null;			
			
			Double value = Double.parseDouble(formattedValue.replaceAll("\\.", "").replaceAll("\\,", "\\.").trim().replaceAll(" ", ""));
			
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
			df = DecimalFormat.getInstance();
			df.setMinimumFractionDigits(AbstractNumberFieldModel.MAX_FRACTION_DIGITS);
			df.setMaximumFractionDigits(AbstractNumberFieldModel.MAX_FRACTION_DIGITS);
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
	
	/**
	 * Establecer el número mínimo de dígitos decimales.
	 * @param digits
	 */
	public void setMinimumFractionDigits(int digits) {
		getFormat().setMinimumFractionDigits(digits);
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
