package com.javalego.ui.vaadin.view.editor.validators;

import com.javalego.ui.actions.ISelectActionEditor;
import com.javalego.ui.editor.data.IValueDataEditor;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.vaadin.data.Validator;

/**
 * Validador de un dato del editor que tiene asociada una acción de selección de
 * valores y su método de validación dentro del flujo de edición.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class SelectDataEditorValidator implements Validator {

	private static final long serialVersionUID = -4555007189263348168L;

	private IValueDataEditor dataEditor;
	
	private IEditorViewListener listener;

	private ISelectActionEditor action;

	public SelectDataEditorValidator(ISelectActionEditor action, IEditorViewListener listener, IValueDataEditor dataEditor) {
		this.dataEditor = dataEditor;
		this.listener = listener;
		this.action = action;
	}
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		try {
			// Validar el nuevo valor definido
			action.validate(listener, dataEditor, listener.getCurrentValue(dataEditor.getName()), value);
		}
		catch (Exception e) {
			throw new InvalidValueException(e.getMessage());
		}
	}

}
