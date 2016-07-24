package com.javalego.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.javalego.errors.Error;
import com.javalego.errors.ErrorLevel;
import com.javalego.locale.LocaleContext;
import com.javalego.model.keys.ErrorKey;
import com.javalego.model.resources.locale.Languages;

/**
 * Excepción de aplicación que incluye la localización de mensajes de error. Permitiendo incluir parámetros para
 * formatear el mensaje (MessageFormat.format("mensaje {0} {1}", param1, param2)).
 * 
 * @author ROBERTO RANZ
 * 
 */
public class LocalizedException extends Exception
{
	private static final long serialVersionUID = 4329587883810372619L;

	private Object[] parameters;

	private Locale locale;

	private Exception exception;

	/**
	 * Mensaje enumerado basado en anotaciones para definir el texto de mensaje en varios idiomas y con parámetros.
	 */
	private ErrorKey key;

	private static final Logger logger = Logger.getLogger(LocalizedException.class);

	/**
	 * Constructor
	 * 
	 * @param exception
	 * @param locale
	 * @param parameters
	 *            Lista de parámetros requeridos para construir el mensaje. Estos parámetros se han definido en las
	 *            anotaciones este tipo de excepción.
	 * @see CGUException_es, CGUException_en, ...
	 */
	public LocalizedException(Exception exception, Locale locale, String[] parameters)
	{
		this.locale = locale;
		this.parameters = parameters;
		this.exception = exception;
	}

	/**
	 * Constructor
	 * 
	 * @param exception
	 *            Excepción
	 */
	public LocalizedException(Exception exception)
	{
		super(exception);
	}

	/**
	 * Constructor
	 * 
	 * @param exception
	 * @param locale
	 * @param key
	 *            Código de excepción basada en enumerados donde se definen los mensajes en multilenguaje mediante
	 *            anotaciones CGUException_es, CGUException_en, ...
	 */
	public LocalizedException(Exception exception, Locale locale, ErrorKey key)
	{
		this.locale = locale;
		this.exception = exception;
		this.key = key;
	}

	/**
	 * Constructor
	 * 
	 * @param exception
	 * @param locale
	 * @param key
	 *            Código de excepción basada en enumerados donde se definen los mensajes en multilenguaje mediante
	 *            anotaciones CGUException_es, CGUException_en, ...
	 * @param parameters
	 *            Lista de parámetros requeridos para construir el mensaje. Estos parámetros se han definido en las
	 *            anotaciones este tipo de excepción.
	 */
	public LocalizedException(Exception exception, Locale locale, ErrorKey key, String[] parameters)
	{
		this.locale = locale;
		this.exception = exception;
		this.key = key;
		this.parameters = parameters;
	}

	/**
	 * Constructor
	 * 
	 * @param exception
	 * @param key
	 *            Código de excepción basada en enumerados donde se definen los mensajes en multilenguaje mediante
	 *            anotaciones CGUException_es, CGUException_en, ...
	 * @param parameters
	 *            Lista de parámetros requeridos para construir el mensaje. Estos parámetros se han definido en las
	 *            anotaciones este tipo de excepción.
	 */
	public LocalizedException(Exception exception, ErrorKey key, String... parameters)
	{
		this.exception = exception;
		this.key = key;
		this.parameters = parameters;
	}

	/**
	 * Constructor
	 * 
	 * @param locale
	 * @param key
	 *            Código de excepción basada en enumerados donde se definen los mensajes en multilenguaje mediante
	 *            anotaciones CGUException_es, CGUException_en, ...
	 * @param parameters
	 *            Lista de parámetros requeridos para construir el mensaje. Estos parámetros se han definido en las
	 *            anotaciones este tipo de excepción.
	 */
	public LocalizedException(Locale locale, ErrorKey key, String... parameters)
	{
		this.locale = locale;
		this.key = key;
		this.parameters = parameters;
	}

	/**
	 * Constructor
	 * 
	 * @param key
	 *            Código de excepción basada en enumerados donde se definen los mensajes en multilenguaje mediante
	 *            anotaciones CGUException_es, CGUException_en, ...
	 * @param parameters
	 *            Lista de parámetros requeridos para construir el mensaje. Estos parámetros se han definido en las
	 *            anotaciones este tipo de excepción.
	 */
	public LocalizedException(ErrorKey key, String... parameters)
	{
		this.key = key;
		this.parameters = parameters;
	}

	public LocalizedException(Object[] parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Mensaje del error
	 * @param parameters
	 */
	public LocalizedException(String message, Object... parameters)
	{
		super(message);
		this.parameters = parameters;
	}

	public LocalizedException(Locale locale)
	{
		this.locale = locale;
	}

	public LocalizedException()
	{
	}

	/**
	 * Construir el mensaje de la excepción en base a:
	 * 
	 * 1. Parámetros definidos en las anotaciones 2. Mensaje 3. Mensaje definido en un objeto enumerado que implementa
	 * la interface Key.
	 */
	@Override
	public String getMessage()
	{

		String message = "";

		try
		{
			// Excepciones enumeradas con anotaciones multilenguaje.
			if (key != null)
			{

				Field field = key.getClass().getDeclaredField(key.toString());

				// Lista de idiomas en java.version hasta la 1.7
				if (field.getAnnotation(Languages.class) != null)
				{
					message = getLocalizedMessage(field.getAnnotation(Languages.class));
				}
				// Versión java version 1.8+ multiple tipo anotaciones @Locale o
				// una simple anotación locale
				else if (field.getAnnotation(com.javalego.model.resources.locale.Locale.class) != null)
				{

					List<com.javalego.model.resources.locale.Locale> list = new ArrayList<com.javalego.model.resources.locale.Locale>();

					for (Annotation annotation : field.getAnnotations())
					{

						if (annotation instanceof com.javalego.model.resources.locale.Locale)
						{
							list.add((com.javalego.model.resources.locale.Locale) annotation);
						}
					}

					message = getLocalizedMessage(list);
				}
				else
				{
					// Buscar anotaciones de excepción.
					Error et = field.getAnnotation(Error.class);

					if (et != null)
					{
						message = getLocalizedMessage(field.getAnnotation(Error.class));
					}
					// Buscar en la lista de recursos de textos localizados
					// mediante ITranslator.
					else
					{
						message = LocaleContext.getText(key, locale);
					}
				}
			}
			// Anotaciones multilenguaje incluidas en la clase derivada de
			// LocalizedException.
			else
			{
				Class<?> _class = getClass();
				message = getLocalizedMessage(_class.getAnnotation(Error.class));
			}

			// Añadir el error y causa de la excepción.
			// if (exception != null && exception.getLocalizedMessage() != null)
			// {
			// message += " . " + exception.getLocalizedMessage() +
			// (exception.getCause() != null ? " Causa: " +
			// exception.getCause().getMessage() : "");
			// }

			message = parameters != null && message != null ? MessageFormat.format(message, parameters) : message;

		}
		catch (SecurityException e)
		{
			logger.error("ERROR: Access field '" + toString());
		}
		catch (NoSuchFieldException e)
		{
			logger.error("ERROR: Field '" + toString() + "' inexistente.");
		}
		return message;
	}

	/**
	 * Obtener el mensaje buscando el lenguaje de las anotaciones definidas.
	 * 
	 * @param languages
	 * @return
	 */
	private String getLocalizedMessage(Languages languages)
	{

		String message = super.getMessage();

		if (languages != null)
		{
			for (com.javalego.model.resources.locale.Locale item : languages.locales())
			{

				if (locale != null && item.locale().indexOf(locale.getLanguage()) == 0)
				{
					message = getMessage(ErrorLevel.ERROR, -1, item.value());
					break;
				}
			}
		}
		return message;
	}

	/**
	 * Obtener el mensaje buscando el lenguaje de las anotaciones definidas.
	 * 
	 * @param locales
	 * @return
	 */
	private String getLocalizedMessage(List<com.javalego.model.resources.locale.Locale> locales)
	{

		String message = super.getMessage();

		if (locales != null && locales.size() > 0)
		{
			for (com.javalego.model.resources.locale.Locale item : locales)
			{

				if (locale != null && item.locale().indexOf(locale.getLanguage()) == 0)
				{
					message = getMessage(ErrorLevel.ERROR, -1, item.value());
					break;
				}
			}
		}
		return message;
	}

	/**
	 * Obtener el mensaje buscando el lenguaje de las anotaciones definidas.
	 * 
	 * @param message
	 * @param typeException
	 * @return
	 */
	private String getLocalizedMessage(Error typeException)
	{

		String message = super.getMessage();

		if (typeException != null)
		{
			for (com.javalego.model.resources.locale.Locale localeException : typeException.locales())
			{

				if (locale != null && localeException.locale().indexOf(locale.getLanguage()) == 0)
				{
					message = getMessage(typeException.level(), typeException.index(), localeException.value());
					break;
				}
			}
			if (message == null || "".equals(message))
			{
				for (com.javalego.model.resources.locale.Locale locale : typeException.locales())
				{
					if (locale != null && ("".equals(locale.locale())
						|| locale.locale().indexOf(Locale.getDefault().getLanguage()) == 0))
					{
						message = getMessage(typeException.level(), typeException.index(), locale.value());
						break;
					}
				}
			}
		}
		return message;
	}

	/**
	 * Obtener el texto del mensaje de la excepción en base al nivel de excepción asociada, al índice secuencial dentro
	 * de este nivel y el título localizado.
	 * 
	 * @param level
	 * @param index
	 * @param title
	 * @return
	 */
	private String getMessage(ErrorLevel level, int index, String title)
	{

		String text = (index > -1 ? "[" + level.name().substring(0, 1) + (level.getCode() + index) + "] " : "") + title;

		if (exception != null && exception.getLocalizedMessage() != null)
		{
			text += ". " + /* level.name() + ": " + */exception.getLocalizedMessage()
				+ (exception.getCause() != null ? " Cause: " + exception.getCause().getMessage() : "");
		}

		return text;
	}

	/**
	 * Obtener mensaje localizado
	 */
	@Override
	public String getLocalizedMessage()
	{
		return getMessage();
	}

	/**
	 * Obtener el tipo de excepción buscando la anotación ExceptionType.
	 * 
	 * @return
	 */
	private Error getExceptionType()
	{

		if (key != null)
		{
			Field field;
			try
			{
				field = key.getClass().getDeclaredField(key.toString());
				return field.getAnnotation(Error.class);
			}
			catch (NoSuchFieldException e)
			{
			}
			catch (SecurityException e)
			{
			}
		}
		return null;
	}

	/**
	 * Excepción de tipo info
	 * 
	 * @return
	 */
	public boolean isInformation()
	{

		Error et = getExceptionType();

		return et != null ? et.level() == ErrorLevel.INFO : false;
	}

	/**
	 * Excepción de tipo warning
	 * 
	 * @return
	 */
	public boolean isWarning()
	{

		Error et = getExceptionType();

		return et != null ? et.level() == ErrorLevel.WARN : false;
	}

	/**
	 * Excepción de tipo error
	 * 
	 * @return
	 */
	public boolean isError()
	{

		Error et = getExceptionType();

		return et != null ? et.level() == ErrorLevel.ERROR : true;
	}

	/**
	 * Excepción de tipo debug
	 * 
	 * @return
	 */
	public boolean isDebug()
	{

		Error et = getExceptionType();

		return et != null ? et.level() == ErrorLevel.DEBUG : true;
	}

	/**
	 * Excepción de tipo sucess
	 * 
	 * @return
	 */
	public boolean isSucess()
	{

		Error et = getExceptionType();

		return et != null ? et.level() == ErrorLevel.SUCESS : true;
	}

	/**
	 * Excepción de tipo fatal
	 * 
	 * @return
	 */
	public boolean isFatal()
	{

		Error et = getExceptionType();

		return et != null ? et.level() == ErrorLevel.FATAL : true;
	}

	/**
	 * Lista de parámetros a incluir en el mensaje.
	 * 
	 * @return
	 */
	public Object[] getParameters()
	{
		return parameters;
	}

}
