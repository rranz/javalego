package com.javalego.ui.vaadin.view.menu;

import java.util.Iterator;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.MenuView;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.button.css.MenuPanelsCss;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

/**
 * Vista de menú en formato Accordion.
 * 
 * @author ROBERTO RANZ
 */
public class MenuViewAccordion extends CustomComponent implements MenuView {

	private static final long serialVersionUID = 1L;

	private MenuViewListener listener;

	private boolean onlyMenu = true;

	private VerticalLayout layout;

	/**
	 * Constructor
	 */
	public MenuViewAccordion(boolean onlyMenu) {
		this.onlyMenu = onlyMenu;
	}

	@Override
	public void load() throws LocalizedException {

		layout = new VerticalLayout();

		addStyleName(CssVaadin.getShadowRounded());

		if (listener.getHeader() != null) {

			IMenuItem header = listener.getHeader();

			// Abrir o cerrar submenú.
			CssButton bi = new CssButton(header.getTitle(), header.getDescription(), header.getColor(), header.getIcon());
			layout.addComponent(bi);
		}

		for (IMenuItem item : listener.getMainItems()) {

			// Abrir o cerrar submenú.
			final CssButton bi = new CssButton(item.getTitle(), item.getDescription(), item.getColor(), item.getIcon());

			bi.addLayoutClickListener(new LayoutClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void layoutClick(LayoutClickEvent event) {

					if (!event.isDoubleClick()) {

						for (Iterator<?> iterator = layout.iterator(); iterator.hasNext();) {

							Object o = iterator.next();

							if (o != null && o instanceof CssButton) {

								CssButton type = (CssButton) o;

								if (type.getData() != null) {

									if (type == bi) {
										((VerticalLayout) type.getData()).setVisible(!((VerticalLayout) bi.getData()).isVisible());
									}
									else if (onlyMenu) {
										((VerticalLayout) type.getData()).setVisible(false);
									}
								}
							}
						}
					}
				}
			});
			layout.addComponent(bi);

			// Layout para submenús
			if (item.getSubItems().size() > 0) {

				VerticalLayout slayout = new VerticalLayout();

				slayout.setVisible(false);

				for (final IMenuItem si : item.getSubItems()) {

					final CssButton ab = new CssButton(si.getTitle(), si.getDescription(), si.getColor() == null ? Colors.GRAY : si.getColor(), si.getIcon());

					// Notificar cambio de menú
					ab.addLayoutClickListener(new LayoutClickListener() {
						private static final long serialVersionUID = -4147129604881541203L;

						@Override
						public void layoutClick(LayoutClickEvent event) {
							if (!event.isDoubleClick())
								listener.changeItemListener(si);
						}
					});

					ab.addStyleName(MenuPanelsCss.getLevel2(si.getColor()));

					ab.setDescription(si.getDescription());

					slayout.addComponent(ab);
				}
				layout.addComponent(slayout);

				bi.setData(slayout);
			}
		}

		setCompositionRoot(layout);
	}

	@Override
	public void setListener(MenuViewListener listener) {
		this.listener = listener;
	}

	@Override
	public void select(IMenuItem menuItem) {
		// TODO Auto-generated method stub
	}

	@Override
	public void select(Key name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeIcon(IMenuItem item, Icon icon) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unselect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMenuItem getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItem(IMenuItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeItem(IMenuItem item) {
		// TODO Auto-generated method stub
		
	}

}
