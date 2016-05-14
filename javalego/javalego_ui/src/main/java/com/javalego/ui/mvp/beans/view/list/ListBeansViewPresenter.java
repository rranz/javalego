package com.javalego.ui.mvp.beans.view.list;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.IBeansViewPresenter;
import com.javalego.ui.mvp.beans.view.list.IListBeansView.ListBeansViewListener;

/**
 * Presenter para mostrar una lista de beans simple.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public class ListBeansViewPresenter<T> implements ListBeansViewListener<T>, IBeansViewPresenter<T> {

	/**
	 * Listener del visualizador de beans de la lista.
	 */
	private IListBeansViewModel<T> model;

	/**
	 * Vista de visualización de beans.
	 */
	private IListBeansView<T> view;

	/**
	 * Observer para notificar la edición de un bean o cambios en su
	 * visualización (filtros, ...)
	 */
	private ListBeansViewObserver<T> observer;

	/**
	 * Constructor
	 * 
	 * @param listener
	 *            Este listener se usa como modelo de obtención de datos. Se usa
	 *            actualmente para el editor de beans que contiene diferentes
	 *            vistas de beans (tabla, list, ...)
	 * @param view
	 */
	public ListBeansViewPresenter(IListBeansViewModel<T> model, IListBeansView<T> view) {
		this.model = model;
		this.view = view;
		view.setListener(this);
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public IListBeansView<T> getView() {
		return view;
	}

	@Override
	public Collection<T> getBeans(String filter, String order) throws LocalizedException {

		return model.getBeans(filter, order);
	}

	@Override
	public Class<T> getBeanClass() {

		return model.getBeanClass();
	}

	@Override
	public void editBean(T bean) throws LocalizedException {

		if (observer != null) {
			observer.editBean(bean);
		}
	}

	/**
	 * Aplicar filtro de selección de registros.
	 * 
	 * @param statement
	 * @throws LocalizedException
	 */
	@Override
	public void applyFilter(String filter) throws LocalizedException {

		view.reloadBeans(model.getBeans(filter, null));
	}

	/**
	 * Eliminar el filtro actual
	 * 
	 * @throws LocalizedException
	 */
	@Override
	public void removeCurrentFilter() throws LocalizedException {

		view.reloadBeans(model.getBeans(null, null));
	}

	public ListBeansViewObserver<T> getObserver() {
		return observer;
	}

	public void setObserver(ListBeansViewObserver<T> observer) {
		this.observer = observer;
	}

}
