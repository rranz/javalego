package com.javalego.ui.actions.impl;

import com.javalego.model.keys.Icon;
import com.javalego.ui.actions.IActionEditor;


/**
 * Definición de una acción ejecutada dentro del contexto de un editor.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class AbstractActionEditor extends Action implements IActionEditor {

	private static final long serialVersionUID = 5275127877999725667L;

	public AbstractActionEditor(Icon icon) {
		super(icon);
	}

}
