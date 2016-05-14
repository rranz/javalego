package com.javalego.ui.vaadin.view.editor;

import com.vaadin.ui.Field;

/**
 * Observer al que se notifican determinados acciones dentro de la construcción
 * del editor. Nos permitirá personalizar cambios en los componentes estándar
 * generados por el editor en base al modelo pero dentro de una tecnología
 * específica. En este caso Vaadin.
 * 
 * @author ROBERTO RANZ
 */
public interface IEditorViewObserver {

	/**
	 * Nuevo campo creado
	 * 
	 * @param fieldname
	 * @param field
	 * 
	 * @return
	 */
	void createdField(String fieldname, Field<?> field);
}
