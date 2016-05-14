package com.javalego.ui.editor.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.javalego.exception.LocalizedException;
import com.javalego.model.validator.ValidatorAdapter;
import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.bean.IDataBeanFieldModel;
import com.javalego.ui.field.FieldModel;

/**
 * Dato basado en una configuración de campo
 * 
 * @author ROBERTO RANZ
 * 
 */
public class DataBeanFieldModel extends PrimitiveDataFieldModel implements IDataBeanFieldModel {

	private IDataBean<?> dataBean;

	private IActionEditor[] actions;

	/**
	 * Constructor
	 * 
	 * @param dataBean
	 * @param fieldModel
	 * @param options
	 *            Lista de opciones
	 */
	public DataBeanFieldModel(DataBean<?> dataBean, FieldModel fieldModel, IActionEditor... actions) {
		super(fieldModel, null);
		this.dataBean = dataBean;
		this.actions = actions;
	}

	/**
	 * Constructor
	 * 
	 * @param dataBean
	 * @param fieldModel
	 */
	public DataBeanFieldModel(DataBean<?> dataBean, FieldModel fieldModel) {
		super(fieldModel, null);
		this.dataBean = dataBean;
	}

	@Override
	public IDataBean<?> getDataBean() {
		return dataBean;
	}

	@Override
	public void setDataBean(IDataBean<?> dataBean) {
		this.dataBean = dataBean;
	}

	@Override
	public IActionEditor[] getActions() {
		return actions;
	}

	@Override
	public List<LocalizedException> validate(Object value) {

		// Validar las anotaciones definidas en el bean.
		Set<ConstraintViolation<?>> constraints = ValidatorAdapter.getCurrent().validateValue(dataBean.getBean().getClass(), value, getName());

		// Validar el modelo (required, max, min, ...)
		Set<ConstraintViolation<?>> constraints2 = ValidatorAdapter.getCurrent().validateValue(getFieldModel().getName(), getFieldModel().isRequired(), value, dataBean.getBean().getClass());

		if (constraints2 != null) {
			if (constraints != null) {
				constraints.addAll(constraints2);
			}
			else {
				constraints = constraints2;
			}
		}
		
		if (constraints != null && constraints.size() > 0) {

			List<LocalizedException> list = new ArrayList<LocalizedException>();

			for (ConstraintViolation<?> item : constraints) {
				list.add(new LocalizedException(item.getMessage()));
			}

			return list;
		}
		else {
			return null;
		}
	}

}
