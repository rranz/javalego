package com.javalego.store.mvp.list;

import java.util.Collection;

import com.javalego.data.DataContext;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.model.locales.LocaleWarnings;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.util.MessagesUtil;

/**
 * Presenter de visualización del item seleccionado correspondienda a uno de los
 * menús principales.
 * 
 * @author ROBERTO RANZ
 */
public class ListItemPresenter<T extends IBaseItem> extends AbstractListItemPresenter<T> {

	/**
	 * Vista de items
	 */
	private ListItemView<T> view;

	/**
	 * Servicios de negocio
	 */
	private StoreDataServices services;

	/**
	 * Código del tipo de item de la lista
	 */
	private Key key;

	/**
	 * Aplicar persistencia
	 */
	private boolean persist = true;

	/**
	 * Realizar la notificación de persistencia al usuario.
	 */
	private boolean notify = true;

	private String filter;

	/**
	 * Constructor
	 * 
	 * @param view
	 */
	public ListItemPresenter(StoreDataServices services, Key key, ListItemView<T> view) throws LocalizedException {
		this.view = view;
		this.services = services;
		this.key = key;
		view.setListener(this);
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public ListItemView<T> getView() {
		return view;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getItems() throws LocalizedException {

		return (Collection<T>) services.getItems(key, filter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getInstance() throws LocalizedException {

		return (T) services.getInstance(key);
	}

	@Override
	public boolean isInsertable() {

		return services.isInsertable(key) && !view.isInsertable();
	}

	@Override
	public Colors getColor(T item) throws LocalizedException {

		return services.getColor(item);
	}

	@Override
	public Icon getIcon(T bean) {

		return services.getIcon(bean);
	}

	@Override
	public Collection<T> getBeans() throws LocalizedException {

		return view.getBeans();
	}

	@Override
	public boolean isReadOnly(T bean) {

		return services.isReadOnly(bean);
	}

	@Override
	public void save(T bean) throws LocalizedException {

		if (persist && bean instanceof Entity) {

			// Persistencia
			if (DataContext.getProvider() != null) {
				DataContext.getProvider().save((Entity<?>) bean);
			}

			if (notify) {
				MessagesUtil.information(UIContext.getText(LocaleWarnings.SAVED));
			}
		}
	}

	@Override
	public void delete(T bean) throws LocalizedException {

		if (persist && bean instanceof Entity) {
			
			// Persistencia
			if (DataContext.getProvider() != null) {
				DataContext.getProvider().delete((Entity<?>) bean);
			}

			if (notify) {
				MessagesUtil.information(UIContext.getText(LocaleWarnings.REMOVED));
			}
		}
	}

	/**
	 * Realizar persistencia al aceptar los cambios
	 * 
	 * @return
	 */
	public boolean isPersist() {
		return persist;
	}

	/**
	 * Realizar persistencia al aceptar los cambios
	 * 
	 * @param persist
	 */
	public void setPersist(boolean persist) {
		this.persist = persist;
	}

	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	@Override
	public boolean isFullWidth() {
		return key == LocaleStore.NEWS;
	}

	/**
	 * Establecer un filtro sobre los items actuales.
	 * @param filter
	 * @throws LocalizedException 
	 */
	public void setFilter(String filter) throws LocalizedException {
		this.filter = filter;
		view.load();
	}

}
