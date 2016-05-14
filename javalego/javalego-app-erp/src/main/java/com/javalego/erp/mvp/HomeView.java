package com.javalego.erp.mvp;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Vista incial.
 * 
 * @author ROBERTO RANZ
 *
 */
public class HomeView extends VerticalLayout implements View {

	public HomeView() {

		String text = "<h2>ERP Demo for JAVALEGO</h2>";
		String text2 = "Esta aplicación muestra cómo desarrollar aplicaciones empresariales adaptadas a plataformas web y mobile.<p>" +
					   "Incluye una serie de módulos sencillos típicos de cualquier ERP y cuyo principal objetivo es mostrar su usabilidad y adaptación para construir cualquier módulo empresarial que se requiera.";
		
		Label l = new Label(text, ContentMode.HTML);
		l.addStyleName(ValoTheme.LABEL_COLORED);
		
		addComponent(l);
		addComponent(new Label(text2, ContentMode.HTML));
		
		setMargin(true);
		setSpacing(true);
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
	
}
