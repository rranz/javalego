package com.javalego.ui.vaadin.component.util;

import java.io.File;
import java.util.Iterator;

import com.vaadin.data.util.TextFileProperty;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * Utilidades relativas a componentes.
 * @author ROBERTO RANZ
 */
public class ComponentUtil {

	/**
	 * Realizar el foco en el primer campo editable (recursividad).
	 * @param container
	 */
	public static void firstFieldFocus(AbstractComponentContainer container) {
		
		if (container == null)
			return;

		Iterator<Component> iterator = container.iterator();
		while (iterator.hasNext()) {
			Component c = iterator.next();
			if (c instanceof AbstractComponentContainer) {
				firstFieldFocus((AbstractComponentContainer)c);
			}
			else if (c instanceof AbstractField && !c.isReadOnly() && c.isEnabled()) {
				((AbstractField<?>)c).focus();
				break;
			}
		}
	}
	
	/**
	 * Establecer la propiedad visible en todos los componentes del container.
	 * @param container
	 */
	public static void setVisible(AbstractComponentContainer container, boolean visible) {
		
		if (container == null)
			return;

		Iterator<Component> iterator = container.iterator();
		while (iterator.hasNext()) {
			Component c = iterator.next();
			c.setVisible(visible);
		}
	}
	
	/**
	 * Mostrar el contenido de un fichero en un Label.
	 * @param fileName
	 * @return
	 */
	public static Label getFileContent(String fileName) {
		
//		FilesystemContainer f = new FilesystemContainer(new File("d:\\gana"));
//		Select s = new Select(null, f);
		Label l = new Label("", ContentMode.TEXT);
		
		l.setPropertyDataSource(new TextFileProperty(new File(fileName)));
		return l;
	}
	
	/**
	 * Obtiene la ventana donde está incluido un componente.
	 * @param component
	 * @return
	 */
	public static Window getWindow(Component component) {
	
		component = component.getParent();
		while (component != null) {
			if (component instanceof Window) {
				return (Window)component;
			}
			component = component.getParent();
		}
		return null;
	}
	
}
