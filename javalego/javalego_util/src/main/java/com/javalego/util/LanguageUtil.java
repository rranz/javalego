package com.javalego.util;

import java.util.Locale;

/**
 * Funciones relativas a los lenguajes.
 * @author ROBERTO RANZ
 */
public class LanguageUtil {

	/**
	 * Buscamos el código ISO de un lenguaje (idioma + "_" + país) en la lista que proporciona Java.
	 * @param ISOLanguage
	 * @return
	 */
	public static Locale getLocale(String ISOLanguage) {
		
		// Idioma de la solución.
		for(Locale item : Locale.getAvailableLocales()) {
			if (item.toString().equals(ISOLanguage)) {
				return item;
			}
		}
		return null;
	}
	
}
