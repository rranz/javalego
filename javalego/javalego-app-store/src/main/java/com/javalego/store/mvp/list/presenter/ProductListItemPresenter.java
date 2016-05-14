package com.javalego.store.mvp.list.presenter;

import java.util.ArrayList;
import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.mvp.list.AbstractListItemPresenter;
import com.javalego.store.mvp.list.ListItemView;

/**
 * Lista de productos de un proyecto.
 * 
 * @author ROBERTO RANZ
 */
public class ProductListItemPresenter extends AbstractListItemPresenter<IProduct> {

	private ListItemView<IProduct> view;

	private StoreDataServices services;

	private IProject project;

	private Collection<IProduct> products;

	/**
	 * Constructor
	 * 
	 * @param view
	 */
	public ProductListItemPresenter(StoreDataServices services, IProject project, ListItemView<IProduct> view) throws LocalizedException {
		this.view = view;
		this.services = services;
		this.project = project;

		if (project != null) {
			products = project.getProducts();
		}

		view.setListener(this);
		load();
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public ListItemView<IProduct> getView() {
		return view;
	}

	@Override
	public synchronized Collection<IProduct> getItems() {

		if (products == null) {
			products = new ArrayList<IProduct>();
		}

		return products;
	}

	@Override
	public IProduct getInstance() throws LocalizedException {

		return services.newInstanceProduct(project);
	}

	@Override
	public boolean isInsertable() {

		return true && !view.isInsertable();
	}

	@Override
	public Colors getColor(IProduct item) throws LocalizedException {

		return services.getColor(item);
	}

	@Override
	public Icon getIcon(IProduct bean) {
		return services.getIcon(bean);
	}

	@Override
	public Collection<IProduct> getBeans() throws LocalizedException {
		return view.getBeans();
	}

	@Override
	public boolean isReadOnly(IProduct bean) {
		return services.isReadOnly(bean);
	}

	@Override
	public void save(IProduct bean) {
	}

	@Override
	public void delete(IProduct bean) {
	}

	@Override
	public boolean isFullWidth() {
		return false;
	}

}
