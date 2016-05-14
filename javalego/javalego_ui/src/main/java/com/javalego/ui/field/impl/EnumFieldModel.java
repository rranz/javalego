package com.javalego.ui.field.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.javalego.model.keys.Key;
import com.javalego.util.StringUtils;

/**
 * Propiedad de tipo enumerado (valores y etiquetas).
 * 
 * @author ROBERTO RANZ
 */
public class EnumFieldModel extends AbstractFieldModel implements IListValuesFieldModel {

	private static final long serialVersionUID = 3945352135123226257L;

	/**
	 * Lista de valores posibles.
	 */
	protected List<EnumItem> items = new ArrayList<EnumItem>();

	/**
	 * Constructor
	 */
	public EnumFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param enumValues
	 */
	public EnumFieldModel(String name, Key title, Class<?> enumValues) {
		super(name, title);
		addEnumValues(enumValues);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 * @param enumValues
	 */
	public EnumFieldModel(String name, Key title, Key description, Class<?> enumValues) {
		super(name, title, description);
		addEnumValues(enumValues);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param enumItems
	 */
	public EnumFieldModel(String name, Key title, String... valueItems) {
		super(name, title);
		if (valueItems != null) {
			for (String item : valueItems) {
				addItem(item, item);
			}
		}
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 * @param enumItems
	 */
	public EnumFieldModel(String name, Key title, Key description, String... valueItems) {
		super(name, title, description);
		if (valueItems != null) {
			for (String item : valueItems) {
				addItem(item, item);
			}
		}
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param values
	 */
	public EnumFieldModel(String name, Key title, List<?> valueItems) {
		this(name, title);
		if (valueItems != null) {
			for (Object item : valueItems) {
				if (item != null) {
					addItem(item.toString(), item);
				}
			}
		}
	}

	/**
	 * Añadir un elemento a la lista.
	 * 
	 * @param title
	 * @param value
	 * @param description
	 */
	public void addItem(String title, String value, String description) {
		items.add(new EnumItem(title, value, description));
	}

	/**
	 * Añadir un elemento a la lista.
	 * 
	 * @param title
	 * @param value
	 */
	public void addItem(String title, Object value) {
		items.add(new EnumItem(title, value, null));
	}

	/**
	 * Añadir un elemento a la lista con un icono.
	 * 
	 * @param title
	 * @param value
	 * @param icon
	 */
	public void addIconItem(String title, String value, String icon) {
		items.add(new EnumItem(title, value, null, icon));
	}

	/**
	 * Añadir un elemento a la lista con un color.
	 * 
	 * @param title
	 * @param value
	 * @param icon
	 */
	public void addColorItem(String title, String value, String color) {
		items.add(new EnumItem(title, value, null, null, color));
	}

	/**
	 * Añadir un elemento a la lista con un color e icono.
	 * 
	 * @param title
	 * @param value
	 * @param icon
	 */
	public void addColorItem(String title, String value, String icon, String color) {
		items.add(new EnumItem(title, value, null, icon, color));
	}

	/**
	 * Añadir un elemento a la lista.
	 * 
	 * @param EnumItem
	 */
	public void addItem(EnumItem item) {
		items.add(item);
	}

	/**
	 * Ordenar el valor de los items por un campo o propiedades de sus objetos.
	 * Este método solo se puede utilizar cuando estamos incluyendo objetos
	 * complejos en cada item. Si son valores primitivos generará un error.
	 * 
	 * @param name
	 */
	public void sortItems(String name) {

		StringUtils.sortList(items, "value." + name);
	}

	public List<EnumItem> getItems() {
		return items;
	}

	public void setItems(List<EnumItem> items) {
		this.items = items;
	}

	/**
	 * Lista de iconos
	 * 
	 * @return
	 */
	public String[] getListIcons() {

		if (items.size() > 0 && hasIcons()) {

			String[] values = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				values[i] = items.get(i).getIcon();

			return values;
		}
		else
			return null;
	}

	/**
	 * Lista de colores
	 * 
	 * @return
	 */
	@Override
	public String[] getListColors() {

		if (items.size() > 0 && hasColors()) {

			String[] values = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				values[i] = items.get(i).getColor();

			return values;
		}
		else
			return null;
	}

	/**
	 * Comprueba si se han definido iconos en los elementos del enumerado.
	 * 
	 * @return
	 */
	public boolean hasIcons() {

		for (EnumItem item : items)
			if (item.getIcon() != null)
				return true;
		return false;
	}

	/**
	 * Comprueba si se han definido colores en los elementos del enumerado.
	 * 
	 * @return
	 */
	public boolean hasColors() {

		for (EnumItem item : items)
			if (item.getColor() != null)
				return true;
		return false;
	}

	/**
	 * Obtiene el color del item seleccionado = value actual.
	 * 
	 * @param value
	 * @return
	 */
	public String getColor(String value) {

		for (EnumItem item : items) {

			if (item.getValue().equals(value))
				return item.getColor();
		}
		return null;
	}

	@Override
	public Object[] getListValues() {

		if (items.size() > 0) {

			Object[] values = new Object[items.size()];

			for (int i = 0; i < items.size(); i++)
				values[i] = items.get(i).getValue();

			return values;
		}
		else
			return null;
	}

	@Override
	public String[] getListLabels() {

		if (items.size() > 0) {

			String[] labels = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				labels[i] = items.get(i).getTitle();

			return labels;
		}
		else
			return null;
	}

	/**
	 * Lista de descripciones por cada item.
	 * 
	 * @return
	 */
	public String[] getListDescriptions() {

		if (items.size() > 0) {

			String[] labels = new String[items.size()];

			for (int i = 0; i < items.size(); i++)
				labels[i] = items.get(i).getDescription();

			return labels;
		}
		else
			return null;
	}

	/**
	 * Obtener el valor de una interface.
	 * 
	 * @param interfaceName
	 * @return
	 */
	public Object getInterfaceValue(String interfaceName, Object value) {

		for (EnumItem item : items) {
			if (value != null && item.getValue() != null && value.toString().equals(item.getValue().toString())) {
				Object _value = item.getInterfaceValue(interfaceName);
				if (_value != null)
					return _value;
			}
		}
		return null;
	}

	/**
	 * Tiene descripciones en algunos de sus items.
	 * 
	 * @return
	 */
	public boolean hasDescriptions() {
		for (EnumItem item : items)
			if (item.getDescription() != null)
				return true;
		return false;
	}

	/**
	 * Eliminar todos los items de la propiedad.
	 */
	public void clear() {
		items.clear();
	}

	/**
	 * Añadir los valores enumerados al modelo
	 * 
	 * @param list
	 */
	public void addEnumValues(Class<?> enumValues) {

		for (Object value : Arrays.asList(enumValues.getEnumConstants())) {
			addItem(value.toString(), value);
		}
	}

	/**
	 * Añadir items a partir de una lista de objetos. El título será el valor
	 * toString() de cada objeto y el propio objeto su valor.
	 * 
	 * @param list
	 */
	public void addItems(Collection<?> list) {

		if (list != null && list.size() > 0) {
			for (Object object : list) {
				addItem(object.toString(), object);
			}
		}

	}
}
