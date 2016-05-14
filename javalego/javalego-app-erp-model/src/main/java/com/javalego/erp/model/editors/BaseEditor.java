package com.javalego.erp.model.editors;

import com.javalego.data.DataContext;
import com.javalego.erp.data.ErpDataServices;
import com.javalego.ui.mvp.editor.beans.impl.AbstractBeansEditorModel;

/**
 * Editor básico para que todos los editores puedan reutilizar la interface del
 * modelo de campos y la implementación básica del editor de beans para
 * simplificar la codificación del editor y evitar usar la interface
 * IBeansEditorModel.
 * 
 * @author ROBERTO RANZ
 *
 * @param <T>
 */
public abstract class BaseEditor<T> extends AbstractBeansEditorModel<T> implements ModeloCampos {

	private static final long serialVersionUID = -1067500074607883591L;

	/**
	 * Servicios de datos ERP
	 * @return
	 */
	public ErpDataServices getDataServices() {
		return (ErpDataServices) DataContext.getCurrent().getBusinessService();
	}
}
