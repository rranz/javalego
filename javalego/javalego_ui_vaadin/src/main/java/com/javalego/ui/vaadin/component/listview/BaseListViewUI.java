package com.javalego.ui.vaadin.component.listview;

import java.util.ArrayList;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.vaadin.component.search.ISearch;
import com.javalego.ui.vaadin.component.search.SearchUtil;
import com.javalego.util.StringUtils;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;

/**
 * Clase básica de vista de items.
 * 
 * @author ROBERTO RANZ
 */
public abstract class BaseListViewUI extends CustomComponent implements ISearch {

	private static final long serialVersionUID = 9018492905219543683L;

	/**
	 * Lista de items añadidos al grid.
	 */
	protected List<IItemListView> items = new ArrayList<IItemListView>();

	/**
	 * ChecksBox cuando optionGroup = false
	 */
	protected List<CheckBox> checks;

	/**
	 * OptionGroups cuando optionGroup = trus
	 */
	protected List<OptionGroup> groups = new ArrayList<OptionGroup>();

	/**
	 * Variable temporal para controlar el cambio de valor de items optiongroup.
	 */
	private OptionGroup processOption;

	/**
	 * Items seleccionables
	 */
	protected boolean checked = true;

	/**
	 * Cuando queremos que sólo se pueda seleccion una opción del grupo de
	 * opciones disponibles.
	 */
	protected boolean option_group;

	/**
	 * @return the items
	 */
	public List<IItemListView> getItems() {
		return items;
	}

	/**
	 * Añadir elemento a la lista
	 * 
	 * @param html
	 * @throws LocalizedException 
	 */
	public void addItem(final String html) throws LocalizedException {
		addItem(html, null, null);
	}

	/**
	 * Añadir elemento a la lista
	 * 
	 * @param html
	 * @param icon
	 * @throws LocalizedException 
	 */
	public void addItem(final String html, final String icon) throws LocalizedException {
		addItem(html, icon, null);
	}

	/**
	 * Añadir elemento a la lista
	 * 
	 * @param html
	 * @param icon
	 * @throws LocalizedException 
	 */
	public void addItem(final String html, final String icon, Object value) throws LocalizedException {
		addItem(html, icon, value, null, false);
	}

	/**
	 * Añadir elemento a la lista
	 * 
	 * @param html
	 * @param icon
	 * @throws LocalizedException 
	 */
	public void addItem(final String html, final String icon, Object value, boolean select) throws LocalizedException {
		addItem(html, icon, value, null, select);
	}

	/**
	 * Añadir elemento a la lista
	 * 
	 * @param html
	 * @param icon
	 * @throws LocalizedException 
	 */
	public void addItem(final String html, final String icon, final Object value, final List<Component> components, boolean select) throws LocalizedException {

		addItem(new IItemListView() {

			@Override
			public String getIcon() {
				return icon;
			}

			@Override
			public String getHtml() {
				return html;
			}

			@Override
			public List<Component> getComponents() {
				return components;
			}

			@Override
			public Object getValue() {
				return value;
			}
			
			@Override
			public String toString() {
				return html;
			}
		}, select);
	}

	/**
	 * Comprueba si un elemento se ha seleccionado.
	 * 
	 * @param item
	 * @return
	 */
	public boolean isSelected(IItemListView item) {

		if (checks != null) {
			for (CheckBox chk : checks) {
				if (chk.getValue() && ((IItemListView) chk.getData()) == item) {
					return true;
				}
			}
		}
		else if (groups != null) {
			for (OptionGroup chk : groups) {
				if (chk.getValue() != null && ((IItemListView) chk.getData()) == item) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Obtener la lista de items seleccionados.
	 * 
	 * @return
	 */
	public List<IItemListView> getSelectedItems() {

		List<IItemListView> list = new ArrayList<IItemListView>();

		if (checks != null) {

			for (CheckBox chk : checks) {
				if (chk.getValue()) {
					list.add((IItemListView) chk.getData());
				}
			}
		}
		else if (groups != null) {

			for (OptionGroup chk : groups) {
				if (chk.getValue() != null) {
					list.add((IItemListView) chk.getData());
				}
			}
		}

		return list.size() == 0 ? null : list;
	}

	/**
	 * Obtener los objetos seleccionados.
	 * @return
	 */
	public Object[] getSelectedObjects() {
		
		List<IItemListView> list = getSelectedItems();
		
		if (list != null && list.size() > 0) {
			
			Object[] objects = new Object[list.size()];
			
			int i = 0;
			
			for(IItemListView item : list) {
			
				objects[i++] = item.getValue();
			}
			return objects;
		}
		
		return null;
	}	
	
	/**
	 * Comprobar si se ha seleccionado algún item.
	 * 
	 * @return
	 */
	public boolean hasSelectedItems() {

		for (CheckBox chk : checks) {
			if (chk.getValue()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Añadir Item.
	 * 
	 * @param iItemListView
	 * @throws LocalizedException 
	 */
	public abstract void addItem(IItemListView iItemListView, boolean select) throws LocalizedException;

	/**
	 * Cargar los componentes en el layout de acciones.
	 * 
	 * @param item
	 * @param layout
	 */
	public void loadComponents(IItemListView item, Layout layout) {

		for (Component component : item.getComponents()) {

			// Asociar item al componente.
			if (component instanceof AbstractComponent) {
				((AbstractComponent) component).setData(item);
			}
			layout.addComponent(component);
		}
	}

	// /**
	// * Añadir evento para desactivar el resto de opciones cuando se selecciona
	// una y option_group = true.
	// * @param check
	// */
	// protected void addListenerOptionGroup(final CheckBox check) {
	//
	// check.setImmediate(true);
	//
	// check.addListener(new ClickListener() {
	// @Override
	// public void buttonClick(ClickEvent event) {
	// unselect(check);
	// }
	// });
	// }

	/**
	 * Añadir evento para desactivar el resto de opciones cuando se selecciona una
	 * y option_group = true.
	 * 
	 * @param check
	 */
	protected void addListenerOptionGroup(final OptionGroup check) {

		check.setImmediate(true);

		check.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 2950907737796595619L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (processOption != null)
					return;

				processOption = check;

				unselect(check);

				processOption = null;
			}
		});

	}

	//
	// /**
	// * Desseleccionar todos los elementos excepto el pasado como parámetro.
	// * @param check
	// */
	// protected void unselect(CheckBox check) {
	//
	// for(CheckBox chk : checks) {
	// if (chk != check) {
	// chk.setValue(false);
	// }
	// }
	// }

	/**
	 * Desseleccionar todos los elementos excepto el pasado como parámetro.
	 * 
	 * @param check
	 */
	protected void unselect(OptionGroup check) {

		for (OptionGroup chk : groups) {
			if (chk != check) {
				chk.setValue(null);
			}
		}
	}

	/**
	 * Obtener el valor seleccionado
	 * 
	 * @return
	 */
	public Object getValue() {

		List<IItemListView> list = getSelectedItems();

		return list != null && list.size() > 0 ? list.get(0).getValue() : null;
	}

	/**
	 * Añadir un nuevo item a la lista de checks
	 * 
	 * @param item
	 * @return
	 */
	public Component newItem(IItemListView item) {

		// Selección única
		if (option_group) {

			OptionGroup chk = new OptionGroup();
			Integer id = (Integer) chk.addItem();
			chk.setItemCaption(id, "");

			addListenerOptionGroup(chk);
			chk.setData(item);

			if (groups == null) {
				groups = new ArrayList<OptionGroup>();
			}
			groups.add(chk);

			chk.setSizeUndefined();
			return chk;
		}
		// Multiselección de items.
		else {

			if (checks == null) {
				checks = new ArrayList<CheckBox>();
			}
			CheckBox chk = new CheckBox();
			chk.setData(item);
			checks.add(chk);

			chk.setSizeUndefined();
			return chk;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void find(String text) {

		List<IItemListView> items = (List<IItemListView>) SearchUtil.findObjects(this.items, text);

		for (IItemListView item : this.items) {

			boolean visible = items != null ? items.contains(item) : StringUtils.isEmpty(text);

			setVisibleItem(item, visible);
		}
		
		clearOptions();
	}

	/**
	 * Eliminar elementos seleccionados.
	 */
	private void clearOptions() {

		if (checks != null) {

			for (CheckBox chk : checks) {
				if (chk.getValue()) {
					chk.setValue(null);
				}
			}
		}
		else if (groups != null) {

			for (OptionGroup chk : groups) {
				if (chk.getValue() != null) {
					chk.setValue(null);
				}
			}
		}
	}

	/**
	 * Mostar u ocultar item en el layout de items.
	 * 
	 * @param item
	 */
	protected abstract void setVisibleItem(IItemListView item, boolean visible);

	/**
	 * Seleccionar todos los elemeentos
	 */
	public void selectAll(boolean select) {
		if (checks != null) {
			for (CheckBox chk : checks) {
				chk.setValue(select);
			}
		}
	}
	
}
