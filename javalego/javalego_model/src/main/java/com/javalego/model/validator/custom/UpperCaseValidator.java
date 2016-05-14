package com.javalego.model.validator.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpperCaseValidator implements ConstraintValidator<UpperCase, String> {

	@Override
	public void initialize(UpperCase constraintAnnotation) {
		// nothing to do
	}

	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {

		if (object == null)
			return true;

		boolean valid = object.equals(object.toUpperCase());

		if (!valid) {
			//constraintContext.getDefaultConstraintMessageTemplate();
			constraintContext.disableDefaultConstraintViolation();
			// Se utiliza una constante pero puede ser un valor de
			// ValidationMessages.properties {key.property}
			// Falta incluir UIContext.getText(Enum).
			
			// TODO utilizar constraintContext.getDefaultConstraintMessageTemplate()
			
			constraintContext.buildConstraintViolationWithTemplate("El texto debe estar en mayúsculas").addConstraintViolation();
		}
		return valid;
	}

}
