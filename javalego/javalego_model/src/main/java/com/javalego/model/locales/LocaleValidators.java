package com.javalego.model.locales;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos para las diferentes tipologías de validadores.
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum LocaleValidators implements Key {

	@Languages(locales = { 
			@Locale(value = "El valor no puede ser nulo"),
			@Locale(locale = "en", value = "Value is null")
			})
	NOTNULL,
	
	@Languages(locales = { 
			@Locale(value = "No puede tener valor {0}"),
			@Locale(locale = "en", value = "You can not have value {0}")
			})
	NULL,
	
	@Languages(locales = { 
			@Locale(value = "Longitud {0} incorrecta"),
			@Locale(locale = "en", value = "Incorrect length {0}")
			})
	SIZE,
	
	@Languages(locales = { 
			@Locale(value = "Longitud mínima debe ser de {0} caracteres."),
			@Locale(locale = "en", value = "Minimum length should be {0} characters.")
			})
	MIN_LENGTH,
	
	@Languages(locales = { 
			@Locale(value = "Longitud máxima debe ser de {0} caracteres."),
			@Locale(locale = "en", value = "Maximun length should be {0} characters.")
			})
	MAX_LENGTH,
	
	@Languages(locales = { 
			@Locale(value = "Valor {0} es inferior a {1}"),
			@Locale(locale = "en", value = "Value {0} is less than {1}")
			})
	MIN,
	
	@Languages(locales = { 
			@Locale(value = "Valor {0} es superior a {1}"),
			@Locale(locale = "en", value = "Value {0} is greater than {1}")
			})
	MAX,
	
	@Languages(locales = { 
			@Locale(value = "Valor {0} es inferior a {1}"),
			@Locale(locale = "en", value = "Value {0} is less than {1}")
			})
	DECIMALMIN,
	
	@Languages(locales = { 
			@Locale(value = "Valor {0} es superior a {1}"),
			@Locale(locale = "en", value = "Value {0} is greater than {1}")
			})
	DECIMALMAX,
	
	@Languages(locales = { 
			@Locale(value = "Valor {0} incorrecto. Máximo de dígitos: {1} y máxima fracción: {2}"),
			@Locale(locale = "en", value = "Value {0} Maximum incorrect digit. {1} and maximum fraction: {2}")
			})
	DIGITS,
	
	@Languages(locales = { 
			@Locale(value = "La fecha {0} debe ser un valor futuro"),
			@Locale(locale = "en", value = "The {0} must be a future date value")
			})
	FUTURE,
	
	@Languages(locales = { 
			@Locale(value = "La fecha {0} debe ser un valor pasado"),
			@Locale(locale = "en", value = "The {0} must be a past date value")
			})
	PAST,
	
	@Languages(locales = { 
			@Locale(value = "Debe ser un valor verdadero"),
			@Locale(locale = "en", value = "Must be a real value")
			})
	TRUE,
	
	@Languages(locales = { 
			@Locale(value = "Debe ser un valor false"),
			@Locale(locale = "en", value = "Must be a false value")
			})
	FALSE,
	
	@Languages(locales = { 
			@Locale(value = "Valor incorrecto para el patrón definido"),
			@Locale(locale = "en", value = "Incorrect value for the defined pattern")
			})
	PATTERN, 
	
	@Languages(locales = { 
			@Locale(value = "Valor de correo electrónico incorrecto"),
			@Locale(locale = "en", value = "Incorrect email value")
			})
	MAIL, 
	
	@Languages(locales = { 
			@Locale(value = "Valor de url incorrecto"),
			@Locale(locale = "en", value = "Incorrect url value")
			})
	URL
	
}
