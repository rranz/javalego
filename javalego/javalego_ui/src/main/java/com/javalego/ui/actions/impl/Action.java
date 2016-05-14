package com.javalego.ui.actions.impl;

import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IAction;
import com.javalego.ui.options.Option;

/**
 * Definición de una acción que podemos utilizar en diferentes contextos. 
 * Acciones de un editor de beans, acciones de un campo en edición, ...
 * 
 * @author ROBERTO RANZ
 *
 */
public class Action extends Option implements IAction {

	private static final long serialVersionUID = -654585546403430152L;

	public Action(Key languages, Colors color, Icon icon) {
		super(languages, color, icon);
	}

	public Action(Key title, Key description, Colors color) {
		super(title, description, color);
	}

	public Action(Key title, Key description, Icon icon) {
		super(title, description, icon);
	}

	public Action(Key description, Icon icon) {
		super(null, description, icon);
	}

	public Action(Icon icon) {
		super(icon);
	}
}
