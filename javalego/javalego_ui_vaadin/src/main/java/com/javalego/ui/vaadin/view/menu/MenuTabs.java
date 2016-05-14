package com.javalego.ui.vaadin.view.menu;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.TabsMenuView;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.button.TabButton;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 * Menú basado en Tabs
 * 
 * @author ROBERTO RANZ
 */
public class MenuTabs extends CssLayout implements TabsMenuView {

	private static final String MENUTABS_CSS = "menutabs-";

	private static final long serialVersionUID = -7516627497646737309L;

	private MenuViewListener listener;

	private CssLayout layout;

	private Colors color;

	/**
	 * Menú seleccionado
	 */
	private CssButton menuSelected;

	/**
	 * Si incluimos el layout donde se irán cargando los componentes de cada
	 * tab. La sustitución de estos componentes se realizará de forma
	 * automática.
	 */
	private AbstractLayout tabLayout;

	/**
	 * Constructor
	 */
	public MenuTabs() {
	}

	/**
	 * Constructor
	 * 
	 * @param color
	 *            de la barra de botones
	 */
	public MenuTabs(Colors color) {
		this.color = color;
	}

	/**
	 * Constructor
	 * 
	 * @param tabLayout
	 *            Si incluimos el layout donde se irán cargando los componentes
	 *            de cada tab. La sustitución de estos componentes se realizará
	 *            de forma automática.
	 */
	public MenuTabs(AbstractLayout tabLayout) {
		this.tabLayout = tabLayout;
	}

	/**
	 * Constructor
	 * 
	 * @param tabLayout
	 *            Si incluimos el layout donde se irán cargando los componentes
	 *            de cada tab. La sustitución de estos componentes se realizará
	 *            de forma automática.
	 * @param color
	 *            de la barra de botones
	 */
	public MenuTabs(AbstractLayout tabLayout, Colors color) {
		this(color);
		this.tabLayout = tabLayout;
	}

	@Override
	public void load() throws LocalizedException {

		if (color != null) {
			addStyleName(MENUTABS_CSS + color.name().toLowerCase());
		}

		if (listener != null) {
			for (final IMenuItem item : listener.getMainItems()) {
				getLayout().addComponent(getTabButton(item));
			}
		}

		addComponent(getLayout());
	}

	/**
	 * Obtener el layout donde incluir los componentes de cada IMenuItem.
	 * 
	 * @return
	 */
	private synchronized CssLayout getLayout() {

		if (layout == null) {

			layout = new CssLayout() {
				private static final long serialVersionUID = -6276198195329679979L;

				@Override
				protected String getCss(Component c) {
					return "margin: 0px; vertical-align: top; display: inline-block;";
				}
			};
		}
		return layout;
	}

	/**
	 * Obtener el componente a partir del IMenuItem.
	 * 
	 * @param item
	 * @return
	 */
	private TabButton getTabButton(final IMenuItem item) {

		if (item.getColor() == null && color != null) {
			item.setColor(color);
		}

		TabButton bi = new TabButton(item.getTitle(), item.getDescription(), item.getColor(), item.getIcon());

		bi.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = -5506744127299424380L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick())
					selectTab((CssButton) event.getSource(), item);
			}
		});

		bi.setData(item);

		return bi;
	}

	/**
	 * Seleccionar elemento
	 * 
	 * @param item
	 * @param source
	 */
	protected void selectTab(CssButton button, IMenuItem item) {

		if (menuSelected != null) {
			menuSelected.removeCssSelected();
		}

		menuSelected = button;

		button.addCssSelected();

		if (listener != null) {
			listener.changeItemListener(item);
		}
	}

	@Override
	public void setListener(MenuViewListener listener) {
		this.listener = listener;
	}

	@Override
	public void select(IMenuItem menuItem) {

		selectTab(findButton(menuItem), menuItem);
	}

	/**
	 * Buscar button por el menú item.
	 * 
	 * @param menuItem
	 * @return
	 */
	private CssButton findButton(IMenuItem menuItem) {

		for (int i = 0; i < getLayout().getComponentCount(); i++) {
			if (((CssButton) getLayout().getComponent(i)).getData() == menuItem) {
				return (CssButton) getLayout().getComponent(i);
			}
		}
		return null;
	}

	/**
	 * Buscar button por el nombre enumerado.
	 * 
	 * @param name
	 * @return
	 */
	private CssButton findButton(Key name) {

		for (int i = 0; i < getLayout().getComponentCount(); i++) {

			if (((IMenuItem) ((CssButton) getLayout().getComponent(i)).getData()).getName() == name) {
				return (CssButton) getLayout().getComponent(i);
			}
		}
		return null;
	}

	@Override
	public void select(Key name) {

		CssButton b = findButton(name);
		if (b != null) {
			selectTab(b, (IMenuItem) b.getData());
		}
	}

	/**
	 * Layout de ubicación del componente asociado a cada tab.
	 * 
	 * @return
	 */
	public AbstractLayout getTabLayout() {
		return tabLayout;
	}

	/**
	 * Layout de ubicación del componente asociado a cada tab.
	 * 
	 * @param tabLayout
	 */
	public void setTabLayout(AbstractLayout tabLayout) {
		this.tabLayout = tabLayout;
	}

	/**
	 * Establecer el como visible el componente del tab seleccionado.
	 * 
	 * @param component
	 */
	public void setTabComponent(Component component) {

		tabLayout.removeAllComponents();
		if (tabLayout != null && component != null) {
			tabLayout.addComponent(component);
		}
	}

	@Override
	public void setVisibleTab(IMenuItem item, boolean visible) {

		CssButton b = findButton(item);
		if (b != null) {
			b.setVisible(visible);
		}
	}

	@Override
	public void setVisibleTab(Key item, boolean visible) {

		CssButton b = findButton(item);
		if (b != null) {
			b.setVisible(visible);
		}
	}

	@Override
	public boolean isVisibleTab(IMenuItem item) {

		CssButton b = findButton(item);
		if (b != null) {
			return b.isVisible();
		}
		return false;
	}

	@Override
	public boolean isVisibleTab(Key item) {

		CssButton b = findButton(item);
		if (b != null) {
			return b.isVisible();
		}
		return false;
	}

	@Override
	public void setEnabledTab(IMenuItem item, boolean enabled) {

		CssButton b = findButton(item);
		if (b != null) {
			b.setEnabled(enabled);
		}
	}

	@Override
	public void setEnabledTab(Key item, boolean enabled) {

		CssButton b = findButton(item);
		if (b != null) {
			b.setEnabled(enabled);
		}
	}

	@Override
	public boolean isEnabledTab(IMenuItem item) {

		CssButton b = findButton(item);
		if (b != null) {
			return b.isEnabled();
		}
		return false;
	}

	@Override
	public boolean isEnabledTab(Key item) {

		CssButton b = findButton(item);
		if (b != null) {
			return b.isEnabled();
		}
		return false;
	}

	@Override
	public void setCaptionTab(IMenuItem item, String caption) {

		CssButton b = findButton(item);
		if (b != null) {
			b.setCaption(caption);
		}
	}

	@Override
	public void setCaptionTab(Key item, String caption) {

		CssButton b = findButton(item);
		if (b != null) {
			b.setCaption(caption);
		}
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

		if (item != null) {
			getLayout().addComponent(getTabButton(item));
		}
	}

	@Override
	public void removeItem(IMenuItem item) {
		// TODO Auto-generated method stub

	}

}
