package com.javalego.ui.vaadin.component.button;

import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.server.Resource;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Extensión de Button para simplificar su configuración a partir de sus constructores.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ButtonExt extends com.vaadin.ui.Button {

	private static final long serialVersionUID = -5654127077873538793L;

	public ButtonExt(String caption, String description) {
		super(caption);
		setDescription(description);
	}
	
	public ButtonExt(String caption) {
		super(caption);
	}
	
	public ButtonExt(String caption, Resource resource) {
		super(caption, resource);
	}

	public ButtonExt(String caption, String description, Resource resource) {
		super(caption, resource);
		setDescription(description);
	}

	/**
	 * Constructor
	 * @param caption
	 * @param icon
	 */
	public ButtonExt(Key caption, Icon icon) {
		this(caption, null, icon);
	}
	
	/**
	 * Constructor
	 * @param caption
	 * @param icon
	 */
	public ButtonExt(String caption, Icon icon) {
		super(caption);
		if (icon != null) {
			setIcon(ResourceIconsVaadin.getCurrent().getResource(icon));
		}
	}
	
	/**
	 * Constructor
	 * @param caption
	 */
	public ButtonExt(Key caption) {
		this(caption, null, null);
	}
	
	/**
	 * Constructor
	 * @param caption
	 * @param description
	 * @param icon
	 */
	public ButtonExt(Key caption, Key description, Icon icon) {
		
		super(caption != null ? UIContext.getText(caption) : null);
		
		if (description != null) {
			setDescription(UIContext.getText(description));
		}
		
		if (icon != null) {
			setIcon(ResourceIconsVaadin.getCurrent().getResource(icon));
		}
	}
	
	/**
	 * Establecer style como primary blue
	 * @return
	 */
	public ButtonExt blue() {
		addStyleName(ValoTheme.BUTTON_PRIMARY);
		return this;
	}
	
	
	/**
	 * Establecer style como primary color
	 * @return
	 */
	public ButtonExt primaryColor() {
		addStyleName(ValoTheme.BUTTON_PRIMARY);
		return this;
	}	

	/**
	 * Establecer color rojo
	 * @return
	 */
	public ButtonExt red() {
		addStyleName(ValoTheme.BUTTON_DANGER);
		return this;
	}

	/**
	 * Establecer color verde.
	 * @return
	 */
	public ButtonExt green() {
		addStyleName(ValoTheme.BUTTON_FRIENDLY);
		return this;
	}

	/**
	 * Establecer borderless
	 * @return
	 */
	public ButtonExt bordeless() {
		addStyleName(ValoTheme.BUTTON_BORDERLESS);
		return this;
	}	

	/**
	 * Establecer borderless colored
	 * @return
	 */
	public ButtonExt bordelessColored() {
		addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		return this;
	}	

	/**
	 * Establecer estilo Link
	 * @return
	 */
	public ButtonExt link() {
		addStyleName(ValoTheme.BUTTON_LINK);
		return this;
	}

	public ButtonExt large() {
		addStyleName(ValoTheme.BUTTON_LARGE);
		return this;
	}	
	
	public ButtonExt huge() {
		addStyleName(ValoTheme.BUTTON_HUGE);
		return this;
	}

	public ButtonExt friendlyColor() {
		addStyleName(ValoTheme.BUTTON_FRIENDLY);
		return this;
	}	
	
}
