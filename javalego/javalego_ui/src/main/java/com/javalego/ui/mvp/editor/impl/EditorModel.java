package com.javalego.ui.mvp.editor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.exception.JavaLegoException;
import com.javalego.model.AbstractBaseModel;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.IValueDataEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.bean.IDataBeanFieldModel;
import com.javalego.ui.editor.data.impl.PrimitiveDataEditor;
import com.javalego.ui.editor.data.impl.PrimitiveDataFieldModel;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.mvp.editor.IEditorModel;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.util.ReflectionUtils;

/**
 * Modelo de datos del editor.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class EditorModel extends AbstractBaseModel implements IEditorModel {

	private static final long serialVersionUID = -4111728684320580036L;

	/**
	 * Lista de datos del editor.
	 */
	protected List<IItemEditor> data = new ArrayList<IItemEditor>();
	
	/**
	 * Detalle
	 */
	private Collection<IBeansEditorModel<?>> detail;

	@Override
	public List<IItemEditor> getData() {
		return data;
	}

	@Override
	public IEditorModel add(String name, Object object) {
		data.add(new PrimitiveDataEditor(name, null, object));
		return this;
	}

	@Override
	public IEditorModel add(IItemEditor dataEditor) {
		data.add(dataEditor);
		return this;
	}

	@Override
	public IEditorModel add(FieldModel fieldModel, Object object) {
		data.add(new PrimitiveDataFieldModel(fieldModel, object));
		return this;
	}

	@Override
	public IEditorModel add(FieldModel fieldModel) {
		data.add(new PrimitiveDataFieldModel(fieldModel));
		return this;
	}

	@Override
	public IItemEditor find(String name) {

		if (name == null)
			return null;

		for (IItemEditor item : data) {
			
			// Buscar en los campos de tipo bean
			if (item instanceof IDataBean) {
			
				IItemEditor result = ((IDataBean<?>) item).find(name);
				
				if (result != null) {
					return result;
				}
			}
			// Resto de campos primitivos
			else if (item.getName().equals(name)) {
				return item;
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateBean(Object bean) {

		for (IItemEditor item : data) {
			if (item instanceof IDataBean) {
				((IDataBean)item).setBean(bean);
			}
			else if (item instanceof IDataBeanFieldModel) {
				((IDataBean)((IDataBeanFieldModel)item).getDataBean()).setBean(bean);;
			}
		}
	}

	@Override
	public Object getValue(String fieldName) throws LocalizedException {

		for (IItemEditor item : data) {
			// Buscar por el DataBean. Este item no es un campo editable sino un
			// contenedor de IDataBeanFieldModel
			if (item instanceof IDataBean) {
				if (((IDataBean<?>) item).find(fieldName) != null) {
					return ((IDataBean<?>) item).getValue(fieldName);
				}
			}
			else if (item instanceof IDataBeanFieldModel && item.getName().equals(fieldName)) {
				return ((IDataBeanFieldModel) item).getDataBean().getValue(fieldName);
			}
			else if (item instanceof IValueDataEditor && item.getName().equals(fieldName)) {
				return ((IValueDataEditor) item).getValue();
			}
		}
		return null;
	}

	@Override
	public Object getBean(String fieldName) throws LocalizedException {

		if (fieldName == null)
			return null;

		for (IItemEditor item : data) {
			if (item instanceof IDataBean) {
				IDataBean<?> bean = (IDataBean<?>) item;
				try {
					if (ReflectionUtils.getField(bean.getBean().getClass(), fieldName) != null) {
						return bean.getBean();
					}
				}
				catch (JavaLegoException e) {
					throw new LocalizedException(e);
				}
			}
		}
		return null;
	}

	@Override
	public Object getBean() {

		for (IItemEditor item : data) {
			if (item instanceof IDataBean) {
				return ((IDataBean<?>) item).getBean();
			}
		}
		return null;
	}

	@Override
	public List<LocalizedException> validate(String propertyName, Object value) {

		IItemEditor item = find(propertyName);

		return item != null ? item.validate(value) : null;
	}

	@Override
	public Collection<IBeansEditorModel<?>> getDetail() {
		return detail;
	}

	/**
	 * Establecer el modelo de sus editores detalle.
	 * @param detail
	 */
	public void setDetail(Collection<IBeansEditorModel<?>> detail) {
		this.detail = detail;
	}
	
}
