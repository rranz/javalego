package com.javalego.model.validator;

import java.lang.annotation.Annotation;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;

/**
 * This <code>AssertTrue</code> is used to validate urls
 * 
 * @author ROBERTO RANZ
 */
public class AssertTrueValidator extends AbstractValidator<Boolean> {

	/**
	 * Creates a new AssertTrue with a given error message
	 * 
	 * @param value
	 */
	public AssertTrueValidator() {
	}
	
	/**
	 * Creates a new AssertTrue with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public AssertTrueValidator(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Checks if the given value is valid.
	 * 
	 * @param value
	 *            the value to validate.
	 * @return <code>true</code> for valid value, otherwise <code>false</code>.
	 */
	@Override
	protected boolean isValidValue(Boolean bool) {
		return (bool == null) || (bool.booleanValue());
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public void initialized(Annotation annotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.TRUE;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}

	
}
