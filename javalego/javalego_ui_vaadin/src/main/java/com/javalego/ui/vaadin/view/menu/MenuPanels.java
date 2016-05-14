package com.javalego.ui.vaadin.view.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.MenuView;
import com.javalego.ui.patterns.IPresenter;
import com.javalego.ui.vaadin.component.backbutton.BackButton;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.button.css.MenuPanelsCss;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Menú basado en paneles (similar a Google Play).
 * 
 * @author ROBERTO RANZ
 */
public class MenuPanels extends CustomComponent implements MenuView {

	private static final long serialVersionUID = 1L;

	private MenuViewListener listener;

	private VerticalLayout layout;

	/**
	 * Estilos de visualización: 1. PANELS estilo Google Play con paneles que
	 * muestra un único menú y submenús, 2. LIST estilo lista expandible.
	 * 
	 * @author ROBERTO RANZ
	 *
	 */
	public static enum Style {
		PANELS, LIST
	}

	/**
	 * Estilo de visaulización
	 */
	private Style style;

	/**
	 * Panel back de los submenús.
	 */
	private Component backButton;

	private IMenuItem activeMenu;

	/**
	 * Button seleccionado pero que no es simplemente para resaltar la
	 * selección.
	 */
	private CssButton actualButton;

	/**
	 * Item de menú seleccionado
	 */
	private IMenuItem selectedMenuItem;

	/**
	 * Icono back incluido en los submenús.
	 */
	private Icon icon = IconEditor.LEFTROW;

	/**
	 * Lista de buttons cargados
	 */
	private HashMap<IMenuItem, CssButton> buttons = new HashMap<IMenuItem, CssButton>();

	private HashMap<IMenuItem, ItemPanel> itemPanels = new HashMap<IMenuItem, MenuPanels.ItemPanel>();

	private static final class ItemPanel {

		public ItemPanel(Component component) {
			this.component = component;
		}

		boolean expanded = true;
		Component component;
	}

	/**
	 * Incluir bordes CSS
	 */
	private boolean border;

	private boolean expandAll;

	/**
	 * Constructor
	 * 
	 * @param border
	 *            incluir bordes css en todos los items.
	 * @param style
	 *            estilo de visualización de sus items: panels o lista
	 *            expandible.
	 */
	public MenuPanels(boolean border, Style style) {
		this.border = border;
		this.style = style;
	}

	/**
	 * Constructor
	 * 
	 * @param border
	 *            incluir bordes css en todos los items.
	 * @param style
	 *            estilo de visualización de sus items: panels o lista
	 *            expandible.
	 * @param expandAll
	 *            expandir todos los items. Sólo aplicable para el estilo LIST.
	 */
	public MenuPanels(boolean border, Style style, boolean expandAll) {
		this.border = border;
		this.style = style;
		this.expandAll = expandAll;
	}

	/**
	 * Constructor
	 * 
	 * @param style
	 *            estilo de visualización de sus items: panels o lista
	 *            expandible.
	 * @param shadow
	 *            Sombra
	 */
	public MenuPanels(Style style, boolean shadow) {
		this.style = style;
		if (shadow) {
			addStyleName(CssVaadin.getShadow());
		}
	}

	/**
	 * Constructor
	 */
	public MenuPanels() {
	}

	public boolean isList() {
		return style == Style.LIST;
	}

	public boolean isPanels() {
		return style == Style.PANELS;
	}

	@Override
	public void load() throws LocalizedException {

		if (layout == null) {
			layout = new VerticalLayout();
		} else {
			layout.removeAllComponents();
		}

		if (border) {
			addStyleName(CssVaadin.getBorder());
		}

		// Cabecera
		if (listener != null && listener.getHeader() != null) {

			final IMenuItem header = listener.getHeader();

			// Notificar el cambio de item de menú
			if (isPanels()) {
				listener.changeItemListener(header);
			}

			// Abrir o cerrar submenú.
			CssButton bi = addButton(header);
			bi.addCssDisabled();
			layout.addComponent(bi);
		}

		// Cargar lista de menús del primer nivel.
		List<IMenuItem> items = listener != null ? listener.getMainItems() : new ArrayList<IMenuItem>();

		for (final IMenuItem item : items) {

			// Abrir o cerrar submenú.
			final CssButton bi = addButton(item);
			if (item != items.get(items.size() - 1)) {
				bi.addCssBorder();
			}
			bi.addLayoutClickListener(new LayoutClickListener() {
				private static final long serialVersionUID = -5074822988461133290L;
				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (!event.isDoubleClick())
						try {
							loadChildren(item);
						} catch (LocalizedException e) {
							MessagesUtil.error(e);
						}
				}
			});

			layout.addComponent(bi);

			if (isList()) {
				loadChildren(item);
			}

		}
		setCompositionRoot(layout);
	}

	/**
	 * Generar el panel de los submenús del elemento seleccionado.
	 * 
	 * @param item
	 * @throws LocalizedException
	 */
	protected void loadChildren(final IMenuItem item) throws LocalizedException {

		// Si no existen subitems o un presenter asociado al item, no generar
		// componentes visuales y notificar cambio de menú.
		boolean detail = (item.getSubItems() != null && item.getSubItems().size() > 0) || item.getData() instanceof IPresenter;

		if (!detail && isPanels() && listener != null) {
			listener.changeItemListener(item);
			return;
		}

		if (isList()) {
			loadChildrenList(item);
		} else {
			loadChildrenPanels(item);
		}

		// Notificar el cambio de item de menú
		if (isPanels() && listener != null) {
			listener.changeItemListener(item);
		}

		activeMenu = item;
	}

	/**
	 * Cargar subitems en modalidad panels (google play).
	 * 
	 * @param item
	 * @throws LocalizedException
	 */
	private void loadChildrenPanels(final IMenuItem item) throws LocalizedException {

		layout.removeAllComponents();

		// Cabecera
		CssButton buttonHeader = addButton(item);

		buttonHeader.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 3063122058764596013L;
			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick() && listener != null) {
					listener.changeItemListener(item);
				}
			}
		});

		layout.addComponent(buttonHeader);

		// Botón y submenús.
		HorizontalLayout h = new HorizontalLayout();

		Component btn = getBackButton();
		
		h.addComponent(btn);

		VerticalLayout layoutItems = new VerticalLayout();
		//setWidth("100%");
		List<IMenuItem> list = item.getSubItems();

		if (list == null || list.size() == 0) {
			btn.setHeight("60px");
		} else {
			btn.setHeight("100%");

			// Lista de submenús
			for (final IMenuItem si : list) {

				final CssButton ab = addButton(si);
				ab.addStyleName(MenuPanelsCss.getLevel2(item.getColor()));

				ab.setDescription(si.getDescription());

				ab.addLayoutClickListener(new LayoutClickListener() {
					private static final long serialVersionUID = -4179469116256505006L;

					@Override
					public void layoutClick(LayoutClickEvent event) {
						if (!event.isDoubleClick()) {
							if (listener != null) {
								listener.changeItemListener(si);
							}
							selectItem(ab);
						}
					}
				});
				layoutItems.addComponent(ab);
			}
		}

		// Añadir presenter del item.
		if (item.getData() != null && item.getData() instanceof IPresenter) {
			IPresenter p = (IPresenter) item.getData();
			p.load();
			// ERROR PORQUE NO DEVUELVE LA VISTA UN COMPONENT VER CÓMO SE CREA
			// EL PRESENTER.
			if (p.getView() instanceof Component) {
				layoutItems.addComponent((Component) p.getView());
			}
		}

		h.addComponent(layoutItems);

		h.setExpandRatio(layoutItems, 1);

		h.setWidth("100%");

		layout.addComponent(h);

	}

	/**
	 * Cargar subitems en modalidad lista
	 * 
	 * @param item
	 */
	private void loadChildrenList(IMenuItem item) {

		ItemPanel itemPanel = itemPanels.get(item);

		if (itemPanel != null) {
			itemPanel.expanded = !itemPanel.expanded;
			itemPanel.component.setVisible(itemPanel.expanded);
		} else {
			List<IMenuItem> list = item.getSubItems();

			// Generar componentes por cada submenú.
			if (list != null && list.size() > 0) {

				VerticalLayout vl = new VerticalLayout();
				
				// Lista de submenús
				for (final IMenuItem si : list) {

					HorizontalLayout h = new HorizontalLayout();

					final CssButton ab = addButton(si);
					ab.addStyleName(MenuPanelsCss.getLevel2(item.getColor()));

					ab.setDescription(si.getDescription());

					ab.addLayoutClickListener(new LayoutClickListener() {
						private static final long serialVersionUID = -3581776222924157129L;

						@Override
						public void layoutClick(LayoutClickEvent event) {
							if (!event.isDoubleClick()) {
								if (listener != null) {
									listener.changeItemListener(si);
								}
								selectItem(ab);
							}
						}
					});

					Label lbl = new Label();
					lbl.setWidth("48px");
					lbl.setHeight("100%");
					h.addComponent(lbl);
					h.addComponent(ab);
					h.setWidth("100%");
					h.setExpandRatio(ab, 1);
					vl.addComponent(h);
				}
				layout.addComponent(vl);

				ItemPanel ip = new ItemPanel(vl);

				if (!expandAll) {
					vl.setVisible(false);
					ip.expanded = false;
				}

				itemPanels.put(item, ip);
			}
		}
	}

	/**
	 * Generar button y añadirlo a la lista de botones cargados de todos los
	 * elementos de menú.
	 * 
	 * @param itemMenu
	 * @return
	 */
	private CssButton addButton(IMenuItem itemMenu) {

		CssButton button = buttons.get(itemMenu);

		if (button == null) {
			button = new CssButton(itemMenu.getTitle(), itemMenu.getDescription(), itemMenu.getColor(), itemMenu.getIcon());
			buttons.put(itemMenu, button);
		}

		button.setWidth("100%");

		return button;
	}

	/**
	 * Seleccionar button aplicando estilo de selección.
	 * 
	 * @param ab
	 */
	private void selectItem(final CssButton ab) {

		if (ab.getParent() != null) {
			if (actualButton != null) {
				actualButton.removeCssSelected();
			}
			actualButton = ab;
			actualButton.addCssSelected();
		}
	}

	/**
	 * Botón de atrás.
	 * 
	 * @return
	 */
	public Component getBackButton() {

		if (backButton != null) {
			return backButton;
		}

		VerticalLayout action = new BackButton(icon, "48px");
		action.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 695502934117492267L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				if (!event.isDoubleClick())
					try {
						back();
					} catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
			}
		});

		backButton = action;

		return action;
	}

	/**
	 * Recargar menús anteriores.
	 * 
	 * @throws LocalizedException
	 */
	protected void back() throws LocalizedException {

		if (activeMenu.getParent() != null) {
			loadChildren(activeMenu.getParent());
		} else {
			load();
		}
	}

	@Override
	public void setListener(MenuViewListener listener) {
		this.listener = listener;
	}

	@Override
	public void select(IMenuItem menuItem) {

		if (menuItem == selectedMenuItem) {
			return;
		}

		CssButton b = findButton(menuItem);

		if (b != null) {

			checkButton(b);

			if (selectedMenuItem != null) {
				uncheckButton(findButton(selectedMenuItem));
			}

			selectedMenuItem = menuItem;
		}
	}

	/**
	 * Incluir imagen check del item
	 * 
	 * @param b
	 */
	private void checkButton(CssButton b) {
		if (b != null) {
			b.setIcon(IconEditor.CHECK);
		}
	}

	/**
	 * Incluir imagen uncheck del item
	 * 
	 * @param b
	 */
	private void uncheckButton(CssButton b) {
		if (b != null) {
			b.setIcon(IconEditor.UNCHECK);
		}
	}

	@Override
	public void select(Key name) {
	}

	/**
	 * Buscar button por el menú item.
	 * 
	 * @param menuItem
	 * @return
	 */
	private CssButton findButton(IMenuItem menuItem) {
		return buttons.get(menuItem);
	}

	@Override
	public void changeIcon(IMenuItem item, Icon icon) {
		CssButton b = findButton(item);
		if (b != null) {
			b.setIcon(icon);
		}
	}

	@Override
	public void unselect() {
		// Tipo Check
		if (selectedMenuItem != null) {
			uncheckButton(findButton(selectedMenuItem));
			selectedMenuItem = null;
		}
		// Selección resaltando en negrita el item seleccionado.
		else if (actualButton != null) {
			actualButton.removeCssSelected();
			actualButton = null;
		}
	}

	@Override
	public IMenuItem getSelectedItem() {
		return null;
	}

	@Override
	public void addItem(IMenuItem item) {
	}

	@Override
	public void removeItem(IMenuItem item) {
	}

}
