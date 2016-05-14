package com.javalego.model.validator;

import com.javalego.model.keys.Key;

/**
 * Abstract {@link com.vaadin.data.AbstractValidator Validator} implementation
 * that provides a basic Validator implementation except the
 * {@link #isValidValue(Object)} method.
 * <p>
 * To include the value that failed validation in the exception message you can
 * use "{0}" in the error message. This will be replaced with the failed value
 * (converted to string using {@link #toString()}) or "null" if the value is
 * null.
 * </p>
 * <p>
 * The default implementation of AbstractValidator does not support HTML in
 * error messages. To enable HTML support, override
 * {@link InvalidValueException#getHtmlMessage()} and throw such exceptions from
 * {@link #validate(Object)}.
 * </p>
 * <p>
 * Since Vaadin 7, subclasses can either implement {@link #validate(Object)}
 * directly or implement {@link #isValidValue(Object)} when migrating legacy
 * applications. To check validity, {@link #validate(Object)} should be used.
 * </p>
 * 
 * @param <T>
 *            The type
 * @author Vaadin Ltd.
 * @since 5.4
 */
public interface Validator {

	/**
	 * Checks the given value against this validator. If the value is valid the
	 * method does nothing. If the value is invalid, an
	 * {@link InvalidValueException} is thrown.
	 * 
	 * @param value
	 *            the value to check
	 * @parama errorMessage Custom message error (locale options)
	 * @throws AbstractValidator.InvalidValueException
	 *             if the value is invalid
	 */
	public void validate(Object value, String errorMessage) throws InvalidValueException;

	/**
	 * Exception that is thrown by a {@link AbstractValidator} when a value is
	 * invalid.
	 * 
	 * <p>
	 * The default implementation of InvalidValueException does not support HTML
	 * in error messages. To enable HTML support, override
	 * {@link #getHtmlMessage()} and use the subclass in validators.
	 * </p>
	 * 
	 * @author Vaadin Ltd.
	 * @since 3.0
	 */
	@SuppressWarnings("serial")
	public class InvalidValueException extends Exception {

		/**
		 * Array of one or more validation errors that are causing this
		 * validation error.
		 */
		private InvalidValueException[] causes = null;

		/**
		 * Constructs a new {@code InvalidValueException} with the specified
		 * message.
		 * 
		 * @param message
		 *            The detail message of the problem.
		 */
		public InvalidValueException(String message) {
			this(message, new InvalidValueException[] {});
		}

		/**
		 * Constructs a new {@code InvalidValueException} with a set of causing
		 * validation exceptions. The causing validation exceptions are included
		 * when the exception is painted to the client.
		 * 
		 * @param message
		 *            The detail message of the problem.
		 * @param causes
		 *            One or more {@code InvalidValueException}s that caused
		 *            this exception.
		 */
		public InvalidValueException(String message, InvalidValueException... causes) {
			super(message);
			if (causes == null) {
				throw new NullPointerException("Possible causes array must not be null");
			}

			this.causes = causes;
		}

		/**
		 * Check if the error message should be hidden.
		 * 
		 * An empty (null or "") message is invisible unless it contains nested
		 * exceptions that are visible.
		 * 
		 * @return true if the error message should be hidden, false otherwise
		 */
		public boolean isInvisible() {
			String msg = getMessage();
			if (msg != null && msg.length() > 0) {
				return false;
			}
			if (causes != null) {
				for (int i = 0; i < causes.length; i++) {
					if (!causes[i].isInvisible()) {
						return false;
					}
				}
			}
			return true;
		}

		/**
		 * Returns the {@code InvalidValueExceptions} that caused this
		 * exception.
		 * 
		 * @return An array containing the {@code InvalidValueExceptions} that
		 *         caused this exception. Returns an empty array if this
		 *         exception was not caused by other exceptions.
		 */
		public InvalidValueException[] getCauses() {
			return causes;
		}

	}

	/**
	 * A specific type of {@link InvalidValueException} that indicates that
	 * validation failed because the value was empty. What empty means is up to
	 * the thrower.
	 * 
	 * @author Vaadin Ltd.
	 * @since 5.3.0
	 */
	@SuppressWarnings("serial")
	public class EmptyValueException extends InvalidValueException {

		public EmptyValueException(String message) {
			super(message);
		}

	}

	/**
	 * Key translate error message.
	 * @return
	 */
	public Key getKeyErrorMessage();

	/**
	 * Adapta el mensaje de error al tipo de validador en base a los parámetros definidos ({1} número de dígitos).
	 * Notal: El parámetro {0} es para el valor del campo. No hay que eliminarlo del texto.
	 * @param text
	 * @return
	 */
	public String getAdapterErrorMessage(String text);	
}
