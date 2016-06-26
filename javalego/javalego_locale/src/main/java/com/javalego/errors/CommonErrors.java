package com.javalego.errors;

import com.javalego.model.keys.ErrorKey;
import com.javalego.model.resources.locale.Locale;

/**
 * Errores de uso común usados por las aplicaciones.
 * <p>
 * Incluye la localización en dos idiomas: español e inglés.
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum CommonErrors implements ErrorKey, ErrorData {

	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "Existen errores en la edición ({0}). Revisar datos"),
			@Locale(locale = "en", value = "There are errors in editing ({0}). Review Data")
			})
	ERRORS,

	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "El campo {0} debe tener un valor"),
			@Locale(locale = "en", value = "Field {0} required")
			})
	REQUIRED_FIELD,

	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "Valor del campo {0} incorrecto"),
			@Locale(locale = "en", value = "Field {0} invalid value")
	})	
	INVALID_FIELD, 
	
	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "Datos de usuario no válidos"),
			@Locale(locale = "en", value = "Login failed")
			})
	FAILED_LOGIN, 
	
	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "No existen datos del perfil de usuario"),
			@Locale(locale = "en", value = "No profile data")
			})
	NO_PROFILE_DATA,
	
	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Valor del campo inexistente"),
			@Locale(locale = "en", value = "Field value not found")
			})
	KEY_NOT_FOUND,
	
	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Error al inicializar la aplicación. Consultar con el departamento técnico"),
			@Locale(locale = "en", value = "Error start application.")
			})
	APPLICATION_ERROR,
	
	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Error conectando con la base de datos. Consultar con el departamento técnico"),
			@Locale(locale = "en", value = "Error connection database.")
			})
	DATABASE_ERROR,
	
	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "El usuario ya existe."),
			@Locale(locale = "en", value = "User exist.")
			})
	USER_EXISTS, 
	
	@Error(level = ErrorLevel.WARN, 
	locales={
			@Locale(value = "Las contraseñas no coinciden."),
			@Locale(locale = "en", value = "Distinct passwords.")
			})
	DISTINCT_PASSWORDS,
	
	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Icono {0} inexistente."),
			@Locale(locale = "en", value = "Not found icon {0}.")
			})
	NOT_FOUND_ICON,
	
	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Clase {0} no es un enumerado."),
			@Locale(locale = "en", value = "Class {0} is not enum.")
			})
	NOT_ENUM;
	
	@Override
	public String code()
	{
		return new ErrorManager(this).code();
	}

	@Override
	public String prefixLevel()
	{
		return new ErrorManager(this).prefixLevel();
	}

	@Override
	public String level()
	{
		return new ErrorManager(this).level();
	}

	@Override
	public String message(Object... parameters)
	{
		return message(null, parameters);
	}

	@Override
	public String message(java.util.Locale locale, Object... parameters)
	{
		return new ErrorManager(this).message(locale, parameters);
	}


}
