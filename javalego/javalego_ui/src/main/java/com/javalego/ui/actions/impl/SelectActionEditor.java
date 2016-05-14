package com.javalego.ui.actions.impl;

import com.javalego.model.keys.Icon;
import com.javalego.ui.actions.ISelectActionEditor;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.icons.enums.IconEditor;

/**
 * Definición de una acción ejecutada dentro del contexto de un editor.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class SelectActionEditor extends Action implements ISelectActionEditor {

	private static final long serialVersionUID = 8348242291593772083L;

	private IDataFieldModel fieldModel;
	
	public SelectActionEditor(Icon icon) {
		super(icon);
	}

	public SelectActionEditor() {
		super(IconEditor.SEARCH_SMALL);
	}


	@Override
	public IDataFieldModel getFieldModel() {
		return fieldModel;
	}

	public void setFieldModel(IDataFieldModel fieldModel) {
		this.fieldModel = fieldModel;
	}
}
