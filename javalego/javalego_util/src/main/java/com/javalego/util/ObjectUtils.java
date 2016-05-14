package com.javalego.util;

import java.util.Date;
import java.util.List;

/**
 * Funciones relativas a un objeto para obtener transformaciones de objetos
 * @author ROBERTO
 */
public class ObjectUtils {

	/**
	 * Obtener el valor de tipo String del índice de un array de objetos.
	 * @param object
	 * @param index
	 * @return
	 */
	static public String getStringFromArray(Object object, int index) {
		Object o = getValueFromArray(object, index);
		if (o != null) {
			return o.toString();
		}
		else
			return null;
	}

	/**
	 * Obtener el valor de tipo int del índice de un array de objetos.
	 * @param object
	 * @param index
	 * @return
	 */
	static public int getIntFromArray(Object object, int index) {
		Object o = getValueFromArray(object, index);
		if (o != null && o instanceof Number) {
			return ((Number)o).intValue();
		}
		else
			return 0;
	}

	/**
	 * Obtener el valor de tipo double del índice de un array de objetos.
	 * @param object
	 * @param index
	 * @return
	 */
	static public double getDoubleFromArray(Object object, int index) {
		Object o = getValueFromArray(object, index);
		if (o != null && o instanceof Number) {
			return ((Number)o).doubleValue();
		}
		else
			return 0.00;
	}

	/**
	 * Obtener el valor de tipo long del índice de un array de objetos.
	 * @param object
	 * @param index
	 * @return
	 */
	static public long getLongFromArray(Object object, int index) {
		Object o = getValueFromArray(object, index);
		if (o != null && o instanceof Number) {
			return ((Number)o).longValue();
		}
		else
			return 0;
	}

	/**
	 * Obtener el valor de tipo boolean del índice de un array de objetos.
	 * @param object
	 * @param index
	 * @return
	 */
	static public boolean getBooleanFromArray(Object object, int index) {
		Object o = getValueFromArray(object, index);
		if (o != null && o instanceof Boolean) {
			return ((Boolean)o).booleanValue();
		}
		else
			return false;
	}

	/**
	 * Obtener el valor de tipo Date del índice de un array de objetos.
	 * @param object
	 * @param index
	 * @return
	 */
	static public Date getDateFromArray(Object object, int index) {
		Object o = getValueFromArray(object, index);
		if (o != null && o instanceof Date) {
			return (Date)o;
		}
		else
			return null;
	}

	/**
	 * Obtener el valor de un índice de un array de objetos.
	 * @param object
	 * @param index
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	static private Object getValueFromArray(Object object, int index) {
		if (object != null) {
			if (object instanceof Object[]) {
				if (index > -1 && index < ((Object[])object).length)
					return ((Object[])object)[index];
			}
			else if (object instanceof List) {
				if (index > -1 && index < ((List)object).size())
					return ((List)object).get(index);
			}
		}
		return null;
	}

}
