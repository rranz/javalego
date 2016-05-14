package com.javalego.ui.mvp.editor;

import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.BaseModel;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;

/**
 * Modelo básico de editor.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IBaseEditorModel extends BaseModel {

	/**
	 * Buscar el item de editor de un campo
	 * @param name
	 * @return
	 */
	IItemEditor find(String fieldName);
	
	/**
	 * Lista de campos a editar.
	 * @return
	 */
	List<IItemEditor> getData();

	/**
	 * Valor del campo antes de la edición.
	 * @param fieldName
	 * @return
	 * @throws LocalizedException 
	 */
	Object getValue(String fieldName) throws LocalizedException;

	/**
	 * Validación del valor de una propiedad
	 * @param propertyName
	 * @param value
	 * @return
	 */
	List<LocalizedException> validate(String propertyName, Object value);

	/**
	 * Obtiene el primer bean del editor. Si existen varios beans asociados al editor, obtendremos el primero.
	 * @return
	 */
	Object getBean();

	/**
	 * Modelo de sus editores detalle.
	 * @return
	 */
	Collection<IBeansEditorModel<?>> getDetail();

	/**
	 * Actualizar el Bean de los campos de tipo DataBean y DataBeanFieldModel.
	 * @param bean
	 */
	void updateBean(Object bean);
}
