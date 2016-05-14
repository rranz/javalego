package com.javalego.model.validator;

import java.lang.annotation.Annotation;

/**
 * Interface that implements a method for validating if an {@link Object} is
 * valid or not.
 * <p>
 * Implementors of this class can be added to any
 * {@link com.vaadin.data.Validatable Validatable} implementor to verify its
 * value.
 * </p>
 * <p>
 * {@link #validate(Object)} can be used to check if a value is valid. An
 * {@link InvalidValueException} with an appropriate validation error message is
 * thrown if the value is not valid.
 * </p>
 * <p>
 * Validators must not have any side effects.
 * </p>
 * <p>
 * Since Vaadin 7, the method isValid(Object) does not exist in the interface -
 * {@link #validate(Object)} should be used instead, and the exception caught
 * where applicable. Concrete classes implementing {@link AbstractValidator} can
 * still internally implement and use isValid(Object) for convenience or to ease
 * migration from earlier Vaadin versions.
 * </p>
 * 
 * @author Vaadin Ltd.
 * @since 3.0
 */
public abstract class AbstractValidator<T> implements Validator {

	/**
	 * Error message that is included in an {@link InvalidValueException} if
	 * such is thrown.
	 */
	protected String errorMessage;

	/**
	 * Initsialize with annotations
	 * 
	 * @param annotation
	 */
	public abstract void initialized(Annotation annotation);

	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation
	 */
	public AbstractValidator(String errorMessage, Annotation annotation) {
		this.errorMessage = errorMessage;
		if (annotation != null) {
			initialized(annotation);
		}
	}

	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 */
	public AbstractValidator(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Constructs a validator with the given error message.
	 */
	public AbstractValidator() {
	}

	/**
	 * Since Vaadin 7, subclasses of AbstractValidator should override
	 * {@link #isValidValue(Object)} or {@link #validate(Object)} instead of
	 * {@link #isValid(Object)}. {@link #validate(Object)} should normally be
	 * used to check values.
	 * 
	 * @param value
	 * @return true if the value is valid
	 */
	public boolean isValid(Object value) {
		try {
			validate(value, null);
			return true;
		}
		catch (InvalidValueException e) {
			return false;
		}
	}

	/**
	 * Internally check the validity of a value. This method can be used to
	 * perform validation in subclasses if customization of the error message is
	 * not needed. Otherwise, subclasses should override
	 * {@link #validate(Object)} and the return value of this method is ignored.
	 * 
	 * This method should not be called from outside the validator class itself.
	 * 
	 * @param value
	 * @return
	 */
	protected abstract boolean isValidValue(T value);

	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object value, String errorMessage) throws InvalidValueException {

		// isValidType ensures that value can safely be cast to TYPE
		if (!isValidType(value) || !isValidValue((T) value)) {

			String message = errorMessage != null ? errorMessage : getErrorMessage().replace("{0}", String.valueOf(value));

			throw new InvalidValueException(message);
		}
	}

	/**
	 * Checks the type of the value to validate to ensure it conforms with
	 * getType. Enables sub classes to handle the specific type instead of
	 * Object.
	 * 
	 * @param value
	 *            The value to check
	 * @return true if the value can safely be cast to the type specified by
	 *         {@link #getType()}
	 */
	protected boolean isValidType(Object value) {
		if (value == null) {
			return true;
		}

		return getType().isAssignableFrom(value.getClass());
	}

	/**
	 * Returns the message to be included in the exception in case the value
	 * does not validate.
	 * 
	 * @return the error message provided in the constructor or using
	 *         {@link #setErrorMessage(String)}.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the message to be included in the exception in case the value does
	 * not validate. The exception message is typically shown to the end user.
	 * 
	 * @param errorMessage
	 *            the error message. "{0}" is automatically replaced by the
	 *            value that did not validate.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public abstract Class<T> getType();

}
