package com.javalego.ui.vaadin.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * Lista de valores e imágenes asociadas (opcional) que podemos ir añadiendo al
 * layout como si de una clase JList (Swing) se tratase.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ListValuesLayout extends GridLayout {

	private static final long serialVersionUID = 1L;

	private boolean checkbox;

	/**
	 * Lista de elementos y componentes correspondientes a cada valor incluido en la lista.
	 */
	private List<ListValuesLayoutItem> items = new ArrayList<ListValuesLayoutItem>();
	
	public ListValuesLayout(boolean images, boolean checkbox) {

		setColumns(images || checkbox ? 2 : 1);
		initialize();
		this.checkbox = checkbox;
	}

	public ListValuesLayout(boolean images, boolean checkbox, boolean additionalComponent) {

		int count = images || checkbox ? 2 : 1;
		
		if (additionalComponent)
			++count;
		
		setColumns(count);
		initialize();
		this.checkbox = checkbox;
	}
	
	private void initialize() {
		setRows(1);
		setSpacing(true);
	}

	/**
	 * Constructor de una lista de valores cuyo texto se obtiene de la lista de objetos pasados como parámetro.
	 * @param objects
	 * @throws LocalizedException 
	 */
	public ListValuesLayout(List<?> objects) throws LocalizedException {
		initialize();
		if (objects != null) {
			for(Object object : objects) {
				addItem(object.toString(), null);
			}
		}
	}

	/**
	 * Añadir un elemento a la lista.
	 * 
	 * @param caption
	 * @param icon
	 * @throws LocalizedException 
	 */
	public ListValuesLayoutItem addItem(String caption, Icon icon) throws LocalizedException {
		return addItem(caption, icon, false, null);
	}

	/**
	 * Añadir un elemento a la lista.
	 * 
	 * @param caption
	 * @param icon
	 * @param checkBox
	 * @param application
	 * @throws LocalizedException 
	 */
	public ListValuesLayoutItem addItem(String caption, Icon icon, Object data) throws LocalizedException {
		return addItem(caption, icon, false, data);
	}

	/**
	 * Añadir un elemento a la lista.
	 * 
	 * @param caption
	 * @param icon
	 * @param checkBox
	 * @param application
	 * @throws LocalizedException 
	 */
	public ListValuesLayoutItem addItem(String caption, Icon icon, boolean select, Object data) throws LocalizedException {
		return addItem(caption, icon, select, data, null);
	}
	
	/**
	 * Añadir un elemento a la lista.
	 * 
	 * @param caption
	 * @param icon
	 * @param checkBox
	 * @param application
	 * @throws LocalizedException 
	 */
	public ListValuesLayoutItem addItem(String caption, Icon icon, boolean select, Object data, Component additionalComponent) throws LocalizedException {

		ListValuesLayoutItem item = new ListValuesLayoutItem();
		items.add(item);
		
		Label label = new Label(caption, ContentMode.HTML);
		item.setLabel(label);
		
		CheckBoxExt check = null;

		Component image = null;

		if (checkbox) {
			check = new CheckBoxExt();

			if (data != null) 
				check.setData(data);

			check.setValue(select);
			
			if (icon != null)
				check.setIcon(ResourceIconsVaadin.getCurrent().getResource(icon));
		} 
		else if (icon != null) {
			image = UIContextVaadin.getComponent(icon);
		}

		if (check != null) {
			addComponent(check);
			setComponentAlignment(check, Alignment.TOP_CENTER);
			item.setCheck(check);
		} 
		else if (icon != null) {
			addComponent(image);
			setComponentAlignment(image, Alignment.TOP_LEFT);
			item.setEmbebed(image);
		}
		item.setData(data);

		addComponent(label);
		setComponentAlignment(label, Alignment.TOP_LEFT);

		// Componente adicional
		if (additionalComponent != null) {
			addComponent(additionalComponent);
			setComponentAlignment(additionalComponent, Alignment.MIDDLE_LEFT);
			item.setAdditionalComponent(additionalComponent);
		}
		
		return item;
	}

	/**
	 * Número de elementos seleccionados (opción check activada).
	 * 
	 * @return
	 */
	public int getSelectedCount() {

		int count = 0;
		for (Iterator<Component> i = iterator(); i.hasNext();) {

			AbstractComponent c = (AbstractComponent) i.next();

			if (c instanceof CheckBox) {

				CheckBox check = (CheckBox) c;

				if (check.getValue()) {
					++count;
					// ItemStateProperty item = ((ItemStateProperty)check.getData());
					//
					// String value = Functions.getSingleQuotedStr(item.getValue());

				}
			}
		}
		return count;
	}

	/**
	 * Buscar un item por su objeto data pasado como parámetro en el método addItem().
	 * @param data
	 * @return
	 */
	public ListValuesLayoutItem getItem(Object data) {
		
		for(ListValuesLayoutItem item : items)
			if (item.getData() == data)
				return item;
		
		return null;
	}
	
	/**
	 * Lista de objetos seleccionados.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> getSelectedObjects() {

		List objects = null;

		for (Iterator<Component> i = iterator(); i.hasNext();) {

			AbstractComponent c = (AbstractComponent) i.next();

			if (c instanceof CheckBox) {

				CheckBox check = (CheckBox) c;

				if (check.getValue()) {

					if (objects == null)
						objects = new ArrayList();

					objects.add(check.getData());
				}
			}
		}
		return objects;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

}
