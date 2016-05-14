package com.javalego.ui.mvp.editor.rules.impl;

import com.javalego.ui.mvp.editor.IEditorPresenter;
import com.javalego.ui.mvp.editor.rules.IEditionRules;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener;

/**
 * Listener de cambio de datos de una propiedad.
 * 
 * @author ROBERTO RANZ
 *
 */
public class EditionRulesListener implements IEditionRulesListener.ValueChangeEvent {

	private static final long serialVersionUID = -7808999117386357246L;

	private IEditorPresenter presenter;
	
	private String fieldName;

	public EditionRulesListener(IEditorPresenter presenter, String fieldName) {
		this.presenter = presenter;
		this.fieldName = fieldName;
	}
	
	@Override
	public Object getOldValue() {
		return presenter.getOldValue(fieldName);
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public Object getValue() {
		return presenter.getValue(fieldName);
	}

	@Override
	public void discard() {
		presenter.discard(fieldName);
	}

	@Override
	public IEditionRules getEditorRules() {
		return presenter instanceof IEditionRules ? (IEditionRules)presenter : null;
	}

}
