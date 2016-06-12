package com.javalego.exception;

import com.javalego.model.keys.ErrorKey;
import com.javalego.model.resources.locale.Locale;

/**
 * Errores comunes de acceso a datos.
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum DataErrors implements ErrorKey {

	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "Entidad inexistente"),
			@Locale(locale = "en", value = "Entity not found")
			})
	ENTITY_NOT_FOUND,

}
