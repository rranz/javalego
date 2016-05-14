package com.javalego.ui.vaadin.component.numbers;

import com.javalego.ui.vaadin.component.formatter.CurrencyFieldFormatter;
import com.vaadin.data.Property;

/**
 * Edición de un valor monetario.
 * @author ROBERTO RANZ
 */
public class CurrencyField extends BaseNumberField {

	private static final long serialVersionUID = 1L;

	private CurrencyFieldFormatter formatter;

	public CurrencyField(Property<?> property) {
		initialize(property);
	}

	public CurrencyField() {
		initialize(null);
	}

//	@Override
//	public void setValue(Object newValue) {
//		
//		String value = Functions.toString(newValue);
//		
//		if (value != null && !"".equals(Functions.toString(newValue))) {
//			try {
//				Number d = formatter.getFormat().parse(Functions.toString(value));
//				super.setValue(formatter.getFormat().format(d.doubleValue()));
//			} catch (ParseException e) {
//				super.setValue(value);
//			}
//		}
//		else
//			super.setValue(value);
//	}

	private void initialize(Property<?> property) {

		setImmediate(true);
		
		formatter = new CurrencyFieldFormatter(property, this);
		
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
		formatter.setMaximumFractionDigits(maximunFractionDigits);
	}

	@Override
	public void setGroupingUsed(boolean groupingUsed) {
		if (formatter != null)
			formatter.getFormat().setGroupingUsed(groupingUsed);
	}

	@Override
	public CurrencyFieldFormatter getFormatter() {
		return formatter;
	}

	@Override
	public void setMinimumFractionDigits(int minimumFractionDigits) {
		formatter.setMaximumFractionDigits(minimumFractionDigits);
	}
	
}
