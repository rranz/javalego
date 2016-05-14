package com.javalego.store.ui.editor.impl;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.IComment;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IScreenShot;
import com.javalego.store.mvp.list.ListItemView;
import com.javalego.store.mvp.list.ListItemViewObserver;
import com.javalego.store.mvp.list.presenter.CommentListItemPresenter;
import com.javalego.store.mvp.list.presenter.ScreenShotListItemPresenter;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.components.UIFactory;
import com.javalego.store.ui.editor.BaseItemEditor;
import com.javalego.ui.editor.builder.BeanEditorsBuilder;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorPresenter;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.view.editor.ToolBarEditorView;
import com.javalego.ui.vaadin.view.editor.bean.BeanEditorView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;

/**
 * Mostrar detalle de un PRODUCTO.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ProductEditor extends BaseItemEditor<IProduct> {

	private static final long serialVersionUID = -6078895634284885616L;

	private IProduct product;

	private CommentListItemPresenter commentPresenter;

	private ScreenShotListItemPresenter screenShotspresenter;

	private ToolBarEditorView toolbar;

	/**
	 * Constructor
	 * 
	 * @param product
	 * @param readOnly
	 */
	public ProductEditor(IProduct product, boolean readOnly, boolean remove) {
		this.product = product;
		this.readOnly = readOnly;
		this.remove = remove;
	}

	/**
	 * Cargar editor del producto
	 * 
	 * @throws LocalizedException
	 */
	@SuppressWarnings("serial")
	@Override
	public void load() throws LocalizedException {

		super.load();

		try {
			// Editar los datos del producto.
			final BeanEditorPresenter<IProduct> presenter = new BeanEditorsBuilder<IProduct>().getEditor(StoreAppContext.getCurrent().getModelService()
					.getProductFields(product.getProject().getType()), new BeanEditorView<IProduct>().light().setToolbar(false), product, readOnly, remove);

			presenter.setLayout(StoreAppContext.getCurrent().getModelService().getProductLayoutEditor(presenter.getModel(), readOnly));

			presenter.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					if (event.getFieldName().equals("title")) {
						loadTitle(event.getValue() == null ? "" : event.getValue().toString());
					}
					else if (event.getFieldName().equals("category")) {
						try {
							updateIcon(event.getValue());
						}
						catch (LocalizedException e) {
							MessagesUtil.error(e);
						}
					}
				}
			});

			// Controlar la edición CRUD
			if (observer != null) {
				presenter.setObserver(observer);
			}

			presenter.load();
			addComponent((Component) presenter.getView());
			addSocial();
			addScreenShots();
			addComments();

			if (!presenter.isReadOnly()) {
				toolbar = new ToolBarEditorView(presenter);
				addComponent(toolbar);
			}

		}
		catch (Exception e) {
			MessagesUtil.error("ERROR EDIT PRODUCT", e);
		}

	}

	/**
	 * Imágenes
	 * 
	 * @throws LocalizedException
	 */
	private void addScreenShots() throws LocalizedException {

		Layout layout = UIFactory.getScreenShots(product.getScreenshots());

		layout.setWidth("100%");

		ListItemView<IScreenShot> view = new ListItemView<IScreenShot>(readOnly);

		screenShotspresenter = new ScreenShotListItemPresenter(StoreAppContext.getCurrent().getDataServices(), product.getScreenshots(), view);
		screenShotspresenter.load();
		screenShotspresenter.setObserver(new ListItemViewObserver<IScreenShot>() {
			@Override
			public void inserting(IScreenShot bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void editing(IScreenShot bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void saved(IScreenShot bean) {
				if (!commentPresenter.isEditing()) {
					toolbar.setEnabled(true);
				}
			}

			@Override
			public void canceled(IScreenShot bean) {
				if (!commentPresenter.isEditing()) {
					toolbar.setEnabled(true);
				}
			}
		});

		layout.addComponent(view);

		addComponent(layout);
	}

	/**
	 * Comentarios
	 * 
	 * @throws LocalizedException
	 */
	private void addComments() throws LocalizedException {

		Layout layout = UIFactory.getComments();
		layout.setWidth("100%");

		ListItemView<IComment> view = new ListItemView<IComment>(readOnly);

		commentPresenter = new CommentListItemPresenter(StoreAppContext.getCurrent().getDataServices(), product.getComments(), view);
		commentPresenter.load();
		commentPresenter.setObserver(new ListItemViewObserver<IComment>() {
			@Override
			public void inserting(IComment bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void editing(IComment bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void saved(IComment bean) {
				if (!screenShotspresenter.isEditing()) {
					toolbar.setEnabled(true);
				}
			}

			@Override
			public void canceled(IComment bean) {
				if (!screenShotspresenter.isEditing()) {
					toolbar.setEnabled(true);
				}
			}
		});

		layout.addComponent(view);

		addComponent(layout);
	}

	/**
	 * Redes sociales
	 */
	private void addSocial() {

		addComponent(UIFactory.getResources(product.getProject().getSocial(), product.getProject().getCode(), product.getDemo()));
	}

	@Override
	public IBaseItem getItem() {
		return product;
	}

	@Override
	protected String getBeanTitle() {
		return product.getTitle();
	}

	@Override
	protected IProduct getBean() {
		return product;
	}

	@Override
	protected Icon getBeanIcon() {
		return product.getCategory() != null ? product.getCategory().getIcon() : product.getIcon();
	}
}
