package com.javalego.ui.vaadin.component.numbers;

import com.javalego.ui.vaadin.component.formatter.PercentFieldFormatter;
import com.vaadin.data.Property;

/**
 * Edición de un valor porcentaje
 * @author ROBERTO RANZ
 */
public class PercentField extends BaseNumberField {

	private static final long serialVersionUID = 1L;

	private PercentFieldFormatter formatter;

	public PercentField(Property<?> property) {
		initialize(property);
	}

	public PercentField() {
		initialize(null);
	}

	private void initialize(Property<?> property) {

		setImmediate(true);
		
		formatter = new PercentFieldFormatter(property, this);
		
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
	public void setMinimumFractionDigits(int minimumFractionDigits) {
		formatter.setMaximumFractionDigits(minimumFractionDigits);
	}
	
	@Override
	public void setGroupingUsed(boolean groupingUsed) {
		if (formatter != null)
			formatter.getFormat().setGroupingUsed(groupingUsed);
	}

	@Override
	public PercentFieldFormatter getFormatter() {
		return formatter;
	}
	
}
