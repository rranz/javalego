package com.javalego.model.validator;

import java.lang.annotation.Annotation;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Factoría de generación de validadores basándose en el tipo de anotación.
 */
class ValidatorFactory {

	private ValidatorFactory() {
	}

	/**
	 * Obtener una instancia de validador en base a la anotación de tipo de
	 * validación definida en la propiedad del bean. (Ej.: @NotNull, @Size,...)
	 * 
	 * @param annotation
	 * @return
	 */
	public static Validator getInstance(Annotation annotation) {

		Validator validator = null;
		
		if (annotation instanceof NotNull) {
			validator = new NullValidator("El valor no puede ser nulo", annotation);
		}
		else if (annotation instanceof Null) {
			validator = new NullValidator("No puede tener valor {0}", annotation);
		}
		else if (annotation instanceof Size) {
			validator = new StringLengthValidator("Longitud {0} incorrecta", annotation);
		}
		else if (annotation instanceof Min) {
			validator = new MinValidator("Valor {0} es inferior a " + ((Min)annotation).value(), annotation);
		}
		else if (annotation instanceof Max) {
			validator = new MaxValidator("Valor {0} es superior a " + ((Min)annotation).value(), annotation);
		}
		else if (annotation instanceof DecimalMin) {
			validator = new DecimalMinValidator("Valor {0} es inferior a " + ((Min)annotation).value(), annotation);
		}
		else if (annotation instanceof DecimalMax) {
			validator = new DecimalMaxValidator("Valor {0} es superior a " + ((Min)annotation).value(), annotation);
		}
		else if (annotation instanceof Digits) {
			validator = new DigitsValidator("Valor {0} incorrecto. Máximo de dígitos: " + ((Digits)annotation).integer() + " y máxima fracción: " + ((Digits)annotation).fraction(), annotation);
		}
		else if (annotation instanceof Future) {
			validator = new FutureValidator("La fecha {0} debe ser un valor futuro.", annotation);
		}
		else if (annotation instanceof Past) {
			validator = new PastValidator("La fecha {0} debe ser un valor pasado.", annotation);
		}
		else if (annotation instanceof AssertTrue) {
			validator = new MinValidator("Debe ser un valor verdadero", annotation);
		}
		else if (annotation instanceof AssertFalse) {
			validator = new MinValidator("Debe ser un valor falso", annotation);
		}
		else if (annotation instanceof Pattern) {
			validator = new PatternValidator("Valor incorrecto para el patrón definido", annotation);
		}

		return validator;
	}

}
