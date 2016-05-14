package com.javalego.ui.mvp.editor;

import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.mvp.editor.services.IEditorServices;

/**
 * Interface que define los métodos básicos de un editor de datos que serán
 * implementados por el Presenter para que estén disponibles para la vista (MVP
 * patter).
 * 
 * @author ROBERTO RANZ
 */
public interface IEditorViewListener extends ICrudListener {
	
	/**
	 * Modelo de la lista de campos a editar.
	 * @return
	 */
	List<IItemEditor> getData();
	
	/**
	 * Notificar el cambio del valor de una propiedad.
	 * 
	 * @param fieldName
	 */
	void fireValueChange(String fieldName);

	/**
	 * Distribuidor de campos de edición.
	 * 
	 * @return
	 */
	ILayoutEditorModel getLayoutEditorModel();

	/**
	 * Distribuidor de campos de edición.
	 * 
	 * @return
	 */
	ICustomLayoutEditor<?> getCustomLayoutEditor();

	/**
	 * Establecer el valor de un campo de tipo bean o campo anidado.
	 * 
	 * @param elementEditor
	 * @param value
	 */
	void setBeanValues(IItemEditor elementEditor, Object value) throws LocalizedException;

	/**
	 * Realizar la validación del valor de una propiedad editada.
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	List<LocalizedException> validate(String propertyName, Object value);

	/**
	 * Obtener el valor actual de un campo del editor.
	 * 
	 * @param fieldName
	 * @return
	 */
	Object getCurrentValue(String fieldName);

	/**
	 * Obtener el valor antes de la edición y actual valor en el bean.
	 * 
	 * @param fieldName
	 * @return
	 */
	Object getOldValue(String fieldName);

	/**
	 * Establecer el valor de un campo
	 * 
	 * @param fieldName
	 * @param value
	 * @throws LocalizedException
	 */
	void setValue(String fieldName, Object value) throws LocalizedException;

	/**
	 * Comprueba si tiene listeners de cambio de valor de cualquier campo.
	 * 
	 * @return
	 */
	boolean hasValueChangeListeners();

	/**
	 * Lista de listeners definidos para notificar el cambio de valor de un
	 * campo en edición.
	 * 
	 * @return
	 */
	List<ValueChangeListener> getValueChangeListeners();

	/**
	 * Servicios del editor de beans.
	 * 
	 * Actualmente, se utiliza para servicios de obtención y validación de beans
	 * nested como campos de entidad incluidos en un objeto de tipo entidad
	 * (JPA).
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	IEditorServices getServices();

	/**
	 * Establecer el valor en el bean del editor. Si existen varios beans, se
	 * intentará asignar el primero. TODO Falta crear un método que filtre el
	 * bean al que queremos realizar la asignación.
	 * 
	 * @param fieldName
	 * @param value
	 * @throws LocalizedException
	 */
	void setBeanValue(String fieldName, Object value) throws LocalizedException;

	/**
	 * Lista de modelos de editor de detalle del bean (Ej.: empresas y su
	 * personal).
	 * 
	 * @return
	 */
	Collection<IBeansEditorModel<?>> getDetail();

	/**
	 * Cargar editor detalle
	 * 
	 * @param detail
	 */
	void loadDetailBeanEditor(IBeansEditorModel<?> detail);

	/**
	 * Comprobar si un campo es visible para su edición. (Ej.: master/detail
	 * campos del objeto master o nested bean).
	 * 
	 * @return
	 */
	boolean isFieldVisible(String fieldname);
}
