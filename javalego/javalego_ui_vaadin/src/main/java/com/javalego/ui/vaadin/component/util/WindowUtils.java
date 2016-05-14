package com.javalego.ui.vaadin.component.util;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Utilidades relativas a la clase Window de Vaadin
 * 
 * @author ROBERTO RANZ
 */
public class WindowUtils {

	private WindowUtils() {
	}

	/**
	 * Crear una ventana con un componente y añadirla a la ventana owner.
	 * 
	 * @param owner
	 * @param title
	 * @param component
	 * @param maxWidth
	 * @param maxHeight
	 */
	public static Window addWindow(String title, Component component, int maxWidth, int maxHeight) {

		final Window window = new Window(title);

		window.setContent((ComponentContainer) component);

		window.setSizeUndefined();
		window.setWidth(maxWidth, Unit.PERCENTAGE);
		window.setHeight(maxHeight, Unit.PERCENTAGE);

		window.center();

		UI.getCurrent().addWindow(window);

		return window;
	}

	/**
	 * Crear una ventana con un componente y añadirla a la ventana owner.
	 * 
	 * @param owner
	 * @param title
	 * @param component
	 */
	public static Window addWindow(String title, Component component) {

		final Window window = new Window(title);

		VerticalLayout c = (VerticalLayout) window.getContent();
		c.setSizeUndefined();
		c.setSpacing(false);
		c.setMargin(true);

		window.setContent(component);

		window.center();

		UI.getCurrent().addWindow(window);

		return window;
	}
	
	/**
	 * Mostrar ventana con un texto de ayuda.
	 * @param owner
	 * @param text
	 */
	public static void showHelp(String text) {
		
		Window w = new Window();
		w.setCaption("Help");
		
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		Label label = new Label(text, ContentMode.HTML);
		l.setSizeUndefined();
		l.setWidth("400px");
		l.addComponent(label);
		
		w.setModal(true);
		w.setContent(l);
		
		w.setVisible(true);
		
		UI.getCurrent().addWindow(w);
	}

}
