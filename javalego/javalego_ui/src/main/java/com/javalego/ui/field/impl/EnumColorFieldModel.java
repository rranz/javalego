package com.javalego.ui.field.impl;


/**
 * Propiedad de tipo enumerado (valores y etiquetas).
 * 
 * @author ROBERTO RANZ
 */
public class EnumColorFieldModel extends EnumFieldModel {

	private static final long serialVersionUID = -5048112078878018888L;

	/**
	 * Lista de títulos asociados a la lista de valores al objeto de crear un
	 * combobox que muestre los títulos y no los valores.
	 */
	protected String[] listColors;

	public EnumColorFieldModel() {
	}

	@Override
	public String[] getListColors() {
		return listColors;
	}

	/**
	 * Lista de valores separados por el carácter |.
	 * 
	 * @param listValues
	 */
	public void setListColors(String attribute) {
		String[] tokens = attribute.split("\\|");
		this.listColors = tokens;
	}

	/**
	 * Lista de títulos de valores
	 * 
	 * @param listLabels
	 */
	public void setListColors(String[] listColors) {
		if (listColors != null) {
			// Purgar items = null debido a la reflexión de Java.
			String list = "";
			for (int i = 0; i < listColors.length; i++) {
				if (listColors[i] != null)
					list += (list.equals("") ? "" : "|") + listColors[i];
			}
			this.listColors = list.split("\\|");
		}
		else
			this.listColors = null;
	}

}
