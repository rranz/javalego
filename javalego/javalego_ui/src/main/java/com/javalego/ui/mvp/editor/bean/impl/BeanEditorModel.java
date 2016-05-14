package com.javalego.ui.mvp.editor.bean.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.AbstractBaseModel;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.bean.IDataBeanFieldModel;
import com.javalego.ui.editor.data.impl.DataBean;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.editor.bean.IBeanEditorModel;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;

/**
 * Modelo de datos del editor.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class BeanEditorModel<T> extends AbstractBaseModel implements IBeanEditorModel<T> {

	private static final long serialVersionUID = -1380828441951997188L;

	private Collection<IBeansEditorModel<?>> detail;

	private DataBean<T> dataBean;

	/**
	 * Constructor
	 * 
	 * @param bean
	 * @param fields
	 */
	public BeanEditorModel(T bean, Collection<FieldModel> fields) {

		if (bean != null) {

			// Crear item de bean
			dataBean = new DataBean<T>(bean);

			// Añadir campos
			if (fields != null) {
				for (FieldModel item : fields) {
					dataBean.addField(item);
				}
			}
		}
	}

	@Override
	public Collection<IBeansEditorModel<?>> getDetail() {
		return detail;
	}

	/**
	 * Establecer el modelo de sus editores detalle.
	 * 
	 * @param detail
	 */
	public void setDetail(Collection<IBeansEditorModel<?>> detail) {

		this.detail = detail;
	}

	@Override
	public IDataBean<T> getDataBean() {

		return dataBean;
	}

	@Override
	public List<IDataBeanFieldModel> getFieldModel() {

		return dataBean != null ? dataBean.getFields() : null;
	}

	@Override
	public Object getValue(String fieldName) throws LocalizedException {

		return dataBean != null ? dataBean.getValue(fieldName) : null;
	}

	@Override
	public List<LocalizedException> validate(String propertyName, Object value) {

		if (dataBean != null) {
			IDataFieldModel _value = dataBean.find(propertyName);
			if (value != null) {
				return _value.validate(value);
			}
		}
		return null;
	}

	@Override
	public T getBean() {
		return dataBean.getBean();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateBean(Object bean) {
		dataBean.setBean((T)bean);
	}

	@Override
	public IItemEditor find(String fieldName) {
		return dataBean != null ? dataBean.find(fieldName) : null;
	}

	@Override
	public List<IItemEditor> getData() {

		if (dataBean != null) {
			List<IItemEditor> list = new ArrayList<IItemEditor>();
			for(IDataBeanFieldModel item : dataBean.getFields()) {
				list.add(item);
			}
			return list;
		}
		else {
			return null;
		}
	}

}
