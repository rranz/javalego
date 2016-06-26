package com.javalego.errors;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import com.javalego.model.keys.ErrorKey;
import com.javalego.model.resources.locale.Locale;

/**
 * Clase encargada de obtener información de las anotaciones incluidas en el enumerado {@link Error} para la
 * localización de los mensajes y códigos de error.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ErrorManager
{
	private ErrorKey errorKey;
	
	private Error error;

	/**
	 * Constructor
	 * 
	 * @param error
	 */
	public ErrorManager(ErrorKey errorKey)
	{
		this.errorKey = errorKey;
	}

	/**
	 * Código de error definido en el enumerado @Error y atributo index.
	 * 
	 * @return código error
	 */
	public String code()
	{
		Error a = getError();
		if (a != null)
		{
			return a.level().name().substring(0, 1) + (Integer.valueOf(a.level().toString()).intValue() + a.index());
		}
		return "(unknown error code)";
	}

	/**
	 * Prefijo del nivel de severidad del error. E, W, I, S, T...
	 * 
	 * @return Nivel
	 */
	public String prefixLevel()
	{
		Error a = getError();
		if (a != null)
		{
			return a.level().name().substring(0, 1);
		}
		return "(unknown error code)";
	}

	/**
	 * Nivel de severidad del error
	 * 
	 * @return Nivel
	 */
	public String level()
	{
		Error a = getError();
		if (a != null)
		{
			return a.level().name();
		}
		return "(unknown error code)";
	}

	/**
	 * Localiza la anotación @Error en el enumerado.
	 * 
	 * @return
	 */
	private Error getError()
	{
		if (error != null) {
			return error;
		}
		try
		{
			Field field = errorKey.getClass().getDeclaredField(errorKey.name());
			error = field.getAnnotation(Error.class);
			return error;
		}
		catch (NoSuchFieldException e)
		{
			throw new RuntimeException("Annotation 'Error' does not exist", e);
		}
	}

	/**
	 * Mensaje de error definido en el enumerado @Error.@LocaleError y atributo title.
	 * 
	 * @param parameters
	 *            Lista de valores de los parámetros definidos en el mensaje. Ej.: 'Mi nombre es %s y vivo en %s'.
	 * @return mensaje de error
	 */
	public String message(Object... parameters)
	{
		return message(null, parameters);
	}

	/**
	 * Mensaje de error definido en el enumerado @Error.@LocaleError y atributo title.
	 * 
	 * @param locale
	 *            Locale
	 * @param parameters
	 *            Lista de valores de los parámetros definidos en el mensaje. Ej.: 'Mi nombre es %s y vivo en %s'.
	 * @return mensaje de error
	 */
	public String message(Locale locale, Object... parameters)
	{
		String message = "";
		Error error = getError();
		if (error == null)
		{
			return "(unknown error message)";
		}

		for (Locale item : error.locales())
		{
			if (locale != null && item.locale().indexOf(locale.locale()) == 0)
			{
				message = getMessage(item.value(), parameters);
				break;
			}
		}
		if ("".equals(message))
		{
			for (Locale item : error.locales())
			{
				if (item != null
					&& ("".equals(item.locale())
						|| item.locale().indexOf(java.util.Locale.getDefault().getLanguage()) == 0))
				{
					message = getMessage(item.value(), parameters);
					break;
				}
			}
		}
		return message;
	}

	/**
	 * Obtener el texto del mensaje del error en base al nivel de error, al índice secuencial dentro de este nivel y el
	 * título localizado.
	 * 
	 * @param title
	 * @param parameters
	 *            Lista de los valores de los parámetros definidos en el mensaje. Ej.: 'Mi nombre es %s y vivo en %s'.
	 * @return message
	 */
	private String getMessage(String title, Object... parameters)
	{
		String message = "[" + code() + "] " + title;

		return parameters != null && parameters.length > 0 ? MessageFormat.format(message, parameters) : message;
	}

}
