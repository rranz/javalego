package com.javalego.model.validator;

import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;

/**
 * This <code>Mail</code> is used to validate mails
 * 
 * @author ROBERTO RANZ
 */
public class MailValidator extends AbstractStringValidator {
	
	private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
	
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	
	private Pattern pattern = Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$", 2);

	/**
	 * Creates a new MailValidator with a given error message
	 * 
	 * @param value
	 */
	public MailValidator() {
	}
	
	/**
	 * Creates a new MailValidator with a given error message
	 * 
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 * @param value
	 */
	public MailValidator(String errorMessage) {
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
	public MailValidator(String errorMessage, Annotation annotation) {
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
		if ((value == null) || (value.length() == 0)) {
			return true;
		}
		Matcher m = this.pattern.matcher(value);
		return m.matches();
	}

	@Override
	public void initialized(Annotation annotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.MAIL;
	}
	
	@Override
	public String getAdapterErrorMessage(String text) {
		return text;
	}

}
