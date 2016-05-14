package com.javalego.model.validator;

import java.lang.annotation.Annotation;

import javax.validation.constraints.DecimalMin;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.util.StringUtils;

/**
 * This <code>DecimalMin</code> is used to validate min number value.
 * 
 * @author ROBERTO RANZ
 */
public class DecimalMinValidator extends AbstractValidator<Double> {

	private Double value = null;

	/**
	 * Creates a new DecimalMin with a given error message
	 * 
	 * @param value
	 */
	public DecimalMinValidator(Double value) {
		this.value = value;
	}
	
	/**
	 * Creates a new DecimalMax with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public DecimalMinValidator(String errorMessage, Double value) {
		super(errorMessage);
		this.value = value;
	}
	
	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation           
	 */
	public DecimalMinValidator(String errorMessage, Annotation annotation) {
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
	protected boolean isValidValue(Double value) {
		if (value == null) {
			return true;
		}
		return value >= this.value;
	}

	@Override
	public Class<Double> getType() {
		return Double.class;
	}

	@Override
	public void initialized(Annotation annotation) {
		if (annotation instanceof DecimalMin) {
			String value = ((DecimalMin)annotation).value();
			if (value != null) {
				this.value = StringUtils.toDouble(value);
			}
		}
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.DECIMALMIN;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text.replaceAll("\\{1\\}", value != null ? value.toString() : "0");
	}
	
}
