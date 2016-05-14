package com.javalego.store.mvp.list;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IBaseItem;
import com.javalego.ui.patterns.IView;

/**
 * Vista de una lista de componentes
 * 
 * @author ROBERTO RANZ
 */
public interface IListItemView<T extends IBaseItem> extends IView {

	/**
	 * Métodos implementados que proporcionan datos y ejecutan servicios del
	 * Presenter y que son utilizados por la vista (mvp pattern).
	 * 
	 * @author ROBERTO RANZ
	 */
	public interface ListItemViewListener<T extends IBaseItem> extends ListItemViewObserver<T>  {

		public static enum State {
			EDITING, INSERTING, CANCELED, SAVED
		}
		
		/**
		 * Estado de edición.
		 * @return
		 */
		State getState();
		
		/**
		 * Obtener la lista de productos de una categoría o tipo.
		 * 
		 * @param menuItem
		 * @return
		 * @throws LocalizedException 
		 */
		Collection<T> getItems() throws LocalizedException;

		/**
		 * Obtener una instancia de un item en base a una tipología
		 * 
		 * @param key
		 * @return
		 */
		T getInstance() throws LocalizedException;

		/**
		 * Incluye la opción insertar nuevos componentes de esta tipología.
		 * (Ej.: Productos no tiene esta opción ya que se insertan desde los
		 * proyectos y los proyectos sí tienen esta opción)
		 * 
		 * @param key
		 * @return
		 */
		boolean isInsertable();
		
		/**
		 * Obtener el color asociado al item pasado como parámetro.
		 * @param bean
		 * @return
		 * @throws LocalizedException 
		 */
		Colors getColor(T bean) throws LocalizedException;

		/**
		 * Icon asociado al bean
		 * @param bean
		 * @return
		 */
		Icon getIcon(T bean);

		/**
		 * Obtener la lista de beans o items editados en la vista.
		 * @return
		 * @throws LocalizedException 
		 */
		Collection<T> getBeans() throws LocalizedException;

		/**
		 * Comprobar acceso al bean para su edición.
		 * @return
		 */
		boolean isReadOnly(T bean);
		
		/**
		 * Persistir bean
		 * @param bean
		 * @throws LocalizedException
		 */
		void save(T bean) throws LocalizedException;
		
		/**
		 * Eliminar bean
		 * @param bean
		 * @throws LocalizedException
		 */
		void delete(T bean) throws LocalizedException;

		/**
		 * Visualizar el bean al tamaño máximo de la ventana para visualizarlos en modo lista. (ej.: news).
		 * @return
		 */
		boolean isFullWidth();
	}

	/**
	 * Establece el listener en la vista para utilizar los datos y servicios
	 * proporcionados por el Presenter que son necesarios para la vista (mvp
	 * pattern).
	 * 
	 * @param listener
	 */
	public void setListener(ListItemViewListener<T> listener);

}
