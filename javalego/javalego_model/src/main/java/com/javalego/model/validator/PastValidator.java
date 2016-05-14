package com.javalego.model.validator;

import java.lang.annotation.Annotation;
import java.util.Date;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;

/**
 * This <code>Past</code> is used to validate past date.
 * 
 * @author ROBERTO RANZ
 */
public class PastValidator extends AbstractValidator<Date> {

	/**
	 * Creates a new FutureValidator with a given error message
	 * 
	 * @param value
	 */
	public PastValidator() {
	}
	
	/**
	 * Creates a new FutureValidator with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public PastValidator(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation           
	 */
	public PastValidator(String errorMessage, Annotation annotation) {
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
	protected boolean isValidValue(Date date) {
		if (date == null) {
			return true;
		}
		return date.before(new Date());
	}

	@Override
	public Class<Date> getType() {
		return Date.class;
	}

	@Override
	public void initialized(Annotation annotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.PAST;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}

	
}
