package com.javalego.ui.vaadin.component.layout.home;

import com.javalego.ui.vaadin.css.ResponsiveCss;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * Sección del menú y contenido del item de menú seleccionado.
 * 
 * @see HomeLayout
 * 
 * @author ROBERTO RANZ
 *
 */
public class MenuLayout extends HorizontalLayout implements ResponsiveCss {

	private static final String MINWIDTH = "minwidth";

	private static final long serialVersionUID = -4222437361555138058L;

	private CssLayout contentArea = new CssLayout();
	private CssLayout menuArea = new CssLayout();
	
	private boolean expandedMenu;

	public MenuLayout() {

		setSizeFull();

		//addStyleName(TOGGLEDISPLAY);
		
		contentArea.addStyleName(SCROLLABLE);
		contentArea.setSizeFull();

		menuArea.addStyleName(SCROLLABLE);
		//menuArea.addStyleName(ENOUGHSPACE);
		menuArea.addStyleName(MINWIDTH);
		menuArea.addStyleName(HIDE_SCROLL_X);
		
		addComponents(menuArea, contentArea);

		setExpandRatio(contentArea, 1);

		Responsive.makeResponsive(this);
	}

	/**
	 * Conmutar la visibilidad del menú en función de su estado actual.
	 */
	public void switchVisibleMenu() {
		menuArea.setVisible(!menuArea.isVisible());
	}
	
	public void hideMenu() {
		menuArea.setWidth("-1px");
	}
	
	public ComponentContainer getContentContainer() {
		return contentArea;
	}

	public void addMenu(Component menu) {
		menuArea.setHeight(FULL);
		menuArea.addComponent(menu);
	}

	/**
	 * Conmutar la visibilidad del área de menú.
	 */
	public void switchMenu() {
		if (menuArea != null) {
			if (!expandedMenu/*menuArea.getStyleName().contains(ENOUGHSPACE)*/) {
				expandMenu();
			} else {
				collapseMenu();
			}
		}
	}

	/**
	 * Volver a su posición el menú tras se maximizado cuando no el botón de
	 * menú se encuentra visible por falta de espacio en pantalla.
	 */
	private void collapseMenu() {

//		contentArea.removeStyleName(ENOUGHSPACE);
//		menuArea.addStyleName(ENOUGHSPACE);

		setExpandRatio(menuArea, 0);
		setExpandRatio(contentArea, 1);
		
		hideMenu();
		
		//menuArea.removeStyleName("maxwidth");
		//menuArea.addStyleName("minwidth");
		
		expandedMenu = false;
	}

	/**
	 * Comprueba si el menú está expandido al ancho de la pantalla.
	 * @return
	 */
	public boolean isExpanded() {
		return expandedMenu;
	}
	
	/**
	 * Maximizar el menú cuando se pulsa el botón de menú que se encuentra
	 * visible por falta de espacio en pantalla.
	 */
	private void expandMenu() {
		
//		contentArea.addStyleName(ENOUGHSPACE);
//		menuArea.removeStyleName(ENOUGHSPACE);

		//menuArea.removeStyleName("minwidth");
		//menuArea.addStyleName("maxwidth");

		setExpandRatio(contentArea, 0);
		setExpandRatio(menuArea, 1);

		menuArea.setWidth("100%");
		
		expandedMenu = true;
	}

	/**
	 * Actualizar layout al pulsar una opción de menú. Dependiendo de si está
	 * activo en menú en 100% hay que ocultarlo.
	 */
	public void update() {
		if (expandedMenu) {
			menuArea.setVisible(false);
		}

	}

}
