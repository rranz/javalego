package com.javalego.ui.mvp.editor;

import com.javalego.ui.editor.data.bean.IDataBean;

/**
 * Layout de editor donde se pueden incluir los datos de la edición y otro tipo de componentes de edición.
 * 
 * @see IDataBean
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IEditorViewModel extends IEditorModel {

	/**
	 * Añadir datos de edición del modelo de editor al layout.
	 * @param editorModel
	 * @param fieldNames
	 */
	void addDataEditor(String... names);

}
