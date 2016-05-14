package com.javalego.ui.binding.field;

import com.javalego.ui.binding.Property;

/**
 * TODO document
 * 
 * @author Vaadin Ltd.
 * 
 * @param T
 *            the type of values in the field, which might not be the same type
 *            as that of the data source if converters are used
 * 
 * @author IT Mill Ltd.
 */
public interface Field<T> extends Property<T>, Property.ValueChangeNotifier, Property.ValueChangeListener, Property.Editor {

	/**
	 * Is this field required.
	 * 
	 * Required fields must filled by the user.
	 * 
	 * @return <code>true</code> if the field is required,otherwise
	 *         <code>false</code>.
	 * @since 3.1
	 */
	public boolean isRequired();

	/**
	 * Sets the field required. Required fields must filled by the user.
	 * 
	 * @param required
	 *            Is the field required.
	 * @since 3.1
	 */
	public void setRequired(boolean required);

	/**
	 * Sets the error message to be displayed if a required field is empty.
	 * 
	 * @param requiredMessage
	 *            Error message.
	 * @since 5.2.6
	 */
	public void setRequiredError(String requiredMessage);
    
	/**
     * Commit value
     */
	public void commit();
	
	/**
	 * Discard value
	 */
	public void discard();  
	
	/**
	 * Gets the error message that is to be displayed if a required field is
	 * empty.
	 * 
	 * @return Error message.
	 * @since 5.2.6
	 */
	public String getRequiredError();

	/**
	 * An <code>Event</code> object specifying the Field whose value has been
	 * changed.
	 * 
	 * @author Vaadin Ltd.
	 * @since 3.0
	 */
	@SuppressWarnings("serial")
	public static class ValueChangeEvent<T> implements Property.ValueChangeEvent {

		private Field<T> field;
		
		private T currentValue;

		/**
		 * Constructs a new event object with the specified source field object.
		 * 
		 * @param source
		 *            the field that caused the event.
		 */
		public ValueChangeEvent(Field<T> source, T currentValue) {
			field = source;
			this.currentValue = currentValue;
		}

		/**
		 * Gets the Property which triggered the event.
		 * 
		 * @return the Source Property of the event.
		 */
		@Override
		public Property<T> getProperty() {
			return field;
		}

		@Override
		public T getCurrentValue() {
			return currentValue;
		}
	}
}
