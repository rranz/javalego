package com.javalego.store.ui.components;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.javalego.store.items.IScreenShot;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.util.Html;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Editar y mostrar una lista de imágenes.
 * 
 * @author ROBERTO RANZ
 * 
 */
class ScreenShotsUI<T extends IScreenShot> extends CssLayout {

	private static final long serialVersionUID = 1591435024668979614L;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ScreenShotsUI.class);

	Collection<T> screenshots;
	
	@Override
	protected String getCss(Component c) {
		return "margin: 8px; padding-right: 16px; display: inline-block;";
	}

	public ScreenShotsUI(Collection<T> screenshots) {
		this.screenshots = screenshots;
		load();
	}

	public void load() {

		addComponent(new Html.H3(UIContext.getText(LocaleStore.SCREENSHOTS)).colored());

		setWidth("100%");
	}

}
