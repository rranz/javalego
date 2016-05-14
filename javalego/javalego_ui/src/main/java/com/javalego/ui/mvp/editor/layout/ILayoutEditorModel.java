package com.javalego.ui.mvp.editor.layout;

import java.util.List;

import com.javalego.model.BaseModel;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.mvp.editor.IBaseEditorModel;

/**
 * Modelo de editor de objetos.
 * 
 * @see IDataBean
 * 
 * @author ROBERTO RANZ
 *
 */
public interface ILayoutEditorModel extends BaseModel {

	/**
	 * Lista de otros layouts hijos. Recursividad.
	 * @return
	 */
	List<ILayoutEditorModel> getChildren();	
	
	/**
	 * Lista de datos a incluir en el layout.
	 * 
	 * @return
	 */
	List<IItemEditor> getData();

	/**
	 * Icono existente en el repository y accesible por su valor enumerado.
	 * 
	 * @return
	 */
	Icon getIcon();

	/**
	 * Color de la cabecera
	 * 
	 * @return
	 */
	Colors getColor();

	/**
	 * Añadir campos desde el modelo del editor.
	 * 
	 * @param editorModel
	 * @param names
	 */
	void addDataEditor(IBaseEditorModel editorModel, String[] names);

	/**
	 * Añadir campos desde el modelo del editor.
	 * 
	 * @param editorModel
	 * @param names
	 */
	void addDataEditor(IBaseEditorModel editorModel, Key[] names);

	/**
	 * Actualizar los campos de tipo bean con la instancia del bean existen en
	 * el model de editor (IEditorModel). Este método permitirá reutilizar los
	 * mismos campos para la edición de diferentes beans.
	 * 
	 * @param bean
	 */
	void updateBean(IBaseEditorModel editorModel);

	/**
	 * Añadir un layout hijo o agrupación de campos dentro del layout. Ej.: Bean
	 * Persona añadimos el layout de datos de contacto (child).
	 * 
	 * @param title
	 * @param fieldnames
	 */
	ILayoutEditorModel addChildLayout(Key title, String... fieldnames);

	/**
	 * Añadir un layout hijo o agrupación de campos dentro del layout. Ej.: Bean
	 * Persona añadimos el layout de datos de contacto (child).
	 * 
	 * @param title
	 * @param fields
	 */
	ILayoutEditorModel addChildLayout(Key title, Key... fields);

	/**
	 * Añadir un layout hijo o agrupación de campos dentro del layout. Ej.: Bean
	 * Persona añadimos el layout de datos de contacto (child).
	 * 
	 * @param title título de agrupación de campos
	 * @param color Color del título
	 * @param fields
	 */
	ILayoutEditorModel addChildLayout(Key title, Colors color, Key... fields);

	/**
	 * Añadir un layout hijo o agrupación de campos dentro del layout. Ej.: Bean
	 * Persona añadimos el layout de datos de contacto (child).
	 * 
	 * @param title título de agrupación de campos
	 * @param color Color del título
	 * @param fieldnames
	 */
	ILayoutEditorModel addChildLayout(Key title, Colors color, String... fieldnames);

	/**
	 * Añadir un layout hijo o agrupación de campos dentro del layout. Ej.: Bean
	 * Persona añadimos el layout de datos de contacto (child).
	 * 
	 * @param layoutEditorModel
	 */
	void addChildLayout(ILayoutEditorModel layoutEditorModel);

	/**
	 * Añadir un campo en una posición de la lista de campos actual.
	 * @param fieldname
	 * @param index
	 */
	void addField(String fieldname, int index);
	
	/**
	 * Añadir un campo en una posición de la lista de campos actual.
	 * @param fieldname
	 */
	void addField(String fieldname);

}
