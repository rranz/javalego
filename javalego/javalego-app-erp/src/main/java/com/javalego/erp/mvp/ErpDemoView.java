package com.javalego.erp.mvp;

import java.util.Locale;

import com.javalego.erp.model.Menu;
import com.javalego.erp.model.MenuItemModule;
import com.javalego.erp.mvp.editor.EditorView;
import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.listeners.ItemChangeEvent;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.MenuPresenter;
import com.javalego.ui.menu.MenuPresenterListener;
import com.javalego.ui.mvp.login.LoginPresenter.AutenticatedListener;
import com.javalego.ui.mvp.register.IRegisterView;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.layout.home.HomeLayout;
import com.javalego.ui.vaadin.component.util.Html;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.editor.ErrorView;
import com.javalego.ui.vaadin.view.editor.ProfileView;
import com.javalego.ui.vaadin.view.menu.MenuPanels;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * Vista principal de la aplicación ERP de demostración de la arquitectura.
 * 
 * @author ROBERTO RANZ
 *
 */
public class ErpDemoView extends CustomComponent implements IErpDemoView {

	private static final String LOCALE_SPANISH = "es";

	// Listener MVP
	private IErpDemoViewListener listener;

	// Configuración del navigator Vaadin para incluir las vistas de los módulos
	// accesibles por el usuario.
	private Navigator navigator;

	// Layout principal responsive que incluye cabecera, menú y body para carga
	// de los componentes de los módulos abiertos.
	private HomeLayout layout;

	// Ventana de cambio de idioma
	private Window langWindow;

	// Botón de login. Actualmente, sólo disponible para acceder a los datos del
	// perfil de usuario. El login se realiza al iniciar la aplicación en una
	// vista independiente.
	private CssButton btnLogin;

	// Menú MVP de los módulos accesibles por el usuario.
	private MenuPresenter menuPresenter;

	@Override
	public void load() throws LocalizedException {

		if (layout != null) {
			layout.removeAllComponents();
		}

		setSizeFull();

		// Menú de los Módulos accesibles por el usuario.
		MenuPanels mp = new MenuPanels(MenuPanels.Style.LIST, true);
		try {
			// Crear Menu Presenter MVP
			menuPresenter = new MenuPresenter(new Menu(), mp);

			// Cargar el módulo asociado al nombre clave del item seleccionado
			menuPresenter.addItemChangeListener(new MenuPresenterListener() {
				@Override
				public void propertyChange(ItemChangeEvent<IMenuItem> event) {
					// Cargar el módulo asociado al nombre clave definido en
					// cada menú.
					navigator.navigateTo(event.getItemChange().getName().name());
					// Actualizar el item seleccionado del menú.
					layout.update();
				}
			});

			// Pantalla principal con opciones de cabecera, menú y body para
			// editar módulos.
			layout = new HomeLayout("<h3><strong><font color=white>JAVALEGO</font>" + Html.getSpace(2) + "</strong><font color=white>Demo ERP</font></h3>", mp, getHeaderOptions());

			// Configurar las vistas de los editores para el navigator
			configNavigator();

			setCompositionRoot(layout);

		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
			return;
		}
	}

	/**
	 * Configurar el acceso a los editores de cada uno de los módulos.
	 * 
	 * @param list
	 * 
	 * @param viewDisplay
	 * @throws LocalizedException
	 */
	private void configNavigator() throws LocalizedException {

		// Añadir vistas de los items del menú ERP
		navigator = new Navigator(UI.getCurrent(), layout.getContentContainer());

		UI.getCurrent().setNavigator(navigator);

		// Cargar las vistas de los items de editores
		for (IMenuItem item : menuPresenter.getAllItems()) {
			if (item instanceof MenuItemModule) {
				navigator.addView(item.getName().name(), new EditorView(((MenuItemModule) item).getEditor()));
			}
		}

		// Vista inicial y de finalización de sesión
		navigator.setErrorView(ErrorView.class);
		navigator.addView(ErpDemoPresenter.HOMEVIEW, HomeView.class);

		// Eliminar la selección actual del item de menú
		navigator.addViewChangeListener(new ViewChangeListener() {
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				if (event.getViewName().equals(ErpDemoPresenter.HOMEVIEW)) {
					menuPresenter.unselect();
				}
			}
		});

		navigator.navigateTo(ErpDemoPresenter.HOMEVIEW);

	}

	/**
	 * Cabecera y opciones de la aplicación.
	 * 
	 * @return
	 */
	private CssLayout getHeaderOptions() {

		CssLayout headerOptions = new CssLayout();
		headerOptions.addStyleName("v-component-group");

		// Datos del perfil de usuario o login
		btnLogin = new CssButton(listener.isAuthenticated() ? IconEditor.USEROK : IconEditor.USER, UIContext.getText(listener.isAuthenticated() ? LocaleEditor.PROFILE : LocaleEditor.LOGIN));
		btnLogin.addCssIcDrawer();
		btnLogin.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					login(event);
				}
			}
		});
		headerOptions.addComponent(btnLogin);

		// Alertas
		CssButton btn = new CssButton(IconEditor.BELL, UIContext.getText(LocaleEditor.ALERTS));
		btn.addCssIcDrawer();
		btn.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					MessagesUtil.optionNotAvailable();
				}
			}
		});
		headerOptions.addComponent(btn);

		// Cambio de idioma
		btn = new CssButton(IconEditor.FLAG, UIContext.getText(LocaleEditor.CHANGE_LANG));
		btn.addCssIcDrawer();
		btn.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					changeLocale(event);
				}
			}
		});
		headerOptions.addComponent(btn);

		// btn = new CssButton(IconEditorOptions.IC_ACTION_OVERFLOW);
		// btn.addCssIcDrawer();
		// headerOptions.addComponent(btn);
		return headerOptions;
	}

	@Override
	public void setListener(IErpDemoViewListener listener) {
		this.listener = listener;
	}

	/**
	 * Diálogo de cambio de idioma
	 * 
	 * @param event
	 */
	private void changeLocale(LayoutClickEvent event) {

		if (langWindow != null) {
			UI.getCurrent().removeWindow(langWindow);
		}

		langWindow = new Window(UIContext.getText(LocaleEditor.CHANGE_LANG));
		VerticalLayout l = new VerticalLayout();
		l.setMargin(true);
		l.setSpacing(true);
		langWindow.setContent(l);
		langWindow.setWidth("230px");
		langWindow.setStyleName(Reindeer.WINDOW_LIGHT);
		langWindow.setClosable(true);
		langWindow.setResizable(false);
		langWindow.setDraggable(false);
		langWindow.setPositionX(event.getClientX() - event.getRelativeX() - 200);
		langWindow.setPositionY((event.getClientY() - event.getRelativeY()) + 40);
		langWindow.setCloseShortcut(KeyCode.ESCAPE, null);

		CssButton b = new CssButton(UIContext.getText(LocaleEditor.ENGLISH), "");
		if (!UIContext.getUserSessionLocale().getLanguage().equals(LOCALE_SPANISH)) {
			b.getLabel().addStyleName("colored");
		}
		l.addComponent(b);
		b.addStyleName(CssVaadin.getRounded());
		b.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					// Establecer el idioma en la sesión de usuario
					UIContext.setUserSessionLocale(Locale.ENGLISH);
					langWindow.close();
					// Recargar aplicación para activar el idioma seleccionado.
					listener.reload();
				}
			}
		});

		b = new CssButton(UIContext.getText(LocaleEditor.SPANISH), "");
		if (UIContext.getUserSessionLocale().getLanguage().equals(LOCALE_SPANISH)) {
			b.getLabel().addStyleName("colored");
		}

		b.addStyleName(CssVaadin.getRounded());
		l.addComponent(b);
		b.addLayoutClickListener(new LayoutClickListener() {
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					// Establecer el idioma en la sesión de usuario
					UIContext.setUserSessionLocale(new Locale(LOCALE_SPANISH));
					langWindow.close();
					// Recargar aplicación para activar el idioma seleccionado.
					listener.reload();
				}
			}
		});

		langWindow.setVisible(true);

		UI.getCurrent().removeWindow(langWindow);
		UI.getCurrent().addWindow(langWindow);
	}

	/**
	 * Diálogo login o datos del perfil de usuario si ya está autenticado.
	 * 
	 * @param event
	 */
	private void login(LayoutClickEvent event) {

		ProfileView.dialog(event, new AutenticatedListener() {
			@Override
			public void valid() {
				listener.logout();
			}

			@Override
			public void register(IRegisterView view) {
			}
		});
	}

	/**
	 * Actualizar button de login en base a la autentiación del usuario.
	 */
	protected void autenticated() {
		btnLogin.setDescription(UIContext.getText(LocaleEditor.PROFILE));
		btnLogin.setIcon(IconEditor.USEROK);
	}

	/**
	 * Actualizar button login al cerrar la sesión de usuario.
	 */
	protected void logout() {
		btnLogin.setDescription(UIContext.getText(LocaleEditor.LOGIN));
		btnLogin.setIcon(IconEditor.USER);
	}
}
