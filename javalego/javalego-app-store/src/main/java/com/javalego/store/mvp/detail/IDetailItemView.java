package com.javalego.store.mvp.detail;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.INews;
import com.javalego.store.model.StoreDataServices;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.patterns.IView;

/**
 * Vista de menú básica
 * 
 * @author ROBERTO RANZ
 */
public interface IDetailItemView<T extends IBaseItem> extends IView {

	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del
	 * Presenter y que son utilizados por la vista (mvp pattern).
	 * 
	 * @author ROBERTO RANZ
	 */
	interface DetailItemViewListener<T extends IBaseItem> {

		/**
		 * Obtener la lista de productos de una categoría o tipo.
		 * 
		 * @param menuItem
		 * @return
		 * @throws LocalizedException 
		 */
		Collection<T> getItems(Key key) throws LocalizedException;

		/**
		 * Obtiene el menú principal.
		 * 
		 * @return
		 * @throws LocalizedException
		 */
		IMenuModel getMainMenuModel() throws LocalizedException;

		/**
		 * Obtiene las noticias
		 * 
		 * @return
		 * @throws LocalizedException 
		 */
		Collection<INews> getNews() throws LocalizedException;

		/**
		 * Obtener una instancia de un item en base a una tipología
		 * 
		 * @param key
		 * @return
		 */
		T getInstance(Key key) throws LocalizedException;

		/**
		 * Incluye la opción insertar nuevos componentes de esta tipología.
		 * (Ej.: Productos no tiene esta opción ya que se insertan desde los
		 * proyectos y los proyectos sí tienen esta opción)
		 * 
		 * @param key
		 * @return
		 */
		boolean isInsertable(Key key);

		/**
		 * Servicios de datos
		 * @return
		 */
		StoreDataServices getDataService();
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios
	 * proporcionados por el Presenter que son necesarios para la vista (mvp
	 * pattern).
	 * 
	 * @param listener
	 */
	public void setListener(DetailItemViewListener<T> listener);

	/**
	 * Cargar el componente asociada a un item de menú.
	 * 
	 * @param menuItem
	 * @return
	 * @throws LocalizedException
	 */
	public boolean load(IMenuItem menuItem) throws LocalizedException;

}
