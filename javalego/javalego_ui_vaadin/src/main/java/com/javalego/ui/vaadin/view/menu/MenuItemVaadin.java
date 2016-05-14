package com.javalego.ui.vaadin.view.menu;

import com.javalego.ui.menu.MenuItem;
import com.vaadin.server.Resource;

/**
 * Menú item para Vaadin que incluye la posibilidad de incluir un icono a partir de un recurso.
 * 
 * @author ROBERTO RANZ
 *
 */
public class MenuItemVaadin extends MenuItem {

	private static final long serialVersionUID = -7029197838795167513L;

	private Resource resourceIcon;

	public MenuItemVaadin(String html) {
		super(html);
	}

	public Resource getResourceIcon() {
		return resourceIcon;
	}
	
	public void setResourceIcon(Resource resource) {
		this.resourceIcon = resource;
	}
	
}
