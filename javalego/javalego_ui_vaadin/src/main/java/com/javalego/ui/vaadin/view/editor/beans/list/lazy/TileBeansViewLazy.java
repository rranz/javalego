package com.javalego.ui.vaadin.view.editor.beans.list.lazy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.list.IListBeansView;
import com.javalego.ui.mvp.beans.view.list.adapter.IListBeansViewAdapter;
import com.javalego.ui.vaadin.component.tiles.Tile;
import com.javalego.ui.vaadin.component.tiles.TilesLayout;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.editor.beans.list.ICustomBeanView;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

/**
 * Vista de una lista de beans.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public class TileBeansViewLazy<T> extends CustomComponent implements IListBeansView<T> {

	private static final long serialVersionUID = 4525496169611841245L;

	private static final Logger logger = Logger.getLogger(TileBeansViewLazy.class);

	/**
	 * Listener de la lista de beans
	 */
	private ListBeansViewListener<T> listener;

	/**
	 * formato de datos
	 */
	private IListBeansViewAdapter<T> viewAdapter;

	/**
	 * Vista o componente personalizado por bean
	 */
	private ICustomBeanView<T> customBeanView;

	/**
	 * Layout principal o contenedor de los beans
	 */
	private Layout layout = null;

	/**
	 * Incluir sombra por cada item
	 */
	private boolean shadow = true;

	/**
	 * Incluir border por cada item
	 */
	private boolean border;

	/**
	 * Modo lista de items
	 */
	private boolean fullWidth;

	/**
	 * Campos de ordenación
	 */
	private String[] sortFieldNames;

	/**
	 * Container de beans
	 */
	protected LazyPagedContainer<T> container;

	/**
	 * Posición de lectura de beans
	 */
	private int index;

	/**
	 * Número de beans por página
	 */
	private int number = 20;

	/**
	 * Constructor
	 * 
	 * @param customBeanView
	 * @param container
	 */
	public TileBeansViewLazy(ICustomBeanView<T> customBeanView, LazyPagedContainer<T> container) {

		this.customBeanView = customBeanView;
		this.container = container;

		init();
	}

	/**
	 * Constructor
	 * 
	 * @param customBeanView
	 */
	public TileBeansViewLazy(ICustomBeanView<T> customBeanView) {

		this.customBeanView = customBeanView;

		init();
	}

	/**
	 * Constructor
	 * 
	 * @param customBeanView
	 * @param container
	 * @param number
	 *            Número de beans por carga (more).
	 */
	public TileBeansViewLazy(ICustomBeanView<T> customBeanView, LazyPagedContainer<T> container, int number) {

		this.customBeanView = customBeanView;
		this.number = number;
		this.container = container;

		init();
	}

	/**
	 * Constructor
	 * 
	 * @param viewAdapter
	 */
	public TileBeansViewLazy(IListBeansViewAdapter<T> viewAdapter) {

		this.viewAdapter = viewAdapter;

		init();
	}

	/**
	 * Inicializar el componente de tipo layout.
	 * 
	 * NOTA: se podría inicializar diferentes tipos de layout dependiendo de
	 * nuestras necesidades de visualización de beans.
	 */
	@SuppressWarnings("serial")
	private void init() {

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(new MarginInfo(false, false, true, false));
		mainLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);

		TilesLayout layout = new TilesLayout();
		layout.setWidth("100%");

		Button b = new Button("More");
		b.addStyleName(ValoTheme.BUTTON_PRIMARY);
		b.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					more();
				}
				catch (LocalizedException e) {
					MessagesUtil.error(e);
				}
			}
		});

		mainLayout.addComponent(layout);
		mainLayout.addComponent(b);

		setCompositionRoot(mainLayout);

		this.layout = layout;
	}

	/**
	 * Mostrar un nueva lista de beans
	 * 
	 * @throws LocalizedException
	 */
	protected void more() throws LocalizedException {

		List<T> beans = container.getItemIds(index, number);
		if (beans != null) {
			for (T bean : beans) {
				addItem(bean);
			}
			index += beans.size();
		}

	}

	@Override
	public void removeSelectedBean() {
	}

	@Override
	public T getSelectedBean() {
		return null;
	}

	@Override
	public void setSelectedBeans(Collection<T> beans) {
	}

	@Override
	public Collection<T> getSelectedBeans() {
		return null;
	}

	@Override
	public void removeBean(T bean) {

		Component c = findComponent(bean);

		if (c != null) {

			// Eliminar el componente asociado al bean
			layout.removeComponent(c);

			// Eliminar el bean de la lista
			container.removeItem(bean);
		}
	}

	@Override
	public void update(T bean) throws LocalizedException {

		Tile tile = findComponent(bean);

		if (tile != null) {

			if (customBeanView != null) {

				tile.removeAllComponents();

				Component c = customBeanView.getComponent(bean);

				if (c != null) {
					tile.addComponent(c);
				}
			}
			else {
				tile.setTitle(viewAdapter.toHtml(bean));
				try {
					tile.setIcon(viewAdapter.getIcon(bean));
				}
				catch (LocalizedException e) {
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		}
	}

	@Override
	public void insert(T bean) throws LocalizedException {

		if (bean != null) {

			addItem(bean);

			// Añadir bean a la lista
			container.addItem(bean);
		}
	}

	/**
	 * Establece la ordenación de los beans antes de su carga en la lista.
	 * 
	 * @param fieldNames
	 */
	public void setSortFieldNames(String[] fieldNames) {
		this.sortFieldNames = fieldNames;
	}

	@Override
	public void reloadBeans(Collection<T> beans) throws LocalizedException {
		layout.removeAllComponents();
		more();
	}

	@Override
	public void applyFilter(String statement) throws LocalizedException {
		listener.applyFilter(statement);
	}

	@Override
	public void removeCurrentFilter() throws LocalizedException {
		listener.removeCurrentFilter();
	}

	@Override
	public void load() throws LocalizedException {
		reloadBeans(null);
	}

	/**
	 * Buscar el componente a partir del bean.
	 * 
	 * @param bean
	 * @return
	 */
	public Tile findComponent(T bean) {

		Iterator<Component> iterator = layout.iterator();

		while (iterator.hasNext()) {

			AbstractComponent c = ((AbstractComponent) iterator.next());

			if (c.getData() == bean) {
				return (Tile) c;
			}
		}
		return null;
	}

	/**
	 * Añadir un componente en base a un Bean.
	 * 
	 * @param bean
	 * @throws LocalizedException
	 */
	private void addItem(final T bean) throws LocalizedException {

		Tile tile = null;

		// Personalizar componente del bean
		if (customBeanView != null) {

			AbstractComponent c = customBeanView.getComponent(bean);

			if (c != null) {
				tile = new Tile(shadow);
				tile.addComponent(c);
			}
		}
		// estándar
		else {
			tile = new Tile(viewAdapter.toHtml(bean));
			try {
				tile.setIcon(viewAdapter.getIcon(bean));
			}
			catch (LocalizedException e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}

		// Asociar el bean al tile para realizar búsquedas del
		// componente a partir del bean y viceversa.
		if (tile != null) {

			if (fullWidth) {
				tile.setWidth("100%");
			}

			if (!shadow && border) {
				tile.addStyleName(CssVaadin.getBorder());
			}

			layout.addComponent(tile);

			tile.setData(bean);

			tile.addLayoutClickListener(new LayoutClickListener() {
				private static final long serialVersionUID = 7531741980132997022L;

				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (!event.isDoubleClick()) {
						if (listener != null) {
							try {
								listener.editBean(bean);
							}
							catch (LocalizedException e) {
								MessagesUtil.error(e);
							}
						}
					}
				}
			});
		}

	}

	@Override
	public void setListener(ListBeansViewListener<T> listener) {
		this.listener = listener;
	}

	/**
	 * Obtener la lista actual de beans
	 * 
	 * @return
	 */
	@Override
	public Collection<T> getBeans() throws LocalizedException {
		return container.getItemIds();
	}

	public boolean isShadow() {
		return shadow;
	}

	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public boolean isFullWidth() {
		return fullWidth;
	}

	public void setFullWidth(boolean fullWidth) {
		this.fullWidth = fullWidth;
	}

	public String[] getSortFieldNames() {
		return sortFieldNames;
	}

	public LazyPagedContainer<T> getContainer() {
		return container;
	}

	public void setContainer(LazyPagedContainer<T> container) {
		this.container = container;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		if (number > 0) {
			this.number = number;
		}
	}

}
