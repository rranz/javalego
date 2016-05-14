package com.javalego.ui.mvp.editor.bean;

import java.util.List;

import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.bean.IDataBeanFieldModel;
import com.javalego.ui.mvp.editor.IBaseEditorModel;

/**
 * Modelo de editor de un Bean
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBeanEditorModel<T> extends IBaseEditorModel {

	/**
	 * Lista de campos a editar.
	 * @return
	 */
	IDataBean<T> getDataBean();

	/**
	 * Añadir objeto a editar.
	 * @param name
	 * @param value
	 */
	List<IDataBeanFieldModel> getFieldModel();
	
}
