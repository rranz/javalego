package com.javalego.util;

import java.util.Comparator;
import java.util.Date;

import com.javalego.util.ReflectionUtils;
import com.javalego.util.StringUtils;

/**
 * Clase que compara los atributos de dos beans. Se utiliza para realizar la
 * ordenación de una lista de objetos por varios atributos.
 * 
 * @author ROBERTO
 */
@SuppressWarnings("rawtypes")
public class BeanComparator implements Comparator {

	// Relación de propiedades a comparar.
	private BeanAttributeSort[] attributes = null;

	public BeanComparator() {

	}

	public BeanComparator(BeanAttributeSort[] propertyNames) {
		this.attributes = propertyNames;
	}

	public BeanComparator(BeanAttributeSort propertyName) {
		this.attributes = new BeanAttributeSort[] { propertyName };
	}

	/**
	 * Compara dos objetos.
	 * 
	 * @param object1
	 * @param object2
	 * @return
	 */
	static public boolean isEqualObjects(Object object1, Object object2) {
		BeanComparator b = new BeanComparator();
		return b.compare(object1, object2) == 0;
	}

	// Comparar todas las propiedades del bean hasta que una sea distinta o
	// todas sean iguales.
	@Override
	public int compare(Object object1, Object object2) {

		Object o1 = null;
		Object o2 = null;

		if (attributes == null || attributes.length == 0)
			return -1;

		for (int i = 0; i < attributes.length; i++) {

			// Obtener valores por reflection
			try {
				o1 = ReflectionUtils.getPropertyValue(object1,
						attributes[i].getAttributeName());
				o2 = ReflectionUtils.getPropertyValue(object2,
						attributes[i].getAttributeName());
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			// System.out.println(o1);
			int comparison = 0;
			// Define null less than everything, except null.
			if (o1 == null && o2 == null)
				comparison = 0;
			else if (o1 == null)
				comparison = -1;
			else if (o2 == null)
				comparison = 1;
			else {
				// tipos de campos
				if (o1 instanceof Integer || o1 instanceof Long
						|| o1 instanceof Double || o1 instanceof Float) {
					double d1 = Double.parseDouble(o1.toString());
					double d2 = Double.parseDouble(o2.toString());
					comparison = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
				} else if (o1 instanceof java.util.Date) {
					long d1 = ((Date) o1).getTime();
					long d2 = ((Date) o2).getTime();
					comparison = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
				} else {

					if (o1.toString() == null && o2.toString() == null)
						comparison = 1;
					else if (o1.toString() == null && o2.toString() != null)
						comparison = 0;
					else if (o1.toString() != null && o2.toString() == null)
						comparison = 0;
					else
						comparison = StringUtils.clearAccents(o1.toString(),
								true).compareTo(
								StringUtils.clearAccents(o2.toString(), true));
				}
			}
			if (comparison != 0)
				return attributes[i].direction == BeanAttributeSort.DESCENDING ? -comparison
						: comparison;
		}
		return 0;
	}

	/**
	 * Relación de atributos del bean que desea ordenar.
	 * 
	 * @return
	 */
	public BeanAttributeSort[] getAttributes() {
		return attributes;
	}

	public void setAttributes(BeanAttributeSort[] attributes) {
		this.attributes = attributes;
	}
}
