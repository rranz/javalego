package com.javalego.model.resources;

import java.util.HashMap;
import java.util.Locale;

import com.javalego.model.AbstractBaseModel;
import com.javalego.util.LanguageUtil;

/**
 * Idioma 
 *  
 * @author ROBERTO RANZ
 */
public class Language extends AbstractBaseModel {

	private static final long serialVersionUID = 5807332746373882180L;

	/**
	 * Código del lenguaje 
	 */
	private String code;
	
	/**
	 * Lista de códigos
	 */
	private HashMap<String, String> codes = new HashMap<String, String>();
	
	/**
	 * Constructor
	 * @param code
	 * @param codes 
	 */
	public Language(String code, HashMap<String, String> codes) {
		this.code = code;
		this.codes = codes;
	}
	
	/**
	 * Constructor
	 * @param code
	 */
	public Language(String code) {
		this.code = code;
	}
	
	/**
	 * Constructor que establece el idioma por defecto del sistema.
	 */
	public Language() {
		this.code = Locale.getDefault().getLanguage();
	}
	
	@Override
	public String toString() {
		
		Locale locale = LanguageUtil.getLocale(code);
		
		// Idioma de la solución.
		if (locale != null)
			return locale.getDisplayLanguage() + " - " + locale.getDisplayCountry();
		else
			return "(code invalid)";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Códigos
	 * @return
	 */
	public HashMap<String, String> getCodes() {
		return codes;
	}

	public void setCodes(HashMap<String, String> codes) {
		this.codes = codes;
	}

	/**
	 * Busca el valor de un código.
	 * @param key
	 * @return
	 */
	public String getCode(String key) {
		return codes.get(key);
	}

	/**
	 * Obtiene el locale java.util a partir del código definido en language.
	 * @return
	 */
	public java.util.Locale getLocale() {
		return new java.util.Locale(code);
	}

}
