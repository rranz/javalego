package com.javalego.ui.vaadin.view.editor.beans.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.icons.enums.IconEditor;
import com.javalego.ui.listeners.ItemChangeEvent;
import com.javalego.ui.menu.IMenuItem;
import com.javalego.ui.menu.IMenuModel;
import com.javalego.ui.menu.MenuPresenter;
import com.javalego.ui.menu.MenuPresenterListener;
import com.javalego.ui.mvp.beans.view.list.IListBeansView;
import com.javalego.ui.mvp.beans.view.list.adapter.IListBeansViewAdapter;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.component.util.ResourceUtils;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.javalego.ui.vaadin.view.menu.ListMenu;
import com.javalego.ui.vaadin.view.menu.MenuItemVaadin;
import com.javalego.util.ReflectionUtils;
import com.javalego.util.StringUtils;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * Vista de tipo Lista para representar los datos de cada Bean.
 * 
 * El modelo ListBeansViewListener es el que proporciona el formato en Html de
 * renderizado de los datos de cada Bean de forma personalizada.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ListBeansView<T> extends ListMenu implements IListBeansView<T> {

	private static final long serialVersionUID = -8061680549812016941L;

	private static final Logger logger = Logger.getLogger(ListBeansView.class);

	private ListBeansViewListener<T> listener;

	// formato de datos
	private IListBeansViewAdapter<T> viewAdapter;

	// ordenación de beans
	private String[] sortFieldNames;

	private Collection<T> beans;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param viewAdapter
	 *            Formato de datos mostrados en la vista.
	 */
	public ListBeansView(IListBeansViewAdapter<T> viewAdapter) {

		this.viewAdapter = viewAdapter;
		try {
			init();
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param viewAdapter
	 *            Formato de datos mostrados en la vista.
	 * @param filter
	 *            filtro aplicado en la obtención de beans.
	 * @param sortFieldNames
	 *            ordenación de campos
	 */
	public ListBeansView(IListBeansViewAdapter<T> viewAdapter, String[] sortFieldNames) {

		this.viewAdapter = viewAdapter;
		this.sortFieldNames = sortFieldNames;
		try {
			init();
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
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

	/**
	 * Inicializar la lista de beans
	 * 
	 * @throws LocalizedException
	 */
	private void init() throws LocalizedException {

		MenuPresenter p = new MenuPresenter(new IMenuModel() {
			@Override
			public List<IMenuItem> getSubItems(IMenuItem menuItem) {
				return null;
			}

			@Override
			public List<IMenuItem> getMainItems() {
				List<IMenuItem> items = new ArrayList<IMenuItem>();
				if (beans != null) {
					for (T bean : beans) {
						items.add(getMenuItem(bean));
					}
				}
				return items;
			}

			@Override
			public IMenuItem getHeader() {
				return null;
			}
		}, this, false);

		// Editar bean seleccionado
		p.addItemChangeListener(new MenuPresenterListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void propertyChange(ItemChangeEvent<IMenuItem> event) {
				if (event != null) {
					T bean = (T) event.getItemChange().getData();
					if (bean != null) {
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

	/**
	 * Obtener el menú item a partir de un bean.
	 * 
	 * @param bean
	 * @return
	 */
	protected IMenuItem getMenuItem(T bean) {

		MenuItemVaadin item = new MenuItemVaadin(viewAdapter.toHtml(bean));

		if (viewAdapter.getImageFieldName() != null) {

			byte[] data;
			try {
				data = ReflectionUtils.getPropertyValue(bean, viewAdapter.getImageFieldName());
				if (data != null) {
					item.setResourceIcon(ResourceUtils.getStreamResourcePng(data));
				}
				else {
					item.setResourceIcon(ResourceIconsVaadin.getCurrent().getResource(IconEditor.IC_ACTION_PICTURE));
				}
			}
			catch (Exception e) {
				logger.error("ERROR GET RESOURCE ICON.", e);
			}
		}

		item.setData(bean);
		return item;
	}

	@Override
	public void load() throws LocalizedException {

		loadBeans();
		super.load();
	}

	/**
	 * Obtener la lista de beans del modelo de datos del editor.
	 * 
	 * @throws LocalizedException
	 */
	private void loadBeans() throws LocalizedException {

		beans = listener.getBeans(null, null);

		sort();
	}

	/**
	 * Cambiar la vista que formatea los datos del bean.
	 * 
	 * @param viewAdapter
	 */
	public void setViewAdapter(IListBeansViewAdapter<T> viewAdapter) {
		this.viewAdapter = viewAdapter;
	}

	@Override
	public void removeSelectedBean() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getSelectedBean() {

		if (getSelectedItem() != null) {
			Object data = getSelectedItem().getData();
			return data != null ? (T) data : null;
		}
		else {
			return null;
		}
	}

	@Override
	public void setSelectedBeans(Collection<T> beans) {
	}

	@Override
	public Collection<T> getSelectedBeans() {
		return null;
	}

	// CRUD

	@Override
	public void removeBean(T bean) {

		if (bean != null) {
			removeComponent(findMenuItem(bean));
			if (beans != null) {
				beans.remove(bean);
			}
		}
	}

	/**
	 * Obtener el componente asociado al bean.
	 * 
	 * @param bean
	 * @return
	 */
	private Component findMenuItem(T bean) {

		for (int i = 0; i < getComponentCount(); i++) {
			if (getComponent(i) instanceof AbstractComponent) {
				IMenuItem c = (IMenuItem) ((AbstractComponent) getComponent(i)).getData();
				if (c.getData() == bean) {
					return getComponent(i);
				}
			}
		}
		return null;
	}

	@Override
	public void update(T bean) {

		IMenuItem item = getSelectedItem();
		
		if (bean != null && item != null) {
			item.setData(bean);
			item.setTitle(viewAdapter.toHtml(bean));
			refreshSelectedItem();
		}
	}

	@Override
	public void insert(T bean) {

		if (bean != null) {
			addItem(getMenuItem(bean));
			if (beans == null) {
				beans = new ArrayList<T>();
			}
			beans.add(bean);
		}
	}

	@Override
	public void setListener(ListBeansViewListener<T> listener) {
		this.listener = listener;
	}

	/**
	 * Obtener el bean de una posición en la lista.
	 * 
	 * @param pos
	 * @return
	 */
	public T getBeanPosition(int pos) {
		return null;
	}

	public IListBeansViewAdapter<T> getViewAdapter() {
		return viewAdapter;
	}

	@Override
	public void reloadBeans(Collection<T> beans) throws LocalizedException {
		this.beans = beans;
		sort();
		super.load();
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
	public void applyFilter(String statement) throws LocalizedException {
		listener.applyFilter(statement);
	}

	@Override
	public void removeCurrentFilter() throws LocalizedException {
		listener.removeCurrentFilter();
	}

	@Override
	public Collection<T> getBeans() {
		return beans;
	}

}
