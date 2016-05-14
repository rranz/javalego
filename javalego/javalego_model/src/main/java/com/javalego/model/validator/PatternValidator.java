package com.javalego.model.validator;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import javax.validation.constraints.Pattern;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;

/**
 * This <code>Pattern</code> is used to validate patterns
 * 
 * @author ROBERTO RANZ
 */
public class PatternValidator extends AbstractStringValidator {

	private java.util.regex.Pattern pattern;

	/**
	 * Creates a new MinValidator with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public PatternValidator(String errorMessage, java.util.regex.Pattern pattern) {
		super(errorMessage);
		this.pattern = pattern;
	}
	
	/**
	 * Creates a new MinValidator with a given error message
	 * 
	 * @param value
	 */
	public PatternValidator(java.util.regex.Pattern pattern) {
		this.pattern = pattern;
	}
	
	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation           
	 */
	public PatternValidator(String errorMessage, Annotation annotation) {
		super(errorMessage, annotation);
	}
	
	public void initialize(Pattern parameters) {
		Pattern.Flag flags[] = parameters.flags();
		int intFlag = 0;
		for (Pattern.Flag flag : flags) {
			intFlag = intFlag | flag.getValue();
		}

		try {
			pattern = java.util.regex.Pattern.compile(parameters.regexp(), intFlag);
		} catch (PatternSyntaxException e) {
			throw new IllegalArgumentException("Invalid regular expression.", e);
		}
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
		Matcher m = pattern.matcher(value);
		return m.matches();
	}

	@Override
	public void initialized(Annotation annotation) {
		if (annotation instanceof Pattern) {
			pattern = java.util.regex.Pattern.compile(((Pattern)annotation).regexp());
		}
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.PATTERN;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}	
}
