package com.javalego.errors;

import java.util.Locale;

/**
 * Interface que define la lista de errores vinculados a las diferentes tipos de excepciones usadas por las
 * aplicaciones.
 * <p>
 * Esta interface se usa para que las clases que implementen la interface Errros, puedan obtener los datos definidos en
 * las anotaciones incluidas en cada tipo de error para su localización.
 * <p>
 * La información del enumerado será utilizada por {@link LocalizedException} para generar los mensajes de error que
 * incluyen localización, codificación y naturaleza del error.
 * <p>
 * 
 * @see Error
 * 
 * @author ROBERTO RANZ
 */
public interface ErrorData
{
	/**
	 * Código de error definido en el enumerado @Error y atributo index.
	 * 
	 * @return código error
	 */
	public String code();

	/**
	 * Prefijo del nivel de severidad del error. E, W, I, S, T...
	 * 
	 * @return Nivel
	 */
	public String prefixLevel();

	/**
	 * Nivel de severidad del error
	 * 
	 * @return Nivel
	 */
	public String level();

	/**
	 * Mensaje de error definido en el enumerado @Error.@LocaleError y atributo title.
	 * 
	 * @param parameters
	 *            Lista de valores de los parámetros definidos en el mensaje. Ej.: 'Mi nombre es %s y vivo en %s'.
	 * @return mensaje de error
	 */
	public String message(Object... parameters);

	/**
	 * Mensaje de error definido en el enumerado @Error.@LocaleError y atributo title.
	 * 
	 * @param locale
	 *            Locale
	 * @param parameters
	 *            Lista de valores de los parámetros definidos en el mensaje. Ej.: 'Mi nombre es %s y vivo en %s'.
	 * @return mensaje de error
	 */
	public String message(Locale locale, Object... parameters);
}
