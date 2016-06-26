package com.javalego.errors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.javalego.model.resources.locale.Locale;

/**
 * Tipología de errores.
 * 
 * @author ROBERTO RANZ
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface Error
{
	/**
	 * Nivel de excepción: Warning, info, error, ...
	 */
	ErrorLevel level();

	/**
	 * Idiomas soportados
	 */
	Locale[] locales();

	/**
	 * Número de secuencia dentro del nivel excepción (opcional)
	 */
	int index() default -1;

}
