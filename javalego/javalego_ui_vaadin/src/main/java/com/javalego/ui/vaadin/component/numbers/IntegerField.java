package com.javalego.ui.vaadin.component.numbers;

import com.javalego.ui.vaadin.component.formatter.IntegerFieldFormatter;
import com.javalego.util.StringUtils;
import com.vaadin.data.Property;

/**
 * Edición de un valor entero.
 * @author ROBERTO RANZ
 */
public class IntegerField extends BaseNumberField {

	private static final long serialVersionUID = 1L;

	private IntegerFieldFormatter formatter;

	public IntegerField(Property<?> property) {
		initialize(property);
	}

	private void initialize(Property<?> property) {

		setImmediate(true);
		
		formatter = new IntegerFieldFormatter(property, this);
		
		setPropertyDataSource(formatter);

//		addListener(new BlurListener() {
//			@Override
//			public void blur(BlurEvent event) {
//				event.getComponent().requestRepaint();
//			}
//		});

	}

//	@Override
//	public void setProperty(Property property) {
//		formatter.setPropertyDataSource(property);
//	}

	@Override
	public void setMaximunFractionDigits(int maximunFractionDigits) {
		
	}

	@Override
	public void setGroupingUsed(boolean groupingUsed) {
		if (formatter != null)
			formatter.getFormat().setGroupingUsed(groupingUsed);
	}

	@Override
	public void setConvertedValue(Object newValue) {
		super.setConvertedValue(newValue instanceof Double ? StringUtils.toString(((Double)newValue).intValue()) : newValue);
	}

	@Override
	public IntegerFieldFormatter getFormatter() {
		return formatter;
	}
	@Override
	public void setMinimumFractionDigits(int minimumFractionDigits) {
		
	}	
}
