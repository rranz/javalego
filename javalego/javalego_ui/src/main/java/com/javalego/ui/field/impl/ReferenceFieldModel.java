package com.javalego.ui.field.impl;


/**
 * Obtener la configuración de un campo de otra Entidad a modo de referencia. El
 * tipo de campo es indiferente y sólo se pueden sobreescribir alguna de las
 * propieaddes que son comunes a todos los campos.
 * 
 * @author ROBERTO RANZ
 */
public class ReferenceFieldModel extends AbstractFieldModel {

	private static final long serialVersionUID = 7639611051958307409L;

	private String viewName;

	private String fieldName;

	public ReferenceFieldModel() {
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
