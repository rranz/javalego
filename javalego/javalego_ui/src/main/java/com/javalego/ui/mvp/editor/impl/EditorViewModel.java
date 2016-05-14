package com.javalego.ui.mvp.editor.impl;

import org.apache.log4j.Logger;

import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.IEditorModel;
import com.javalego.ui.mvp.editor.IEditorViewModel;

/**
 * Basado en la definición de datos de un EditorModel, podemos seleccionar solo
 * aquellos datos que queremos editar, descartando el resto. Es similar a una vista.
 * 
 * @author ROBERTO RANZ
 *
 */
public class EditorViewModel extends EditorModel implements IEditorViewModel {

	private static final long serialVersionUID = 3943402442739178861L;

	private static final Logger logger = Logger.getLogger(EditorViewModel.class);
	
	/**
	 * Modelo del editor que contiene la lista de campos a editar y de la que obtenemos una vista de ciertos campos.
	 */
	private IEditorModel editorModel;

	/**
	 * Constructor
	 * @param editorModel
	 * @param names
	 */
	public EditorViewModel(IEditorModel editorModel) {
		this.editorModel = editorModel;
	}
	
	/**
	 * Constructor
	 * @param editorModel
	 * @param names
	 */
	public EditorViewModel(IEditorModel editorModel, String... names) {
		this.editorModel = editorModel;
		addDataEditor(names);
	}
	
	@Override
	public void addDataEditor(String... names) {

		if (names != null) {
			for(String name : names) {
				IItemEditor data = editorModel.find(name);
				if (data != null) {
					this.data.add(data);
				}
				else {
					logger.warn("DATA EDITOR '" + name + "' NOT EXIST.");
				}
			}
		}
	}

	/**
	 * Modelo de la vista del editor.
	 * @return
	 */
	public IEditorModel getEditorModel() {
		return editorModel;
	}

}
