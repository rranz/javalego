package com.javalego.ui.vaadin.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.OptionGroup;

/**
 * Agrupación de checks asociados a componentes.
 * 
 * @author ROBERTO RANZ
 *
 */
public class OptionGroupResponsive<T> extends CssLayout {

	private static final long serialVersionUID = -2589443739476777L;

	private Map<Object, OptionGroup> items = new HashMap<Object, OptionGroup>();

	private String width;

	/**
	 * Constructor
	 */
	public OptionGroupResponsive() {
		Responsive.makeResponsive(this);
	}

	/**
	 * Constructor
	 * 
	 * @param width
	 *            Ancho de cada item.
	 */
	public OptionGroupResponsive(String width) {
		this();
		this.width = width;
	}

	@Override
	protected String getCss(Component c) {
		return "margin: 8px; vertical-align: top; display: inline-block;";
	}

	/**
	 * Añadir item al grupo
	 * 
	 * @param object
	 * @param icon
	 */
	public OptionGroupResponsive<T> addItem(T object, Icon icon) {
		return addItem(object, icon, false);
	}

	/**
	 * Añadir item al grupo
	 * 
	 * @param object
	 * @param icon
	 * @param selected
	 *            Seleccionar item añadido.
	 */
	public OptionGroupResponsive<T> addItem(T object, Icon icon, boolean selected) {

		if (object == null) {
			return this;
		}

		if (!items.containsKey(object)) {

			OptionGroup check = new OptionGroup();
			check.setHtmlContentAllowed(true);
			// check.addStyleName("large");
			check.setMultiSelect(true);
			check.addItem(object);
			
			if (selected) {
				check.select(object);
			}

//			check.addValueChangeListener(new ValueChangeListener() {
//				@Override
//				public void valueChange(ValueChangeEvent event) {
//					System.out.println(getSelectedItems());
//				}
//			});

			check.setReadOnly(isReadOnly());

			if (width != null) {
				check.setWidth(width);
			}

			if (icon != null) {
				check.setItemIcon(object, ResourceIconsVaadin.getCurrent().getResource(icon));
			}

			items.put(object, check);

			addComponent(check);
		}

		return this;
	}

	/**
	 * Comprueba si un objeto ha sido seleccionado.
	 * 
	 * @param object
	 * @return
	 */
	public boolean isSelected(Object object) {

		OptionGroup check = items.get(object);

		return check != null ? check.isSelected(object) : false;
	}

	/**
	 * Obtener la lista de objetos seleccionados.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getSelectedItems() {

		List<T> list = new ArrayList<T>();

		for (Entry<Object, OptionGroup> item : items.entrySet()) {
			
			if (item.getValue().isSelected(item.getKey())) {
				list.add((T)item.getKey());
			}
		}

		return list;
	}

}
