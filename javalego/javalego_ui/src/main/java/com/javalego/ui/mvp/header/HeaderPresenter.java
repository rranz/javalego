package com.javalego.ui.mvp.header;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.header.IHeaderView.HeaderViewListener;
import com.javalego.ui.patterns.IPresenter;
import com.javalego.ui.patterns.IView;

/**
 * Presenter cambio de idioma de la sesión de usuario.
 * 
 * @author ROBERTO RANZ
 */
public class HeaderPresenter implements HeaderViewListener, IPresenter {
	
	IHeaderModel model;
	
	IHeaderView view;

	/**
	 * Incluir componente adiciónal de barra de opciones en el componente de cabecera.
	 */
	private IPresenter toolbar;

	/**
	 * Constructor
	 * @param controller
	 * @param model
	 * @param view
	 */
	public HeaderPresenter(IHeaderModel model, IHeaderView view, IPresenter toolbar) {
		this.model = model;
		this.view = view;
		this.toolbar = toolbar;
		view.setListener(this);
	}

	@Override
	public void load() throws LocalizedException {
		view.load();
	}

	@Override
	public IHeaderModel getModel() {
		return model;
	}

	@Override
	public IHeaderView getView() {
		return view;
	}

	public IPresenter getToolBar() {
		return toolbar;
	}
	
	@Override
	public boolean hasToolBar() {
		return toolbar != null;
	}

	@Override
	public IView getToolBarView() throws LocalizedException {
		return toolbar.getView();
	}

}
