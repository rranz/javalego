package com.javalego.ui.vaadin.component.panels;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;

/**
 * Panel basado en Css
 * 
 * @author ROBERTO RANZ
 */
public class PanelCss extends CssLayout {

	private static final long serialVersionUID = 1L;

	private Layout bodyLayout;

	private String title;

	private String description;

	private Icon icon;

	private Colors color;

	/**
	 * Constructor
	 * 
	 * @param iconName
	 * @throws LocalizedException
	 */
	public PanelCss(Layout bodyLayout, String title, String description, Colors color, Icon icon) {
		this.icon = icon;
		this.bodyLayout = bodyLayout;
		this.title = title;
		this.color = color;
		this.description = description;
		load();
	}

//	@Override
//	protected String getCss(Component c) {
//		return "margin: 0px;";
//	}

	/**
	 * Cargar configuración de panel
	 * 
	 * @throws LocalizedException
	 */
	private void load() {
		fill(bodyLayout, title, description, color, icon);
	}

	/**
	 * Generar componente UI.
	 */
	private void fill(final Layout bodyLayout, String title, String description, Colors color, Icon icon) {

		final CssButton button = new CssButton(title, description, color, icon, true);
		button.setWidth("100%");
		button.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -4059224013778241846L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick()) {
					bodyLayout.setVisible(!bodyLayout.isVisible());
					if (!bodyLayout.isVisible())
						button.removeCssBorder();
					else
						button.addCssBorder();
				}
			}
		});
		addComponent(button);
		button.addCssBorder();

		addComponent(bodyLayout);
		addStyleName(CssVaadin.getShadow());
	}

	public Layout getBodyLayout() {
		return bodyLayout;
	}

	public void setBodyLayout(Layout bodyLayout) {
		this.bodyLayout = bodyLayout;
	}

}
