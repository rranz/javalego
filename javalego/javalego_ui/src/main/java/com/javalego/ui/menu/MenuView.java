package com.javalego.ui.menu;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.patterns.IView;

/**
 * Vista de menú básica
 * 
 * @author ROBERTO RANZ
 */
public interface MenuView extends IView {
	
	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del Presenter y que son utilizados por la vista (mvp pattern).
	 * @author ROBERTO RANZ
	 */
	public interface MenuViewListener {
		
		/**
		 * Items principales
		 * @return
		 * @throws LocalizedException 
		 */
		List<IMenuItem> getMainItems() throws LocalizedException;
		
		/**
		 * SubItems
		 * @param menuItem
		 * @return
		 */
		List<IMenuItem> getSubItems(IMenuItem menuItem);
		
		/**
		 * Cabecera
		 * @return
		 */
		IMenuItem getHeader();
		
		/**
		 * Cambio de item de menú
		 * @param menuItem
		 */
		void changeItemListener(IMenuItem menuItem);
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios proporcionados por el Presenter que son necesarios para la vista (mvp pattern).
	 * @param listener
	 */
	void setListener(MenuViewListener listener);
	
	/**
	 * Seleccionar un item de menú
	 * @param menuItem
	 */
	void select(IMenuItem menuItem);

	/**
	 * Seleccionar un item de menú
	 * @param menuItem
	 */
	void select(Key name);

	/**
	 * Cambiar el icono de un item de menú.
	 * @param item
	 * @param icon
	 */
	void changeIcon(IMenuItem item, Icon icon);

	/**
	 * Desmarcar item de menú seleccionado
	 */
	void unselect();

	/**
	 * Obtiene el menú actualmente seleccionado
	 * @return
	 */
	IMenuItem getSelectedItem();
	
	/**
	 * Añadir un nuevo elemento de menú
	 * @param item
	 */
	void addItem(IMenuItem item);
	
	/**
	 * Eliminar item de menú.
	 * @param item
	 */
	void removeItem(IMenuItem item);

}
