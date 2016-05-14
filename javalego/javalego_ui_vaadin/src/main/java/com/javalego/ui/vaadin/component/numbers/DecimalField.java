package com.javalego.ui.vaadin.component.numbers;

import com.javalego.ui.vaadin.component.formatter.DoubleFieldFormatter;
import com.vaadin.data.Property;

/**
 * Edición de un valor decimal o double.
 * @author ROBERTO RANZ
 */
public class DecimalField extends BaseNumberField {

	private static final long serialVersionUID = 1L;

	private DoubleFieldFormatter formatter;

	public DecimalField(Property<?> property) {
		initialize(property);
	}

	public DecimalField() {
		initialize(null);
	}

	private void initialize(Property<?> property) {

		setImmediate(true);
		
		formatter = new DoubleFieldFormatter(property, this);
		
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
	public DoubleFieldFormatter getFormatter() {
		return formatter;
	}

	@Override
	public void setMinimumFractionDigits(int minimumFractionDigits) {
		formatter.setMaximumFractionDigits(minimumFractionDigits);
	}

}
