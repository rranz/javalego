package com.javalego.model.validator;

import java.lang.annotation.Annotation;

import javax.validation.constraints.Min;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;


/**
 * This <code>Min</code> is used to validate el valor máximo de un valor
 * numérico.
 * 
 * @author Vaadin Ltd.
 * @since 3.0
 */
public class MinValidator extends AbstractValidator<Long> {

	private Long value = null;

	/**
	 * Creates a new MinValidator with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public MinValidator(String errorMessage, Long value) {
		super(errorMessage);
		setMin(value);
	}
	
	/**
	 * Creates a new MinValidator with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public MinValidator(Long value) {
		setMin(value);
	}
	
	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation           
	 */
	public MinValidator(String errorMessage, Annotation annotation) {
		super(errorMessage, annotation);
	}

	/**
	 * Checks if the given value is valid.
	 * 
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(Long value) {
		if (value == null) {
			return false;
		}
		if ((this.value != null && value < this.value)) {
			return false;
		}
		return true;
	}

	/**
	 * Sets the max permissible value.
	 * 
	 * @param value
	 *            the max value
	 */
	public void setMin(Long value) {
		this.value = value;
	}

	public Long getMin() {
		return value;
	}

	@Override
	public Class<Long> getType() {
		return Long.class;
	}

	@Override
	public void initialized(Annotation annotation) {
		if (annotation instanceof Min) {
			this.value = ((Min)annotation).value();
		}
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.MIN;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text.replaceAll("\\{1\\}", value != null ? value.toString() : "0");
	}	
}
