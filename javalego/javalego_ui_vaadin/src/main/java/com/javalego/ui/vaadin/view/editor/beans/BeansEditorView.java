package com.javalego.ui.vaadin.view.editor.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.data.DataContext;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.model.locales.LocaleWarnings;
import com.javalego.office.export.ExportBeans;
import com.javalego.ui.UIContext;
import com.javalego.ui.actions.IActionBeansEditor;
import com.javalego.ui.filter.IFilterService;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.listeners.ItemChangeEvent;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.menu.MenuItem;
import com.javalego.ui.menu.MenuPresenter;
import com.javalego.ui.menu.MenuPresenterListener;
import com.javalego.ui.menu.MenuView.MenuViewListener;
import com.javalego.ui.mvp.beans.view.IBeansView;
import com.javalego.ui.mvp.beans.view.format.IFormatBeansView;
import com.javalego.ui.mvp.beans.view.list.adapter.IListBeansViewAdapter;
import com.javalego.ui.mvp.editor.IEditorPresenter;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorPresenter;
import com.javalego.ui.mvp.editor.beans.BeansEditorPresenter;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.beans.IBeansEditorView;
import com.javalego.ui.mvp.editor.beans.filters.EditorFiltersPresenter;
import com.javalego.ui.mvp.editor.beans.filters.EditorFiltersPresenter.EditorFilterObserver;
import com.javalego.ui.mvp.editor.beans.filters.IEditorFiltersModel;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.factory.EditorsFactoryVaadin;
import com.javalego.ui.vaadin.view.actions.ButtonActionBeansEditorView;
import com.javalego.ui.vaadin.view.actions.ExportServices;
import com.javalego.ui.vaadin.view.actions.ExportServices.ExportFormat;
import com.javalego.ui.vaadin.view.editor.bean.BeanEditorView;
import com.javalego.ui.vaadin.view.editor.beans.filters.EditorFiltersView;
import com.javalego.ui.vaadin.view.menu.ListMenu;
import com.javalego.ui.vaadin.view.menu.MenuTabs;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Editor de Beans que incluye un conjunto de funcionalidades: opciones CRUD,
 * acciones, vistas, ordenación, filtros, ...
 * 
 * @author ROBERTO RANZ
 * 
 */
public class BeansEditorView<T> extends VerticalLayout implements IBeansEditorView<T> {

	private static final long serialVersionUID = -2459873654343875710L;

	public static Logger logger = Logger.getLogger(BeansEditorView.class);

	/**
	 * Listener del presenter para obtener datos del editor de beans de su
	 * modelo
	 */
	private BeansEditorViewListener<T> listener;

	/**
	 * Layout principal
	 */
	private VerticalLayout homeLayout;

	/**
	 * Layout de visualización de beans (grid o list).
	 */
	private VerticalLayout recordsLayout;

	/**
	 * Opciones principales de edición
	 */
	private MenuTabs tabsLayout;

	/**
	 * Opciones de visualización de beans o registros
	 */
	private MenuTabs tabsRecords;

	/**
	 * Sección de filtros
	 */
	private EditorFiltersPresenter filtersPresenter;

	/**
	 * Vista de presentación de datos
	 */
	private IBeansView<T> beansView;

	/**
	 * Editor de beans utilizado
	 */
	private IEditorPresenter editorPresenter;

	/**
	 * Componente asociado a la edición del bean.
	 */
	private Component editBeanComponent;

	/**
	 * Constructor
	 * 
	 * @param beansView
	 */
	public BeansEditorView(IBeansView<T> beansView) {
		this();
		this.beansView = beansView;
	}

	/**
	 * Constructor
	 * 
	 * @param beansView
	 */
	public BeansEditorView() {
		addStyleName(CssVaadin.getShadow());
	}

	@Override
	public void load() throws LocalizedException {

		addComponent(getTabs());

		tabsLayout.select(BeansEditorOptions.RECORDS);
	}

	/**
	 * Menú de opciones
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	private Component getTabs() throws LocalizedException {

		if (homeLayout != null) {
			return homeLayout;
		}

		homeLayout = new VerticalLayout();

		VerticalLayout v = new VerticalLayout();
		// v.setMargin(true);
		tabsLayout = new MenuTabs(v, Colors.INDIGO);

		tabsLayout.setWidth("100%");

		IMenuModel model = getMainMenuModel(getHomeOptions());

		MenuPresenter p = new MenuPresenter(model, tabsLayout);
		p.addItemChangeListener(new MenuPresenterListener() {
			@Override
			public void propertyChange(ItemChangeEvent<IMenuItem> event) {
				loadItem(event.getItemChange(), tabsLayout);
			}
		});
		homeLayout.addComponent(tabsLayout);
		homeLayout.addComponent(tabsLayout.getTabLayout());

		// Ocultar edición
		tabsLayout.setVisibleTab(BeansEditorOptions.EDIT, false);
		// tabsLayout.setVisibleTab(EBeansEditorOptions.DETAIL, false);

		return homeLayout;
	}

	/**
	 * Cargar el componente del tab seleccionado.
	 * 
	 * @param itemChange
	 * @param menuTabs
	 */
	private void loadItem(IMenuItem itemChange, MenuTabs menuTabs) {

		try {
			Component component = null;

			if (itemChange.getName() == null) {
				component = new Label("");
			}
			// Lista de beans
			else if (itemChange.getName().equals(BeansEditorOptions.RECORDS)) {
				component = getTabRecords();
			}
			// Todos los registros
			else if (itemChange.getName().equals(BeansEditorOptions.ALL)) {
			}
			// Filtros
			else if (itemChange.getName().equals(BeansEditorOptions.FILTER)) {
				component = getFilters();
			}
			// Acciones
			else if (itemChange.getName().equals(BeansEditorOptions.ACTIONS)) {
				component = getActions();
			}
			// Lista de vistas disponibles
			else if (itemChange.getName().equals(BeansEditorOptions.VIEWS)) {
				component = getViews();
			}
			// Editar bean
			else if (itemChange.getName().equals(BeansEditorOptions.EDIT) && beansView != null) {

				component = getEdit(beansView.getSelectedBean(), false);
			}
			// Añadir bean
			else if (itemChange.getName().equals(BeansEditorOptions.ADD)) {

				component = getEdit(listener.getNewBean(), true);
			}
			else {
				component = new Label("");
			}

			menuTabs.setTabComponent(component);

		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

	/**
	 * Tab de registros.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	private Component getTabRecords() throws LocalizedException {

		if (recordsLayout != null) {
			return recordsLayout;
		}

		recordsLayout = new VerticalLayout();

		tabsRecords = new MenuTabs(new VerticalLayout());
		tabsRecords.setWidth("100%");
		IMenuModel model = getMainMenuModel(getRecordOptions());

		MenuPresenter p = new MenuPresenter(model, tabsRecords);
		p.addItemChangeListener(new MenuPresenterListener() {
			@Override
			public void propertyChange(ItemChangeEvent<IMenuItem> event) {
				loadItem(event.getItemChange(), tabsRecords);
			}
		});
		recordsLayout.addComponent(tabsRecords);
		tabsRecords.select(BeansEditorOptions.ALL);
		recordsLayout.addComponent(tabsRecords.getTabLayout());

		recordsLayout.addComponent(getActualBeansView());

		return recordsLayout;
	}

	/**
	 * Obtiene el componente de visualización de beans utilizados por el editor.
	 * Si no existe se define uno por defecto.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	private Component getActualBeansView() throws LocalizedException {

		if (beansView != null) {
			return (Component) beansView;
		}

		// Buscar primero si existe una vista de tipo List Adapter antes de
		// crear una de tipo grid.
		beansView = getListBeansView(getFirstBeansViewAdapter());

		if (beansView == null) {
			beansView = getTableBeansView();
			// beansView = getTablePagedBeansView();
		}

		return (Component) beansView;
	}

	/**
	 * Obtiene la primera vista formateada mediante un adaptador existente en el
	 * editor de beans.
	 * 
	 * @return
	 */
	private IListBeansViewAdapter<T> getFirstBeansViewAdapter() {

		// Localizar vista de beans de tipo Adapter.
		IListBeansViewAdapter<T> adapter = null;

		Collection<IFormatBeansView<T>> views = listener.getFormatViews();

		if (views != null) {
			for (IFormatBeansView<T> item : views) {
				if (item instanceof IListBeansViewAdapter) {
					adapter = (IListBeansViewAdapter<T>) item;
				}
			}
		}

		return adapter;
	}

	/**
	 * Obtiene el componente de visualización de beans de tipo list.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	private IBeansView<T> getListBeansView(IListBeansViewAdapter<T> adapter) throws LocalizedException {

		IBeansView<T> beansView = null;

		if (adapter != null) {

			beansView = EditorsFactoryVaadin.getListBeansView(listener, adapter);
			beansView.load();

			// Ajustar al ancho del tabs
			if (beansView instanceof Component) {
				((Component) beansView).setWidth("100%");
			}
		}
		return beansView;
	}

	/**
	 * Obtiene el componente de visualización de beans paginados en rejilla.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private IBeansView<T> getTablePagedBeansView() throws LocalizedException {

		beansView = EditorsFactoryVaadin.getTablePagedBeansView(listener);
		beansView.load();

		return beansView;
	}

	/**
	 * Obtiene el componente de visualización de beans en tabla sin paginación
	 * en rejilla.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings("unchecked")
	private IBeansView<T> getTableBeansView() throws LocalizedException {

		beansView = EditorsFactoryVaadin.getTableBeansView(listener);
		beansView.load();

		return beansView;
	}

	/**
	 * Menú principal de opciones
	 * 
	 * @param options
	 * @return
	 * @throws LocalizedException
	 */
	public IMenuModel getMainMenuModel(final List<IMenuItem> options) throws LocalizedException {

		IMenuModel menuModel = new IMenuModel() {
			@Override
			public List<IMenuItem> getSubItems(IMenuItem menuItem) {
				return null;
			}

			@Override
			public List<IMenuItem> getMainItems() {
				return options;
			}

			@Override
			public IMenuItem getHeader() {
				return null;
			}
		};
		return menuModel;
	}

	/**
	 * Opciones de inicio.
	 * 
	 * @return
	 */
	public final List<IMenuItem> getHomeOptions() {

		List<IMenuItem> items = new ArrayList<IMenuItem>();
		items.add(new MenuItem(BeansEditorOptions.RECORDS, UIContext.getText(LocaleEditor.RECORDS)));
		items.add(new MenuItem(BeansEditorOptions.ADD, UIContext.getText(LocaleEditor.INSERT)));
		items.add(new MenuItem(BeansEditorOptions.EDIT, UIContext.getText(LocaleEditor.EDIT)));
		items.add(new MenuItem(BeansEditorOptions.ACTIONS, UIContext.getText(LocaleEditor.ACTIONS)));

		return items;
	}

	/**
	 * Opciones de registros.
	 * 
	 * @return
	 */
	public final List<IMenuItem> getRecordOptions() {

		List<IMenuItem> items = new ArrayList<IMenuItem>();
		items.add(new MenuItem(BeansEditorOptions.ALL, UIContext.getText(LocaleEditor.ALL)));

		if (listener.hasFilters()) {
			items.add(new MenuItem(BeansEditorOptions.FILTER, UIContext.getText(LocaleEditor.FILTER)));
		}
		if (listener.hasViews()) {
			items.add(new MenuItem(BeansEditorOptions.VIEWS, UIContext.getText(LocaleEditor.VIEWS)));
		}

		return items;
	}

	/**
	 * Edición del objeto seleccionado.
	 * 
	 * @param bean
	 *            Bean a editar
	 * @throws LocalizedException
	 */
	protected Component getEdit(T bean, boolean inserting) throws LocalizedException {

		if (bean == null) {
			return null;
		}
		// Editor por defecto.
		editorPresenter = getDefaultEditorPresenter(bean, inserting);

		editBeanComponent = (Component) editorPresenter.getView();

		return editBeanComponent;
	}

	/**
	 * Crear el editor por defecto
	 * 
	 * @param bean
	 * @return
	 * @throws LocalizedException
	 */
	@SuppressWarnings({ "unchecked" })
	private IEditorPresenter getDefaultEditorPresenter(final T bean, final boolean inserting) throws LocalizedException {

		// Creación del presenter con la configuración definida en el editor de
		// beans (readOnly y layout).
		BeanEditorPresenter<T> p = listener.getEditorPresenter(bean, inserting, new BeanEditorView<T>().fullWidth().light());

		p.setEditorListener(new BeanEditorPresenter.EditorListener() {
			@Override
			public void remove() throws LocalizedException {

				if (bean instanceof Entity && DataContext.getProvider() != null) {
					DataContext.getProvider().delete((Entity) bean);
				}

				beansView.removeBean(bean);
				tabsLayout.select(BeansEditorOptions.RECORDS);
				tabsLayout.setVisibleTab(BeansEditorOptions.EDIT, false);

				MessagesUtil.information(UIContext.getText(LocaleWarnings.REMOVED_RECORD));
			}

			@Override
			public void discard() throws LocalizedException {
				tabsLayout.select(BeansEditorOptions.RECORDS);
			}

			@Override
			public void commit() throws LocalizedException {

				T actual = bean;

				if (actual instanceof Entity && DataContext.getProvider() != null) {
					actual = (T) DataContext.getProvider().save((Entity) actual);
				}

				if (inserting) {
					beansView.insert(actual);
				}
				else {
					beansView.update(actual);
				}

				tabsLayout.select(BeansEditorOptions.RECORDS);
				tabsLayout.setVisibleTab(BeansEditorOptions.EDIT, true);
			}

			@Override
			public void loadDetailBeanEditor(IBeansEditorModel<?> detail) {

				addBeanDetail(detail, beansView.getSelectedBean());
			}
		});

		p.load();

		return p;
	}

	/**
	 * Añadir un tab para editar el detalle del bean seleccionado.
	 * 
	 * @param detail
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addBeanDetail(IBeansEditorModel<?> model, T bean) {

		// MenuItem item = new MenuItem(UIContext.getText(detail.getTitle()));
		// item.setData(detail);
		// tabsLayout.addItem(item);

		BeansEditorView view = new BeansEditorView();

		// presenter
		BeansEditorPresenter presenter = new BeansEditorPresenter(model, view, bean);
		try {
			presenter.load();

			ButtonExt back = new ButtonExt(UIContext.getText(model.getTitle()) + " " + bean.toString(), IconEditor.PREVIOUS);
			back.large();
			back.bordeless();

			back.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 3121278809003131887L;

				@Override
				public void buttonClick(ClickEvent event) {
					// Volver a mostrar el componente del editor del bean
					// master.
					if (editBeanComponent != null) {
						tabsLayout.setTabComponent(editBeanComponent);
					}
				}
			});

			VerticalLayout dl = new VerticalLayout();
			dl.addComponent(back);
			dl.addComponent(view);

			tabsLayout.setTabComponent(dl);

		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}

	}

	/**
	 * Filtros
	 * 
	 * @throws LocalizedException
	 */
	private Component getFilters() throws LocalizedException {

		if (filtersPresenter == null) {

			// Mostrar la lista de filtros disponibles.
			filtersPresenter = new EditorFiltersPresenter(new IEditorFiltersModel() {
				@Override
				public Collection<IFilterService> getFilters() {
					return listener.getFilters();
				}

				@Override
				public Class<?> getBeanClass() {
					return listener.getBeanClass();
				}
			}, new EditorFiltersView());

			// Ejecutar filtro
			filtersPresenter.setObserver(new EditorFilterObserver() {
				@Override
				public void execute(String statement, String naturalStatement) {
					try {
						listener.applyFilter(statement);
						tabsRecords.setVisibleTab(BeansEditorOptions.ALL, false);
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}

				@Override
				public void removeCurrentFilter() {
					try {
						listener.removeCurrentFilter();
						tabsRecords.setVisibleTab(BeansEditorOptions.ALL, true);
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}

				}
			});

			filtersPresenter.load();

		}
		return (Component) filtersPresenter.getView();
	}

	/**
	 * Acciones
	 * 
	 * @throws LocalizedException
	 */
	private Component getActions() throws LocalizedException {

		Collection<IActionBeansEditor> actions = listener.getActions();

		VerticalLayout actionLayout = new VerticalLayout();
		actionLayout.setMargin(true);
		actionLayout.setSpacing(true);

		// actionLayout.addComponent(new ButtonActionBeansEditorView<T>(new
		// ExportServices(UIContext.getText(listener.getTitle()), new
		// ExportBeans(), ExportFormat.XML), listener));
		// actionLayout.addComponent(new ButtonActionBeansEditorView<T>(new
		// ExportServices(UIContext.getText(listener.getTitle()), new
		// ExportBeans(), ExportFormat.JSON), listener));
		actionLayout.addComponent(new ButtonActionBeansEditorView<T>(new ExportServices(UIContext.getText(listener.getTitle()), new ExportBeans(), ExportFormat.EXCEL), listener));

		if (actions != null && actions.size() > 0) {
			for (IActionBeansEditor action : actions) {
				actionLayout.addComponent(new ButtonActionBeansEditorView<T>(action, listener));
			}
		}
		return actionLayout;
	}

	/**
	 * Views
	 * 
	 * @throws LocalizedException
	 */
	private Component getViews() throws LocalizedException {

		ListMenu layout = new ListMenu();

		MenuViewListener listener = new MenuViewListener() {

			@Override
			public List<IMenuItem> getSubItems(IMenuItem menuItem) {
				return null;
			}

			@Override
			public List<IMenuItem> getMainItems() {

				List<IMenuItem> items = new ArrayList<IMenuItem>();

				Collection<IFormatBeansView<T>> list = getListener().getFormatViews();

				if (list != null && list.size() > 0) {

					for (IFormatBeansView<T> view : list) {

						MenuItem menu = new MenuItem(view.getTitle());
						menu.setData(view);
						items.add(menu);
					}
				}
				MenuItem m = new MenuItem(UIContext.getText(LocaleEditor.GRID));
				items.add(m);

				return items.size() == 0 ? null : items;
			}

			@Override
			public IMenuItem getHeader() {
				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			public void changeItemListener(IMenuItem menuItem) {
				if (menuItem != null) {
					try {
						// Cambiar la vista de beans actual por la seleccionada
						// de la lista.
						changeView(menuItem.getData() instanceof IFormatBeansView ? (IFormatBeansView<T>) menuItem.getData() : null);
					}
					catch (LocalizedException e) {
						MessagesUtil.error(e);
					}
				}
			}
		};

		layout.setListener(listener);
		layout.load();
		// layout.addStyleName(CssVaadin.getBorder());
		layout.setWidth("100%");

		VerticalLayout v = new VerticalLayout();
		v.setWidth("100%");
		v.setMargin(true);
		v.addComponent(layout);

		return v;
	}

	/**
	 * Cambiar la vista de beans actual por otra.
	 * 
	 * @param data
	 * @throws LocalizedException
	 */
	protected void changeView(IFormatBeansView<T> data) throws LocalizedException {

		// Eliminar vista actualmente cargada.
		if (beansView != null) {
			recordsLayout.removeComponent((Component) beansView);
		}

		// Grid por defecto
		if (data == null) {
			beansView = getTableBeansView();
		}
		else if (data instanceof IListBeansViewAdapter) {
			beansView = getListBeansView((IListBeansViewAdapter<T>) data);
		}

		if (beansView != null) {
			recordsLayout.addComponent((Component) beansView);
		}
		else {
			logger.error("IFormatBeansView no cast IListBeansViewAdapter. Option not available.");
		}
	}

	@Override
	public void setListener(BeansEditorViewListener<T> listener) {
		this.listener = listener;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected BeanItemContainer getBeanItemContainer() {

		return new BeanItemContainer(listener.getBeanClass());
	}

	public BeansEditorViewListener<T> getListener() {
		return listener;
	}

	@Override
	public Collection<T> getSelectedBeans() {
		return beansView.getSelectedBeans();
	}

	@Override
	public void setSelectedBeans(Collection<T> beans) {
		beansView.setSelectedBeans(beans);
	}

	@Override
	public T getCurrentBean() {
		return (T) beansView.getSelectedBean();
	}

	@Override
	public void removeSelectedBeans() {
		beansView.removeSelectedBean();
	}

	@Override
	public IBeansView<T> getBeansView() {
		return beansView;
	}

	/**
	 * Establece la vista de beans utilizada por el editor.
	 */
	public void setBeansView(IBeansView<T> beansView) {
		this.beansView = beansView;
	}

	public void setEditorPresenter(IEditorPresenter editorPresenter) {
		this.editorPresenter = editorPresenter;
	}

	@Override
	public void editBean(T bean) {

		// Mostrar tab de edición del registro.
		tabsLayout.setVisibleTab(BeansEditorOptions.EDIT, true);
		// tabsLayout.setVisibleTab(EBeansEditorOptions.DETAIL, true);
		tabsLayout.select(BeansEditorOptions.EDIT);
	}

	@Override
	public void applyFilter(String statement) throws LocalizedException {
		beansView.applyFilter(statement);
	}

	@Override
	public void removeCurrentFilter() throws LocalizedException {
		beansView.removeCurrentFilter();
	}

}
