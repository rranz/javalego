package com.javalego.ui.vaadin.component.formatter;

import com.vaadin.ui.Component;

/**
 * Interface Property que sirve para incluir métodos que deben compartir las clases POJOProperty y BasePropertyFormatter
 * para que puedan gestionarse en la edición de campos de ObjectEditorFactory.
 * @author ROBERTO RANZ
 */
public interface IBaseField {

	/**
	 * Asigna el componente visual para poder obtener información del contexto de edición.
	 * @param component
	 */
	void setComponent(Component component);

}
