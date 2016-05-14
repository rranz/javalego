package com.javalego.store.ui.components;

import java.util.Collection;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.IProvider;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.util.ResourceUtils;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Muestra la relación de proveedores que implementa el producto o projecto.
 * 
 * @author ROBERTO RANZ
 * 
 */
class ProvidersUI extends CssLayout {

	private static final long serialVersionUID = -7947324836860474741L;

	private Collection<IProvider> providers;

	@Override
	protected String getCss(Component c) {
		return "margin-top: 4px; padding: 0px; display: padding-right: 16px; inline-block;";
	}

	protected ProvidersUI(Collection<IProvider> providers) {
		this.providers = providers;
		load();
	}

	public void load() {

		setSizeUndefined();

		if (providers != null) {
			for (IProvider provider : providers) {
				addItem(provider.getIcon(), provider.getName() + " " + provider.getTitle(), provider.getUrl());
			}
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

				private static final long serialVersionUID = -4608985020291926384L;

				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (!event.isDoubleClick())
						ResourceUtils.openUrl(url);
				}
			});
		}
		addComponent(css);
	}
}
