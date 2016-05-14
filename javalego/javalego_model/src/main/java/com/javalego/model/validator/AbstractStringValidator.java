package com.javalego.model.validator;

import java.lang.annotation.Annotation;

/**
 * Validator base class for validating strings.
 * <p>
 * To include the value that failed validation in the exception message you can
 * use "{0}" in the error message. This will be replaced with the failed value
 * (converted to string using {@link #toString()}) or "null" if the value is
 * null.
 * </p>
 * 
 * @author Vaadin Ltd.
 * @since 5.4
 */
public abstract class AbstractStringValidator extends AbstractValidator<String> {

	/**
	 * Constructs a validator for strings.
	 * 
	 * <p>
	 * Null and empty string values are always accepted. To reject empty values,
	 * set the field being validated as required.
	 * </p>
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * */
	public AbstractStringValidator(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructs a validator for strings.
	 * 
	 * <p>
	 * Null and empty string values are always accepted. To reject empty values,
	 * set the field being validated as required.
	 * </p>
	 * 
	 * */
	public AbstractStringValidator() {
	}

	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation
	 */
	public AbstractStringValidator(String errorMessage, Annotation annotation) {
		super(errorMessage, annotation);
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}
}
