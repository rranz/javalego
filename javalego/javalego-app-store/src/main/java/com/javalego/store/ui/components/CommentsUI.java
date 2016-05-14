package com.javalego.store.ui.components;

import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.util.Html;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Comentarios
 * 
 * @author ROBERTO RANZ
 * 
 */
class CommentsUI extends CssLayout {

	private static final long serialVersionUID = -7763286144948559833L;

	@Override
	protected String getCss(Component c) {
		return "margin: 8px; padding-right: 16px; display: inline-block;";
	}

	protected CommentsUI() {
		addComponent(new Html.H3(UIContext.getText(LocaleStore.COMMENTS)).colored());
	}

}
