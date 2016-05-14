package com.javalego.store.mvp.detail;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.mvp.list.ListItemPresenter;
import com.javalego.store.mvp.list.ListItemView;
import com.javalego.store.ui.components.UIFactory;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.listeners.ItemChangeEvent;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.menu.MenuPresenter;
import com.javalego.ui.menu.MenuPresenterListener;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.button.CssButton;
import com.javalego.ui.vaadin.component.button.css.MenuPanelsCss;
import com.javalego.ui.vaadin.component.tiles.TilesLayout;
import com.javalego.ui.vaadin.component.util.LayoutUtils;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.menu.MenuTabs;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Sección detalle del elemento de la tienda seleccionada (productos, proyectos,
 * miembros, ayuda, ...)
 * 
 * @author ROBERTO RANZ
 */
public class DetailItemView<T extends IBaseItem> extends VerticalLayout implements IDetailItemView<T> {

	private static final long serialVersionUID = 5027671185898256864L;

	private DetailItemViewListener<T> listener;

	private CssLayout homeLayout;

	private boolean readOnly;

	/**
	 * Constructor
	 * 
	 * @param readOnly
	 */
	public DetailItemView(boolean readOnly) {

		this.readOnly = readOnly;

		setMargin(true);
		setSpacing(true);

		setWidth("100%");
	}

	@Override
	public void load() throws LocalizedException {
	}

	@Override
	public void setListener(DetailItemViewListener<T> listener) {
		this.listener = listener;
	}

	@Override
	public boolean load(IMenuItem menuItem) throws LocalizedException {

		removeAllComponents();

		if (menuItem != null && menuItem.getParent() != null && menuItem.getName() != LocaleStore.HOME) {
			loadDetail(menuItem);
			return true;
		}
		else {
			addComponent(getMainComponent());
			return false;
		}
	}

	/**
	 * Crear sección de tiles para mostrar la lista de componentes disponibles
	 * de la sección seleccionada.
	 * 
	 * @param menuItem
	 * @return
	 */
	private void loadDetail(IMenuItem menuItem) {

		Component header = getTitle(menuItem);
		if (header != null) {
			addComponent(header);
		}

		try {
			ListItemView<T> view = new ListItemView<T>(readOnly);
			ListItemPresenter<T> presenter = new ListItemPresenter<T>(listener.getDataService(), menuItem.getName(), view);
			presenter.load();
			addComponent(view);
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

	/**
	 * Obtener el título de la sección
	 * 
	 * @param menuItem
	 * @return
	 */
	private Component getTitle(IMenuItem menuItem) {

		if (menuItem != null) {

			CssButton l = new CssButton("<strong>" + menuItem.getTitle() + "</strong>", menuItem.getDescription(), menuItem.getColor(), menuItem.getIcon());
			l.setWidth("100%");
			// Color de segundo nivel del item de menú seleccionado para mostrarse como título en su detalle.
			if (menuItem.getColor() == null) {
				if (l.getIconComponent() != null) {
					l.getIconComponent().addStyleName(getLevel2(menuItem));
				}
			}

			l.addStyleName(CssVaadin.getShadow());
			return l;
		}
		else {
			return null;
		}

	}

	/**
	 * Color de nivel 2 para el título.
	 * 
	 * @param menuItem
	 * @return
	 */
	private String getLevel2(IMenuItem menuItem) {

		return MenuPanelsCss.getLevel2(menuItem.getParent().getColor());
	}

	/**
	 * Opciones de la pantalla principal.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	private Component getMainComponent() throws LocalizedException {

		if (homeLayout == null) {

			homeLayout = LayoutUtils.getCssLayout(false, "margin: 4px; margin-top: 0px; padding: 4px", true);
			homeLayout.setWidth("100%");

			MenuTabs tabsLayout = new MenuTabs();
			tabsLayout.setWidth("100%");
			
			IMenuModel model = listener.getMainMenuModel();

			MenuPresenter p = new MenuPresenter(model, tabsLayout);
			p.addItemChangeListener(new MenuPresenterListener() {
				@Override
				public void propertyChange(ItemChangeEvent<IMenuItem> event) {
					try {
						loadMainMenu(event.getItemChange());
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			});
			homeLayout.addComponent(tabsLayout);

			p.select(LocaleStore.NEWS);
		}

		return homeLayout;
	}

	/**
	 * Cargar menú principal
	 * 
	 * @param itemChange
	 * @throws LocalizedException 
	 */
	protected void loadMainMenu(IMenuItem itemChange) throws LocalizedException {

		if (homeLayout.getComponentCount() == 2) {
			homeLayout.removeComponent(homeLayout.getComponent(1));
		}

		if (itemChange.getName() == LocaleStore.NEWS) {
			loadNews();
		}
		else if (itemChange.getName() == LocaleStore.FIND) {
			loadFind();
		}
		else if (itemChange.getName() == LocaleStore.FAVORITES) {
			loadFavorites();
		}

	}

	/**
	 * Favoritos sólo activo cando esté registrado.
	 */
	private void loadFavorites() {

		CssLayout pl = LayoutUtils.getCssLayout(false, "margin: 8px; margin-top: 0px; padding-right:14px;", false);

		pl.addComponent(new Label("<h3>" + "No existe información" + "</h3>", ContentMode.HTML));

		homeLayout.addComponent(pl);
	}

	/**
	 * Buscar productos y proyectos. encontrado.
	 * @throws LocalizedException 
	 */
	@SuppressWarnings("serial")
	private void loadFind() throws LocalizedException {

		VerticalLayout findLayout = new VerticalLayout();
		findLayout.setSpacing(true);
		findLayout.setMargin(true);
		
		TilesLayout layout = new TilesLayout();
		layout.setCss("margin: 8px; spacing: 4px; display: inline-block;");

		final TextField tf = new TextField();
		tf.setColumns(18);
		layout.addComponent(tf);

		ButtonExt b = new ButtonExt(LocaleStore.FIND);
		b.blue();
		layout.addComponent(b);

		// Añadir vista para los componentes.
		ListItemView<T> view = new ListItemView<T>(readOnly);
		final ListItemPresenter<T> presenter = new ListItemPresenter<T>(listener.getDataService(), LocaleStore.PRODUCTS, view);
		presenter.load();

		b.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					presenter.setFilter(tf.getValue());
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});

		findLayout.addComponent(layout);
		findLayout.addComponent(view);
		homeLayout.addComponent(findLayout);
	}

	/**
	 * Noticias
	 * @throws LocalizedException 
	 */
	private void loadNews() throws LocalizedException {

		homeLayout.addComponent(UIFactory.getNews(listener.getNews()));
	}

}
