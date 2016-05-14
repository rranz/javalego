package com.javalego.store.ui.editor.impl;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.IComment;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.mvp.list.ListItemView;
import com.javalego.store.mvp.list.ListItemViewObserver;
import com.javalego.store.mvp.list.presenter.CommentListItemPresenter;
import com.javalego.store.mvp.list.presenter.ProductListItemPresenter;
import com.javalego.store.ui.StoreAppContext;
import com.javalego.store.ui.components.UIFactory;
import com.javalego.store.ui.editor.BaseItemEditor;
import com.javalego.store.ui.icons.ProviderIcons;
import com.javalego.ui.editor.builder.BeanEditorsBuilder;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorPresenter;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.view.editor.ToolBarEditorView;
import com.javalego.ui.vaadin.view.editor.bean.BeanEditorView;
import com.vaadin.ui.Layout;

/**
 * Mostrar detalle de un PROYECTO.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class ProjectEditor extends BaseItemEditor<IProject> {

	private static final long serialVersionUID = -7045736841844893722L;

	private IProject project;

	private ProductListItemPresenter productPresenter;

	private CommentListItemPresenter commentPresenter;

	private ToolBarEditorView toolbar;

	/**
	 * Constructor
	 * 
	 * @param product
	 * @param readOnly
	 * @param remove
	 */
	public ProjectEditor(IProject product, boolean readOnly, boolean remove) {
		this.project = product;
		this.remove = remove;
		this.readOnly = readOnly;
	}

	/**
	 * Incluir todos los componentes del proyecto.
	 * 
	 * @throws LocalizedException
	 */
	@SuppressWarnings("serial")
	@Override
	public void load() throws LocalizedException {

		super.load();

		// Editar los datos del producto.
		BeanEditorView<IProject> view = new BeanEditorView<IProject>().light();

		final BeanEditorPresenter<IProject> presenter = new BeanEditorsBuilder<IProject>().getEditor(StoreAppContext.getCurrent().getModelService().getProjectFields(readOnly), view.setToolbar(false),
				project, readOnly, remove);

		presenter.setLayout(StoreAppContext.getCurrent().getModelService().getProjectLayoutEditor(presenter.getModel(), readOnly));

		view.setWidth("100%");

		presenter.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getFieldName().equals("title")) {
					loadTitle(event.getValue() == null ? "" : event.getValue().toString());
				}
			}
		});

		// Controlar la edición CRUD
		if (observer != null) {
			presenter.setObserver(observer);
		}

		try {
			presenter.load();

			addComponent(view);

			// Editores detalle
			addSocial();
			addProducts();
			addComments();
		}
		catch (Exception e) {
			MessagesUtil.error("ERROR EDIT PROJECT", e);
		}

		if (!readOnly) {
			toolbar = new ToolBarEditorView(presenter);
			addComponent(toolbar);
		}
	}

	/**
	 * Redes sociales
	 */
	private void addSocial() {

		if (readOnly) {
			addComponent(UIFactory.getResources(project.getSocial(), project.getCode(), project.getDemo()));
		}
	}

	/**
	 * Productos
	 * 
	 * @return
	 * @throws LocalizedException
	 */
	private void addProducts() throws LocalizedException {

		Layout layout = UIFactory.getProducts();
		layout.setWidth("100%");

		ListItemView<IProduct> view = new ListItemView<IProduct>(readOnly);

		productPresenter = new ProductListItemPresenter(StoreAppContext.getCurrent().getDataServices(), project, view);
		productPresenter.load();
		productPresenter.setObserver(new ListItemViewObserver<IProduct>() {
			@Override
			public void saved(IProduct bean) {
				if (!commentPresenter.isEditing()) {
					toolbar.setEnabled(true);
				}
			}

			@Override
			public void inserting(IProduct bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void editing(IProduct bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void canceled(IProduct bean) {
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

		commentPresenter = new CommentListItemPresenter(StoreAppContext.getCurrent().getDataServices(), project.getComments(), view);
		commentPresenter.load();
		commentPresenter.setObserver(new ListItemViewObserver<IComment>() {
			@Override
			public void saved(IComment bean) {
				if (!productPresenter.isEditing()) {
					toolbar.setEnabled(true);
				}
			}

			@Override
			public void inserting(IComment bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void editing(IComment bean) {
				toolbar.setEnabled(false);
			}

			@Override
			public void canceled(IComment bean) {
				if (!productPresenter.isEditing()) {
					toolbar.setEnabled(true);
				}
			}
		});

		layout.addComponent(view);

		addComponent(layout);
	}

	@Override
	public IBaseItem getItem() {
		return project;
	}

	@Override
	protected String getBeanTitle() {
		return project.getTitle();
	}

	@Override
	protected IProject getBean() {
		return project;
	}

	@Override
	protected Icon getBeanIcon() {
		return ProviderIcons.PROJECT;
	}

}
