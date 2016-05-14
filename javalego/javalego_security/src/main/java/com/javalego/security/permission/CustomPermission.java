package com.javalego.security.permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface CustomPermission {

	/**
	 * Clase que implementa la validación del permiso a un recurso de la aplicación.
	 * @return
	 */
	Class<? extends Permission> type();
}
