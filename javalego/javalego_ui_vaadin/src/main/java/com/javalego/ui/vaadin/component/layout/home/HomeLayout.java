package com.javalego.ui.vaadin.component.layout.home;

import com.javalego.security.SecurityContext;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

/**
 * Layout que representa la pantalla principal de una aplicación que incluye una
 * cabecera, un menú y un layout del contenido del menú seleccionado.
 * 
 * @author ROBERTO RANZ
 *
 */
public class HomeLayout extends VerticalLayout {

	private static final long serialVersionUID = -1932967859247285597L;

	/**
	 * Layout de cabecera que incluye la opción de selección del menú si no hay
	 * espacio para incluirlo.
	 */
	private HeaderLayout header;

	/**
	 * Sección del menú de opciones
	 */
	private MenuLayout menu;

	/**
	 * Título
	 */
	private String title;

	/**
	 * Component del menú
	 */
	private Component menuContent;

	/**
	 * Componente que incluye la lista de opciones de la cabecera.
	 */
	private Component headerOptions;

	/**
	 * Constructor
	 * 
	 * @param title
	 *            Título representativo de la pantalla.
	 * @param menuContent
	 *            Menú
	 * @param headerOptions
	 *            Options de la cabecera como notificaciones, ...
	 */
	public HomeLayout(String title, Component menuContent, Component headerOptions) {

		this.title = title;

		this.menuContent = menuContent;

		this.headerOptions = headerOptions;

		init();
	}

	/**
	 * Construir layout
	 */
	@SuppressWarnings("serial")
	private void init() {

		header = new HeaderLayout(title, headerOptions);

		menu = new MenuLayout();

		addComponents(header, menu);

		setSizeFull();

		menu.addMenu(menuContent);

		header.getMenuButton().addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -1316974100086699316L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					menu.switchVisibleMenu();
				}
			}
		});

		setExpandRatio(menu, 1);

		// Controlar la redimensión de la pantalla para adaptar el menú
		Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				switchMenu();
			}
		});

		switchMenu();
	}

	/**
	 * Adaptar la visualización del menú en función del tamaño del dispositivo o
	 * pantalla.
	 */
	public void switchMenu() {

		if (!menu.isExpanded() && !SecurityContext.getCurrent().getUserSession().isLargeScreen()) {
			menu.switchMenu();
		}
		else if (menu.isExpanded() && SecurityContext.getCurrent().getUserSession().isLargeScreen()) {
			menu.switchMenu();
		}
	}

	/**
	 * Obtiene el container que representa el menú en el homelayout.
	 * 
	 * @return
	 */
	public ComponentContainer getContentContainer() {

		return menu != null ? menu.getContentContainer() : null;
	}

	/**
	 * Actualizar layout al pulsar una opción de menú. Si el menú está expandido
	 * o 100% hay que ocultarlo.
	 */
	public void update() {
		menu.update();
	}

}
