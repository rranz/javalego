package com.javalego.ui.mvp.editor.actions;

import java.util.Collection;

import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.patterns.IModel;

/**
 * Lista de acciones
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IEditorActionsModel extends IModel {
	
	Collection<IActionEditor> getActions();

}
