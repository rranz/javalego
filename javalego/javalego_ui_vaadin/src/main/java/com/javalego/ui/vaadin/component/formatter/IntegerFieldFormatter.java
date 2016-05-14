package com.javalego.ui.vaadin.component.formatter;

import java.text.NumberFormat;

import com.javalego.ui.vaadin.component.numbers.IntegerField;
import com.javalego.util.StringUtils;
import com.vaadin.data.Property;
import com.vaadin.server.UserError;

/**
 * Formatter de valores tipo Integer
 * @author ROBERTO RANZ
 */
public class IntegerFieldFormatter extends BaseFieldFormatter {

	private static final long serialVersionUID = 3426166593804767042L;

	private NumberFormat df;

	private IntegerField component;

	public IntegerFieldFormatter(Property<?> propertyDataSource, IntegerField component) {
		super(propertyDataSource);
		this.component = component;
	}

	@Override
	public Object parse(String formattedValue) throws Exception {
		
		try {
			if (isDefaultNull() && "".equals(formattedValue))
				return null;			
			
			Integer value = Integer.parseInt(formattedValue.replaceAll("\\.", "").replaceAll("\\,", "").trim().replaceAll(" ", ""));

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
			return getFormat().format(StringUtils.toInt(value));
	}

	@Override
	public NumberFormat getFormat() {
		if (df == null) {
			df = NumberFormat.getIntegerInstance();
		}
		return df;
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
