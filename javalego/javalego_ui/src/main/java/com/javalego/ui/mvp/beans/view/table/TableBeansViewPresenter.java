package com.javalego.ui.mvp.beans.view.table;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.IBeansViewPresenter;
import com.javalego.ui.mvp.beans.view.list.IListBeansView;
import com.javalego.ui.mvp.beans.view.list.IListBeansView.ListBeansViewListener;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView.BeansEditorViewListener;

/**
 * Presenter para mostrar una lista de beans simple.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public class TableBeansViewPresenter<T> implements ListBeansViewListener<T>, IBeansViewPresenter<T> {

	/**
	 * Listener del editor de beans necesario para notificar la edición del bean
	 * de la lista.
	 */
	private BeansEditorViewListener<T> beansEditorListener;

	private IListBeansView<T> view;

	public TableBeansViewPresenter(BeansEditorViewListener<T> beansEditorListener, IListBeansView<T> view) {
		this.beansEditorListener = beansEditorListener;
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
		return beansEditorListener.getBeans(filter, order);
	}

	@Override
	public Class<T> getBeanClass() {
		return beansEditorListener.getBeanClass();
	}

	@Override
	public void editBean(T bean) throws LocalizedException {
		if (beansEditorListener != null) {
			beansEditorListener.editBean(bean);
		}
	}

	/**
	 * Aplicar filtro de selección de registros.
	 * @param statement
	 * @throws LocalizedException 
	 */
	@Override
	public void applyFilter(String filter) throws LocalizedException {
		view.reloadBeans(beansEditorListener.getBeans(filter,  null));
	}

	/**
	 * Eliminar el filtro actual
	 * @throws LocalizedException 
	 */
	@Override
	public void removeCurrentFilter() throws LocalizedException {
		view.reloadBeans(beansEditorListener.getBeans(null,  null));
	}

}
