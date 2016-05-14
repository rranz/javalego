package com.javalego.ui.mvp.editor.detail;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.editor.detail.IDetailEditorView.DetailEditorViewListener;
import com.javalego.ui.patterns.IPresenter;

/**
 * Presenter de detalle (iterable list) de un objeto. Puede ser una relación JPA o una simple lista de una
 * propiedad del objeto maestro u owner.
 * 
 * @author ROBERTO RANZ
 */
public class DetailEditorPresenter implements DetailEditorViewListener, IPresenter {

	protected IDetailEditorModel<?> model;

	protected IDetailEditorView view;
	
	//private static final Logger logger = Logger.getLogger(CollectionEditorPresenter.class);
	
	/**
	 * Constructor
	 * 
	 * @param controller
	 * @param model
	 * @param view
	 */
	public DetailEditorPresenter(IDetailEditorModel<?> model, IDetailEditorView view) {
		this.model = model;
		this.view = view;
		view.setListener(this);
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public Collection<?> getData() {
		return model.getData();
	}

	@Override
	public IDetailEditorView getView() {
		return view;
	}

	@Override
	public Class<?> getBeanClass() {
		return model.getBeanClass();
	}

	@Override
	public Object getNewObject() {
		return model.getNewObject();
	}

}
