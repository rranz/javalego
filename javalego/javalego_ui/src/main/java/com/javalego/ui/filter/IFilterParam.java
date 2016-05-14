package com.javalego.ui.filter;

import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.editor.data.IItemEditor;

/**
 * Parámetro del filtro basado en un campo del editor y un condición de búsqueda (like, igual, menor, ...)
 * 
 * @author ROBERTO RANZ
 *
 */
public interface IFilterParam {

	/**
	 * Campo del editor
	 * @return
	 */
	IItemEditor getDataEditor();
	
	/**
	 * Tipo de condición de búsqueda.
	 * @return
	 */
	ConditionType getCondition();
	
}
