package com.javalego.ui.vaadin.component.button;

import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.component.button.css.ICssButtonCss;
import com.javalego.ui.vaadin.component.button.css.TabButtonCss;

/**
 * 
 * Botón utilizado como Tab en MenuTabs.
 * 
 * @author ROBERTO RANZ
 *
 */
public class TabButton extends CssButton {

	private static final long serialVersionUID = 5747041950303754987L;

	public TabButton(String title, String description, Colors color, Icon icon) {
		super(title, description, color, icon);
	}

	public TabButton(String title, Colors color, Icon icon) {
		super(title, null, color, icon);
	}	
	
	public TabButton(String title, String description, Icon icon) {
		super(title, description, null, icon);
	}	
	
	public TabButton(String title, String description) {
		super(title, description);
	}	
	
	public TabButton(String title, Colors color) {
		super(title, color);
	}	
	
	public TabButton(String title, String description, Colors color) {
		super(title, description, color);
	}	
	
	public TabButton(String title, Icon icon) {
		super(title, icon);
	}	
	
	public TabButton(String caption, String description, Colors color, Icon icon, boolean colorLabel, boolean shadow) {
		super(caption, description, color, icon, colorLabel, shadow);
	}
	
	public TabButton(String caption, String description, Colors color, boolean shadow) {
		super(caption, description, color, null, true, shadow);
	}
	
	public TabButton(String caption, Colors color, boolean shadow) {
		super(caption, null, color, null, true, shadow);
	}
	
	
	@Override
	public ICssButtonCss getCss() {
		if (css == null) {
			css = new TabButtonCss();
		}
		return css;
	}
	
	
}
