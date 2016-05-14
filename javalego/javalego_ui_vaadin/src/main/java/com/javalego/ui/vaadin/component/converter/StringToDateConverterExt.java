package com.javalego.ui.vaadin.component.converter;

import java.util.Date;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToDateConverter;

/**
 * Conversor de valores de campos BigDecimal para parsear los errores de conversión. 
 * @author ROBERTO RANZ
 *
 */
public class StringToDateConverterExt extends StringToDateConverter {

	private static final long serialVersionUID = -2763875763382344875L;

	@Override
	public Date convertToModel(String value, Class<? extends Date> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		return convertToModel(value, targetType, locale);
	}

	@Override
	public String convertToPresentation(Date value, Class<? extends String> targetType, Locale locale) throws com.vaadin.data.util.converter.Converter.ConversionException {
		return this.convertToPresentation(value, targetType, locale);
	}

}
