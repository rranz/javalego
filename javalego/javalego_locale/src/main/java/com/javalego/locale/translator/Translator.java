package com.javalego.locale.translator;

import java.util.Locale;

import com.javalego.model.BaseModel;
import com.javalego.model.context.Services;
import com.javalego.model.keys.Key;
import com.javalego.model.resources.Language;

/**
 * Servicio de localización de textos.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface Translator extends Services {

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo value
	 * de la enumeración Locale.
	 * 
	 * @see com.javalego.model.resources.locale.Locale
	 * @param code
	 * @param locale
	 * @throws Exception
	 */
	String getValue(Key code, String locale, Object... params);

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo value
	 * de la enumeración Locale.
	 * 
	 * @see com.javalego.model.resources.locale.Locale
	 * @param code
	 * @param locale
	 * @throws Exception
	 */
	String getValue(Key code, Locale locale, Object... params);

	/**
	 * Obtener el valor description del key en el idioma pasado como parámetro.
	 * 
	 * @param code
	 * @param locale
	 * @param params
	 * @return
	 */
	String getDescription(Key code, String locale, Object[] params);

	/**
	 * Obtiene la traducción de un enumerado que incluye anotaciones para
	 * obtener este texto en varios idiomas. Obtiene su valor del atributo
	 * description de la enumeración Locale.
	 * 
	 * @see com.javalego.model.resources.locale.Locale
	 * @param code
	 * @param locale
	 * @throws Exception
	 */
	String getDescription(Key code, Locale locale, Object... params);

	/**
	 * Obtiene la traducción del título de un objeto BaseModel.
	 * 
	 * @param baseModel
	 * @param locale
	 * @throws Exception
	 */
	String getTitle(BaseModel baseModel, Locale locale, Object... params);

	/**
	 * Obtiene la traducción de la descripción de un objeto BaseModel.
	 * 
	 * @param baseModel
	 * @param locale
	 * @throws Exception
	 */
	String getDescription(BaseModel baseModel, Locale locale, Object... params);

	/**
	 * Establecer el idioma por defecto.
	 * 
	 * @param defaultLanguage
	 */
	void setDefaultLanguage(Language defaultLanguage);

	/**
	 * Obtener el idioma por defecto.
	 * 
	 * @param defaultLanguage
	 */
	Language getDefaultLanguage();

//	/**
//	 * Obtener el atributo value de la lista de idiomas enumerados.
//	 * 
//	 * @param locales
//	 * @param locale
//	 * @param params
//	 * @throws Exception
//	 */
//	String getValue(com.javalego.model.resources.locale.Locale[] locales, String locale, Object... params);
//
//	/**
//	 * Obtener el atributo description de la lista de idiomas enumerados.
//	 * 
//	 * @param locales
//	 * @param locale
//	 * @param params
//	 * @throws Exception
//	 */
//	String getDescription(com.javalego.model.resources.locale.Locale[] locales, String locale, Object... params);

	/**
	 * Obtener el locale por defecto
	 * 
	 * @return
	 */
	Locale getDefaultLocale();

}
