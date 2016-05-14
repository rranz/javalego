package com.javalego.model.locales;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos para su uso de acciones
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum LocaleActions implements Key {

	@Languages(locales = { 
			@Locale(value = "Informe Excel"),
			@Locale(locale = "en", value = "Excel Report")
			})
	EXCEL,

	@Languages(locales = { 
			@Locale(value = "Exportar datos a XML"),
			@Locale(locale = "en", value = "XML Export")
			})
	EXPORT_XML,
	
	@Languages(locales = { 
			@Locale(value = "Exportar datos a JSON"),
			@Locale(locale = "en", value = "JSON Export")
			})
	EXPORT_JSON,
	
}
