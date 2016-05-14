package com.javalego.ui.mvp.editor.actions;

import java.util.Collection;

import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.patterns.IView;

/**
 * Vista que contiene los componentes UI de cada acción.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IEditorActionsView extends IView {

	void setListener(IEditorActionsListener listener);
	
	public interface IEditorActionsListener {
		Collection<IActionEditor> getActions();
	}
	
}
