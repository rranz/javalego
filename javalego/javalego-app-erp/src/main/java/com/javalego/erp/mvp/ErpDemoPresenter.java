package com.javalego.erp.mvp;

import com.javalego.erp.mvp.IErpDemoView.IErpDemoViewListener;
import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
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
public class ErpDemoPresenter implements IPresenter, IErpDemoViewListener {

	private IErpDemoView view;

	public static final String LOGOUTVIEW = "logout", LOGINVIEW = "login", HOMEVIEW = "home";

	public ErpDemoPresenter(IErpDemoView view) {
		this.view = view;
		view.setListener(this);
	}

	@Override
	public void load() {
		loadView();
	}

	/**
	 * Cargar la vista principal de los módulos
	 */
	private void loadView() {

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
	public IErpDemoView getView() {
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
