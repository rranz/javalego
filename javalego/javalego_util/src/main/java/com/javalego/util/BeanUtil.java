package com.javalego.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utilidades de beans creadas para Android debido a la incompatibilidad del uso
 * de la librería common bean utils.
 * 
 * @author ROBERTO RANZ
 * 
 */
public final class BeanUtil {

	/**
	 * Obtener la lista de propiedades (getter & setter) de un bean.
	 * 
	 * @param _class
	 * @return
	 */
	public static List<Field> getProperties(Class<?> _class) {

		List<Field> list = new ArrayList<Field>();

		for (Field field : getAllFields(_class)) {
			if (hasMethodGetterSetter(_class, field.getName())) {
				list.add(field);
			}
		}

		return list.size() > 0 ? list : null;
	}

	/**
	 * Obtiene la lista de campos de una clase incluyendo los campos de las
	 * clases que hereda.
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getAllFields(Class<?> clazz) {

		List<Field> result = new ArrayList<Field>();

		Class<?> i = clazz;
		while (i != null && i != Object.class) {
			Field[] fields = i.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				result.addAll(Arrays.asList(fields));
			}
			i = i.getSuperclass();
		}

		return result;
	}

	/**
	 * Obtiene la lista de métodos de una clase incluyendo los métodos de las
	 * clases que hereda.
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Method> getAllMethods(Class<?> clazz) {

		List<Method> result = new ArrayList<Method>();

		Class<?> i = clazz;
		while (i != null && i != Object.class) {
			Method[] methods = i.getDeclaredMethods();
			if (methods != null && methods.length > 0) {
				result.addAll(Arrays.asList(methods));
			}
			i = i.getSuperclass();
		}

		return result;
	}
	
	/**
	 * Verificar que la propiedad tenga el método getter y setter como propiedad
	 * de bean.
	 * 
	 * @param _class
	 * @param propertyName
	 * @return
	 */
	private static boolean hasMethodGetterSetter(Class<?> _class, String propertyName) {

		List<Method> methods = getAllMethods(_class);

		boolean get = false, set = false;

		propertyName = StringUtils.capitalize(propertyName);

		for (Method m : methods) {

			String name = m.getName();

			if (name.startsWith("get") && name.substring(3).equals(propertyName)) {
				get = true;
			}
			if (name.startsWith("is") && name.substring(2).equals(propertyName)) {
				get = true;
			}
			else if (name.startsWith("set") && name.substring(3).equals(propertyName)) {
				set = true;
			}
		}
		return get && set;
	}

}
