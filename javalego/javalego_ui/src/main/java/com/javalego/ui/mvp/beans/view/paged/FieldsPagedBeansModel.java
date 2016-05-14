package com.javalego.ui.mvp.beans.view.paged;

import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.field.FieldModel;

/**
 * Modelo donde se define la lista de campos que queremos mostrar en los componentes UI.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public class FieldsPagedBeansModel<T> extends AbstractPagedBeansModel<T> {

	private Collection<FieldModel> fieldsModel;
	
	public FieldsPagedBeansModel(List<T> beans, Class<T> beanClass, Collection<FieldModel> fieldsModel) {
		super(beans, beanClass);
		this.fieldsModel = fieldsModel;
	}


	public Collection<FieldModel> getFieldsModel() {
		return fieldsModel;
	}

	public void setFieldsModel(Collection<FieldModel> fieldsModel) {
		this.fieldsModel = fieldsModel;
	}


	@Override
	public Collection<T> getBeans(String filter, String order) throws LocalizedException {
		// TODO Auto-generated method stub
		return null;
	}


}
