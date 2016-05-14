package com.javalego.model.validator;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;

/**
 * This <code>Url</code> is used to validate urls
 * 
 * @author ROBERTO RANZ
 */
public class UrlValidator extends AbstractStringValidator {

	/**
	 * Creates a new MinValidator with a given error message
	 * 
	 * @param value
	 */
	public UrlValidator() {
	}
	
	/**
	 * Creates a new MinValidator with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public UrlValidator(String errorMessage) {
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
	public UrlValidator(String errorMessage, Annotation annotation) {
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
	protected boolean isValidValue(String value) {
		if ((value == null) || (value.length() == 0))
			return true;
		try {
			new java.net.URL(value);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}

	@Override
	public void initialized(Annotation annotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.URL;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}

}
