package com.javalego.store.mvp.detail;

import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.INews;
import com.javalego.store.items.impl.News;
import com.javalego.store.model.MenuModel;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.mvp.detail.IDetailItemView.DetailItemViewListener;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter de visualización del item seleccionado correspondienda a uno de los menús principales.
 * 
 * @author ROBERTO RANZ
 */
public class DetailItemPresenter<T extends IBaseItem> implements DetailItemViewListener<T>, IPresenter {
	
	IDetailItemView<T> view;
	
	StoreDataServices services;

	/**
	 * Constructor
	 * @param controller
	 * @param model
	 * @param view
	 */
	public DetailItemPresenter(StoreDataServices services, IDetailItemView<T> view) throws LocalizedException {
		this.view = view;
		this.services = services;
		view.setListener(this);
		load();
	}

	/**
	 * Cargar detalle de un menú
	 * @param itemChange
	 * @throws LocalizedException 
	 */
	public void load(IMenuItem menuItem) throws LocalizedException {
		view.load(menuItem);
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public IDetailItemView<T> getView() {
		return view;
	}

	@Override
	public IMenuModel getMainMenuModel() throws LocalizedException {

		IMenuModel menuModel = new IMenuModel() {
			@Override
			public List<IMenuItem> getSubItems(IMenuItem menuItem) {
				return null;
			}
			@Override
			public List<IMenuItem> getMainItems() {
				return MenuModel.getHomeOptions();
			}
			@Override
			public IMenuItem getHeader() {
				return null;
			}
		};
		return menuModel;
	}

	@Override
	public List<News> getNews() throws LocalizedException {
		return services.getAllNews();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getItems(Key key) throws LocalizedException {
		return (Collection<T>) services.getItems(key, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getInstance(Key key) throws LocalizedException {
		return (T) services.getInstance(key);
	}

	@Override
	public boolean isInsertable(Key key) {
		return services.isInsertable(key);
	}

	@Override
	public StoreDataServices getDataService() {
		return services;
	}

}
