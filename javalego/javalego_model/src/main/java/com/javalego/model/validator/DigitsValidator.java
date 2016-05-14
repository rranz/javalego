package com.javalego.model.validator;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;

import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleValidators;
import com.javalego.model.validator.Validator.InvalidValueException;

/**
 * This <code>DecimalMax</code> is used to validate el valor máximo de un valor
 * numérico.
 * 
 * @author Vaadin Ltd.
 * @since 3.0
 */
public class DigitsValidator extends AbstractValidator<Number> {

	private int maxIntegerLength;
	private int maxFractionLength;

	/**
	 * Creates a new DecimalMax with a given error message
	 * 
	 * @param value
	 */
	public DigitsValidator(int maxIntegerLength, int maxFractionLength) {
		this.maxIntegerLength = maxIntegerLength;
		this.maxFractionLength = maxFractionLength;
		validateParameters();
	}

	public DigitsValidator(String errorMessage, int maxIntegerLength, int maxFractionLength) {
		super(errorMessage);
		this.maxIntegerLength = maxIntegerLength;
		this.maxFractionLength = maxFractionLength;
		validateParameters();
	}

	/**
	 * Constructs a validator with the given error message.
	 * 
	 * @param errorMessage
	 *            the message to be included in an {@link InvalidValueException}
	 *            (with "{0}" replaced by the value that failed validation).
	 * @param annotation
	 */
	public DigitsValidator(String errorMessage, Annotation annotation) {
		super(errorMessage, annotation);
	}

	@Override
	public Class<Number> getType() {
		return Number.class;
	}

	@Override
	public boolean isValidValue(Number num) {
		if (num == null)
			return true;
		BigDecimal bigNum;

		if ((num instanceof BigDecimal)) {
			bigNum = (BigDecimal) num;
		}
		else {
			bigNum = new BigDecimal(num.toString()).stripTrailingZeros();
		}

		int integerPartLength = bigNum.precision() - bigNum.scale();
		int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();

		return (this.maxIntegerLength >= integerPartLength) && (this.maxFractionLength >= fractionPartLength);
	}

	private void validateParameters() {
		if (this.maxIntegerLength < 0) {
			throw new IllegalArgumentException("The length of the integer part cannot be negative.");
		}
		if (this.maxFractionLength < 0)
			throw new IllegalArgumentException("The length of the fraction part cannot be negative.");
	}

	@Override
	public void initialized(Annotation annotation) {
		if (annotation instanceof Digits) {
			maxIntegerLength = ((Digits) annotation).integer();
			maxFractionLength = ((Digits) annotation).fraction();
		}
	}

	@Override
	public Key getKeyErrorMessage() {
		return LocaleValidators.DIGITS;
	}

	@Override
	public String getAdapterErrorMessage(String text) {
		text = text.replaceAll("\\{1\\}", String.valueOf(maxIntegerLength));
		return text.replaceAll("\\{2\\}", String.valueOf(maxFractionLength));
	}

}
