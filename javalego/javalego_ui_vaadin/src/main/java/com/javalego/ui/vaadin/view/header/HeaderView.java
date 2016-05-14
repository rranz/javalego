package com.javalego.ui.vaadin.view.header;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.header.IHeaderView;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * Vista Header para Vaadin.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class HeaderView extends CustomComponent implements IHeaderView {

	private static final long serialVersionUID = 5430844406785353339L;

	private HeaderViewListener listener;

	// private Component toolbar;

	@Override
	public void load() throws LocalizedException {

		//if (listener.getModel().getTitle() != null || listener.hasToolBar()) {
			grid();
//		}
//		else {
//			vertical();
//		}
	}

	private void grid() throws LocalizedException {

		GridLayout layout = new GridLayout(3, 1);
		layout.setMargin(true);
		layout.setSpacing(true);

		layout.setWidth("100.0%");

		layout.setStyleName(Reindeer.LAYOUT_WHITE);

		if (listener.getModel().getTitle() != null) {
			Label s = getLabelTitle();
			layout.addComponent(s, 1, 0);
			layout.setComponentAlignment(s, Alignment.MIDDLE_LEFT);
		}

		// LOGO y título solución.
		if (listener.getModel().getIcon() != null) {
			Component s = ResourceIconsVaadin.getCurrent().getComponent(listener.getModel().getIcon());
			layout.addComponent(s, 0, 0);
			layout.setComponentAlignment(s, Alignment.MIDDLE_LEFT);
		}

		if (listener.hasToolBar()) {
			Component toolbar = (Component) listener.getToolBarView();
			layout.addComponent(toolbar, 2, 0);
			layout.setComponentAlignment(toolbar, Alignment.MIDDLE_RIGHT);
		}
		
		layout.setColumnExpandRatio(2, 2);

		setCompositionRoot(layout);

	}

	private Label getLabelTitle() {
		Label s = new Label(listener.getModel().getTitle(), ContentMode.HTML);
		s.setSizeUndefined();
		return s;
	}

	/**
	 * Posición vertical de sus componentes.
	 * 
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unused")
	private void vertical() throws LocalizedException {

		CssLayout layout = new CssLayout() {
			private static final long serialVersionUID = 5240317626978692035L;

			@Override
			protected String getCss(Component c) {
				return "margin: 0px; padding: 4px; padding-right: 25px; display: inline-block;";
			}
		};
		
		setStyleName(Reindeer.LAYOUT_WHITE);
		setWidth("100.0%");

		// LOGO y título solución.
		if (listener.getModel().getIcon() != null) {
			layout.addComponent(ResourceIconsVaadin.getCurrent().getComponent(listener.getModel().getIcon()));
		}

		if (listener.getModel().getTitle() != null) {
			layout.addComponent(getLabelTitle());
		}
	}

	/**
	 * Posición horizontal de sus componentes
	 * 
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unused")
	private void horizontal() throws LocalizedException {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(new MarginInfo(false, true, false, false));
		layout.setWidth("100.0%");
		layout.setStyleName(Reindeer.LAYOUT_WHITE);

		VerticalLayout v = new VerticalLayout();
		v.setMargin(false);
		v.setSizeUndefined();

		if (listener.getModel().getTitle() != null) {
			v.addComponent(getLabelTitle());
		}

		// LOGO y título solución.
		if (listener.getModel().getIcon() != null) {
			layout.addComponent(ResourceIconsVaadin.getCurrent().getComponent(listener.getModel().getIcon()));
		}

		layout.addComponent(v);
		layout.setComponentAlignment(v, Alignment.MIDDLE_LEFT);

		if (listener.hasToolBar()) {
			Component toolbar = (Component) listener.getToolBarView();
			toolbar.setSizeUndefined();
			layout.addComponent(toolbar);
			layout.setComponentAlignment(toolbar, Alignment.MIDDLE_RIGHT);
			layout.setExpandRatio(toolbar, 1);
		}
		else {
			layout.setExpandRatio(v, 1);
		}

		setCompositionRoot(layout);
	}

	// private Component getToolBar() {
	// return toolbar;
	// }

	@Override
	public void setListener(HeaderViewListener listener) {
		this.listener = listener;
	}

}
