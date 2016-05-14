package com.javalego.ui.mvp.editor.filter.params;

import java.util.Collection;
import java.util.Map;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.filter.IFilterParam;
import com.javalego.ui.patterns.IView;

/**
 * Edición de los parámetros del filtro.
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IEditorFilterParamsView extends IView {

	interface EditorFilterParamsViewListener {
		
		/**
		 * Lista de parámetros a editar.
		 * @return
		 */
		Collection<IFilterParam> getParams();
		
		/**
		 * Ejecutar filtro con los valores en edición de la vista
		 * @param fieldValues Lista de nombre de campos y sus valores
		 * @throws LocalizedException 
		 */
		void execute(Map<String, ?> fieldValues) throws LocalizedException;

		String getStatement();

		String getNaturalStatement();

		Key getTitle();

		Key getDescription();

		String getName();
	}
	
	public void setListener(EditorFilterParamsViewListener listener);
}
