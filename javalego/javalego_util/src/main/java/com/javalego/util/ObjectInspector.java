package com.javalego.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Inspector de objeto que obtiene la lista de propiedades de un objeto.
 * 
 * @author ROBERTO
 */
@SuppressWarnings("rawtypes")
public class ObjectInspector {

	private Class type;
	private String name;
	private Object value;
	private List fields;

	@SuppressWarnings("unchecked")
	public ObjectInspector(Class aType, String aName, Object aValue) {
		type = aType;
		name = aName;
		value = aValue;
		fields = new ArrayList();

		/*
		 * find all fields if we have a class type except we don't expand
		 * strings and null values
		 */

		if (!type.isPrimitive() && !type.isArray() && !type.equals(String.class) && value != null) { // get
																										// fields
			// from the
			// class and
			// all
			// superclasses
			for (Class c = value.getClass(); c != null; c = c.getSuperclass()) {
				Field[] f = c.getDeclaredFields();
				AccessibleObject.setAccessible(f, true);

				// get all nonstatic fields
				for (int i = 0; i < f.length; i++)
					if ((f[i].getModifiers() & Modifier.STATIC) == 0)
						fields.add(f[i]);
			}
		}
		// System.out.println(fields.size());
	}

	/**
	 * Obtiene una propiedad buscando por su nombre.
	 * 
	 * @param propertyName
	 * @return
	 */
	public Field getProperty(String propertyName) {
		for (int i = 0; i < fields.size(); i++) {
			Field field = (Field) fields.get(i);
			if (field.getName().equals(propertyName))
				return field;
		}
		return null;
	}

	/**
	 * Valor u objeto de una propiedad
	 * 
	 * @param propertyName
	 * @return
	 */
	public Object getValue(String propertyName) {
		Field field = getProperty(propertyName);
		if (field != null)
			try {
				return field.get(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * Obtiene un array de propiedades buscando por un prefijo.
	 * 
	 * @param propertyName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Field[] getProperties(String prefix) {
		List items = new ArrayList();
		for (int i = 0; i < fields.size(); i++) {
			Field field = (Field) fields.get(i);
			if (field.getName().startsWith(prefix))
				items.add(field);
		}
		return (Field[]) items.toArray(new Field[items.size()]);
	}

	public Object getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	public Field[] getFields() {
		return (Field[]) fields.toArray(new Field[fields.size()]);
	}

	@Override
	public String toString() {
		String r = type + " " + name;
		if (type.isPrimitive())
			r += "=" + value;
		else if (type.equals(String.class))
			r += "=" + value;
		else if (value == null)
			r += "=null";
		return r;
	}

}
