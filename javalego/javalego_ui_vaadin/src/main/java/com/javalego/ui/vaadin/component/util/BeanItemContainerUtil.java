package com.javalego.ui.vaadin.component.util;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.exception.JavaLegoException;
import com.javalego.util.ReflectionUtils;
import com.vaadin.data.util.BeanItemContainer;

/**
 * Utilidades relacionadas con la clase BeanItemContainer y los campos nested
 * que son necesarios para una correcta visualización de los beans ya que no las
 * propiedades de objetos relacionados (Ej.: Usuario.persona no puede ser null
 * si utilizamos el campo persona.nombre por ejemplo).
 * 
 * @author ROBERTO RANZ
 */
public class BeanItemContainerUtil {

	private BeanItemContainerUtil() {
	}

	/**
	 * Añadir propiedades de entidades relacionadas al contenedor Bean (de lo contrario se producirá un error en la ejecución).
	 * @param container
	 * @param fieldNames
	 */
	public static void addNestedProperties(BeanItemContainer<?> container, String[] fieldNames) {

		for (String fieldName : fieldNames) {
			if (fieldName.indexOf(".") > 0) {
				container.addNestedContainerProperty(fieldName);
			}
		}
	}
	
	/**
	 * Instanciar los objetos de las propiedades nested de objeto relacionados (Ej.: Usuario.persona (Persona)) si persona = null y 
	 * hay una propiedad persona.nombre se generará un error al intentar obtener el valor de la propiedad.
	 * @param object
	 * @throws Exception
	 */
	public static void createNestedProperties(Collection<?> objects, String[] fieldNames) throws LocalizedException {
		
		if (objects != null)
			for(Object object : objects) {
				createNestedProperties(object, fieldNames);
			}
	}
	
	/**
	 * Instanciar los objetos de las propiedades nested de objeto relacionados (Ej.: Usuario.persona (Persona)) si persona = null y 
	 * hay una propiedad persona.nombre se generará un error al intentar obtener el valor de la propiedad.
	 * @param object
	 * @throws Exception
	 */
	public static void createNestedProperties(Object object, String[] fieldNames) throws LocalizedException {
		
		for(String fieldName : fieldNames) {
			if (fieldName.indexOf(".") > 0) {
				try {
					ReflectionUtils.generateClassProperty(object, fieldName);
				}
				catch (JavaLegoException e) {
					new LocalizedException(e);
				}
			}
		}
	}	

}
