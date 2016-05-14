package com.javalego.store.mvp.list.presenter;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IScreenShot;
import com.javalego.store.items.impl.ScreenShot;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.mvp.list.AbstractListItemPresenter;
import com.javalego.store.mvp.list.ListItemView;

/**
 * Lista de comentarios
 * 
 * @author ROBERTO RANZ
 */
public class ScreenShotListItemPresenter extends AbstractListItemPresenter<IScreenShot> {

	private ListItemView<IScreenShot> view;

	private StoreDataServices services;

	private Collection<IScreenShot> screenShots;
	
	/**
	 * Constructor
	 * 
	 * @param view
	 */
	public ScreenShotListItemPresenter(StoreDataServices services, Collection<IScreenShot> screenShots, ListItemView<IScreenShot> view) throws LocalizedException {
		this.view = view;
		this.services = services;
		this.screenShots = screenShots;
		view.setListener(this);
		load();
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public ListItemView<IScreenShot> getView() {
		return view;
	}

	@Override
	public Collection<IScreenShot> getItems() {

		return (Collection<IScreenShot>) screenShots;
	}

	@Override
	public IScreenShot getInstance() throws LocalizedException {
		return new ScreenShot();
	}

	@Override
	public boolean isInsertable() {

		return true && !view.isInsertable();
	}

	@Override
	public Colors getColor(IScreenShot item) throws LocalizedException {
		return services.getColor(item);
	}

	@Override
	public Icon getIcon(IScreenShot bean) {
		return services.getIcon(bean);
	}

	@Override
	public Collection<IScreenShot> getBeans() throws LocalizedException {
		return view.getBeans();
	}
	
	@Override
	public boolean isReadOnly(IScreenShot bean) {
		return services.isReadOnly(bean);
	}

	@Override
	public void save(IScreenShot bean) {
	}

	@Override
	public void delete(IScreenShot bean) {
	}

	@Override
	public boolean isFullWidth() {
		return false;
	}

}
