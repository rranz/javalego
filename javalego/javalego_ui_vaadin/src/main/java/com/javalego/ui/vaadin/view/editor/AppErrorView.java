package com.javalego.ui.vaadin.view.editor;

import com.javalego.model.locales.LocaleEditor;
import com.javalego.ui.UIContext;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Vista para mostrar un error al ejecutar la sesión de usuario.
 * 
 * {@link com.vaadin.navigator.Navigator}.
 */
public class AppErrorView extends VerticalLayout implements View {

	private static final long serialVersionUID = 8422875383162283951L;

	public AppErrorView(String error) {

		setMargin(true);

		setSpacing(true);

		Label header = new Label(FontAwesome.WARNING.getHtml() + " " + UIContext.getText(LocaleEditor.WARN), ContentMode.HTML);
		header.addStyleName(ValoTheme.LABEL_HUGE);

		addComponent(header);

		addComponent(new Label("<h3><font color=red>" + error + "</font></h3>", ContentMode.HTML));
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
	}
}
