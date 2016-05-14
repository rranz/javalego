package com.javalego.store.ui.login;

import java.io.Serializable;

import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
import com.javalego.store.items.IMember;
import com.javalego.store.items.impl.Developer;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.login.LoginPresenter;
import com.javalego.ui.mvp.login.LoginPresenter.AutenticatedListener;
import com.javalego.ui.mvp.login.LoginServices;
import com.javalego.ui.mvp.profile.impl.BasicProfile;
import com.javalego.ui.mvp.register.IRegisterView;
import com.javalego.ui.mvp.register.RegisterPresenter;
import com.javalego.ui.mvp.register.RegisterPresenter.RegisterListener;
import com.javalego.ui.vaadin.component.util.Html;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.view.editor.LoginView;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * LOGIN Contenido de la interfaz de usuario cuando el usuario no está conectado
 * todavía.
 */
public class LoginScreen extends CssLayout {

	private static final long serialVersionUID = 7860268799962629346L;

	/**
	 * Listener de validación de acceso al sistema.
	 */
	private LoginListener loginListener;

	private VerticalLayout centeringLayout;

	private RegisterView registerView;

	private LoginView loginView;

	/**
	 * Constructor
	 * 
	 * @param loginListener
	 * @throws LocalizedException
	 */
	public LoginScreen(LoginListener loginListener) throws LocalizedException {

		this.loginListener = loginListener;
		buildUI();
	}

	private void buildUI() throws LocalizedException {

		addStyleName("login-screen");

		// login form, centered in the available part of the screen
		Component loginForm = buildLoginForm();

		// layout to center login form when there is sufficient screen space
		// - see the theme for how this is made responsive for various screen
		// sizes
		centeringLayout = new VerticalLayout();
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
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	private Component buildLoginForm() throws LocalizedException {

		if (loginView != null) {
			return loginView;
		}

		loginView = new LoginView();

		RegisterPresenter registerPresenter = new RegisterPresenter(new RegisterServices(), new RegisterView());

		registerPresenter.setListener(new RegisterListener() {
			@Override
			public void registered() {
				loginListener.loginSuccessful();
			}

			@Override
			public void discard() {
				showLoginView();
			}
		});

		LoginPresenter p = new LoginPresenter(new LoginServices(), loginView).setRegister(registerPresenter);

		p.load();

		p.setListener(new AutenticatedListener() {
			@Override
			public void valid() {
				
				loginListener.loginSuccessful();
				
				// Buscar el miembro cuyo código de usuario coincida con el principal de login
				IMember member;
				try {
					member = StoreAppContext.getCurrent().getDataServices().findMember(SecurityContext.getCurrent().getServices().getPrincipal().toString());
					if (member instanceof Developer) {
						
						Developer d = (Developer)member;
						
						// Crear profile en base a los datos del developer localizado en la tienda.
						BasicProfile profile = new BasicProfile();
						profile.setFirstname(d.getFirstName());
						profile.setLastname(d.getLastName());
						profile.setEmail(d.getEmail());
						profile.setPhone(d.getPhone());
						profile.setPhoto(d.getPhoto());
						
						// Establecer el perfil de usuario en la sesión
						SecurityContext.getCurrent().getUserSession().setProfile(profile);
					}
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}

			@Override
			public void register(IRegisterView view) {
				try {
					showRegisterView(view);
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});
		loginView.addStyleName("login-form");

		return loginView;
	}

	/**
	 * Mostrar la vista de registro de usuarios
	 * 
	 * @param view
	 * @throws LocalizedException
	 */
	private void showRegisterView(IRegisterView view) throws LocalizedException {

		if (registerView == null) {
			registerView = (RegisterView) view;
			registerView.load();
			registerView.addStyleName("register-form");
			registerView.setWidth("380px");
		}
		updateView(registerView);
	}

	/**
	 * Mostrar login view
	 * @throws LocalizedException 
	 */
	private void showLoginView() {
		try {
			updateView(buildLoginForm());
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

	/**
	 * Actualizar la vista del panel para conmutar entre login y registro de usuarios.
	 * @param view
	 */
	private void updateView(Component view) {
		
		centeringLayout.removeAllComponents();
		centeringLayout.addComponent(view);
		centeringLayout.setComponentAlignment(view, Alignment.MIDDLE_CENTER);
	}
	
	/**
	 * Datos informativos de la aplicación.
	 * 
	 * @return
	 */
	private CssLayout buildLoginInformation() {

		CssLayout loginInformation = new CssLayout();

		loginInformation.setStyleName("login-information");

		Label loginInfoText = new Label("<h3><strong><font color=white>JAVALEGO</font>" + Html.getSpace(2) + "</strong><font color=white>Store</font></h3><h1>" + UIContext.getText(LocaleStore.LOGIN)
				+ "</h1>" + UIContext.getText(LocaleStore.LOGIN_DATA), ContentMode.HTML);

		loginInformation.addComponent(loginInfoText);

		return loginInformation;
	}

	public interface LoginListener extends Serializable {
		void loginSuccessful();
	}
}
