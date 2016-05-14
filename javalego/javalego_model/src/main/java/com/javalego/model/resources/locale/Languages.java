package com.javalego.model.resources.locale;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Localización de un texto en varios idiomas.
 * 
 * @author ROBERTO RANZ
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Languages
{
	/**
	 * Traducción en varios idiomas
	 * @return
	 */
	Locale[] locales();	
	
}
