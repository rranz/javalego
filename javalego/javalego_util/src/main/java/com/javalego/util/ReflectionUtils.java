package com.javalego.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.w3c.dom.Element;

import com.javalego.exception.JavaLegoException;
import com.javalego.xml.XmlBeanReflection;

/**
 * Funciones de ámbito general utilizando Reflection.
 * 
 * @author ROBERTO RANZ
 * 
 */
public final class ReflectionUtils {

	/**
	 * Clase que nos permite la gestión de reflexion en java (commons-bean.jar).
	 * NOTA: se elimina para poder utilizar esta clase en Android y sus
	 * limitaciones de JVM.
	 */
	// static public final PropertyUtilsBean bean = new PropertyUtilsBean();

	/**
	 * Establecer los atributos definidos en el parámetro element sobre el
	 * objeto pasado como parámetro.
	 * 
	 * @param object
	 * @param element
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element) throws Exception {

		XmlBeanReflection.pasteAttributes(object, element, true);
	}

	/**
	 * Establecer los atributos definidos en el parámetro element sobre el
	 * objeto pasado como parámetro.
	 * 
	 * @param object
	 * @param element
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element, boolean ignoreErrors) throws Exception {

		XmlBeanReflection.pasteAttributes(object, element, true, ignoreErrors, null);
	}

	/**
	 * Establecer los atributos definidos en el parámetro element sobre el
	 * objeto pasado como parámetro.
	 * 
	 * @param object
	 * @param element
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element, String excludeAttributes) throws Exception {

		XmlBeanReflection.pasteAttributes(object, element, true, excludeAttributes);
	}

	/**
	 * Establecer los atributos definidos en el parámetro element sobre el
	 * objeto pasado como parámetro.
	 * 
	 * @param object
	 * @param element
	 * @param ignerErrors
	 * @param excludeAttributes
	 * @throws Exception
	 */
	static public void pasteAttributes(Object object, Element element, boolean ignoreErrors, String excludeAttributes) throws Exception {

		XmlBeanReflection.pasteAttributes(object, element, true, ignoreErrors, excludeAttributes);
	}

	/**
	 * Pega el valor de los campos declarados de un objeto en un Element de un
	 * documento DOM.
	 * 
	 * @param element
	 * @param object
	 */
	static public void pasteAttributesToElement(Element element, Object object) {

		Field[] fields = ReflectionUtils.getDeclaredFields(object);

		for (int i = 0; i < fields.length; i++) {
			try {
				Object value = ReflectionUtils.getPropertyValue(object, fields[i].getName());
				if (value != null && (value instanceof String || value instanceof Number || value instanceof Boolean)) {
					if (value instanceof Number && ((Number) value).intValue() < 1)
						;
					else
						element.setAttribute(fields[i].getName(), value.toString());
				}
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 * Crear un objeto mediante una Clase.
	 * 
	 * @param objectClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T createObject(Class<?> objectClass) throws JavaLegoException {

		if (objectClass != null) {
			try {
				return (T) ConstructorUtils.invokeConstructor(objectClass, new Object[] {});
			}
			catch (Exception e) {
				throw new JavaLegoException(e.getMessage());
			}
		}
		else {
			throw new JavaLegoException("CLASS IS NULL");
		}
	}

	/**
	 * Crear un objeto mediante un nombre de clase.
	 * 
	 * @param className
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T createObject(String className) throws JavaLegoException {
		try {
			return (T) ConstructorUtils.invokeConstructor(Class.forName(className), new Object[] {});
		}
		catch (Exception e) {
			if (e instanceof NoSuchMethodException)
				throw new JavaLegoException("No es posible crear la clase '" + className + "' porque no tiene un constructor válido.", JavaLegoException.ERROR);
			else
				throw new JavaLegoException("No es posible crear la clase '" + className + "'. Clase inexistente.", JavaLegoException.ERROR, e);
		}
	}

	/**
	 * Crear un objeto mediante un nombre de clase.
	 * 
	 * @param className
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T createObject(String className, Object[] args) throws Exception {
		try {
			return (T) ConstructorUtils.invokeConstructor(Class.forName(className), args);
		}
		catch (Exception e) {
			throw new JavaLegoException("No es posible crear la clase '" + className + "'.", JavaLegoException.ERROR, e);
		}
	}

	/**
	 * Crear un objeto mediante un nombre de clase.
	 * 
	 * @param className
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T createObject(Class<?> _class, Object[] args) throws Exception {
		try {
			return (T) ConstructorUtils.invokeConstructor(_class, args);
		}
		catch (Exception e) {
			throw new JavaLegoException("No es posible crear la clase '" + _class.getCanonicalName() + "'.", JavaLegoException.ERROR, e);
		}
	}

	/**
	 * Crear un objeto mediante una Clase y una lista de propiedades y valores.
	 * 
	 * @param objectClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T createObject(Class<?> objectClass, HashMap<String, Object> properties) throws Exception {

		if (objectClass != null) {

			try {
				Object object = ConstructorUtils.invokeConstructor(objectClass, new Object[] {});

				Set<String> list = properties.keySet();

				for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
					ReflectionUtils.setPropertyValue(object, iterator.toString(), properties.get(iterator.toString()));
				}
				return (T) object;
			}
			catch (Exception e) {
				throw new JavaLegoException("No es posible crear la clase '" + objectClass + "'.", JavaLegoException.ERROR, e);
			}
		}
		else
			throw new Exception("CLASS IS NULL");
	}

	/**
	 * Invocar a un método de una clase.
	 * 
	 * @param objectClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T invokeMethod(String className, String methodName) throws Exception {

		Object object = createObject(className);

		return (T) MethodUtils.invokeMethod(object, methodName, null);
	}

	/**
	 * Invocar a un método de una clase.
	 * 
	 * @param objectClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T invokeMethod(Object object, String methodName) throws Exception {

		try {
			return (T) MethodUtils.invokeMethod(object, methodName, null);
		}
		catch (Exception e) {
			if (e.getCause() != null) {
				if (e instanceof JavaLegoException)
					throw e;
				else {
					e.printStackTrace();
					throw new JavaLegoException(e.getCause().getMessage(), JavaLegoException.ERROR);
				}
			}
			else
				throw e;
		}
	}

	/**
	 * Invocar a un método de una clase.
	 * 
	 * @param objectClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T invokeMethod(Class<?> _class, String methodName) throws Exception {

		Object object = createObject(_class);
		try {
			return (T) MethodUtils.invokeMethod(object, methodName, null);
		}
		catch (Exception e) {
			if (e.getCause() != null)
				throw new JavaLegoException(e.getCause().getMessage(), JavaLegoException.ERROR);
			else
				throw e;
		}
	}

	/**
	 * Invocar a un método de una clase.
	 * 
	 * @param objectClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T invokeMethod(Class<?> _class, String methodName, Object... parameters) throws Exception {

		Object object = createObject(_class);
		try {
			return (T) MethodUtils.invokeMethod(object, methodName, parameters != null && parameters.length == 1 ? parameters[0] : parameters);
		}
		catch (Exception e) {
			if (e.getCause() != null)
				throw new JavaLegoException(e.getCause().getMessage(), JavaLegoException.ERROR);
			else
				throw e;
		}
	}

	/**
	 * Invocar a un método de una clase con parámetros.
	 * 
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T invokeMethod(Object object, String methodName, Object[] parameters) throws Exception {

		try {
			return (T) MethodUtils.invokeMethod(object, methodName, parameters != null && parameters.length == 1 ? parameters[0] : parameters);
		}
		catch (Exception e) {
			if (e.getCause() != null)
				throw new JavaLegoException(e.getCause().getMessage(), JavaLegoException.ERROR);
			else
				throw e;
		}
	}

	/**
	 * Invocar a un método de una clase con parámetros.
	 * 
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T invokeMethod2(Object object, String methodName, Object[] parameters) throws Exception {

		Method[] allMethods = object.getClass().getDeclaredMethods();

		for (Method m : allMethods) {

			if (methodName.equals(m.getName())) {

				boolean accessible = m.isAccessible();

				m.setAccessible(true);

				try {
					return (T) m.invoke(object, parameters);

				}
				catch (Exception e) {
					if (e.getCause() != null)
						throw new JavaLegoException(e.getCause().getMessage(), JavaLegoException.ERROR);
					else
						throw e;
				}
				finally {
					m.setAccessible(accessible);
				}
			}
		}
		return null;
	}

	/**
	 * Invocar a un método de una clase con parámetros.
	 * 
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @param types
	 *            (tipo de parámetros para que si alguno contiene un valor null
	 *            se pueda ejecutar el método).
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T invokeMethod(Object object, String methodName, Object[] parameters, Class<?>[] types) throws Exception {
		
		try {
			return (T) MethodUtils.invokeMethod(object, methodName, parameters, types);
		}
		catch (Exception e) {
			if (e instanceof InvocationTargetException && ((InvocationTargetException) e).getTargetException() instanceof JavaLegoException) {
				throw new JavaLegoException(((InvocationTargetException) e).getTargetException().getMessage(), ((JavaLegoException) ((InvocationTargetException) e).getTargetException()).getType());
			}
			if (e.getCause() != null)
				throw new JavaLegoException(e.getCause().getMessage(), JavaLegoException.ERROR);
			else
				throw e;
		}
	}

	/**
	 * Obtiene el valor de una propiedad de un objeto.
	 * 
	 * @param object
	 * @param propertyName
	 * @param ignoreException
	 *            ignorar excepción cuando la propiedad no exista en el objeto.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T getPropertyValue(Object object, String propertyName, boolean ignoreException) throws JavaLegoException {

		if (propertyName == null)
			return (T) object;

		// Crear el objeto si accedemos a una propiedad de un objeto que es
		// propiedad el objeto pasado como parámetro.
		if (propertyName.indexOf(".") > -1) {
			generateClassProperty(object, propertyName);
		}

		Object value = null;
		try {
			value = getProperty(object, propertyName);
		}
		catch (Exception e) {
			if (!ignoreException) {
				throw new JavaLegoException(propertyName + " not exist", JavaLegoException.ERROR);
			}
		}
		return (T) value;
	}

	/**
	 * Obtener el valor de una propiedad de un objeto utilizando Reflection
	 * básico.
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws JavaLegoException
	 */
	private static Object getProperty(Object object, String propertyName) throws JavaLegoException {

		if (object == null || propertyName == null) {
			return null;
		}

		Field field = getField(object.getClass(), propertyName);
		if (field != null) {
			try {
				// Buscar el objeto nested para obtener el valor. En caso
				// contrario, se generará error ya que el objeto del parámetro
				// no contiene el field.
				if (propertyName.indexOf(".") > 0) {
					String name = propertyName;
					while (name.indexOf(".") > -1) {
						// Primera propiedad.
						int pos = name.indexOf(".");
						// Siguiente propiedad "."
						Field f = getField(object.getClass(), name.substring(0, pos));
						f.setAccessible(true);
						object = f.get(object);
						name = name.substring(name.indexOf(".") + 1);
					}
				}

				field.setAccessible(true);
				return field.get(object);
			}
			catch (IllegalArgumentException e) {
				throw new JavaLegoException(propertyName + " not exist", e);
			}
			catch (IllegalAccessException e) {
				throw new JavaLegoException(propertyName + " not exist", e);
			}
		}
		else {
			return null;
		}
	}

	/**
	 * Establecer el valor de una propiedad de un objeto utilizando Reflection
	 * básico.
	 * 
	 * @param object
	 * @param propertyName
	 * @throws JavaLegoException
	 */
	private static void setProperty(Object object, String propertyName, Object value) throws JavaLegoException {

		if (object == null || propertyName == null) {
			return;
		}

		Field field = getField(object.getClass(), propertyName);
		if (field != null) {

			// Buscar el campo nested del objeto para asignarle su valor
			// TODO falta probar con campos anidados de los niveles
			// Empresa.plantilla.persona.id
			if (propertyName.indexOf(".") > 0) {
				object = getPropertyValue(object, propertyName.substring(0, propertyName.lastIndexOf(".")));
			}

			field.setAccessible(true);
			try {
				field.set(object, value);
			}
			catch (IllegalArgumentException e) {
				throw new JavaLegoException(propertyName + " not exist", e);
			}
			catch (IllegalAccessException e) {
				throw new JavaLegoException(propertyName + " not exist", e);
			}
		}
	}

	/**
	 * Obtiene el valor de una propiedad de un objeto.
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T getPropertyValue(Object object, String propertyName) throws JavaLegoException {

		return (T) getPropertyValue(object, propertyName, false);
	}

	/**
	 * Obtiene el valor de una propiedad de un objeto SIN INSTANCIAR CLASE (ej.:
	 * pais.nombre).
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T getPropertyValueNotCreateClass(Object object, String propertyName) throws Exception {

		return (T) getPropertyValueNotCreateClass(object, propertyName, true);
	}

	/**
	 * Obtiene el valor de una propiedad de un objeto SIN INSTANCIAR CLASE (ej.:
	 * pais.nombre).
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static public <T> T getPropertyValueNotCreateClass(Object object, String propertyName, boolean hideException) throws Exception {

		Object value = null;
		try {
			value = getProperty(object, propertyName);
		}
		catch (Exception e) {
			if (propertyName.indexOf(".") < 0)
				throw new JavaLegoException(propertyName + " NO VALID", JavaLegoException.ERROR);
			if (!hideException)
				throw e;
		}
		return (T) value;
	}

	/**
	 * Establecer el valor de una propiedad de un objeto.
	 * 
	 * @param object
	 * @param propertyName
	 * @param value
	 * @throws Exception
	 */
	static public void setPropertyValue(Object object, String propertyName, Object value) throws JavaLegoException {

		// Crear el objeto si accedemos a una propiedad de un objeto que es
		// propiedad el objeto pasado como parámetro.
		if (propertyName.indexOf(".") > -1)
			generateClassProperty(object, propertyName);

		try {
			setProperty(object, propertyName, value);
		}
		catch (Exception e) {
			throw new JavaLegoException("ERROR GET VALUE PROPERTY '" + propertyName + "'", e);
		}
	}

	/**
	 * Crear el objeto si accedemos a una propiedad de un objeto que es
	 * propiedad el objeto pasado como parámetro.
	 * 
	 * @param object
	 * @param propertyNames
	 *            Lista de propiedades a crear objeto si = null.
	 */
	static public void generateClassProperties(Object object, String[] propertyNames) throws Exception {

		if (propertyNames == null)
			return;

		for (String propertyName : propertyNames) {
			generateClassProperty(object, propertyName);
		}

	}

	/**
	 * Crear el objeto si accedemos a una propiedad de un objeto que es
	 * propiedad el objeto pasado como parámetro.
	 * 
	 * @param object
	 * @param propertyName
	 */
	static public void generateClassProperty(Object object, String propertyName) throws JavaLegoException {

		if (propertyName.indexOf(".") > -1) {
			generateNestedClassProperty(object, propertyName);
		}
	}

	/**
	 * Crear el objeto si accedemos a una propiedad de un objeto que es
	 * propiedad el objeto pasado como parámetro.
	 * 
	 * @param object
	 * @param propertyName
	 */
	static public void generateNestedClassProperty(Object object, String propertyName) throws JavaLegoException {

		String findName = propertyName;
		int pos = -1;
		while (true) {

			pos = findName.indexOf(".", pos + 1);

			if (pos > -1) {
				String name = findName.substring(0, pos);

				if (getPropertyValue(object, name) == null) {

					setPropertyValue(object, name, createObjectFromField(object.getClass(), name));
				}
			}
			else
				break;
		}
	}

	/**
	 * Clonar un objeto mediante reflexion. Nota: para que este método actúe
	 * correctamente, es necesario implementar todos los métodos set() que fijar
	 * el valor de las propiedades del objeto. En caso contrario, estas
	 * propiedades no serán clonadas. Si la clase no puede instanciarse (ej.:
	 * Double, Float, etc.) el método devuelve el mismo objeto pasado como
	 * parámetro.
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public Object cloneBean(Object object) {

		if (object == null || object instanceof String || object instanceof Number || object instanceof Boolean)
			return object;
		else
			// Si ocurre una excepción debido a un error de instanciación (ej:
			// Double,
			// Float, etc.),
			// devolvemos el objeto pasado como parámetro.
			try {
				if (object instanceof Vector) {
					Object newObject = (Vector) BeanUtils.cloneBean(object);

					for (int i = 0; i < ((Vector) object).size(); i++)
						((Vector) newObject).addElement(cloneBean(((Vector) object).get(i)));

					return newObject;
				}
				else
					return BeanUtils.cloneBean(object);
			}
			catch (Exception e) {
				e.printStackTrace();
				return object;
			}
	}

	/**
	 * Copiar propiedades de un bean a otro mediante reflection.
	 * 
	 * @param object1
	 * @param object2
	 * @throws Exception
	 */
	static public void copyProperties(Object dest, Object origin) throws Exception {
		
		BeanUtils.copyProperties(dest, origin);
	}

	/**
	 * Obtenemos el valor por defecto de un tipo de campo de un objeto.
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 */
	static public Object getDefaultValueProperty(Class<?> _class, String fieldName) {

		if (_class != null) {
			Field f = null;
			try {
				// Propiedades de una propiedad que es una referencia a una
				// clase. (Ej.:
				// banco.id).
				f = ReflectionUtils.getField(_class, fieldName);

				if (f != null) {
					if (f.getType() == String.class)
						return "";
					else if (f.getType() == Integer.class || f.getType() == int.class)
						return 0;
					else if (f.getType() == Float.class || f.getType() == float.class)
						return 0;
					else if (f.getType() == Double.class || f.getType() == double.class)
						return 0;
					else if (f.getType() == Long.class || f.getType() == long.class)
						return 0;
					else if (f.getType() == Date.class)
						return 0;
					else if (f.getType() == Time.class)
						return 0;
					else if (f.getType() == Boolean.class || f.getType() == boolean.class)
						return false;
					else if (f.getType() == byte[].class)
						return 0;
					else
						return "";
				}
			}
			catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Buscar un Field de una clase dentro de sus sus propiedades teniendo en
	 * cuenta propiedades que sean a su vez clases. (recursividad)
	 * 
	 * @param _class
	 * @param name
	 * @return
	 */
	static public Field getField(Class<?> _class, String fieldName) throws JavaLegoException {

		Field f = null;

		String name = fieldName;

		try {
			while (name.indexOf(".") > -1) {

				// Primera propiedad.
				int pos = name.indexOf(".");

				// Siguiente propiedad "."
				int pos2 = name.indexOf(".", pos + 1);

				f = getDeclaredField(_class, name.substring(0, pos));
				if (pos2 > -1) {
					_class = f.getType();
					try {
						f = _class.getDeclaredField(name.substring(pos + 1, pos2));
					}
					catch (Exception e) {
						f = getDeclaredField(_class, name.substring(pos + 1, pos2));
					}
				}
				else
					try {
						f = f.getType().getDeclaredField(name.substring(pos + 1));
					}
					catch (Exception e) {
						f = getDeclaredField(f.getType(), name.substring(pos + 1));
					}

				name = name.substring(name.indexOf(".") + 1);
			}
			
			if (f == null) {
				f = getDeclaredField(_class, name);
			}
			return f;
		}
		catch (Exception e) {

			// Si es una interface, no generar error ya que es normal que no localice el campo porque no puede tenerlo.
			if (f != null && f.getType().isInterface()) {
				return null;
			}
			else {
				throw new JavaLegoException("ERROR REFLECTION GET FIELD '" + name + "'.", e);
			}
		}
	}

	/**
	 * Buscar un Field de una clase dentro de sus sus propiedades teniendo en
	 * cuenta propiedades que sean a su vez clases. (recursividad)
	 * 
	 * @param _class
	 * @param _class2
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	static public Field getField(Class _class, Class _class2) throws Exception {
		for (Field f : _class2.getDeclaredFields()) {
			if (f.getType() == _class) {
				return f;
			}
		}
		return null;
	}

	/**
	 * Obtener la clase que representa un campo en un objeto.
	 * 
	 * @param _class
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	static public Class getClass(Class _class, String fieldName) throws Exception {

		Field field = getField(_class, fieldName);
		if (field != null)
			return field.getType();
		else
			return null;
	}

	/**
	 * Instanciar un objeto de una clase representada por una propiedad en una
	 * clase.
	 * 
	 * @param _class
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	static public <T> T createObjectFromField(Class<?> _class, String fieldName) throws JavaLegoException {

		Field field = getField(_class, fieldName);
		if (field != null)
			return createObject(field.getType());
		else
			return null;
	}

	/**
	 * Buscar un campo declarado en una clase y sino en clase de la que deriva
	 * de forma recursiva.
	 * 
	 * @param _class
	 * @param fieldName
	 * @return
	 */
	static public Field getDeclaredField(Class<?> classRef, String fieldName) throws Exception {
		Class<?> _class = classRef;
		while (_class != Object.class) {
			try {
				Field f = _class.getDeclaredField(fieldName);
				return f;
			}
			catch (Exception e) {
				_class = _class.getSuperclass();
			}
		}
		throw new Exception(fieldName + " " + _class.getName());
	}

	/**
	 * Comprueba si una clase tiene un campo declarado. (El campo puede estar en
	 * sus clases heredadas).
	 * 
	 * @param _class
	 * @param fieldName
	 * @return
	 */
	static public boolean hasDeclaredField(Class<?> classRef, String fieldName) throws Exception {
		
		Class<?> _class = classRef;
		
		while (_class != Object.class) {
			try {
				Field f = _class.getDeclaredField(fieldName);
				return f != null;
			}
			catch (Exception e) {
				_class = _class.getSuperclass();
			}
		}
		return false;
	}

	/**
	 * Buscar un campo declarado en una clase y si no, en la clase de la que
	 * deriva de forma recursiva.
	 * 
	 * @param _class
	 * @param fieldName
	 * @return
	 */
	static public List<Field> getDeclaredFields(Class<?> classRef, String[] fieldNames) throws Exception {

		List<Field> list = new ArrayList<Field>();

		for (String fieldName : fieldNames) {
			if (fieldName != null) {
				list.add(getDeclaredField(classRef, fieldName));
			}
		}

		return list;
	}

	/**
	 * Obtener la lista de campos declarados del objeto y sus superclases hasta
	 * Object.
	 * 
	 * @param object
	 * @return
	 */
	static public Field[] getDeclaredFields(Object object) {

		Class<?> _class = object.getClass();
		
		return getDeclaredFields(_class);
	}

	/**
	 * Obtener la lista de campos declarados del objeto y sus superclases hasta
	 * Object.
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static public Field[] getDeclaredFields(Class<?> _class) {

		List list = new ArrayList();

		while (_class != Object.class) {

			Field[] f = _class.getDeclaredFields();

			for (int i = 0; i < f.length; i++)
				list.add(f[i]);

			if (_class.getSuperclass() != null)
				_class = _class.getSuperclass();
		}
		return (Field[]) list.toArray(new Field[list.size()]);
	}

	/**
	 * Obtener un array de String[] del valor de una propiedad de una lista de
	 * objetos.
	 * 
	 * @param reports
	 * @param string
	 * @return
	 */
	public static String[] getStringsFromProperty(List<?> list, String propertyName) throws Exception {

		if (list == null || propertyName == null)
			return null;

		String[] items = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			Object value = getPropertyValue(list.get(i), propertyName);
			if (value != null)
				items[i] = value.toString();
		}

		return items;
	}

	// /**
	// * Pega el valor de los campos declarados de un objeto en un Element de un
	// documento DOM.
	// * @param element
	// * @param object
	// */
	// static public void pasteAttributes(Element element, Object object){
	//
	// Field[] fields = ReflectionUtils.getDeclaredFields(object);
	//
	// for(int i = 0; i < fields.length; i++){
	// try {
	// Object value = ReflectionUtils.getPropertyValue(object,
	// fields[i].getName());
	// if (value != null && (value instanceof String || value instanceof Number
	// ||
	// value instanceof Boolean)) {
	// if (value instanceof Number && ((Number)value).intValue() < 1)
	// ;
	// else
	// element.setAttribute(fields[i].getName(), value.toString());
	// }
	// }
	// catch(Exception e){
	// }
	// }
	// }

	/**
	 * Obtener una anotación del CAMPO de un objeto, pasando el tipo de
	 * anotación
	 */
	@SuppressWarnings("rawtypes")
	public static Annotation getFieldAnnotation(Object object, String fieldName, Class annotationClass) {

		if (object != null)
			return getFieldAnnotation(object.getClass(), fieldName, annotationClass);
		else
			return null;
	}

	/**
	 * Obtener una anotación del CAMPO de un objeto, pasando el tipo de
	 * anotación
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Annotation getFieldAnnotation(Class<?> _class, String fieldName, Class annotationClass) {

		while (_class != Object.class) {
			try {
				Field f = _class.getDeclaredField(fieldName);
				if (f.getName().equals(fieldName))
					return f.getAnnotation(annotationClass);
			}
			catch (Exception e) {
				_class = _class.getSuperclass();
			}
		}

		return null;
	}

	/**
	 * Obtener una anotación del MÉTODO de un objeto, pasando el tipo de
	 * anotación
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Annotation getMethodAnnotation(Object object, String methodName, Class annotationClass) {

		Class _class = object.getClass();
		while (_class != Object.class) {
			try {
				Method method = _class.getDeclaredMethod(methodName);
				if (method.getName().equals(methodName))
					return method.getAnnotation(annotationClass);
			}
			catch (Exception e) {
				_class = _class.getSuperclass();
			}
		}

		return null;
	}

	/**
	 * Obtener una anotación del MÉTODO de un objeto, pasando el tipo de
	 * anotación
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Annotation getMethodAnnotation(Object object, String methodName, Class annotationClass, Class... parameterTypes) {

		Class _class = object.getClass();
		while (_class != Object.class) {
			try {
				Method method = _class.getDeclaredMethod(methodName, parameterTypes);
				if (method.getName().equals(methodName))
					return method.getAnnotation(annotationClass);
			}
			catch (Exception e) {
				_class = _class.getSuperclass();
			}
		}

		return null;
	}

	/**
	 * Obtener todos los campos de un objeto de forma recursiva
	 * 
	 * @param object
	 * @return
	 */
	public static List<Field> getAllDeclaratedFields(Object object) {

		if (object == null)
			return null;
		else
			return getAllDeclaratedFields(object.getClass());
	}

	/**
	 * Obtener todos las propiedades de tipo LIST (List, ArrayList, Collection,
	 * ...) de una clase de forma recursiva (hasta llegar a Object).
	 * 
	 * @param object
	 * @return
	 */
	public static List<Field> getDeclaratedFields(Class<?> _class, boolean listFields) {

		if (_class == null)
			return null;

		List<Field> list = new ArrayList<Field>();

		while (_class != Object.class) {

			Field[] fields = _class.getDeclaredFields();

			for (Field field : fields) {

				if (listFields && isList(field.getType())) {
					list.add(field);
				}
				else if (!listFields && !isList(field.getType())) {
					list.add(field);
				}
			}

			if (_class.getSuperclass() != null) {
				_class = _class.getSuperclass();
			}
			else {
				break;
			}
		}

		return list;
	}

	/**
	 * Obtener todos las propiedades de una clase de forma recursiva (hasta
	 * llegar a Object).
	 * 
	 * @param object
	 * @return
	 */
	public static List<Field> getAllDeclaratedFields(Class<?> _class) {

		if (_class == null)
			return null;

		List<Field> list = new ArrayList<Field>();

		while (_class != Object.class) {

			Field[] fields = _class.getDeclaredFields();

			for (Field field : fields)
				list.add(field);

			if (_class.getSuperclass() != null)
				_class = _class.getSuperclass();
			else
				break;
		}

		return list;
	}

	@SuppressWarnings("rawtypes")
	public static List<Class> getClasses(String packageName) throws Exception {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		assert classLoader != null;

		String path = packageName.replace('.', '/');

		Enumeration<URL> resources = classLoader.getResources(path);

		List<File> dirs = new ArrayList<File>();

		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}

		List<Class> classes = new ArrayList<Class>();

		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes" })
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {

		List<Class> classes = new ArrayList<Class>();

		if (!directory.exists()) {
			directory = new File(directory.getAbsolutePath().replaceAll("%20", " "));
			if (!directory.exists())
				return classes;
		}
		File[] files = directory.listFiles();

		for (File file : files) {

			String fileName = file.getName();

			if (file.isDirectory()) {
				assert !fileName.contains(".");
				classes.addAll(findClasses(file, packageName + "." + fileName));
			}
			else if (fileName.endsWith(".class") && !fileName.contains("$")) {

				Class _class;
				try {
					_class = Class.forName(packageName + '.' + fileName.substring(0, fileName.length() - 6));
				}
				catch (ExceptionInInitializerError e) {
					// happen, for example, in classes, which depend on
					// Spring to inject some beans, and which fail,
					// if dependency is not fulfilled
					_class = Class.forName(packageName + '.' + fileName.substring(0, fileName.length() - 6), false, Thread.currentThread().getContextClassLoader());
				}
				classes.add(_class);
			}
		}
		return classes;
	}

	/**
	 * Buscar todas las clases que heredan de la clase pasada como parámetro.
	 * 
	 * @param _class
	 * @return
	 * @throws Exception
	 */
	public static List<Class<?>> findHiearchy(Class<?> _class) throws Exception {
		return findHiearchy(_class, true);
	}

	/**
	 * Buscar las clases de un tipo de clase que implementan una interface
	 * concreta.
	 * 
	 * @param _class
	 * @return
	 */
	public static List<Class<?>> getHierarchyInterfaces(Class<?> _class, Class<?> interfaceClass) {

		List<Class<?>> result = new ArrayList<Class<?>>();

		if (_class.getInterfaces() != null) {
			for (Class<?> _interface : _class.getInterfaces())
				if (_interface == interfaceClass && result.indexOf(_interface) < 0)
					result.add(_class);
		}

		Class<?> parent = _class.getSuperclass();
		while (parent != null) {
			for (Class<?> _interface : parent.getInterfaces()) {
				if (_interface == interfaceClass && result.indexOf(_interface) < 0) {
					result.add(_class);
					break;
				}
			}
			parent = parent.getSuperclass();
		}
		return result;
	}

	/**
	 * Buscar todas las clases que heredan de la clase pasada como parámetro.
	 * 
	 * @param _class
	 * @param includeAbstract
	 *            incluir las clases abstractas.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class<?>> findHiearchy(Class<?> _class, boolean includeAbstract) throws Exception {

		List<Class> list = getClasses(_class.getCanonicalName().substring(0, _class.getCanonicalName().lastIndexOf(".")));

		List<Class<?>> result = new ArrayList<Class<?>>();

		for (Class c : list) {

			// Si es una interface
			if (_class.isInterface()) {
				// Ver interfaces propias y de las superclases.
				for (Class<?> _c : getHierarchyInterfaces(c, _class)) {
					result.add(_c);
				}
			}
			// Si es una clase
			else {
				Class parent = c.getSuperclass();
				while (parent != null) {
					if (parent == _class) {
						if (!includeAbstract && Modifier.isAbstract(c.getModifiers()))
							;
						else
							result.add(c);
						break;
					}
					parent = parent.getSuperclass();
				}
			}
		}
		return result;
	}

	// /**
	// * Obtiene la clase incluida en la lista genérica.
	// *
	// * @param _class
	// * @return
	// */
	// public static Class<?> getGenericList(Class<?> _class, String fieldName)
	// throws Exception {
	//
	// Class<?> result = null;
	//
	// Type type = ReflectionUtils.getField(_class, fieldName).getGenericType();
	//
	// // Si es una variable de tipo class xx instanceof ArrayList<?> no
	// // servirá la
	// // anterior declaración y tendremos que acceder a la superclase para
	// // obtener
	// // los parámetros de construcción.
	// if (!(type instanceof ParameterizedTypeImpl)) {
	//
	// Type t = ReflectionUtils.getDeclaredField(_class,
	// fieldName).getType().getGenericSuperclass();
	//
	// if (t instanceof ParameterizedTypeImpl) {
	// if (((ParameterizedTypeImpl) t).getActualTypeArguments().length > 0) {
	// result = ((Class<?>) ((ParameterizedTypeImpl)
	// t).getActualTypeArguments()[0]);
	// }
	// }
	// }
	// // Si se trata de una instancia de tipo List<?> v = ...
	// else if (type instanceof ParameterizedTypeImpl) {
	// if (((ParameterizedTypeImpl) type).getActualTypeArguments().length > 0) {
	// result = ((Class<?>) ((ParameterizedTypeImpl)
	// type).getActualTypeArguments()[0]);
	// }
	// }
	//
	// return result;
	// }

	/**
	 * Comprueba si una clase deriva de una superclase pasada como parámetro.
	 * 
	 * @param type
	 * @param superclass
	 * @return
	 */
	public static boolean isSuperclass(Class<?> type, Class<?> superclass) {

		while (type != null && type != superclass) {
			type = type.getSuperclass();
			if (type == superclass)
				return true;
		}
		return false;
	}

	/**
	 * Comprueba si la clase es una lista o sus derivados
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isList(Class<?> type) {

		return instanceOf(type, new Class[] { List.class, ArrayList.class, Collection.class });
	}

	/**
	 * Comprobar si una clase extiende o implementa una lista de clases.
	 * 
	 * @param type
	 * @param classes
	 * @return
	 */
	public static boolean instanceOf(Class<?> type, Class<?>[] classes) {

		if (type != null && classes != null) {

			// Primera comprobación
			for (Class<?> c : classes) {
				if (type == c)
					return true;
				else if (c.getInterfaces() != null) {
					for (Class<?> i : c.getInterfaces()) {
						if (type == i)
							return true;
					}
				}
			}

			// Comprobación de sus superclases
			Class<?> parent = type.getSuperclass();
			while (parent != null) {

				for (Class<?> c : classes) {
					if (parent == c)
						return true;
					else if (c.getInterfaces() != null) {
						for (Class<?> i : c.getInterfaces()) {
							if (parent == i)
								return true;
						}
					}
				}

				parent = parent.getSuperclass();
			}
		}
		return false;
	}

	/**
	 * Comprueba en valor de una propiedad se encuentra en la lista de objetos
	 * pasada como parámetro.
	 * 
	 * @param list
	 * @param propertyName
	 * @param value
	 *            Valor de la propiedad a comparar en la lista de objetos.
	 */
	public static boolean objectExistsInList(List<?> list, String propertyName, String value) throws Exception {

		if (list != null) {

			for (Object object : list) {

				Object _value = getPropertyValue(object, propertyName);

				if (_value != null && StringUtils.toString(_value).equals(value))
					return true;
			}
		}
		return false;

	}

	/**
	 * Crear objeto mediante reflection. Param1 y 2 son opciones o alternativas
	 * para incluir un parámetro en el constructor en caso de error.
	 * 
	 * @param _class
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createObject(Class<?> _class, Object optionParam1, Object optionParam2) throws Exception {

		Object object = null;
		try {
			createObject(_class);
		}
		catch (Exception e) {

			if (optionParam1 != null)
				object = ReflectionUtils.createObject(_class, new Object[] { optionParam1 });

			else if (optionParam2 != null)
				object = ReflectionUtils.createObject(_class, new Object[] { optionParam2 });

		}
		return (T) object;
	}

	// /**
	// * Obtiene los subtipos o clases que extienden otra clase.
	// *
	// * @param _class
	// * @return
	// */
	// public static List<Class<?>> getSubTypesOf(Class<?> _class) {
	// return getSubTypesOf(_class, false, false);
	// }

	// /**
	// * Obtiene los subtipos o clases que extienden otra clase.
	// *
	// * @param _class
	// * @param filterAbstract
	// * @param filterInterface
	// * @param packageNames
	// * Nombres de los packages que queremos localizar para evitar
	// * buscar en todas las clases.
	// * @return
	// */
	// public static List<Class<?>> getSubTypesOf(Class<?> _class, boolean
	// filterAbstract, boolean filterInterface, String... packageNames) {
	//
	// Set<?> list = getReflections(packageNames).getSubTypesOf(_class);
	//
	// if (list != null) {
	//
	// List<Class<?>> classes = new ArrayList<Class<?>>();
	//
	// for (Object item : list) {
	//
	// Class<?> item_class = (Class<?>) item;
	//
	// if (filterAbstract && Modifier.isAbstract(item_class.getModifiers())) {
	// continue;
	// }
	// else if (filterInterface &&
	// Modifier.isInterface(item_class.getModifiers())) {
	// continue;
	// }
	// else {
	// classes.add(item_class);
	// }
	// }
	// // System.out.println(classes.size());
	//
	// return classes.size() > 0 ? classes : null;
	// }
	// return null;
	// }
	//
	// /**
	// * Instanciar solo una vez Reflections.
	// *
	// * @return
	// */
	// private static Reflections getReflections(String... packageNames) {
	//
	// Set<URL> urls = Sets.newHashSet();
	//
	// // Todas pero con warnings.
	// if (packageNames == null || packageNames.length == 0) {
	// urls.addAll(ClasspathHelper.forClassLoader());
	// }
	// else {
	// for (String packageName : packageNames) {
	// urls.addAll(ClasspathHelper.forPackage(packageName));
	// }
	// }
	//
	// return new Reflections(new ConfigurationBuilder()
	// // .filterInputsBy(new
	// // FilterBuilder().include(FilterBuilder.prefix(packagename)))
	// .setUrls(urls).setScanners(new SubTypesScanner(), new
	// ResourcesScanner()));
	// }

	// LISTA DE FUNCIONES ADICIONALES OBTENIDAS DE LIBRERÍAS ANDROID 2014

	/**
	 * Obtiene el método getter de un campo de la clase
	 * 
	 * @param field
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getter(Field field) throws NoSuchMethodException {

		Class<?> clazz = field.getDeclaringClass();
		String adjustedName = getAjustedName(field);
		Class<?> fieldType = field.getType();
		if (fieldType.isAssignableFrom(boolean.class) || fieldType.isAssignableFrom(Boolean.class)) {
			return clazz.getMethod("is" + adjustedName);
		}
		else {
			return clazz.getMethod("get" + adjustedName);
		}
	}

	/**
	 * Obtiene el método setter de un campo de la clase
	 * 
	 * @param field
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method setter(Field field) throws NoSuchMethodException {

		String adjustedName = getAjustedName(field);
		Class<?> fieldType = field.getType();
		return field.getDeclaringClass().getMethod("set" + adjustedName, fieldType);
	}

	/**
	 * Adaptar el nombre del método para capitalizar la primera letra
	 * 
	 * @param field
	 * @return
	 */
	private static String getAjustedName(Field field) {

		return Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
	}

	/**
	 * Establece el valor de un campo de un objeto o target.
	 * 
	 * @param field
	 * @param target
	 * @param value
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void set(Field field, Object target, Object value) throws IllegalArgumentException, InvocationTargetException {

		try {
			setter(field).invoke(target, value);
		}
		catch (IllegalAccessException e) {
			// It'll never happen
		}
		catch (NoSuchMethodException e) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
				field.set(target, value);
			}
			catch (IllegalAccessException e1) {
				// It'll never happen
			}
		}
	}

	public static void removeFinalModifier(Field field) {

		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void setFinalStatic(Field field, Object newValue) {

		field.setAccessible(true);

		removeFinalModifier(field);

		try {
			field.set(null, newValue);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el valor de un campo del objeto
	 * 
	 * @param field
	 * @param target
	 * @return
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object get(Field field, Object target) throws IllegalArgumentException, InvocationTargetException {

		try {
			return getter(field).invoke(target);
		}
		catch (IllegalAccessException e) {
			// It'll never happen
			return null;
		}
		catch (NoSuchMethodException e) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
				return field.get(target);
			}
			catch (IllegalAccessException e1) {
				// It'll never happen
				return null;
			}
		}
	}

	/**
	 * Obtiene la lista de campos NO estáticos de una clase
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getInstanceVariables(Class<?> clazz) {

		List<Field> listFields = new ArrayList<Field>();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) {
					listFields.add(field);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return listFields;
	}

	/**
	 * Busca el campo por su nombre
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Field getInstanceVariable(Class<?> clazz, String name) {

		try {
			return clazz.getDeclaredField(name);
		}
		catch (NoSuchFieldException e) {
			while (clazz != null) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if (!Modifier.isStatic(field.getModifiers()) && field.getName().equals(name)) {
						return field;
					}
				}
				clazz = clazz.getSuperclass();
			}
			return null;
		}
	}

	/**
	 * Busca un método de la clase filtrando
	 * 
	 * @param clazz
	 * @param name
	 * @param parameterTypes
	 * @return
	 */
	public static Method getRestrictedMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {

		try {
			return clazz.getDeclaredMethod(name, parameterTypes);
		}
		catch (NoSuchMethodException e) {
			while (clazz != null) {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (!Modifier.isStatic(method.getModifiers()) && method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
						return method;
					}
				}
				clazz = clazz.getSuperclass();
			}
			return null;
		}
	}

	/**
	 * Invoca a un método de la clase
	 * 
	 * @param target
	 * @param name
	 * @param parameters
	 * @return
	 * @throws InvocationTargetException
	 */
	public static <T> T invokeRestrictedMethod(Object target, String name, Object[] parameters) throws InvocationTargetException {

		Class<?>[] parameterTypes = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameterTypes[i] = parameters[i].getClass();
		}
		return invokeRestrictedMethod(target, name, parameters, parameterTypes);
	}

	/**
	 * Invoca a un método de la clase
	 * 
	 * @param target
	 * @param name
	 * @param parameters
	 * @param parameterTypes
	 * @return
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invokeRestrictedMethod(Object target, String name, Object[] parameters, Class<?>... parameterTypes) throws InvocationTargetException {

		Method restrictedMethod = getRestrictedMethod(target.getClass(), name, parameterTypes);

		if (restrictedMethod == null) {
			throw new InvocationTargetException(new Exception(String.format("Method %s not found.", name)));
		}

		if (!restrictedMethod.isAccessible()) {
			restrictedMethod.setAccessible(true);
		}

		try {
			return (T) restrictedMethod.invoke(target, parameters);
		}
		catch (IllegalAccessException e) {
			// It'll never happen
			return null;
		}
	}

}
