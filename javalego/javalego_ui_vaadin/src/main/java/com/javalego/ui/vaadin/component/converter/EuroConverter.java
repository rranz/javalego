package com.javalego.ui.vaadin.component.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToBigDecimalConverter;

/**
 * A converter that adds/removes the euro sign and formats currencies with two
 * decimal places.
 */
public class EuroConverter extends StringToBigDecimalConverter {

	private static final long serialVersionUID = -1091103936455012538L;

	@Override
    public BigDecimal convertToModel(String value,
            Class<? extends BigDecimal> targetType, Locale locale)
                    throws com.vaadin.data.util.converter.Converter.ConversionException {
        value = value.replaceAll("[â‚¬\\s]", "").trim();
        if ("".equals(value)) {
            value = "0";
        }
        return super.convertToModel(value, targetType, locale);
    }

    @Override
    protected NumberFormat getFormat(Locale locale) {
        // Always display currency with two decimals
        NumberFormat format = super.getFormat(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setMaximumFractionDigits(2);
            ((DecimalFormat) format).setMinimumFractionDigits(2);
        }
        return format;
    }

    @Override
    public String convertToPresentation(BigDecimal value,
            Class<? extends String> targetType, Locale locale)
                    throws com.vaadin.data.util.converter.Converter.ConversionException {
        return super.convertToPresentation(value, targetType, locale) + " â‚¬";
    }
}