package com.javalego.ui.vaadin.view.editor.beans.table;

import java.util.Collection;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.beans.view.list.IListBeansView;

/**
 * Vista Vaadin de paginación de beans.
 * 
 * @author ROBERTO RANZ
 * 
 * @param <T>
 */
public class TableBeansView<T> extends AbstractTableBeansView<T> implements IListBeansView<T> {

	private static final long serialVersionUID = 937331736226587814L;

	protected ListBeansViewListener<T> listener;
	
	public TableBeansView(Collection<FieldModel> fieldModels, String[] columnNames) {
		super(fieldModels, columnNames);
	}

	@Override
	public void load() throws LocalizedException {

		setSpacing(true);
		addComponent(getGrid());
		reloadBeans(listener.getBeans(null, null));
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
	public void setListener(ListBeansViewListener<T> listener) {
		this.listener = listener;
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
