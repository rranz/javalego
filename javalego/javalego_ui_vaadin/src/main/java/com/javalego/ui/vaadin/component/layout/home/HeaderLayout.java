package com.javalego.ui.vaadin.component.layout.home;

import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.css.ResponsiveCss;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

/**
 * Cabecera de un Layout principal
 * 
 * @see HomeLayout
 * @author ROBERTO RANZ
 */
public class HeaderLayout extends HorizontalLayout implements ResponsiveCss {

	private static final long serialVersionUID = -4042932993534358555L;

	private Label lblTitle;
	
	private CssButton menuButton;

	/**
	 * Constructor
	 * @param title
	 * @param headerOptions
	 */
	public HeaderLayout(String title, Component headerOptions) {

		//addStyleName(TOGGLEDISPLAY);

		addStyleName("background-indigo-800");
		addStyleName(CssVaadin.getShadowBottom());
		//addStyleName("valo-menu-title");

		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		// Botón para activar el menú si no existe espacio.
		menuButton = new CssButton(IconEditor.IC_DRAWER);
		menuButton.addCssIcDrawer();
		//menuButton.addStyleName(NOTENOUGHSPACE);
		
		addComponent(menuButton);

		// Título
		lblTitle = new Label(title, ContentMode.HTML);
		lblTitle.setSizeUndefined();
		addComponent(lblTitle);

		setSpacing(true);
		//setMargin(true);

		// Opciones de la cabecera
		if (headerOptions != null) {
			addComponent(headerOptions);
		}

		setExpandRatio(lblTitle, 1);

		// Aplicar estilos Responsive definidos en meccanoj.css
		Responsive.makeResponsive(this);

		setWidth(FULL);
	}

	/**
	 * Botón para activar el menú.
	 * @return
	 */
	public CssButton getMenuButton() {
		return menuButton;
	}

}
