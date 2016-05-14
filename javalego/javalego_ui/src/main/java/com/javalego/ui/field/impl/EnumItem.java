package com.javalego.ui.field.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Elemento de una propiedad asociada a una lista de valores posibles.
 * 
 * @author ROBERTO RANZ
 */
public class EnumItem implements Serializable {

	private static final long serialVersionUID = -8743462674394398023L;

	/**
	 * Título descriptivo
	 */
	private String title;

	/**
	 * Valor
	 */
	private Object value;

	/**
	 * Descripción ampliada.
	 */
	private String description;

	/**
	 * Imagen que deseamos mostrar asociada a elemento del campo
	 * enumerado.</documentation>
	 */
	private String icon;

	/**
	 * Color del item
	 */
	private String color;

	/**
	 * Lista de valores para otras interfaces.
	 */
	private List<InterfaceEnumItem> interfaces;

	public EnumItem(String title, Object value, String description) {
		this.title = title;
		this.value = value;
		this.description = description;
	}

	public EnumItem(String title, Object value, String description, String icon) {
		this(title, value, description);
		this.icon = icon;
	}

	public EnumItem(String title, Object value, String description, String icon, String color) {
		this(title, value, description);
		this.icon = icon;
		this.color = color;
	}

	public EnumItem() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<InterfaceEnumItem> getInterfaces() {
		if (interfaces == null)
			interfaces = new ArrayList<InterfaceEnumItem>();
		return interfaces;
	}

	public void setInterfaces(List<InterfaceEnumItem> interfaces) {
		this.interfaces = interfaces;
	}

	/**
	 * Obtener el valor de una interface.
	 * 
	 * @param interfaceName
	 * @return
	 */
	public Object getInterfaceValue(String interfaceName) {

		if (interfaces != null && value != null) {
			for (InterfaceEnumItem item : interfaces) {
				if (item.getName().equals(interfaceName))
					return item.getValue();
			}
		}
		return null;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
