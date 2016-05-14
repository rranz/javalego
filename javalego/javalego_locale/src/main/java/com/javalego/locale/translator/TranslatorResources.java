package com.javalego.locale.translator;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.javalego.model.BaseModel;
import com.javalego.model.keys.Key;
import com.javalego.model.resources.Language;

/**
 * Traductor de textos basado en archivos de paquete de recursos.
 * 
 * @see ResourceBundle
 * 
 * @author ROBERTO RANZ
 * 
 */
public class TranslatorResources implements Translator {

	private static final String UNKNOWN = "unknown";

	/**
	 * Lista de ficheros de recursos
	 */
	private String[] resources = null;

	/**
	 * Idioma seleccionado por defecto.
	 */
	private Language defaultLanguage;
	
	/** 
	 * Pasar al lowercase el key
	 */
	private boolean lowercase;

	/**
	 * Constructor
	 * 
	 * @param resources
	 */
	public TranslatorResources(boolean lowercase, String... resources) {
		this.resources = resources;
		this.lowercase = lowercase;
	}

	@Override
	public String getValue(Key code, String locale, Object... params) {

		if (code == null) {
			return null;
		}

		return getValue(code, new java.util.Locale(locale), params);
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

		String value = null;

		if (resources != null) {
			for (String resource : resources) {
				ResourceBundle bundle = ResourceBundle.getBundle(resource, locale == null ? getDefaultLocale() : locale);
				if (bundle != null) {
					try {
						value = bundle.getString(lowercase ? code.name().toLowerCase() : code.name());
						if (value != null) {
							break;
						}
					}
					catch (MissingResourceException e) {
					}
				}
			}
		}
		return params != null && value != null ? MessageFormat.format(value, params) : UNKNOWN + " " + code.name();
	}

	@Override
	public String getDescription(Key code, java.util.Locale locale, Object... params) {

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

		return null;
	}

	@Override
	public java.util.Locale getDefaultLocale() {

		return defaultLanguage != null ? defaultLanguage.getLocale() : java.util.Locale.getDefault();
	}

	@Override
	public String getDescription(Key code, String locale, Object[] params) {

		return null;
	}

}
