package com.javalego.xml;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.javalego.util.StringUtils;


/**
 * Clase que nos permite asignar los attributos de un elemento
 * de un documento Xml a un objeto mediante reflection.
 * 
 * Ejemplo:
 * <table name="Difusión">
 * Objeto: Table con la propiedad name y el método setName(String name).
 * 
 * @author ROBERTO RANZ
 */
public final class XmlBeanReflection {

	/**
	 * Clase que nos permite la gestión de reflexion en java (commons-bean.jar).
	 */
	static private PropertyUtilsBean b = new PropertyUtilsBean();
	
	/**
	 * Crea una clase mediante Reflection.
	 * @param className
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public Object createClass(String className) throws Exception{
    //returns the Class object associated with the class given by the string classAttr
    Class classType = Class.forName(className);
    //returns a Constructor object that reflects the specified public constructor of the class given by the Class object
    Constructor constructor = classType.getConstructor(new Class[]{});
    return constructor.newInstance(new Object[]{});
	}

	/**
	 * Cargar los valores de los atributos en las propiedades
	 * del objeto mediante el acceso a los métodos de asignación. get().
	 * @param object
	 * @param element
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element) throws Exception {
		pasteAttributes(object, element, false);
	}
	/**
	 * Cargar los valores de los atributos en las propiedades
	 * del objeto mediante el acceso a los métodos de asignación. get().
	 * @param object
	 * @param element
	 * @param lowercase buscar la propiedad por su nombre lowercase. Ej.: attributo: defaultvalue y property: defaultValue.
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element, boolean lowercase) throws Exception {
		pasteAttributes(object, element, lowercase, false, null);
	}
	/**
	 * Cargar los valores de los atributos en las propiedades
	 * del objeto mediante el acceso a los métodos de asignación. get().
	 * @param object
	 * @param element
	 * @param lowercase buscar la propiedad por su nombre lowercase. Ej.: attributo: defaultvalue y property: defaultValue.
	 * @param excludeAttributes
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element, boolean lowercase, String excludeAttributes) throws Exception {
		pasteAttributes(object, element, lowercase, false, excludeAttributes);
	}
	/**
	 * Cargar los valores de los atributos en las propiedades
	 * del objeto mediante el acceso a los métodos de asignación. get().
	 * @param object
	 * @param element
	 * @param lowercase buscar la propiedad por su nombre lowercase. Ej.: attributo: defaultvalue y property: defaultValue.
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element, boolean lowercase, boolean ignoreErrors, String excludeAttributes) throws Exception {
		
		if(object == null) return;
		
		if(element == null) return;

		// Atributos a excluir
		String[] excludes = excludeAttributes != null ? excludeAttributes.split("\\|") : null;
		
		for(int i = 0; i < element.getAttributes().getLength(); i++){
		
			// Atributo a buscar
			Attr attribute = (Attr)element.getAttributes().item(i);
			
			// Nombre de la propiedad a buscar.
			String attrName = attribute.getNodeName();
			
			if (excludes != null && StringUtils.existInArrayString(excludes, attrName))
				continue;
			
			if (lowercase)
				attrName = getPropertyNameLowerCase(object, attrName);
			
			// Comprobar si se puede acceder a la propiedad, o sea, ver si tiene el método getPropertyName().
			if (b.isReadable(object, attrName)){
			
				// Obtener el valor del atributo del nodo al tipo de propiedad.
				Object value = ConvertUtils.convert(attribute.getValue(), b.getPropertyType(object, attrName));
				// establecer el valor de la propiedad.
				if (!ignoreErrors)
					BeanUtils.setProperty(object, attrName, value);
				else{
					try{
						BeanUtils.setProperty(object, attrName, value);
					}
					catch(Exception e){
					}
				}
			  //b.setProperty(object, attrName, value);
			}
		}
	}

	/**
	 * Obtiene el nombre de la propiedad de un objeto buscando por un nombre definido en lowercase.
	 * @param object
	 * @param propertyName
	 * @return
	 */
	static private String getPropertyNameLowerCase(Object object, String propertyName){
		String name = propertyName;
		PropertyDescriptor[] items = b.getPropertyDescriptors(object.getClass());
		for(int i = 0; i <  items.length; i++){
			if (items[i].getName().toLowerCase().equals(propertyName))
				return items[i].getName();
		}
		return name;
	}

}
