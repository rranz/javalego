package com.javalego.ui.vaadin.view.editor;

import com.javalego.exception.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleSecurity;
import com.javalego.ui.UIContext;
import com.javalego.ui.mvp.login.ILoginView;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

/**
 * Login
 * 
 * @author ROBERTO RANZ
 */
public class LoginView extends FormLayout implements ILoginView {

	private static final long serialVersionUID = -5950661776986239078L;

	private TextField user;

	private PasswordField password;

	private LoginViewListener listener;

	private String title;

	/**
	 * Constructor
	 */
	public LoginView() {
	}

	/**
	 * Constructor
	 */
	public LoginView(String title) {

		this.title = title;
	}

	@Override
	public void load() throws LocalizedException {

		// form.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		setSizeUndefined();
		setMargin(false);

		if (title != null) {
			Label label = new Label("<strong>" + title + "</strong>", ContentMode.HTML);
			label.addStyleName(CssVaadin.getColored());
			addComponent(label);
		}

		user = new TextField();
		user.setCaption(UIContext.getText(LocaleSecurity.USER));
		user.setWidth(15, Unit.EM);
		user.setIcon(FontAwesome.USER);

		password = new PasswordField();
		password.setCaption(UIContext.getText(LocaleSecurity.PASSWORD));
		password.setWidth(15, Unit.EM);
		password.setIcon(FontAwesome.LOCK);

		final ButtonExt btnSignIn = new ButtonExt(LocaleSecurity.SIGN_IN).friendlyColor();

		btnSignIn.setDisableOnClick(true);
		btnSignIn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btnSignIn.addStyleName(CssVaadin.getMargin10());

		btnSignIn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 2392907721325097771L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if (listener != null) {
						listener.login(String.valueOf(user.getValue()), String.valueOf(password.getValue()));
					}
					setEnabled(false);
				}
				catch (LocalizedException e) {
					user.focus();
					user.selectAll();
					MessagesUtil.error(UIContext.getText(CommonErrors.FAILED_LOGIN), e.getLocalizedMessage());
				}
				finally {
					btnSignIn.setEnabled(true);
				}
			}
		});

		ButtonExt btnRegister = null;

		// Registro de usuarios
		if (listener.hasRegister()) {

			btnRegister = new ButtonExt(LocaleSecurity.REGISTER);
			btnRegister.addStyleName(CssVaadin.getMargin10());

			btnRegister.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 2392907721325097771L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						register();
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			});
		}

		addComponent(user);
		addComponent(password);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addComponent(btnSignIn);
		if (btnRegister != null) {
			toolbar.addComponent(btnRegister);
		}
		addComponent(toolbar);

		// Poner el focus en el usuario.
		user.focus();
	}

	/**
	 * Habilitar la vista de registro de usuarios.
	 * @throws LocalizedException 
	 */
	protected void register() throws LocalizedException {
		listener.register();
	}

	@Override
	public void setData(String user, String password) {

		this.user.setValue(user != null ? user : "");
		this.password.setValue(password != null ? password : "");
	}

	@Override
	public void setListener(LoginViewListener listener) {
		this.listener = listener;
	}

//	/**
//	 * Autenticar usuario
//	 * 
//	 * @param event
//	 * @param listener
//	 *            Evento que se ejecuta al pulsar el botón de login
//	 */
//	public static void dialog(LayoutClickEvent event, final AutenticatedListener listener) {
//
//		final Window window = new Window(UIContext.getText(LocaleEditor.LOGIN));
//
//		LoginPresenter p = new LoginPresenter(new LoginServices(), new LoginView());
//		try {
//			p.getView().load();
//			p.setListener(new AutenticatedListener() {
//				@Override
//				public void valid() {
//					window.close();
//					if (listener != null) {
//						listener.valid();
//					}
//				}
//				@Override
//				public void register(IRegisterView view) {
//				}
//			});
//
//			window.setContent((Component) p.getView());
//			window.setWidth("300px");
//			window.setStyleName(Reindeer.WINDOW_LIGHT);
//			window.setClosable(true);
//			window.setResizable(false);
//			window.setDraggable(false);
//			if (event != null) {
//				window.setPositionX(event.getClientX() - event.getRelativeX() - 260);
//				window.setPositionY((event.getClientY() - event.getRelativeY()) + 40);
//			}
//			else {
//				window.setModal(true);
//				window.center();
//			}
//			window.setCloseShortcut(KeyCode.ESCAPE, null);
//
//			window.setVisible(true);
//
//			UI.getCurrent().removeWindow(window);
//			UI.getCurrent().addWindow(window);
//		}
//		catch (LocalizedException e) {
//			MessagesUtil.error(e);
//		}
//	}

}
