package com.javalego.ui.vaadin.component.formatter;

import java.text.NumberFormat;

import com.vaadin.data.Property;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.ui.Component;

/**
 * Clase básica de formate de valores de propiedades.
 * @author Roberto
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public abstract class BaseFieldFormatter extends PropertyFormatter implements IBaseField {

	private static final long serialVersionUID = 9059867401974278584L;

	protected Component component;
	
	/**
	 * No generar error si el valor es un nulo.
	 */
	protected boolean noErrorIsNull;
	
	/**
	 * Establecer el valor a null cuando el valor es 0 o ''. 
	 */
	protected boolean defaultNull;
	
	public BaseFieldFormatter(Property propertyDataSource) {
		super(propertyDataSource);
	}

	public abstract void setGroupingUsed(boolean groupingUsed);

	public abstract NumberFormat getFormat();

	public Component getComponent() {
		return component;
	}

	@Override
	public void setComponent(Component component) {
		this.component = component;
	}

	public boolean isNoErrorIsNull() {
		return noErrorIsNull;
	}

	public void setNoErrorIsNull(boolean noErrorIsNull) {
		this.noErrorIsNull = noErrorIsNull;
	}

	public boolean isDefaultNull() {
		return defaultNull;
	}

	public void setDefaultNull(boolean defaultNull) {
		this.defaultNull = defaultNull;
	}
}
