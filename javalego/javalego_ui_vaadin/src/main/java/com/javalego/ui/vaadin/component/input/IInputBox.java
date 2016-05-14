/**
 * 
 */
package com.javalego.ui.vaadin.component.input;

import java.util.Collection;
import java.util.List;

import com.javalego.ui.field.FieldModel;
import com.vaadin.ui.Component;

/**
 * Interface para definir el comportamiento de un componente de edición de valores mediante componentes
 * adaptados al tipo de valor.
 * @author ROBERTO RANZ
 */
public interface IInputBox {

	/**
	 * Obtiene la lista de propiedades a editar.
	 * @return
	 */
	List<InputProperty> getProperties();
	
	/**
	 * Establece la lista de propiedades a editar.
	 * @param properties
	 */
	void setProperties(List<InputProperty> properties);
	
	/**
	 * Añadir una propiedad InputProperty
	 * @param property
	 */
	void addProperty(InputProperty property);
	
	/**
	 * Obtiene el layout generado en base a las propiedades definidas.
	 * @return
	 */
	Component getComponent();

	/**
	 * Añadir una propiedad a la lista de propiedades a editar.
	 * @param property 
	 * @param value
	 * @return 
	 */
	InputProperty addProperty(FieldModel property, Object value);
	
	/**
	 * Añadir una propiedad Enum (lista de valores) a la lista de propiedades a editar. 
	 * @param property
	 * @param value
	 * @param values
	 * @return
	 */
	InputProperty addProperty(FieldModel property, Object value, Collection<?> values);
		
	/**
	 * Título a mostrar en la edición para describir la entrada de datos.
	 * @return
	 */
	String getTitle();

	/**
	 * Incluir margen en el layout que contiene a los componentes.
	 * @param margin
	 */
	void setMargin(boolean margin);

}
