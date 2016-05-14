package com.javalego.ui.vaadin.component.rating;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos utilizado para definir los textos del modelo de datos de los editores (Ej.: EditorEmpresas, ...)
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum LocaleRating implements Key {

	@Languages(locales = { 
			@Locale(value = "Calificación", description="Valor entre 1 y 5"),
			@Locale(locale = "en", value = "Rating", description="Value between 1 and 5")
			})
	RATING, 
	
	@Languages(locales = { 
			@Locale(value = "Debe seleccionar al menos una estrella."),
			@Locale(locale = "en", value = "You must select at least one star.")
			})
	RATING_ERROR, 

	@Languages(locales = { 
			@Locale(value = "Horrible"),
			@Locale(locale = "en", value = "Awful")
			})
	AWFUL, 

	@Languages(locales = { 
			@Locale(value = "No es tan bueno"),
			@Locale(locale = "en", value = "Not that good")
			})
	NOT_THAT_GOOD, 

	@Languages(locales = { 
			@Locale(value = "Bueno"),
			@Locale(locale = "en", value = "OK")
			})
	OK, 

	@Languages(locales = { 
			@Locale(value = "Estupendo"),
			@Locale(locale = "en", value = "Great")
			})
	GREAT, 

	@Languages(locales = { 
			@Locale(value = "Excelente"),
			@Locale(locale = "en", value = "Excellent")
			})
	EXCELLENT; 

}
