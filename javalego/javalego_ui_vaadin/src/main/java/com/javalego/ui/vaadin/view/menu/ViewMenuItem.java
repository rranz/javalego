package com.javalego.ui.vaadin.view.menu;

import com.javalego.ui.menu.MenuItem;
import com.vaadin.navigator.View;

/**
 * Extensión de IMenuItem para añadir la vista de navegador para Vaadin. Esta
 * vista asociada al menú nos permitrá configurar el Navigator de forma
 * automática evitando generar la vista de forma dinámica en base al valor del
 * nombre del item de menú.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ViewMenuItem extends MenuItem {

	private static final long serialVersionUID = 6548181619756094807L;

	/**
	 * Vista Navigator Vaadin.
	 */
	private View view;

	/**
	 * Constructor
	 * @param view Vista Navigator Vaadin.
	 */
	public ViewMenuItem(View view) {
		this.view = view;
	}

	/**
	 * Vista Navigator Vaadin.
	 * @return
	 */
	public View getView() {
		return view;
	}
}
