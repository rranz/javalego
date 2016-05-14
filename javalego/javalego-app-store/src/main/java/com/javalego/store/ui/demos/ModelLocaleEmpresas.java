package com.javalego.store.ui.demos;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos utilizado para definir los textos del modelo de datos de los editores (Ej.: EditorEmpresas, ...)
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum ModelLocaleEmpresas implements Key {

	@Languages(locales = { 
			@Locale(value = "Nombre"),
			@Locale(locale = "en", value = "Name")
			})
	NAME,

	@Languages(locales = { 
			@Locale(value = "Descripción"),
			@Locale(locale = "en", value = "Description")
			})
	DESCRIPTION,	
	
	@Languages(locales = { 
			@Locale(value = "Razón social"),
			@Locale(locale = "en", value = "Company name")
			})
	RAZONSOCIAL,	
	
	@Languages(locales = { 
			@Locale(value = "Domicilio"),
			@Locale(locale = "en", value = "Address")
			})
	DIRECCION,	
	
	@Languages(locales = { 
			@Locale(value = "Código postal"),
			@Locale(locale = "en", value = "Zip code")
			})
	CODIGOPOSTAL,	
	
	@Languages(locales = { 
			@Locale(value = "País"),
			@Locale(locale = "en", value = "Country")
			})
	PAIS,	
	
	@Languages(locales = { 
			@Locale(value = "Localidad"),
			@Locale(locale = "en", value = "City")
			})
	LOCALIDAD,	
	
	@Languages(locales = { 
			@Locale(value = "Provincia"),
			@Locale(locale = "en", value = "State")
			})
	PROVINCIA,	
	
	@Languages(locales = { 
			@Locale(value = "Representante"),
			@Locale(locale = "en", value = "Agent")
			})
	REPRESENTANTE,	
	
	@Languages(locales = { 
			@Locale(value = "Fecha creación"),
			@Locale(locale = "en", value = "Created date")
			})
	FECHACREACION,	

	@Languages(locales = { 
			@Locale(value = "Empresas"),
			@Locale(locale = "en", value = "Companies")
			})
	EMPRESAS,	
		
	@Languages(locales = { 
			@Locale(value = "Empresa"),
			@Locale(locale = "en", value = "Company")
			})
	EMPRESA,	
		
	@Languages(locales = { 
			@Locale(value = "Contactos"),
			@Locale(locale = "en", value = "Contacts")
			})
	CONTACTOS,	
		
	@Languages(locales = { 
			@Locale(value = "Contacto"),
			@Locale(locale = "en", value = "Contact")
			})
	CONTACTO,	
		
	
}
