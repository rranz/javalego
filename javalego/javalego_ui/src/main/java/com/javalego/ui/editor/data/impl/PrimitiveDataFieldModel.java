package com.javalego.ui.editor.data.impl;

import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.field.FieldModel;

/**
 * Dato basado en una configuración de campo
 * 
 * @author ROBERTO RANZ
 *
 */
public class PrimitiveDataFieldModel extends PrimitiveDataEditor implements IDataFieldModel {

	private FieldModel fieldModel;
	
	public PrimitiveDataFieldModel(FieldModel fieldModel, Object value) {
		super(fieldModel.getName(), fieldModel.getTitle(), value);
		this.fieldModel = fieldModel;
	}

	public PrimitiveDataFieldModel(FieldModel fieldModel) {
		super(fieldModel.getName(), fieldModel.getTitle(), null);
		this.fieldModel = fieldModel;
	}

	@Override
	public FieldModel getFieldModel() {
		return fieldModel;
	}

	public void setFieldModel(FieldModel fieldModel) {
		this.fieldModel = fieldModel;
	}

	@Override
	public String getName() {
		return fieldModel.getName();
	}

	@Override
	public Key getTitle() {
		return fieldModel.getTitle();
	}

	@Override
	public IActionEditor[] getActions() {
		return null;
	}

}
