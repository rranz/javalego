package com.javalego.store.mvp.list;

import com.javalego.store.items.IBaseItem;
import com.javalego.store.mvp.list.IListItemView.ListItemViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter de visualización del item seleccionado correspondienda a uno de los
 * menús principales.
 * 
 * @author ROBERTO RANZ
 */
public abstract class AbstractListItemPresenter<T extends IBaseItem> implements ListItemViewListener<T>, IPresenter {

	/**
	 * Estado de edición
	 */
	private State state;

	/**
	 * Observer de los eventos de edición e inserción de items en la vista.
	 */
	private ListItemViewObserver<T> observer;

	@Override
	public void inserting(T bean) {
		if (observer != null) {
			observer.inserting(bean);
		}
		state = State.INSERTING;
	}

	@Override
	public void editing(T bean) {
		if (observer != null) {
			observer.editing(bean);
		}
		state = State.EDITING;
	}

	@Override
	public void canceled(T bean) {
		if (observer != null) {
			observer.canceled(bean);
		}
		state = State.CANCELED;
	}

	@Override
	public void saved(T bean) {
		if (observer != null) {
			observer.saved(bean);
		}
		state = State.SAVED;
	}

	public ListItemViewObserver<T> getObserver() {
		return observer;
	}

	public void setObserver(ListItemViewObserver<T> observer) {
		this.observer = observer;
	}

	@Override
	public State getState() {
		return state;
	}
	
	/**
	 * Comprobar si la vista está en modo edición.
	 * @return
	 */
	public boolean isEditing() {
		return state == State.INSERTING || state == State.EDITING;
	}

	
}
