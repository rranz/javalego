package com.javalego.model.locales;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos para su uso de mensajes al usuario
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum LocaleWarnings implements Key {

	@Languages(locales = { 
			@Locale(value = "Debe seleccionar un registro"),
			@Locale(locale = "en", value = "Please select a record")
			})
	SELECT_RECORD,

	@Languages(locales = { 
			@Locale(value = "¿Borrar registro?"),
			@Locale(locale = "en", value = "Delete record?")
			})
	DELETE,
	
	@Languages(locales = { 
			@Locale(value = "Opción no disponible"),
			@Locale(locale = "en", value = "Disabled option")
			})
	DISABLED_OPTION,
	
	@Languages(locales = { 
			@Locale(value = "Registro eliminado"),
			@Locale(locale = "en", value = "Deleted record")
			})
	REMOVED_RECORD,
	
	@Languages(locales = { 
			@Locale(value = "Dirección de correo incorrecta."),
			@Locale(locale = "en", value = "Invalid email")
			})
	EMAIL,
	
	@Languages(locales = { 
			@Locale(value = "Valor numérico incorrecto."),
			@Locale(locale = "en", value = "Invalid number value")
			})
	NUMERIC,
	
	@Languages(locales = { 
			@Locale(value = "Error"),
			@Locale(locale = "en", value = "Error")
			})
	ERROR,
	
	@Languages(locales = { 
			@Locale(value = "Aviso"),
			@Locale(locale = "en", value = "Warning")
			})
	WARNING,
	
	@Languages(locales = { 
			@Locale(value = "Información"),
			@Locale(locale = "en", value = "Information")
			})
	INFORMATION, 
	
	@Languages(locales = { 
			@Locale(value = "Registro grabado"),
			@Locale(locale = "en", value = "Saved record")
			})
	SAVED, 
	
	@Languages(locales = { 
			@Locale(value = "Registro eliminado"),
			@Locale(locale = "en", value = "Removed record")
			})
	REMOVED, 
	
}
