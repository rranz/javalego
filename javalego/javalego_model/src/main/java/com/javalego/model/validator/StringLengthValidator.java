package com.javalego.model.validator;

import java.lang.annotation.Annotation;

import javax.validation.constraints.Size;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;

/**
 * This <code>StringLengthValidator</code> is used to validate the length of
 * strings.
 * 
 * @author Vaadin Ltd.
 * @since 3.0
 */
public class StringLengthValidator extends AbstractStringValidator {

	private Integer minLength = null;

	private Integer maxLength = null;

	/**
	 * Constructs a validator with the given error message.
	 * 
	 */
	public StringLengthValidator(Integer minLength, Integer maxLength) {
		setMinLength(minLength);
		setMaxLength(maxLength);
	}

	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation
	 */
	public StringLengthValidator(String errorMessage, Annotation annotation) {
		super(errorMessage, annotation);
	}

	/**
	 * Creates a new StringLengthValidator with a given error message and
	 * minimum and maximum length limits.
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param minLength
	 *            the minimum permissible length of the string or null for no
	 *            limit. A negative value for no limit is also supported for
	 *            backwards compatibility.
	 * @param maxLength
	 *            the maximum permissible length of the string or null for no
	 *            limit. A negative value for no limit is also supported for
	 *            backwards compatibility.
	 * @param allowNull
	 *            Are null strings permissible? This can be handled better by
	 *            setting a field as required or not.
	 */
	public StringLengthValidator(String errorMessage, Integer minLength, Integer maxLength) {
		super(errorMessage);
		setMinLength(minLength);
		setMaxLength(maxLength);
	}

	/**
	 * Checks if the given value is valid.
	 * 
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(String value) {
		if (value == null) {
			return true;
		}
		final int len = value.length();
		if ((minLength != null && minLength > -1 && len < minLength) || (maxLength != null && maxLength > -1 && len > maxLength)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the maximum permissible length of the string.
	 * 
	 * @return the maximum length of the string or null if there is no limit
	 */
	public Integer getMaxLength() {
		return maxLength;
	}

	/**
	 * Gets the minimum permissible length of the string.
	 * 
	 * @return the minimum length of the string or null if there is no limit
	 */
	public Integer getMinLength() {
		return minLength;
	}

	/**
	 * Sets the maximum permissible length of the string.
	 * 
	 * @param maxLength
	 *            the maximum length to accept or null for no limit
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * Sets the minimum permissible length.
	 * 
	 * @param minLength
	 *            the minimum length to accept or null for no limit
	 */
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	@Override
	public void initialized(Annotation annotation) {
		if (annotation instanceof Size) {
			minLength = ((Size) annotation).min();
			maxLength = ((Size) annotation).max();
		}
	}

	@Override
	public Key getKeyErrorMessage() {
		return minLength != null && maxLength == null ? LocaleValidators.MIN_LENGTH : maxLength != null && minLength == null ? LocaleValidators.MAX_LENGTH : LocaleValidators.SIZE;
	}

	@Override
	public String getAdapterErrorMessage(String text) {

		if (minLength != null && maxLength == null) {
			return text.replaceAll("\\{0\\}", minLength.toString());
		}
		else if (maxLength != null && minLength == null) {
			return text.replaceAll("\\{0\\}", maxLength.toString());
		}
		else {
			return text;
		}
	}
}
