package com.javalego.erp;

import java.io.Serializable;

import com.javalego.erp.model.Texts;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.login.LoginPresenter;
import com.javalego.ui.mvp.login.LoginPresenter.AutenticatedListener;
import com.javalego.ui.mvp.login.LoginServices;
import com.javalego.ui.mvp.register.IRegisterView;
import com.javalego.ui.vaadin.component.util.Html;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.editor.LoginView;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * LOGIN
 * Contenido de la interfaz de usuario cuando el usuario no está conectado todavía.
 */
public class LoginScreen extends CssLayout {

	private static final long serialVersionUID = 7860268799962629346L;

	/**
	 * Listener de validación de acceso al sistema.
	 */
	private LoginListener loginListener;

	/**
	 * Constructor
	 * @param loginListener
	 */
	public LoginScreen(LoginListener loginListener) {
		
		this.loginListener = loginListener;
		buildUI();
	}

	private void buildUI() {
		
		addStyleName("login-screen");

		// login form, centered in the available part of the screen
		Component loginForm = buildLoginForm();

		// layout to center login form when there is sufficient screen space
		// - see the theme for how this is made responsive for various screen
		// sizes
		VerticalLayout centeringLayout = new VerticalLayout();
		centeringLayout.setStyleName("centering-layout");
		centeringLayout.addComponent(loginForm);
		centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

		// information text about logging in
		CssLayout loginInformation = buildLoginInformation();

		addComponent(centeringLayout);
		addComponent(loginInformation);
	}

	/**
	 * Editor de login
	 * @return
	 */
	private Component buildLoginForm() {

		LoginView view = new LoginView();

		LoginPresenter p = new LoginPresenter(new LoginServices(), view);
		p.setListener(new AutenticatedListener() {
			@Override
			public void valid() {
				loginListener.loginSuccessful();
			}
			@Override
			public void register(IRegisterView view) {
			}
		});
		view.addStyleName(CssVaadin.getShadow());
		view.addStyleName("login-form");
		return view;
	}

	/**
	 * Datos informativos de la aplicación.
	 * @return
	 */
	private CssLayout buildLoginInformation() {

		CssLayout loginInformation = new CssLayout();

		loginInformation.setStyleName("login-information");

		Label loginInfoText = new Label("<h3><strong><font color=white>JAVALEGO</font>" + Html.getSpace(2) + "</strong><font color=white>ERP Demo</font></h3><h1>" + UIContext.getText(Texts.LOGIN) + "</h1>" + UIContext.getText(Texts.LOGIN_DATA), ContentMode.HTML);

		loginInformation.addComponent(loginInfoText);

		return loginInformation;
	}

	public interface LoginListener extends Serializable {
		void loginSuccessful();
	}
}
