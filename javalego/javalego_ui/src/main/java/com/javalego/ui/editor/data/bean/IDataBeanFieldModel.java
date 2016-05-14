package com.javalego.ui.editor.data.bean;

import com.javalego.ui.editor.data.IDataFieldModel;

/**
 * Dato a editar basado en un IFieldModel.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IDataBeanFieldModel extends IDataFieldModel {

	/**
	 * DataBean al que está asociado.
	 * @return
	 */
	IDataBean<?> getDataBean();

	/**
	 * Establecer el DataBean del campo a editar
	 * @param dataBean
	 */
	void setDataBean(IDataBean<?> dataBean);
}
