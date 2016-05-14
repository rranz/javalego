package com.javalego.ui.vaadin.view.editor.beans.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.mvp.beans.view.list.IListBeansView;
import com.javalego.ui.mvp.beans.view.list.adapter.IListBeansViewAdapter;
import com.javalego.ui.vaadin.component.tiles.Tile;
import com.javalego.ui.vaadin.component.tiles.TilesLayout;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.util.StringUtils;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

/**
 * Vista de una lista de beans.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public class TileBeansView<T> extends CustomComponent implements IListBeansView<T> {

	private static final long serialVersionUID = 4525496169611841245L;

	private static final Logger logger = Logger.getLogger(TileBeansView.class);

	/**
	 * Listener de la lista de beans
	 */
	private ListBeansViewListener<T> listener;

	/**
	 * formato de datos
	 */
	private IListBeansViewAdapter<T> viewAdapter;

	/**
	 * Lista de beans de la vista
	 */
	private Collection<T> beans;

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
	 * Constructor
	 * 
	 * @param customBeanView
	 */
	public TileBeansView(ICustomBeanView<T> customBeanView) {

		this.customBeanView = customBeanView;

		init();
	}

	/**
	 * Constructor
	 * 
	 * @param viewAdapter
	 */
	public TileBeansView(IListBeansViewAdapter<T> viewAdapter) {

		this.viewAdapter = viewAdapter;

		init();
	}

	/**
	 * Inicializar el componente de tipo layout.
	 * 
	 * NOTA: se podría inicializar diferentes tipos de layout dependiendo de
	 * nuestras necesidades de visualización de beans.
	 */
	private void init() {

		TilesLayout layout = new TilesLayout();

		setCompositionRoot(layout);

		this.layout = layout;
	}

	/**
	 * Ordenar beans
	 */
	private void sort() {

		if (sortFieldNames != null && this.beans instanceof ArrayList) {
			StringUtils.sortList((ArrayList<T>) this.beans, sortFieldNames);
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
			beans.remove(bean);
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
			beans.add(bean);
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
		this.beans = beans;
		sort();
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

		loadBeans();
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
	 * Obtener la lista de beans del modelo de datos del editor.
	 * 
	 * @throws LocalizedException
	 */
	private void loadBeans() throws LocalizedException {

		beans = listener.getBeans(null, null);

		sort();

		layout.removeAllComponents();

		if (beans != null) {

			for (final T bean : beans) {
				addItem(bean);
			}
		}

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
		return beans;
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

}
