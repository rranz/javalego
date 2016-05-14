package com.javalego.poi.report.elements;


/**
 * Campo asociado a un Grupo de un informe
 */
public class ReportGroupField {

	private ReportField field;

	private Object value = null;

	/**
	 * Separador utilizado en la concetenación de campos de la cabecera del grupo.
	 */
	private String separator;

	public ReportGroupField(ReportField field, boolean hideField) {

		this.field = field;

		// Ocultar el campo en el informe porque no tiene sentido visualizar dos veces el campo.
		if (hideField)
			field.setVisible(false);
	}

	public ReportField getField() {
		return field;
	}

	public void setField(ReportField field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

}
