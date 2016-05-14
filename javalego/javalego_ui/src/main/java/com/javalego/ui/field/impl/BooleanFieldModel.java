package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;
import com.javalego.ui.field.FieldModel;

/**
 * Propieadad que repesenta un valor boolean.
 * @author ROBERTOR RANZ
 */
public class BooleanFieldModel extends AbstractFieldModel {
	
	private static final long serialVersionUID = 7786087050801233255L;

	/**
	 * Alineación del component asociado a la propiedad.
	 */
	private String labelAlignment = FieldModel.ALIGNMENT_LEFT;
	
	/**
	 * Usar un componente de tipo radioButtons para mostrar las opciones sí y no. La propiedad que representa el campo debe 
	 * ser Boolean en lugar de boolean para poder registrar valores = null cuando el usuario no selecciona ninguna opción.
	 */
	private boolean radioButtons;
	
	public BooleanFieldModel() {}

	/**
	 * Constructor
	 * @param name
	 * @param title
	 */
	public BooleanFieldModel(String name, Key title) {
		super(name, title);
	}

	/**
	 * Constructor
	 * @param name
	 * @param title
	 * @param description
	 */
	public BooleanFieldModel(String name, Key title, Key description) {
		super(name, title);
	}
	
	public boolean isLeftAlignment() {
		return labelAlignment.equals(FieldModel.ALIGNMENT_LEFT);
	}

	public boolean isRightAlignment() {
		return labelAlignment.equals(FieldModel.ALIGNMENT_RIGHT);
	}

	public String getLabelAlignment() {
		return labelAlignment;
	}

	public void setLabelAlignment(String labelAlignment) {
		this.labelAlignment = labelAlignment;
	}

	public boolean isRadioButtons() {
		return radioButtons;
	}

	public void setRadioButtons(boolean radioButtons) {
		this.radioButtons = radioButtons;
	}

}
