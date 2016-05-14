package com.javalego.model.validator;

import java.lang.annotation.Annotation;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;

/**
 * This <code>AssertFalse</code> is used to validate urls
 * 
 * @author ROBERTO RANZ
 */
public class AssertFalseValidator extends AbstractValidator<Boolean> {

	/**
	 * Creates a new AssertFalse with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public AssertFalseValidator(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Creates a new AssertFalse with a given error message
	 * 
	 * @param value
	 */
	public AssertFalseValidator() {
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
		return (bool == null) || (!bool.booleanValue());
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
		return LocaleValidators.FALSE;
	}

	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}

}
