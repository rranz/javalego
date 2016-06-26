package com.javalego.locale.translator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.javalego.errors.Error;
import com.javalego.model.BaseModel;
import com.javalego.model.keys.Key;
import com.javalego.model.resources.Language;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;
import com.javalego.util.ArrayUtils;

/**
 * Traductor usado para la localización de textos basado en código java:
 * enumerados y anotaciones.
 * 
 * @see Languages
 * @see Locale
 * 
 * @author ROBERTO RANZ
 * 
 */
public class TranslatorCode implements Translator {

	private static final String UNKNOWN = "unknown";

	private static final Logger logger = Logger.getLogger(TranslatorCode.class);

	/**
	 * Idioma seleccionado por defecto.
	 */
	private Language defaultLanguage;

	@Override
	public String getValue(Key code, String locale, Object... params) {

		if (code == null) {
			return null;
		}

		return getValue(findLocales(code), locale, params);

	}

	private String getValue(Locale[] locales, String locale, Object... params) {

		Locale l = findLocale(locales, locale);

		return l != null ? MessageFormat.format(l.value(), params) : UNKNOWN + " " + locale;
	}

	private String getDescription(Locale[] locales, String locale, Object... params) {

		Locale l = findLocale(locales, locale);

		return l != null && l.description() != null ? MessageFormat.format(l.description(), params) : UNKNOWN + " " + locale;
	}

	/**
	 * Localizar el Locale de las anotaciones que tiene el idioma pasado como
	 * parámetro (locale param)
	 * 
	 * @param languages
	 * @param locale
	 * @return
	 */
	private Locale findLocale(Locale[] locales, String locale) {

		if (locales == null)
			return null;

		if (locale == null) {
			locale = getDefaultLocale().getLanguage();
		}

		Locale l = null;

		// Buscar en anotación Languages
		for (Locale item : locales) {
			if ((l == null && "".equals(item.locale())) || locale.equals(item.locale())) {
				l = item;
			}
		}

		return l;
	}

	@Override
	public Language getDefaultLanguage() {

		if (defaultLanguage == null) {
			defaultLanguage = new Language();
		}
		return defaultLanguage;
	}

	@Override
	public void setDefaultLanguage(Language defaultLanguage) {

		this.defaultLanguage = defaultLanguage;
	}

	@Override
	public String getValue(Key code, java.util.Locale locale, Object... params) {

		if (code == null) {
			return null;
		}

		return getValue(code, locale != null ? locale.getLanguage() : null, params);
	}

	@Override
	public String getDescription(Key code, java.util.Locale locale, Object... params) {

		if (code == null) {
			return null;
		}

		try {
			Locale l = findLocale(findLocales(code), locale != null ? locale.getLanguage() : null);
			return l != null ? MessageFormat.format(l.description(), params) : UNKNOWN + " " + code.name();
		}
		catch (Exception e) {
			logger.error("ERROR GET VALUE IN TRANSLATOR.", e);
		}
		return null;
	}

	/**
	 * Localizar las anotaciones Locale en un enumerado.
	 * 
	 * @param code
	 * @return
	 */
	private Locale[] findLocales(Key code) {

		try {
			Field field = code.getClass().getField(code.name());

			Languages languages = field.getAnnotation(Languages.class);

			if (languages == null) {

				// Verificar si es una excepción localizada con anotaciones
				Error et = field.getAnnotation(Error.class);
				if (et != null) {
					return et.locales();
				}
				// Java version 1.8+ soporta multiples anotaciones.
				else if (field.getAnnotation(com.javalego.model.resources.locale.Locale.class) != null) {

					com.javalego.model.resources.locale.Locale[] list = new com.javalego.model.resources.locale.Locale[] {};

					for (Annotation annotation : field.getAnnotations()) {

						if (annotation instanceof com.javalego.model.resources.locale.Locale) {
							ArrayUtils.add(list, (com.javalego.model.resources.locale.Locale) annotation);
						}
					}

					if (list.length > 0) {
						return list;
					}
				}
			}
			else {
				return languages.locales();
			}
		}
		catch (NoSuchFieldException e) {
			logger.error("ERROR GET VALUE IN TRANSLATOR.", e);
		}
		catch (SecurityException e) {
			logger.error("ERROR GET VALUE IN TRANSLATOR.", e);
		}
		return null;
	}

	@Override
	public String getTitle(BaseModel baseModel, java.util.Locale locale, Object... params) {

		if (baseModel == null) {
			return null;
		}

		if (baseModel.getTitle() != null) {
			return getValue(baseModel.getTitle(), locale != null ? locale.getLanguage() : null, params);
		}
		else {
			return baseModel.getName();
		}
	}

	@Override
	public String getDescription(BaseModel baseModel, java.util.Locale locale, Object... params) {

		if (baseModel == null) {
			return null;
		}

		String result = null;

		if (baseModel.getDescription() != null) {
			result = getValue(baseModel.getDescription(), locale != null ? locale.getLanguage() : null, params);
		}
		// Buscar en la descripción del atributo key del título si no tiene
		// descripción.
		else if (baseModel.getTitle() != null) {
			result = getDescription(baseModel.getTitle(), locale != null ? locale.getLanguage() : null, params);
		}

		return result == null ? baseModel.getName() : result;
	}

	@Override
	public java.util.Locale getDefaultLocale() {

		return defaultLanguage != null ? defaultLanguage.getLocale() : java.util.Locale.getDefault();
	}

	@Override
	public String getDescription(Key code, String locale, Object[] params) {

		if (code == null) {
			return null;
		}

		return getDescription(findLocales(code), locale, params);
	}

}
