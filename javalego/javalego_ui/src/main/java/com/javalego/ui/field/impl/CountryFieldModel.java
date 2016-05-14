package com.javalego.ui.field.impl;

import java.util.Locale;

import com.javalego.model.keys.Key;
import com.javalego.util.StringUtils;

/**
 * Campo de selección de un país.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class CountryFieldModel extends EnumFieldModel {

	private static final long serialVersionUID = 1L;

	public CountryFieldModel() {
		init();
	}

	public CountryFieldModel(String name, Key title) {
		super(name, title);
		init();
	}

	/**
	 * Inicializar la lista de países.
	 */
	private void init() {
		
		String[] locales = Locale.getISOCountries();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			addItem(obj.getDisplayCountry(), obj.getCountry());
		}		
		StringUtils.sortList(items, "title");
	}

	public CountryFieldModel(String name, Key title, Key description) {
		super(name, title, description);
		init();
	}

	
}
