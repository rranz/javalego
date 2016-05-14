package com.javalego.store.ui.components;

import java.net.URL;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.ICode;
import com.javalego.store.items.IDemo;
import com.javalego.store.items.ISocial;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;
import com.javalego.ui.patterns.IView;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.util.Html;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.component.util.ResourceUtils;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.fatalix.vaadin.addon.codemirror.CodeMirror;
import de.fatalix.vaadin.addon.codemirror.CodeMirrorLanguage;
import de.fatalix.vaadin.addon.codemirror.CodeMirrorTheme;

/**
 * Muestra el acceso a los diferentes recursos vinculados a un proyecto.
 * 
 * @author ROBERTO RANZ
 * 
 */
class ResourcesUI extends CssLayout {

	private static final Logger logger = Logger.getLogger(ResourcesUI.class);
	
	private static final long serialVersionUID = -8685644750373758639L;

	private ISocial social;

	private ICode code;

	private IDemo demo;
	
	private int index;

	@Override
	protected String getCss(Component c) {
		return "margin-left: " + (index++ < 2 ? "0" : "0") + "px; padding: 6px; display: inline-block;";
	}

	protected ResourcesUI(ISocial social, ICode code, IDemo demo) {
		this.social = social;
		this.code = code;
		this.demo = demo;
		load();
	}

	public void load() {

		addComponent(new Html.H3(UIContext.getText(LocaleStore.SOCIAL)).colored());
		
		setWidth("100%");

		// Código
		if (code != null && code.getDoc() != null) {
			addItem(MenuIcons2.BOOK, "Doc", code.getDoc());
		}

		if (demo != null) {
			String demoView = demo.getView();
			if (demoView != null) {
				addView(MenuIcons2.MONITOR, demoView);
			}
		}
		else if (demo != null && demo.getUrl() != null) {
			addItem(MenuIcons2.MONITOR, "Demo", demo.getUrl());
		}

		if (demo != null && demo.getUrlCode() != null) {
			showCode(MenuIcons2.SOURCECODE, "Source code", demo.getUrlCode());
		}

		if (code != null && code.getRepository() != null) {
			addItem(MenuIcons2.GITHUB, code.getRepository().toHtml(), code.getRepository().getUrl());
		}

		if (code != null && code.getMetrics() != null) {
			addItem(MenuIcons2.METRICS, "Metrics (SonarQube)", code.getMetrics());
		}

		// Social
		if (social != null && social.getWeb() != null) {
			addItem(MenuIcons2.WEB, "Web", social.getWeb());
		}

		if (social != null && social.getTwitter() != null) {
			addItem(MenuIcons2.TWITTER, "Twitter", social.getTwitter());
		}

		if (social != null && social.getForum() != null) {
			addItem(MenuIcons2.FORUM, "Forum", social.getForum());
		}

		if (social != null && social.getBlog() != null) {
			addItem(MenuIcons2.BLOG, "WordPress", social.getBlog());
		}

		if (social != null && social.getLinkedin() != null) {
			addItem(MenuIcons2.LINKEDIN, "Linkedin", social.getLinkedin());
		}

		if (social != null && social.getFacebook() != null) {
			addItem(MenuIcons2.FACEBOOK, "Facebook", social.getFacebook());
		}

		if (social != null && social.getGooglePlus() != null) {
			addItem(MenuIcons2.GOOGLEPLUS, "Google+", social.getGooglePlus());
		}

		if (social != null && social.getEmail() != null) {
			addItem(MenuIcons2.EMAIL, "Email", social.getEmail());
		}
	}

	/**
	 * Añadir item
	 * 
	 * @param social
	 * @param url
	 */
	private void addItem(Icon icon, String description, final String url) {

		CssButton css = new CssButton(icon, description);
		
		if (url != null) {
		
			css.addLayoutClickListener(new LayoutClickListener() {
				private static final long serialVersionUID = -5525259367098707470L;

				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (!event.isDoubleClick())
						ResourceUtils.openUrl(url);
				}
			});
		}
		addComponent(css);
	}

	/**
	 * Mostrar código de ejemplo
	 * 
	 * @param social
	 * @param code
	 */
	private void showCode(Icon icon, final String description, final String code) {

		CssButton css = new CssButton(icon, description);
		
		if (code != null) {
		
			css.addLayoutClickListener(new LayoutClickListener() {
				private static final long serialVersionUID = 6509702994047761029L;

				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (!event.isDoubleClick()) {
						windowCode(description, code);
					}
				}
			});
		}
		addComponent(css);
	}

	/**
	 * Mostrar el código de ejemplo.
	 * 
	 * @param title
	 * @param code
	 */
	protected void windowCode(String title, String code) {

		Window window = new Window(title);
		window.setVisible(true);
		window.center();
		window.setHeight(360, Unit.PIXELS);
		window.setWidth(720, Unit.PIXELS);
		// window.setStyleName(Reindeer.WINDOW_LIGHT);

		CodeMirror codeMirror = new CodeMirror();
		codeMirror.setSizeFull();
		codeMirror.setLanguage(CodeMirrorLanguage.DEFAULT);
		codeMirror.setTheme(CodeMirrorTheme.LESSER_DARK);
		codeMirror.setVisible(false);
		codeMirror.setReadOnly(true);

		window.setContent(codeMirror);

		UI.getCurrent().addWindow(window);
		try {
			//codeMirror.setCode(IOUtils.toString(Functions.getResource(code)));
			
			String url = Page.getCurrent().getLocation().getScheme() + ":" + Page.getCurrent().getLocation().getSchemeSpecificPart();
			
			url = url.replace("/home", code);
			
			String text = IOUtils.toString(new URL(url), Charsets.ISO_8859_1);
			
			codeMirror.setCode(text);			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR SOURCE CODE DEMO.", e);
		}
		codeMirror.setVisible(true);

	}

	/**
	 * Añadir view
	 * 
	 * @param social
	 * @param url
	 */
	private void addView(Icon icon, final String name) {

		CssButton css = new CssButton(icon, "DEMO VIEW: " + name);
		
		if (name != null) {
			css.addLayoutClickListener(new LayoutClickListener() {
				private static final long serialVersionUID = -2769615617800638384L;

				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (!event.isDoubleClick()) {
						try {
							showView(StoreAppContext.getCurrent().getDataServices().findView(name));
						}
						catch (LocalizedException e) {
							MessagesUtil.error(e);
						}
						//UI.getCurrent().getNavigator().addView(name, view);
						//UI.getCurrent().getNavigator().navigateTo(name);
					}
				}
			});
		}
		addComponent(css);
	}
	
	/**
	 * Mostrar el componente de la vista.
	 * @param view
	 * @throws LocalizedException
	 */
	public void showView(View view) throws LocalizedException {
		
		if (view instanceof IView) {
			((IView)view).load();
		}
		
		Window window = new Window("DEMO");
		window.setVisible(true);
		window.center();
		window.setHeight(90, Unit.PERCENTAGE);
		window.setWidth(90, Unit.PERCENTAGE);
		
		if (view instanceof Component) {
			window.setContent((Component)view);
			UI.getCurrent().addWindow(window);
		}
	}
}
