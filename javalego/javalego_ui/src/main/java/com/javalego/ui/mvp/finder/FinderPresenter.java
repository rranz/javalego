package com.javalego.ui.mvp.finder;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.impl.SelectActionEditor;
import com.javalego.ui.mvp.finder.IFinderView.IFinderViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter de búsqueda de objetos seleccionables.
 * 
 * Representa un buscador con una funcionalidad muy concreta que puede ser usarse en 
 * multitud de contextos. Ej.: acción de búsqueda en un campo en edición.
 * 
 * @see SelectActionEditor
 * 
 * @author ROBERTO RANZ
 *
 */
public class FinderPresenter<T> implements IFinderViewListener<T>, IPresenter {

	private IFinderModel<T> model;
	private IFinderView<T> view;

	public FinderPresenter(IFinderModel<T> finderModel, IFinderView<T> finderView) {
		this.model = finderModel;
		this.view = finderView;
		this.view.setListener(this);
	}
	
	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public void setValue(T value) throws LocalizedException {
		model.setValue(value);
	}

	@Override
	public IFinderView<T> getView() {
		return view;
	}

	@Override
	public Collection<T> getObjects() throws LocalizedException {
		return model.getObjects();
	}

	@Override
	public String[] getProperties() {
		return model.getProperties();
	}

	@Override
	public String[] getHeaders() {
		return model.getHeaders();
	}

	@Override
	public Key getTitle() {
		return model.getTitle();
	}

}
