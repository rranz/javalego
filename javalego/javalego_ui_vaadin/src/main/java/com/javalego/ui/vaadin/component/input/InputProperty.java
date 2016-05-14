/**
 * 
 */
package com.javalego.ui.vaadin.component.input;

import java.io.Serializable;

import com.javalego.ui.field.FieldModel;
import com.vaadin.data.util.ObjectProperty;

/**
 * Clase que define una propiedad para editar un objeto que utiliza su configuración para la edición de un valor.
 * 
 * @author ROBERTO RANZ
 */
public class InputProperty implements Serializable {

	private static final long serialVersionUID = -531674645045880001L;

	/**
	 * Definición de una propiedad para reutilizar en la generación del componente visual
	 */
	private FieldModel property;

	/**
	 * Valor de entrada antes de la edición.
	 */
	private Object value;

	/**
	 * Binding al valor de la propiedad para su edición.
	 */
	private ObjectProperty<?> objectProperty;

	/**
	 * @param property2
	 */
	public InputProperty(FieldModel property, Object value) {
		this.property = property;
		this.value = value;
	}

	/**
	 * @return the property
	 */
	public FieldModel getProperty() {
		return property;
	}

	/**
	 * @param property
	 *          the property to set
	 */
	public void setProperty(FieldModel property) {
		this.property = property;
	}

	/**
	 * Obtener el valor antes de la edición.
	 * 
	 * @return
	 */
	public Object getOriginalValue() {
		return value;
	}

	/**
	 * @return the Value
	 */
	public Object getValue() {

		if (objectProperty != null)
			return objectProperty.getValue();
		else
			return value;
	}

	/**
	 * @param Value
	 *          the Value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the objectProperty
	 */
	public ObjectProperty<?> getObjectProperty() {
		return objectProperty;
	}

	/**
	 * @param objectProperty
	 *          the objectProperty to set
	 */
	public void setObjectProperty(ObjectProperty<?> objectProperty) {
		this.objectProperty = objectProperty;
	}

}
