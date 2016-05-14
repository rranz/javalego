package com.javalego.model.validator;

import java.lang.annotation.Annotation;

import javax.validation.constraints.NotNull;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;
import com.javalego.util.StringUtils;

/**
 * This validator is used for validating properties that do or do not allow null
 * values. By default, nulls are not allowed.
 * 
 * @author Vaadin Ltd.
 * @since 3.0
 */
public class NullValidator extends AbstractValidator<Boolean> {

	private boolean onlyNullAllowed;

	/**
	 * Creates a new NullValidator.
	 * 
	 * @param onlyNullAllowed
	 *            Are only nulls allowed?
	 */
	public NullValidator(boolean onlyNullAllowed) {
		this.onlyNullAllowed = onlyNullAllowed;
	}
	
	/**
	 * Creates a new NullValidator.
	 * 
	 * @param errorMessage
	 *            the error message to display on invalidation.
	 * @param onlyNullAllowed
	 *            Are only nulls allowed?
	 */
	public NullValidator(String errorMessage, boolean onlyNullAllowed) {
		super(errorMessage);
		this.onlyNullAllowed = onlyNullAllowed;
	}
	
	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation           
	 */
	public NullValidator(String errorMessage, Annotation annotation) {
		super(errorMessage, annotation);
	}

	/**
	 * Validates the data given in value.
	 * 
	 * @param value
	 *            the value to validate.
	 * @throws AbstractValidator.InvalidValueException
	 *             if the value was invalid.
	 */
	@Override
	public void validate(Object value, String errorMessage) throws AbstractValidator.InvalidValueException {
		if ((onlyNullAllowed && !StringUtils.isEmpty(value)) || (!onlyNullAllowed && StringUtils.isEmpty(value))) {
			throw new AbstractValidator.InvalidValueException(errorMessage != null ? errorMessage : getErrorMessage());
		}
	}
	
	@Override
	public void initialized(Annotation annotation) {
		onlyNullAllowed = annotation instanceof NotNull ? false : true;
	}

	@Override
	protected boolean isValidValue(Boolean value) {
		return (onlyNullAllowed && StringUtils.isEmpty(value)) || (!onlyNullAllowed && !StringUtils.isEmpty(value));
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	public boolean isOnlyNullAllowed() {
		return onlyNullAllowed;
	}


	@Override
	public Key getKeyErrorMessage() {
		return isOnlyNullAllowed() ? LocaleValidators.NULL : LocaleValidators.NOTNULL;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}	
}
