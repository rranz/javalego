package com.javalego.ui.mvp.editor.actions;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.mvp.editor.actions.IEditorActionsView.IEditorActionsListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter para la visualización de las acciones 
 * 
 * @author ROBERTO RANZ
 *
 */
public class EditorActionsPresenter implements IPresenter, IEditorActionsListener {

	private IEditorActionsModel model;
	
	private IEditorActionsView view;

	public EditorActionsPresenter(IEditorActionsModel model, IEditorActionsView view) {
		this.model = model;
		this.view = view;
		view.setListener(this);
	}
	
	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public IEditorActionsView getView() {
		return view;
	}

	public IEditorActionsModel getModel() {
		return model;
	}

	@Override
	public Collection<IActionEditor> getActions() {
		return model.getActions();
	}

}
