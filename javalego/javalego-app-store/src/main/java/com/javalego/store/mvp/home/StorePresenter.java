package com.javalego.store.mvp.home;

import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
import com.javalego.store.mvp.home.IStoreView.IStoreViewListener;
import com.javalego.ui.UIContext;
import com.javalego.ui.patterns.IPresenter;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

/**
 * Presenter ERP Demo
 * 
 * @author ROBERTO RANZ
 *
 */
public class StorePresenter implements IPresenter, IStoreViewListener {

	private IStoreView view;

	public static final String LOGOUTVIEW = "logoutview", LOGINVIEW = "loginview";

	public StorePresenter(IStoreView view) throws LocalizedException {
		this.view = view;
		view.setListener(this);
	}

	@Override
	public void load() throws LocalizedException {

		if (view != null) {
			try {
				view.load();
				UI.getCurrent().setContent((Component) view);
			}
			catch (LocalizedException e) {
				MessagesUtil.error(e);
			}
		}
	}

	@Override
	public boolean isAuthenticated() {
		return SecurityContext.getCurrent() != null ? SecurityContext.getCurrent().getServices().isAuthenticated() : false;
	}

	@Override
	public IStoreView getView() {
		return view;
	}

	@Override
	public void reload() {
		try {
			view.load();
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

	@Override
	public void logout() {
		UIContext.logout();
	}

}
