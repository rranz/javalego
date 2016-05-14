package com.javalego.ui.vaadin.view.editor.beans.table.paged;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.beans.view.paged.IPagedBeansView;
import com.javalego.ui.vaadin.view.editor.beans.table.AbstractTableBeansView;

/**
 * Vista Vaadin de paginación de beans.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public class TablePagedBeansView<T> extends AbstractTableBeansView<T> implements IPagedBeansView<T> {

	private static final long serialVersionUID = -8529064488969943840L;

	private PagedBeansViewListener<T> listener;

	private ToolBarPagedEditor<T> toolbar;
	
	public TablePagedBeansView(Collection<FieldModel> fieldModels, String[] columnNames) {
		super(fieldModels, columnNames);
	}

	@Override
	public void load() throws LocalizedException {

		setSpacing(true);

		addComponent(getGrid());

		toolbar = new ToolBarPagedEditor<T>(listener);

		reloadBeans(listener.getFirstPageBeans());

		addComponent(toolbar);
	}

	@Override
	public void setListener(PagedBeansViewListener<T> listener) {
		this.listener = listener;
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
	public void editBean(T bean) throws LocalizedException {
		listener.editBean(bean);
	}

	@Override
	public Class<? super T> getBeanClass() {
		return listener.getBeanClass();
	}

}
