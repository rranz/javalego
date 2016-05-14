package com.javalego.ui.mvp.editor;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.field.FieldModel;

/**
 * Modelo de editor de campos.
 * 
 * @see IDataBean
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IEditorModel extends IBaseEditorModel {

	/**
	 * Añadir objeto a editar.
	 * @param name
	 * @param value
	 */
	IEditorModel add(String name, Object value);

	/**
	 * Añadir objeto a editor basado en un fieldModel
	 * @param fieldModel
	 * @param value
	 */
	IEditorModel add(FieldModel fieldModel, Object value);

	/**
	 * Añadir objeto a editor basado en un fieldModel
	 * @param fieldModel
	 */
	IEditorModel add(FieldModel fieldModel);

	/**
	 * Añadir un dato de editor.
	 * @param dataEditor
	 */
	IEditorModel add(IItemEditor dataEditor);

	/**
	 * Buscar un dato
	 * @param name
	 * @return
	 */
	@Override
	IItemEditor find(String name);

	/**
	 * Buscar un bean definido en el editor buscando por uno de sus campos de tipo IDataBean.
	 * @param fieldName
	 * @return
	 * @throws LocalizedException 
	 */
	Object getBean(String fieldName) throws LocalizedException;
	
}
