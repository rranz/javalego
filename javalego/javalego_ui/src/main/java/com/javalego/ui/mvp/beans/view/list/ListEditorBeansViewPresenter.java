package com.javalego.ui.mvp.beans.view.list;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.IBeansViewPresenter;
import com.javalego.ui.mvp.beans.view.list.IListBeansView.ListBeansViewListener;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansViewListener;

/**
 * Presenter para mostrar una lista de beans simple a partir de otro listener de
 * beans que representa el modelo.
 * 
 * Nota: se usa actualmente en BeansEditorPresenter para mostrar los beans en
 * este tipo de visualización (Ej.: tabla, lista, tiles, ...)
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public class ListEditorBeansViewPresenter<T> implements ListBeansViewListener<T>, IBeansViewPresenter<T> {

	/**
	 * Listener del visualizador de beans de la lista.
	 */
	private BeansViewListener<T> listener;

	/**
	 * Vista de visualización de beans.
	 */
	private IListBeansView<T> view;

	/**
	 * Constructor
	 * 
	 * @param listener
	 *            Este listener se usa como modelo de obtención de datos. Se usa
	 *            actualmente para el editor de beans que contiene diferentes
	 *            vistas de beans (tabla, list, ...)
	 * @param view
	 */
	public ListEditorBeansViewPresenter(BeansViewListener<T> listener, IListBeansView<T> view) {
		this.listener = listener;
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

		return listener.getBeans(filter, order);
	}

	@Override
	public Class<T> getBeanClass() {

		return listener.getBeanClass();
	}

	@Override
	public void editBean(T bean) throws LocalizedException {

		if (listener != null) {
			listener.editBean(bean);
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

		view.reloadBeans(listener.getBeans(filter, null));
	}

	/**
	 * Eliminar el filtro actual
	 * 
	 * @throws LocalizedException
	 */
	@Override
	public void removeCurrentFilter() throws LocalizedException {

		view.reloadBeans(listener.getBeans(null, null));
	}

}
