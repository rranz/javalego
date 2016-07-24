package com.javalego.data;

import com.javalego.errors.Error;
import com.javalego.errors.ErrorData;
import com.javalego.errors.ErrorLevel;
import com.javalego.errors.ErrorManager;
import com.javalego.model.keys.ErrorKey;
import com.javalego.model.resources.locale.Locale;

/**
 * Errores comunes de acceso a datos.
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum DataProviderErrors implements ErrorKey, ErrorData {

	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Entidad inexistente"),
			@Locale(locale = "en", value = "Entity not found")
			})
	ENTITY_NOT_FOUND,

	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Error al realizar la persistencia de la entidad"),
			@Locale(locale = "en", value = "Error persist entity")
			})
	PERSIST,

	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Error al eliminar una entidad"),
			@Locale(locale = "en", value = "Error delete entity")
			})
	DELETE,

	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Error al actualizar el contenido de una entidad"),
			@Locale(locale = "en", value = "Error merge entity")
			})
	MERGE,

	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Error al localizar una entidad por su identificador"),
			@Locale(locale = "en", value = "Error find entity por id")
			})
	FIND_ID,

	@Error(level = ErrorLevel.ERROR, 
	locales={
			@Locale(value = "Error al realizar la búsqueda de entidades."),
			@Locale(locale = "en", value = "Error find entities")
			})
	FIND;


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
