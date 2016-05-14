package com.javalego.xml;

import java.io.Serializable;

import org.w3c.dom.Element;

/**
 * Clase básica que representa un nodo de una documento Xml e implementa la
 * reflexión para asignar sus valores definidos en los atributos del nodo.
 * 
 * @author ROBERTO RANZ
 */
public abstract class BasicXmlBean implements IBasicXmlBean, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Carga los valores de los atributos en las propiedades de la clase
	 * mediante reflection.
	 * 
	 * @param element
	 * @throws Exception
	 */
	@Override
	public void loadElement(Element element) throws Exception {
		XmlBeanReflection.pasteAttributes(this, element, false);
	}

	/**
	 * Carga los valores de los atributos en las propiedades de la clase
	 * mediante reflection.
	 * 
	 * @param element
	 * @param lowercase
	 *            buscar la propiedad por su nombre lowercase. Ej.: attributo:
	 *            defaultvalue y property: defaultValue.
	 * @throws Exception
	 */
	public void loadElement(Element element, boolean lowercase) throws Exception {
		XmlBeanReflection.pasteAttributes(this, element, lowercase);
	}

}
