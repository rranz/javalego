package com.javalego.store.mvp.home;

import java.util.Locale;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.store.model.MenuModel;
import com.javalego.store.ui.locale.LocaleStore;
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
import com.javalego.ui.vaadin.view.menu.MenuPanels.Style;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * Vista principal de la aplicación JavaLego.
 * 
 * @author ROBERTO RANZ
 *
 */
public class StoreView extends CustomComponent implements View, IStoreView {

	private static final long serialVersionUID = 1313888869272543764L;

	private static final String LOCALE_SPANISH = "es";

	public static final String NAME = "STORE";

	private static final String HOMEVIEW = "home";

	private CssButton btnLogin;

	private HomeLayout layout;

	private Navigator navigator;

	private MenuPresenter menuPresenter;

	private ContentView homeView;

	private IStoreViewListener listener;

	private Window langWindow;

	/**
	 * Vista basada en HomeLayout
	 * 
	 * Constructor
	 */
	public StoreView() {
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

		homeView = new ContentView();

		// Vista inicial y de finalización de sesión
		navigator.addView(HOMEVIEW, homeView);
		navigator.setErrorView(ErrorView.class);
		navigator.navigateTo(HOMEVIEW);

		// Eliminar la selección actual del item de menú
		navigator.addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 5817516352248807020L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				if (event.getViewName().equals(HOMEVIEW)) {
					menuPresenter.unselect();
				}
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
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
		btnLogin = new CssButton(IconEditor.USEROK, UIContext.getText(LocaleEditor.PROFILE));
		btnLogin.addCssIcDrawer();
		btnLogin.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -8048254260567322924L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					login(event);
				}
			}
		});

		headerOptions.addComponent(btnLogin);

		// Cambio de idioma
		CssButton btn = new CssButton(IconEditor.FLAG, UIContext.getText(LocaleEditor.CHANGE_LANG));
		btn.addCssIcDrawer();
		btn.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -6931322183206313416L;

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


	/**
	 * Pié de página
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Component getFooter() {

		VerticalLayout layout = new VerticalLayout();

		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setStyleName("cssbutton-silver");

		Label copyright = new Label(UIContext.getText(LocaleStore.WARNING), ContentMode.HTML);
		layout.addComponent(copyright);

		copyright = new Label("© 2014 JAVELEGO S.L. All rights reserved.", ContentMode.HTML);
		layout.addComponent(copyright);

		Link link = new Link(UIContext.getText(LocaleStore.USE), new ClassResource("/doc/TerminosUso.docx"));
		layout.addComponent(link);
		copyright.setSizeUndefined();

		return layout;
	}

	@Override
	public void load() throws LocalizedException {

		setSizeFull();

		try {
			MenuPanels mp = new MenuPanels(Style.PANELS, true);

			layout = new HomeLayout("<h3><strong><font color=white>JAVALEGO</font>" + Html.getSpace(2) + "</strong><font color=white>Store</font></h3>", mp, getHeaderOptions());

			menuPresenter = new MenuPresenter(new MenuModel(), mp);
			menuPresenter.addItemChangeListener(new MenuPresenterListener() {
				@Override
				public void propertyChange(ItemChangeEvent<IMenuItem> event) {
					try {
						// Actualizar el item seleccionado del menú.
						if (homeView.load(event.getItemChange())) {
							navigator.navigateTo(HOMEVIEW);
							layout.update();
						}
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			});

			// Configurar las vistas de los editores para el navigator
			configNavigator();

			setCompositionRoot(layout);
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}

	}

	@Override
	public void setListener(IStoreViewListener listener) {
		this.listener = listener;
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
			private static final long serialVersionUID = -3805525874581066039L;

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
			private static final long serialVersionUID = -1479756124226580162L;

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
}
