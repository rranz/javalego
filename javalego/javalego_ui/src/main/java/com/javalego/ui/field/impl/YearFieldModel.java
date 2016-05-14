package com.javalego.ui.field.impl;


/**
 * Propiedad cuyo valor representa un año y sus propiedades se configurarán para
 * este tipo de valor en lugar de utilizar un campo integerField.
 * 
 * @author ROBERTO RANZ
 */
public class YearFieldModel extends IntegerFieldModel {

	private static final long serialVersionUID = -3846858501765561422L;

	public YearFieldModel() {
		size = 4;
		minValue = 1980.0;
		maxValue = 2100.0;
		setZeroAsBlank(true);
		setGroupingUsed(false);
	}

}
