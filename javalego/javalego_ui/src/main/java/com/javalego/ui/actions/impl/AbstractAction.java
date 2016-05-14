package com.javalego.ui.actions.impl;

import com.javalego.model.AbstractBaseModel;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.ui.actions.IAction;

/**
 * Clase Abstract de una acción asociada a un servicio de negocio.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class AbstractAction extends AbstractBaseModel implements IAction {

	private static final long serialVersionUID = 30644816644213597L;

	protected Icon icon;
	
	protected Colors color;

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public Colors getColor() {
		return color;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public void setColor(Colors color) {
		this.color = color;
	}

}
