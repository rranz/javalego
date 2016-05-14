package com.javalego.ui.field.impl;

/**
 * Propiedades que deben implementar las propiedades de tipo lista de valores (EnumProperty, StateProperty, ...)
 * @author ROBERTO RANZ
 *
 */
public interface IListValuesFieldModel {

	/**
	 * Lista de valores
	 * @return
	 */
	Object[] getListValues();
	
	/**
	 * Lista de etiquetas por cada valor
	 * @return
	 */
	String[] getListLabels();
	
	/**
	 * Lista de colores por cada valor.
	 * @return
	 */
	String[] getListColors();	
}
