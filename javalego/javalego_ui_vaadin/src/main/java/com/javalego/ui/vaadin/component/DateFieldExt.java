package com.javalego.ui.vaadin.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Extensión de DateField para establecer por defecto su resolución a sólo fecha.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class DateFieldExt extends DateField {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public DateFieldExt() {
		initialize();
	}

	/**
	 * Constructor
	 * 
	 * @param caption
	 */
	public DateFieldExt(String caption) {

		this.setCaption(caption);
		initialize();
	}

	private void initialize() {

		setResolution(Resolution.DAY);
		addStyleName(ValoTheme.TEXTFIELD_SMALL);
	}

	/**
	 * Obtener el valor en formato texto
	 * 
	 * @param sql
	 * @return
	 */
	public String getText(boolean sql) {

		if (sql) {
			return getValue() != null ? new SimpleDateFormat().format(getValue()) : "";
			//return getValue() != null ? BusinessService.getCurrent().getSQLDateValue(getValue()) : "";
		}
		else {
			return getValue() != null ? new SimpleDateFormat().format(getValue()) : "";
		}
	}

	/**
	 * Fecha en edición.
	 * 
	 * @return
	 */
	public Date getDate() {

		return getValue() instanceof Date ? (Date) getValue() : null;
	}

}
