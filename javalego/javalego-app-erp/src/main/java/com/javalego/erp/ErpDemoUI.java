package com.javalego.erp;

import com.javalego.application.AppContext;
import com.javalego.erp.LoginScreen.LoginListener;
import com.javalego.erp.mvp.ErpDemoPresenter;
import com.javalego.erp.mvp.ErpDemoView;
import com.javalego.security.SecurityContext;
import com.javalego.ui.vaadin.view.editor.AppErrorView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("vaadin_valo")
@PreserveOnRefresh
public class ErpDemoUI extends UI {

	// private AccessControl accessControl = new BasicAccessControl();

	@Override
	protected void init(VaadinRequest request) {

		Responsive.makeResponsive(this);

		setLocale(request.getLocale());

		Page.getCurrent().setTitle(AppContext.getCurrent().getName());

		if (AppContext.getCurrent().getError() != null) {
			setContent(new AppErrorView(AppContext.getCurrent().getError().getLocalizedMessage()));
		}
		else {
			showErpDemo();
		}
	}

	/**
	 * Login o mostrar la vista principal de la aplicación.
	 */
	private void showErpDemo() {

		if (!SecurityContext.getCurrent().getServices().isAuthenticated()) {

			setContent(new LoginScreen(new LoginListener() {
				@Override
				public void loginSuccessful() {
					showMainView();
				}
			}));
		}
		else {
			showMainView();
		}
	}

	/**
	 * Mostrar la vista principal de la aplicación.
	 */
	private void showMainView() {

		new ErpDemoPresenter(new ErpDemoView()).load();

		getNavigator().navigateTo(getNavigator().getState());
	}

}
