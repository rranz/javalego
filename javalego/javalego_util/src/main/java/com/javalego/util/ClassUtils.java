package com.javalego.util;

/**
 * Funciones relativas a la Class no implementadas en el versión 1.
 * @author ROBERTO
 *
 */
public class ClassUtils {

	@SuppressWarnings("rawtypes")
	static public String getCanonicalName(Class _class){
		return SystemUtils.removeAll(_class.getName(), '$');
	}
	
	static public String getCanonicalName(Object object){
		return SystemUtils.removeAll(object.getClass().getName(), '$');
	}
	
	@SuppressWarnings("rawtypes")
	static public String getSimpleName(Class _class){
		String value = getCanonicalName(_class);
		if (value.lastIndexOf(".") > -1)
			return value.substring(value.lastIndexOf(".")+1);
		else
			return value;
	}

		static public String getSimpleName(Object object){
		String value = getCanonicalName(object);
		if (value.lastIndexOf(".") > -1)
			return value.substring(value.lastIndexOf(".")+1);
		else
			return value;
	}
	
}
