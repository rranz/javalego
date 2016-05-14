package com.javalego.ui.menu;

import java.util.ArrayList;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.listeners.ItemChangeEvent;
import com.javalego.ui.menu.MenuView.MenuViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter cambio de idioma de la sesión de usuario.
 * 
 * @author ROBERTO RANZ
 */
public class MenuPresenter implements MenuViewListener, IPresenter {

	IMenuModel model;

	private List<MenuPresenterListener> listeners = new ArrayList<MenuPresenterListener>();

	MenuView view;

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 * @param view
	 */
	public MenuPresenter(IMenuModel model, MenuView view) throws LocalizedException {
		this.model = model;
		this.view = view;
		view.setListener(this);
		load();
	}

	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 * @param view
	 * @param load
	 */
	public MenuPresenter(IMenuModel model, MenuView view, boolean load) throws LocalizedException {
		this.model = model;
		this.view = view;
		view.setListener(this);
		if (load) {
			load();
		}
	}

	@Override
	public List<IMenuItem> getMainItems() throws LocalizedException {
		return model.getMainItems();
	}

	/**
	 * Obtener todos los items de menú de forma recursiva. (Actualmente sólo
	 * recupera items de niveles 1 y 2).
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	public List<IMenuItem> getAllItems() throws LocalizedException {

		List<IMenuItem> items = new ArrayList<IMenuItem>();

		for (IMenuItem item : model.getMainItems()) {
			items.add(item);
			for (IMenuItem child : item.getSubItems()) {
				items.add(child);
			}
		}
		return items.size() > 0 ? items : null;
	}

	@Override
	public List<IMenuItem> getSubItems(IMenuItem menuItem) {
		return model.getSubItems(menuItem);
	}

	@Override
	public IMenuItem getHeader() {
		return model.getHeader();
	}

	/**
	 * Añadir listener de cambio de menú
	 * 
	 * @param listener
	 */
	public void addItemChangeListener(MenuPresenterListener listener) {
		listeners.add(listener);
	}

	@Override
	public void changeItemListener(IMenuItem menuItem) {

		for (MenuPresenterListener listener : listeners) {
			listener.propertyChange(new ItemChangeEvent<IMenuItem>(menuItem));
		}

	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public MenuView getView() {
		return view;
	}

	/**
	 * Seleccionar un item de menú.
	 * 
	 * @param iMenuItem
	 */
	public void select(IMenuItem menuItem) {
		view.select(menuItem);
	}

	/**
	 * Seleccionar un item de menú.
	 * 
	 * @param iMenuItem
	 */
	public void select(Key name) {
		view.select(name);
	}

	/**
	 * Cambiar el icono de un elemento del menú.
	 * 
	 * @param itemChange
	 * @param check
	 */
	public void changeIcon(IMenuItem item, IconEditor icon) {
		view.changeIcon(item, icon);
	}

	/**
	 * Desmarcar item seleccionado.
	 */
	public void unselect() {
		view.unselect();
	}

	/**
	 * Obtener la lista de Items de un tipo de clase pasada por parámetro.
	 * 
	 * @param menuItemType
	 * @return
	 * @throws LocalizedException 
	 */
	public List<IMenuItem> getMenuItems(Class<?> menuItemType) throws LocalizedException {

		List<IMenuItem> list = new ArrayList<IMenuItem>();

		for (IMenuItem mi : getMainItems()) {

			for (IMenuItem si : mi.getSubItems()) {

				if (si.getClass() == menuItemType) {
					list.add(si);
				}
			}
		}
		return list.size() > 0 ? list : null;
	}

}
