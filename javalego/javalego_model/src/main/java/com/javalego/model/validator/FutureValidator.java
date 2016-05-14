package com.javalego.model.validator;

import java.lang.annotation.Annotation;
import java.util.Date;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;

/**
 * This <code>Future</code> is used to validate future date.
 * 
 * @author ROBERTO RANZ
 */
public class FutureValidator extends AbstractValidator<Date> {

	/**
	 * Creates a new FutureValidator with a given error message
	 * 
	 * @param value
	 */
	public FutureValidator() {
	}
	
	/**
	 * Creates a new FutureValidator with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public FutureValidator(String errorMessage) {
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
	public FutureValidator(String errorMessage, Annotation annotation) {
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
		return date.after(new Date());
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
		return LocaleValidators.FUTURE;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}

}
