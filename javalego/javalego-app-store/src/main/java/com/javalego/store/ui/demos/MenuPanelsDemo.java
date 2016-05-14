package com.javalego.store.ui.demos;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.listeners.ItemChangeEvent;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.MenuPresenter;
import com.javalego.ui.menu.MenuPresenterListener;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.menu.MenuPanels;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

/**
 * Demo de paneles
 * 
 * @author ROBERTO RANZ
 */
public class MenuPanelsDemo extends CustomComponent implements View {

	private static final long serialVersionUID = 478668114126589439L;

	public static final String NAME = "MENUPANELSDEMO";

	/**
	 * Constructor
	 * 
	 * @param navigator
	 */
	public MenuPanelsDemo() throws LocalizedException {

		MenuPanels mp = new MenuPanels();
		mp.addStyleName(CssVaadin.getShadow());
		
		MenuPresenter p = new MenuPresenter(new MenuPanelsModel(), mp);
		p.addItemChangeListener(new MenuPresenterListener() {
			@Override
			public void propertyChange(ItemChangeEvent<IMenuItem> event) {
			}
		});		
		
		mp.setWidth("340px");
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);

		layout.addComponent(mp);

		setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
