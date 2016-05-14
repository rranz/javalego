package com.javalego.store.mvp.list;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.IProject;
import com.javalego.store.ui.components.UIFactory;
import com.javalego.store.ui.editor.IItemEditor;
import com.javalego.ui.mvp.beans.view.list.IListBeansView;
import com.javalego.ui.mvp.beans.view.list.IListBeansViewModel;
import com.javalego.ui.mvp.beans.view.list.ListBeansViewObserver;
import com.javalego.ui.mvp.beans.view.list.ListBeansViewPresenter;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorObserver;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.ui.vaadin.component.button.css.MenuPanelsCss;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.view.editor.beans.list.ICustomBeanView;
import com.javalego.ui.vaadin.view.editor.beans.list.TileBeansView;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Detail del item de la tienda seleccionado.
 * 
 * @author ROBERTO RANZ
 */
public class ListItemView<T extends IBaseItem> extends CustomComponent implements IListItemView<T> {

	private static final long serialVersionUID = -7393361450909962487L;

	private boolean isInsertable;

	private CssLayout mainLayout;

	private ListItemViewListener<T> listener;

	private TileBeansView<T> view;

	/**
	 * Constructor
	 * 
	 * @param isInsertable
	 */
	public ListItemView(boolean isInsertable) {
		this.isInsertable = isInsertable;
	}

	/**
	 * Cargar los componentes de la lista
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	@Override
	public void load() throws LocalizedException {
		
		if (view != null) {
			view.load();
		}
		else {
			loadList();
		}
	}

	/**
	 * Visualizar la lista de items.
	 * 
	 * @param menuItem
	 * @return
	 */
	@SuppressWarnings("serial")
	private void loadList() {

		mainLayout = new CssLayout();
		mainLayout.setWidth("100%");

		setCompositionRoot(mainLayout);

		// Modelo de datos
		final IListBeansViewModel<T> model = new IListBeansViewModel<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public Class<T> getBeanClass() {
				return (Class<T>) IBaseItem.class;
			}

			@Override
			public Collection<T> getBeans(String filter, String order) throws LocalizedException {
				return listener.getItems();
			}
		};

		// LazyPagedContainer<T> container = new
		// LazyPagedContainer<T>(IBaseItem.class) {
		// };
		//
		// container.setLazyQuery(new LazyQuery<T>() {
		// @Override
		// public int getLazySize() throws LocalizedException {
		// return model.getBeans(null, null).size();
		// }
		//
		// @SuppressWarnings("unchecked")
		// @Override
		// public List<T> getLazyItemsIds(int startIndex, int numberOfIds)
		// throws LocalizedException {
		// return (List<T>) Arrays.asList(model.getBeans(null, null).toArray());
		// }
		//
		// @Override
		// public int getLazyFilteredSize() throws LocalizedException {
		// return model.getBeans(null, null).size();
		// }
		//
		// @SuppressWarnings("unchecked")
		// @Override
		// public List<T> getLazyFilteredItemsIds(int startIndex, int
		// numberOfIds) throws LocalizedException {
		// return (List<T>) Arrays.asList(model.getBeans(null, null).toArray());
		// }
		// });

		// Personalización del contenido del bean en función del tipo de
		// visualización,
		view = new TileBeansView<T>(new ICustomBeanView<T>() {
			@Override
			public AbstractComponent getComponent(final T bean) throws LocalizedException {
				return getTile(bean);
			}
		});

		view.setFullWidth(listener.isFullWidth());

		final ListBeansViewPresenter<T> presenter = new ListBeansViewPresenter<T>(model, view);

		presenter.setObserver(new ListBeansViewObserver<T>() {
			@SuppressWarnings({ "unchecked" })
			@Override
			public void editBean(T bean) throws LocalizedException {

				// Editor del item
				IItemEditor<T> detail = (IItemEditor<T>) UIFactory.getEditor(bean, listener.isReadOnly(bean), true);

				if (detail == null) {
					throw new LocalizedException("IS NO EDITOR FOR " + bean.getClass().getSimpleName());
				}

				listener.editing(bean);

				// Notificación de cambios
				detail.setObserver(getObserver(view, false));

				detail.load();
				detail.setWidth("100%");
				setContent(detail);
			}
		});

		try {
			presenter.load();

			mainLayout.addComponent((Component) view);

			if (listener.isInsertable()) {

				Button b = new Button();
				b.addStyleName(ValoTheme.BUTTON_LARGE);
				b.setIcon(FontAwesome.PLUS);

				b.addStyleName(CssVaadin.getMargin4());

				b.addClickListener(new ClickListener() {
					@SuppressWarnings({ "unchecked" })
					@Override
					public void buttonClick(ClickEvent event) {

						T bean = null;
						try {
							// Instanciar el bean
							bean = listener.getInstance();
							if (bean != null) {

								listener.inserting(bean);

								// Obtener el editor para el tipo de bean
								IItemEditor<T> detail = (IItemEditor<T>) UIFactory.getEditor(bean, false, false);

								// Notificación de cambios
								detail.setObserver(getObserver(view, true));
								detail.load();
								detail.setWidth("100%");
								setContent(detail);
							}
						}
						catch (LocalizedException e) {
							MessagesUtil.error(e);
						}
					}
				});
				mainLayout.addComponent(b);
			}
		}
		catch (LocalizedException e) {
			MessagesUtil.error(e);
		}
	}

	/**
	 * Configurar el componente Tile para cada item de la lista.
	 * 
	 * @param bean
	 * @param layout
	 * @throws LocalizedException
	 */
	private AbstractComponent getTile(T bean) throws LocalizedException {

		HorizontalLayout layout = new HorizontalLayout();
		layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);

		layout.setWidth("100%");
		// titleLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);

		CssLayout l = new CssLayout();

		Component i = UIContextVaadin.getComponent(listener.getIcon(bean));
		if (i != null) {
			i.addStyleName(CssVaadin.getMargin4());
			l.addComponent(i);
		}

		if (listener.getColor(bean) != null) {
			l.addStyleName(MenuPanelsCss.getLevel2(listener.getColor(bean)));
			l.setHeight("100%");
		}

		layout.addComponent(l);

		CssLayout body = new CssLayout();
		body.addStyleName(CssVaadin.getMargin4());

		layout.addComponent(body);

		Label label = new Label(bean.toTile(), ContentMode.HTML);
		body.addComponent(label);
		layout.setExpandRatio(body, 1);

		if (bean instanceof IProject) {
			IProject p = (IProject) bean;
			body.addComponent(UIFactory.getProviders(p.getProviders()));
		}

		return layout;
	}

	/**
	 * Obtener el observer del editor para aplicar los cambios en la vista de
	 * items.
	 * 
	 * @param view
	 * @param insert
	 * @return
	 */
	private BeanEditorObserver<T> getObserver(final IListBeansView<T> view, final boolean insert) {

		return new BeanEditorObserver<T>() {
			@Override
			public void commit(T bean) throws LocalizedException {

				listener.save(bean);

				if (insert) {
					view.insert(bean);
				}
				else {
					view.update(bean);
				}
				setContent(mainLayout);

				listener.saved(bean);
			}

			@Override
			public void discard(T bean) {

				listener.canceled(bean);

				setContent(mainLayout);
			}

			@Override
			public void remove(T bean) throws LocalizedException {

				listener.delete(bean);

				view.removeBean(bean);

				setContent(mainLayout);
			}
		};
	}

	/**
	 * Establecer el componente visible: lista de items o item en edición.
	 * 
	 * @param component
	 */
	private void setContent(Component component) {

		setCompositionRoot(component);
	}

	@Override
	public void setListener(ListItemViewListener<T> listener) {
		this.listener = listener;
	}

	public boolean isInsertable() {
		return isInsertable;
	}

	public void setInsertable(boolean isInsertable) {
		this.isInsertable = isInsertable;
	}

	/**
	 * Obtener la lista de beans actualmente editada.
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	public Collection<T> getBeans() throws LocalizedException {

		return (Collection<T>) view.getBeans();
	}
}
