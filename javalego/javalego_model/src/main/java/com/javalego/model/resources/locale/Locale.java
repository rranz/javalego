package com.javalego.model.resources.locale;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Localización de un texto en un idioma determinado.
 * 
 * @author ROBERTO RANZ
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Locale
{
	/**
	 * Idioma
	 * @return
	 */
	String locale() default "";
	
	/**
	 * Valor del código
	 */
	String value();	
	
	/**
	 * Descripción 
	 * @return
	 */
	String description() default "";
	
}
