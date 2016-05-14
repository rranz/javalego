package com.javalego.store.ui.editor.impl;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.ui.editor.BaseItemEditor;
import com.javalego.ui.editor.builder.BeanEditorsBuilder;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorPresenter;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.view.editor.ToolBarEditorView;
import com.javalego.ui.vaadin.view.editor.bean.BeanEditorView;
import com.vaadin.ui.Component;

/**
 * Editor común a varios tipos de items (reutilización).
 * 
 * @author ROBERTO RANZ
 * 
 */
public class CommonEditor extends BaseItemEditor<IBaseItem> {

	private static final long serialVersionUID = 6769050402970617000L;

	/**
	 * Bean a editar
	 */
	private IBaseItem bean;

	/**
	 * Modelo de campos a editar 
	 */
	private Collection<FieldModel> model;

	/**
	 * Constructor
	 * @param bean
	 * @param model Modelo de campos a editar
	 * @param readOnly
	 * @param remove
	 */
	public CommonEditor(IBaseItem bean, Collection<FieldModel> model, boolean readOnly, boolean remove) {
		this.bean = bean;
		this.readOnly = readOnly;
		this.remove = remove;
		this.model = model;
	}

	@Override
	protected String getCss(Component c) {
		return "background: #F2F2F2; margin: 0px; padding: 2px; vertical-align: top; display: inline-block;";
	}
	
	
	@Override
	public void load() throws LocalizedException {

		super.load();

		try {
			// Editar los datos
			final BeanEditorPresenter<IBaseItem> presenter = new BeanEditorsBuilder<IBaseItem>().getEditor(model, new BeanEditorView<IBaseItem>().light().setToolbar(false), bean, readOnly, remove);

			// Controlar la edición CRUD
			if (observer != null) {
				presenter.setObserver(observer);
			}

			presenter.load();
			addComponent((Component) presenter.getView());

			if (!presenter.isReadOnly()) {
				addComponent(new ToolBarEditorView(presenter));
			}

		}
		catch (Exception e) {
			MessagesUtil.error("ERROR EDIT ITEM", e);
		}
	}

	@Override
	public IBaseItem getItem() {
		return bean;
	}

	@Override
	protected String getBeanTitle() {
		return bean.getTitle();
	}

	@Override
	protected IBaseItem getBean() {
		return bean;
	}

	@Override
	protected Icon getBeanIcon() {
		return bean.getIcon();
	}

}
