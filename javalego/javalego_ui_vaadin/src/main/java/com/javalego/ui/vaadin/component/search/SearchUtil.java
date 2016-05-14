package com.javalego.ui.vaadin.component.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Funciones de utilidad relacionados con la localización de objetos de una
 * lista.
 * 
 * @see ISearch
 * 
 * @author ROBERTO RANZ
 * 
 */
public class SearchUtil {

	/**
	 * Localizar objeto cuyo toString() contenga el texto pasado como parámetro.
	 * La búsqueda es independiente de mayúsculas y minúsculas.
	 * 
	 * @param list
	 *          Lista de objetos
	 * @param text
	 *          Texto de búsqueda
	 * @param actualValue
	 *          Objeto actualmente seleccionado en la lista
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T findObject(Collection<?> list, String text, Object actualValue) {

		if (list != null && list.size() > 0 && text != null) {

			// Buscar a partir del objeto actualmente seleccionado y si no se
			// encuentra ninguno, se inicia la búsqueda desde el primer elemento al
			// objeto seleccionado.
			if (actualValue != null) {

				Object[] array = list.toArray();

				int index = -1;
				for (int i = 0; i < array.length; i++) {

					Object object = array[i];

					if (index < 0) {
						if (object == actualValue) {
							index = i;
							continue;
						}
					}
					else if (object != null && object.toString().toLowerCase().indexOf(text.toLowerCase()) > -1) {
						return (T) object;
					}
				}

				if (index > 0) {
					for (int i = 0; i < index; i++) {

						Object object = array[i];

						if (object != null && object.toString().toLowerCase().indexOf(text.toLowerCase()) > -1) {
							return (T) object;
						}
					}
				}

			}
			// Buscar texto en la lista de objetos cuando no hay ningún objeto
			// seleccionado en la lista.
			else {
				for (Object object : list) {

					if (object != null && object.toString().toLowerCase().indexOf(text.toLowerCase()) > -1) {
						return (T) object;
					}

				}
			}
		}
		return null;
	}

	/**
	 * Localizar los objetos que contengan el texto pasado como parámetro.
	 * La búsqueda es independiente de mayúsculas y minúsculas.
	 * 
	 * @param list
	 *          Lista de objetos
	 * @param text
	 *          Texto de búsqueda
	 * @return
	 */
	public static List<?> findObjects(Collection<?> list, String text) {

		List<Object> filter = new ArrayList<Object>();

		if (list != null && list.size() > 0 && text != null) {

			// Buscar texto en la lista de objetos cuando no hay ningún objeto seleccionado en la lista.
			for (Object object : list) {

				if (object != null && object.toString().toLowerCase().indexOf(text.toLowerCase()) > -1) {
					filter.add(object);
				}

			}
		}
		return filter.size() > 0 ? filter : null;
	}

}
