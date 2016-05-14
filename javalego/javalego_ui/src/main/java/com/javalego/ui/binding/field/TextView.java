package com.javalego.ui.binding.field;

import java.util.ArrayList;
import java.util.List;

import com.javalego.ui.binding.Property;

/**
 * Edición de un valor de una propiedad bindada a un valor o a una propiedad de
 * un bean.
 * 
 * @author roberto
 * 
 * @param <T>
 */
public class TextView<T> implements Field<T> {

	private static final long serialVersionUID = 1522870037547156684L;

	/**
	 * Connected data-source.
	 */
	private Property<T> dataSource = null;

	private T currentValue;

	private boolean required;

	private String requiredError;

	private boolean readOnly;

	private String caption;

	private List<ValueChangeListener> valueChangeListeners = new ArrayList<ValueChangeListener>();

	/**
	 * Constructor
	 * 
	 * @param caption
	 * @param dataSource
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TextView(String caption, Property dataSource) {
		this.dataSource = dataSource;
		setCaption(caption);
	}

	@Override
	public T getValue() {
		return currentValue;
	}

	@Override
	public void setValue(T newValue) throws com.javalego.ui.binding.Property.ReadOnlyException {
		this.currentValue = newValue;
		
		fireValueChange();
	}

	/**
	 * Gets the value from the data source. This is only here because of clarity
	 * in the code that handles both the data model value and the field value.
	 * 
	 * @return The value of the property data source
	 */
	@SuppressWarnings({ "unused" })
	private T getDataSourceValue() {
		return (T) dataSource.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		return (Class<T>) String.class;
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Override
	public void addValueChangeListener(com.javalego.ui.binding.Property.ValueChangeListener listener) {
		valueChangeListeners.add(listener);
	}

	@Override
	public void removeValueChangeListener(com.javalego.ui.binding.Property.ValueChangeListener listener) {
		valueChangeListeners.remove(listener);
	}

	@Override
	public void valueChange(com.javalego.ui.binding.Property.ValueChangeEvent event) {

		for (ValueChangeListener listener : valueChangeListeners) {
			listener.valueChange(event);
		}
	}

	/**
	 * Emits the value change event. The value contained in the field is
	 * validated before the event is created.
	 */
	protected void fireValueChange() {

		for (ValueChangeListener listener : valueChangeListeners) {
			listener.valueChange(new ValueChangeEvent<T>(this, currentValue));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPropertyDataSource(Property<?> newDataSource) {

		this.dataSource = (Property<T>) newDataSource;

		// Establecer el valor en edición con el valor de la propiedad.
		this.currentValue = getValue();

	}

	@Override
	public Property<?> getPropertyDataSource() {
		return dataSource;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public void setRequired(boolean required) {
		this.required = required;
	}

	@Override
	public void setRequiredError(String requiredMessage) {
		this.requiredError = requiredMessage;
	}

	@Override
	public String getRequiredError() {
		return requiredError;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public void commit() {
		dataSource.setValue(currentValue);
	}

	@Override
	public void discard() {
		currentValue = (T) dataSource.getValue();
	}

}
