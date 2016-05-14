package com.javalego.ui.vaadin.view.menu;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.MenuView;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Menú representado mediante una lista de opciones seleccionables.
 * 
 * Solo muestra los menús definidos del primer nivel. Si desea un menú de varios
 * niveles utilice {@link MenuPanels}.
 * 
 * @author ROBERTO RANZ
 */
public class ListMenu extends CssLayout implements MenuView {

	private static final long serialVersionUID = 1L;

	private MenuViewListener listener;

	private CssButton selectedButton;

	/**
	 * Constructor
	 */
	public ListMenu() {
	}

	/**
	 * Constructor
	 * @param listener
	 */
	public ListMenu(MenuViewListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void load() throws LocalizedException {

		removeAllComponents();
		
		// Cabecera
		if (listener.getHeader() != null) {

			final IMenuItem header = listener.getHeader();

			// Notificar el cambio de item de menú
			listener.changeItemListener(header);

			// Abrir o cerrar submenú.
			CssButton bi = new CssButton(header.getTitle(), header.getDescription(), header.getColor(), header.getIcon());
			bi.setWidth("100%");
			bi.addCssDisabled();

			addComponent(bi);
		}

		// Lista de menús.
		List<IMenuItem> items = listener.getMainItems();
		for (final IMenuItem item : items) {
			addComponent(getComponent(item));
		}
	}

	/**
	 * Obtener el componente de cada item de la lista.
	 * 
	 * @param item
	 * @return
	 */
	private CssButton getComponent(final IMenuItem item) {

		// Abrir o cerrar submenú.
		final CssButton bi = new CssButton(item.getTitle(), item.getDescription(), item.getColor(), item.getIcon(), item instanceof MenuItemVaadin ? ((MenuItemVaadin)item).getResourceIcon() : null);
		bi.setWidth("100%");
		bi.setData(item);
		bi.getLabel().addStyleName(ValoTheme.LABEL_SMALL);
		// if (item != items.get(items.size() - 1)) {
		bi.addCssBorder();
		// }
		bi.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -5659347016095378258L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					selectItem(bi);
					listener.changeItemListener(item);
				}
			}
		});
		return bi;
	}

	/**
	 * Seleccionar button aplicando estilo de selección.
	 * 
	 * @param ab
	 */
	private void selectItem(final CssButton ab) {

		if (ab.getParent() != null) {
			if (selectedButton != null) {
				selectedButton.removeCssSelectedBackground();
			}
			selectedButton = ab;
			selectedButton.addCssSelectedBackground();
		}
	}

	/**
	 * Recargar datos del item seleccionado
	 */
	public void refreshSelectedItem() {
		if (selectedButton != null) {
			selectedButton.getLabel().setValue(getSelectedItem().getTitle());
		}
	}

	@Override
	public IMenuItem getSelectedItem() {
		return selectedButton != null ? (IMenuItem) selectedButton.getData() : null;
	}

	@Override
	public void setListener(MenuViewListener listener) {
		this.listener = listener;
	}

	@Override
	public void select(IMenuItem menuItem) {
		// TODO Auto-generated method stub
	}

	@Override
	public void select(Key name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeIcon(IMenuItem item, Icon icon) {
		// TODO Auto-generated method stub
	}

	@Override
	public void unselect() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addItem(IMenuItem item) {
		if (item != null) {
			addComponent(getComponent(item));
		}
	}

	@Override
	public void removeItem(IMenuItem item) {
		if (item != null) {
			removeComponent(findComponent(item));
		}
	}

	/**
	 * Buscar el componente que tiene el item de menú.
	 * @param item
	 * @return
	 */
	private Component findComponent(IMenuItem item) {

		for(int i = 0; i < getComponentCount(); i++)
		{
			if (getComponent(i) instanceof AbstractComponent) {
				IMenuItem c = (IMenuItem)((AbstractComponent)getComponent(i)).getData();
				if (item == c) {
					return getComponent(i); 
				}
			}
		}
		return null;		
	}

}
