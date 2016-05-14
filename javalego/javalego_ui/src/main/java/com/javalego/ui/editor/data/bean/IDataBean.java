package com.javalego.ui.editor.data.bean;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.field.FieldModel;

/**
 * Bean de datos donde se define la lista de propiedades que vamos a editar o visualizar.
 * 
 * @author ROBERTO RANZ
 */
public interface IDataBean<T> extends IItemEditor {

	/**
	 * Bean de datos del editor.
	 * @return
	 */
	public abstract T getBean();

	/**
	 * Establecer el bean de datos del editor.
	 * @param bean
	 */
	public abstract void setBean(T bean);
	
	/**
	 * Añadir propiedad.
	 * @param name
	 */
	public abstract IDataBeanFieldModel addField(String name);

	/**
	 * Añadir propiedad.
	 * @param name
	 */
	public abstract IDataBeanFieldModel addField(Key title);

	/**
	 * Añadir propiedad.
	 * @param name
	 * @param required
	 */
	public abstract IDataBeanFieldModel addField(Key title, boolean required);

	/**
	 * Añadir propiedades.
	 * @param name
	 */
	public abstract void addFields(Key... titles);

	/**
	 * Añadir propiedad.
	 * @param name
	 * @param readOnly
	 */
	public abstract IDataBeanFieldModel addField(String name, boolean readOnly);

	/**
	 * Añadir propiedad
	 * @param name
	 * @param title
	 */
	public abstract IDataBeanFieldModel addField(String name, Key title);
	
	/**
	 * Añadir propiedad
	 * @param name
	 * @param title
	 * @param size
	 * @param displayWidth
	 */
	public abstract IDataBeanFieldModel addField(String name, Key title, int size, int displayWidth);
	
	/**
	 * Número de propiedades a editar.
	 * @return
	 */
	public abstract int getCount();

	/**
	 * Lista de nombres de propiedades el bean a editar o visualizar.
	 * @return
	 */
	public abstract List<IDataBeanFieldModel> getFields();

	/**
	 * Valor de la propiedad de un objeto.
	 * @param name
	 * @return
	 * @throws LocalizedException 
	 */
	public abstract Object getValue(String name) throws LocalizedException;

	/**
	 * Añadir una propiedad del bean a editar basada en un IFieldModel donde podemos especificar propiedades adicionales como nombre, título, description, ...
	 * @param fieldModel
	 */
	public abstract IDataBeanFieldModel addField(FieldModel fieldModel);

	/**
	 * Añadir propiedades del bean a editar basada en un IFieldModel donde podemos especificar propiedades adicionales como nombre, título, description, ...
	 * @param fieldModel
	 */
	public abstract void addFields(FieldModel... fieldModel);

	/**
	 * Añadir una propiedad del bean a editar basada en un IFieldModel donde podemos especificar propiedades adicionales como nombre, título, description, ...
	 * @param fieldModel
	 * @param options
	 */
	public abstract IDataBeanFieldModel addField(FieldModel fieldModel, IActionEditor... actions);

	/**
	 * Añadir una propiedad del bean donde podemos especificar propiedades adicionales como nombre, título, description, ...
	 * @param fieldModel
	 * @param options
	 */
	public abstract IDataBeanFieldModel addField(String name, IActionEditor... actions);

	/**
	 * Buscar un dato del bean a editar.
	 * @param name
	 * @return
	 */
	public abstract IDataFieldModel find(String name);

	/**
	 * Añadir todos los atributos del bean utilizando reflexion Java.
	 */
	public abstract void addAll();

}
