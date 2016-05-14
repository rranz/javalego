package com.javalego.ui.condition;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Tipología de condiciones SQL
 */
public enum ConditionType implements Key {

	/**
	 * El valor del campo  es igual al valor
	 */
	@Languages(locales = { 
			@Locale(value = "Igual al valor"),
			@Locale(locale = "en", value = "Equal than the value")
			})	
	EQUAL("="), 

	/**
	 * El valor del campo comienza por (sql = like 'value%')
	 */
	@Languages(locales = { 
			@Locale(value = "Comienza por el valor"),
			@Locale(locale = "en", value = "Start with than the value")
			})	
	STARTWITH("LIKE *%"), 
	
	/**
	 * El valor del campo termina en (sql = like '%value')
	 */
	@Languages(locales = { 
			@Locale(value = "Termina en el valor"),
			@Locale(locale = "en", value = "End width than the value")
			})	
	ENDWITH("LIKE %*"), 
	
	/**
	 * Valor del campo es distinto al valor
	 */
	@Languages(locales = { 
			@Locale(value = "Distinto al valor"),
			@Locale(locale = "en", value = "Distinct than the value")
			})	
	DISTINCT("<>"), 

	/**
	 * Valor del campo es menor al valor
	 */
	@Languages(locales = { 
			@Locale(value = "Menor al valor"),
			@Locale(locale = "en", value = "Minor than the value")
			})	
	MINOR("<"), 
	
	/**
	 * Valor del campo es menor o igual al valor
	 */
	@Languages(locales = { 
			@Locale(value = "Menor o igual al valor"),
			@Locale(locale = "en", value = "Minor or equal than the value")
			})	
	MINOREQUAL("<="), 

	/**
	 * Valor del campo es mayor al valor
	 */
	@Languages(locales = { 
			@Locale(value = "Mayor al valor"),
			@Locale(locale = "en", value = "Major than the value")
			})	
	MAJOR(">"), 
	
	/**
	 * Valor del campo es mayor o igual al valor
	 */
	@Languages(locales = { 
			@Locale(value = "Mayor o igual al valor"),
			@Locale(locale = "en", value = "Major or equal than the value")
			})	
	MAJOREQUAL(">="),
	
	/**
	 * El valor del campo contiene el valor (sql= like '%value%')
	 */
	@Languages(locales = { 
			@Locale(value = "Contiene el valor"),
			@Locale(locale = "en", value = "Include the value")
			})	
	CONTAINS("LIKE"), 

	/**
	 * El valor del campo no contiene el valor (sql= not like)
	 */
	@Languages(locales = { 
			@Locale(value = "No contiene el valor"),
			@Locale(locale = "en", value = "Not include the value")
			})	
	NOCONTAINS("NOT LIKE"), 
	
	/**
	 * El valor del campo no es nulo
	 */
	@Languages(locales = { 
			@Locale(value = "Su valor no es nulo"),
			@Locale(locale = "en", value = "Is not null value")
			})	
	WITHVALUE("IS NOT NULL"),
	
	/**
	 * El valor del campo es nulo
	 */
	@Languages(locales = { 
			@Locale(value = "Su valor es nulo"),
			@Locale(locale = "en", value = "Is null value")
			})	
	NOTVALUE("IS NULL"),
	
	/**
	 * El valor del campo se encuentra en una lista de valores.
	 */
	@Languages(locales = { 
			@Locale(value = "Contenido en los valores"),
			@Locale(locale = "en", value = "Exist in list values")
			})	
	EXISTLIST("IN"), 
	
	/**
	 * El valor del campo no se encuentra en una lista de valores
	 */
	@Languages(locales = { 
			@Locale(value = "No está contenido en los valores"),
			@Locale(locale = "en", value = "Not exist in list values")
			})	
	NOTEXISTLIST("NOT IN"), 
	
	/**
	 * El valor del campo está entre dos valores.
	 */
	@Languages(locales = { 
			@Locale(value = "Valor comprendido entre los valores"),
			@Locale(locale = "en", value = "Between values")
			})	
	BETWEEN("BETWEEN"), 
	
	/**
	 * El valor del campo no se encuentra entre dos valores.
	 */
	@Languages(locales = { 
			@Locale(value = "Valor no comprendido entre los valores"),
			@Locale(locale = "en", value = "Not between values")
			})	
	NOTBETWEEN("NOT BETWEEN"),
	/**
	 * El valor del campo contienes las palabras.
	 */
	CONTAINS_WORDS("CONTAINS_WORDS"), 
	
	CONTAINS_ANY_WORDS("CONTAINS_ANY_WORDS"); 

	/**
	 * Stores the human-readable description of this instance, by which it is
	 * identified in the user interface.
	 */
	private final String value;

	/**
	 * Enum constructors are always private since they can only be accessed
	 * through the enumeration mechanism.
	 * 
	 * @param description
	 *            human readable description of the source for the audio, by
	 *            which it is presented in the user interface.
	 */
	private ConditionType(String value) {
		this.value = value;
	}

	/**
	 * Devuelve el valor asociado con la enumeración.
	 * 
	 * @return the human-readable description by which this value is identified
	 *         in the user interface.
	 **/
	public String getValue() {
		return value;
	}

}
