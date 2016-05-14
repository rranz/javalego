package com.javalego.ui.editor.data;

import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.field.FieldModel;

/**
 * Dato a editar basado en un IFieldModel.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IDataFieldModel extends IItemEditor {

	/**
	 * Modelo
	 * @return
	 */
	FieldModel getFieldModel();

	/**
	 * Lista de acciones incluidas en la edición.
	 * @return
	 */
	IActionEditor[] getActions();	
}
