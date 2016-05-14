package com.javalego.ui.mvp.editor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.javalego.exception.JavaLegoException;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.bean.IDataBeanFieldModel;
import com.javalego.ui.mvp.editor.IBaseEditorModel;
import com.javalego.ui.mvp.editor.IBaseEditorView;
import com.javalego.ui.mvp.editor.IEditorPresenter;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.mvp.editor.beans.IBeansEditorModel;
import com.javalego.ui.mvp.editor.layout.ILayoutEditorModel;
import com.javalego.ui.mvp.editor.layout.custom.ICustomLayoutEditor;
import com.javalego.ui.mvp.editor.rules.IEditionRules;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener;
import com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeNotifier;
import com.javalego.ui.mvp.editor.rules.impl.EditionRulesListener;
import com.javalego.ui.mvp.editor.services.IEditorServices;
import com.javalego.ui.mvp.editor.services.NestedFieldsServices;
import com.javalego.util.ReflectionUtils;

/**
 * Presenter Editor de Datos del modelo
 * 
 * @author ROBERTO RANZ
 */
public abstract class BaseEditorPresenter implements IEditorViewListener, IEditionRules, IEditorPresenter, ValueChangeNotifier {

	private static final long serialVersionUID = 630408650867313115L;

	private static final Logger logger = Logger.getLogger(BaseEditorPresenter.class);

	/**
	 * Listener asociado a la vista para obtener datos del modelo o servicios
	 * del editor.
	 */
	protected EditorListener editorListener;

	/**
	 * List of listeners who are interested in the value changes of the Property
	 */
	protected List<ValueChangeListener> valueChangeListeners;

	/**
	 * Layout de edición de campos (agrupación de campos recursiva). Opcional.
	 */
	protected ILayoutEditorModel layoutEditorModel;

	/**
	 * Sólo lectura
	 */
	protected boolean readOnly;

	/**
	 * Incluir opción de eliminación
	 */
	protected boolean remove;

	/**
	 * Layout personalizable
	 */
	protected ICustomLayoutEditor<?> customLayout;

	/**
	 * Servicios del editor de beans.
	 * 
	 * Actualmente, se utiliza para servicios de obtención y validación de beans
	 * nested como campos de entidad incluidos en un objeto de tipo entidad
	 * (JPA).
	 * 
	 * @return
	 */
	protected IEditorServices<?> services;

	/**
	 * Objeto master o nested bean del bean editado. Ej.:
	 * EmpresaContacto.empresa donde Empresa es el masterBean. Se usa
	 * actualmente, para no editar estos campos si es != null ya que
	 * corresponderá a una relación master/detail de editores.
	 */
	protected Object masterBean;

	@Override
	public void load() throws LocalizedException {
		getView().load();
	}

	/**
	 * Modelo
	 * 
	 * @return
	 */
	public abstract IBaseEditorModel getModel();

	@Override
	public abstract IBaseEditorView getView();

	// EDITION RULES

	// EVENTS

	@Override
	public void addValueChangeListener(ValueChangeListener listener) {

		if (listener != null) {
			if (valueChangeListeners == null) {
				valueChangeListeners = new ArrayList<ValueChangeListener>();
			}
			valueChangeListeners.add(listener);
		}
	}

	@Override
	public void removeValueChangeListener(ValueChangeListener listener) {

		if (valueChangeListeners != null) {
			valueChangeListeners.remove(listener);
		}
	}

	/**
	 * Sends a value change event to all registered listeners.
	 */
	@Override
	public void fireValueChange(String fieldName) {

		// Ejecutar el listener del presenter
		if (valueChangeListeners != null) {
			final ValueChangeEvent event = new EditionRulesListener(this, fieldName);
			for (ValueChangeListener l : valueChangeListeners) {
				l.valueChange(event);
			}
		}
	}

	/**
	 * Comprueba si tiene listeners de cambio de valor de cualquier campo.
	 * 
	 * @return
	 */
	@Override
	public boolean hasValueChangeListeners() {
		return valueChangeListeners != null;
	}

	// PROPERTIES

	@Override
	public void setRequired(String fieldName, boolean required) {
		getView().setRequired(fieldName, required);
	}

	@Override
	public void setRequiredError(String fieldName, String requiredError) {
		getView().setRequiredError(fieldName, requiredError);
	}

	@Override
	public void setDescription(String fieldName, String description) {
		getView().setDescription(fieldName, description);
	}

	@Override
	public void setCaption(String fieldName, String caption) {
		getView().setCaption(fieldName, caption);
	}

	@Override
	public void setValue(String fieldName, Object value) {
		getView().setValue(fieldName, value);
	}

	@Override
	public void setEnumValues(String fieldName, List<?> values) {
		getView().setEnumValues(fieldName, values);
	}

	@Override
	public void setEnabled(String fieldName, boolean enabled) {
		getView().setEnabled(fieldName, enabled);
	}

	@Override
	public void setVisible(String fieldNamed, boolean visible) {
		getView().setVisible(fieldNamed, visible);
	}

	@Override
	public void setReadOnly(String fieldName, boolean readOnly) {
		getView().setReadOnly(fieldName, readOnly);
	}

	@Override
	public Object getValue(String fieldName) {
		return getView().getValue(fieldName);
	}

	public Object getValue(Key field) {
		return getView().getValue(field.name().toLowerCase());
	}

	@Override
	public boolean isRequired(String fieldName) {
		return getView().isRequired(fieldName);
	}

	@Override
	public boolean isVisible(String fieldName) {
		return getView().isVisible(fieldName);
	}

	@Override
	public boolean isEnabled(String fieldName) {
		return getView().isEnabled(fieldName);
	}

	@Override
	public boolean isReadOnly(String fieldName) {
		return getView().isReadOnly(fieldName);
	}

	@Override
	public String getCaption(String fieldName) {
		return getView().getCaption(fieldName);
	}

	@Override
	public String getRequiredError(String fieldName) {
		return getView().getRequiredError(fieldName);
	}

	@Override
	public String getDescription(String fieldName) {
		return getView().getDescription(fieldName);
	}

	/**
	 * Valor del modelo de datos antes de la edición.
	 * 
	 * @param fieldName
	 * @return
	 */
	@Override
	public Object getOldValue(String fieldName) {
		try {
			return getModel().getValue(fieldName);
		}
		catch (LocalizedException e) {
			logger.error("ERROR GET OLD VALUE '" + fieldName + "'.", e);
			return null;
		}
	}

	@Override
	public void setBeanValue(String fieldName, Object value) throws LocalizedException {

		Object bean = getModel().getBean();
		if (bean != null) {
			try {
				ReflectionUtils.setPropertyValue(bean, fieldName, value);
			}
			catch (JavaLegoException e) {
				new LocalizedException(e);
			}
		}
		else {
			logger.error("NOT EXIST BEAN IN EDITOR. NO ASSIGNED VALUE TO PROPERTY '" + fieldName + "' AND VALUE " + (value != null ? value.toString() : ""));
		}
	}

	@Override
	public ICustomLayoutEditor<?> getCustomLayoutEditor() {
		return customLayout;
	}

	/**
	 * Validar datos del editor. Podemos usar este método, previo al commit, si
	 * necesitamos realizar alguna tarea previa. Después puede realizar el
	 * commit con el parámetro validate = false ya que se ha realizado
	 * anteriormente.
	 */
	@Override
	public boolean validate() {

		// Validación de los datos de la vista
		List<LocalizedException> exceptions = new ArrayList<LocalizedException>();
		try {
			List<LocalizedException> list = getView().commit();
			if (list != null) {
				exceptions.addAll(list);
			}
		}
		catch (LocalizedException e) {
			exceptions.add(e);
		}

		// Mostrar errores en la vista
		if (exceptions != null && exceptions.size() > 0) {
			getView().showErrors(exceptions);
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean commit() throws LocalizedException {

		// Validación de los datos de la vista
		List<LocalizedException> exceptions = new ArrayList<LocalizedException>();
		try {
			List<LocalizedException> list = getView().commit();
			if (list != null) {
				exceptions.addAll(list);
			}
		}
		catch (LocalizedException e) {
			exceptions.add(e);
		}

		// Ejecutar listener si no ha habido errores en la validación de los
		// campos editados.
		if (exceptions == null || exceptions.size() == 0 && editorListener != null) {
			try {
				editorListener.commit();
			}
			catch (LocalizedException e) {
				exceptions.add(e);
			}
		}

		// Mostrar errores en la vista
		if (exceptions != null && exceptions.size() > 0) {
			getView().showErrors(exceptions);
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean discard() throws LocalizedException {

		// Validación de los datos de la vista
		List<LocalizedException> exceptions = new ArrayList<LocalizedException>();

		getView().discard();

		if (editorListener != null) {
			try {
				editorListener.discard();
			}
			catch (LocalizedException e) {
				exceptions.add(e);
			}
		}

		// Mostrar errores en la vista
		if (exceptions != null && exceptions.size() > 0) {
			getView().showErrors(exceptions);
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Acciones de edición
	 * 
	 * @author ROBERTO RANZ
	 */
	public interface EditorListener {

		/**
		 * Realizar el commit de los datos editados
		 */
		void commit() throws LocalizedException;

		/**
		 * Descartar cambios
		 * 
		 * @throws LocalizedException
		 */
		void discard() throws LocalizedException;

		/**
		 * Eliminar
		 * 
		 * @throws LocalizedException
		 */
		void remove() throws LocalizedException;

		/**
		 * Cargar el editor detalle del bean editado.
		 * 
		 * @param detail
		 */
		void loadDetailBeanEditor(IBeansEditorModel<?> detail);
	}

	public EditorListener getEditorListener() {
		return editorListener;
	}

	public void setEditorListener(EditorListener editorListener) {
		this.editorListener = editorListener;
	}

	@Override
	public boolean remove() throws LocalizedException {
		if (editorListener != null) {
			editorListener.remove();
		}
		return true;
	}

	/**
	 * Obtener el valor de los datos editados
	 * 
	 * @return
	 */
	public Collection<?> getValues() {
		return getView().getValues();
	}

	/**
	 * Obtener la lista de campos y valores en edición.
	 * 
	 * @return
	 */
	public Map<String, ?> getFieldValues() {
		return getView().getFieldValues();
	}

	@Override
	public boolean hasRemove() {
		return remove;
	}

	@Override
	public Object getCurrentValue(String fieldName) {
		return getView().getValue(fieldName);
	}

	/**
	 * Descartar los cambios realizados en el campo.
	 * 
	 * @param fieldName
	 */
	@Override
	public void discard(String fieldName) {
		getView().discard(fieldName);
	}

	@Override
	public void setBeanValues(IItemEditor elementEditor, Object bean) throws LocalizedException {
		try {
			if (/* bean != null && */elementEditor instanceof IDataBeanFieldModel) {

				IDataBeanFieldModel m = (IDataBeanFieldModel) elementEditor;

				if (m.getName().indexOf(".") > 0) {

					String fieldNameBean = m.getName().substring(0, m.getName().indexOf("."));

					// TODO FALTA ESTABLECER EL ID SI ES DE TIPO ENTIDAD.

					// Establecer el valor completo del bean buscando el nombre
					// root (Ej.: country.name buscar country que es la
					// propiedad del bean Ej.: Person.country.
					ReflectionUtils.setPropertyValue(m.getDataBean().getBean(), fieldNameBean, bean);

					// Refresh de los componentes UI que pertenecen a este bean
					// actualizado en su datasource de origen.
					for (IDataBeanFieldModel field : m.getDataBean().getFields()) {

						// Comienza por ej.: country como country.name
						if (field.getName().indexOf(fieldNameBean + ".") == 0) {

							// Descartamos los cambios en edición para obtener
							// los datos origen que han sido actualizados.
							getView().discard(field.getName());
							// String fieldName =
							// field.getName().substring(fieldNameBean.length()
							// + 1);
							// Object value =
							// ReflectionUtils.getPropertyValue(bean,
							// field.getName().substring(fieldNameBean.length()
							// + 1));
							//
							// logger.info("FIELD '" + fieldName + "' UPDATED "
							// + value);
							//
							// view.setValue(field.getName(), value);
						}
					}
				}
			}

		}
		catch (Exception e) {
			throw new LocalizedException(e);
		}
	}

	@Override
	public List<ValueChangeListener> getValueChangeListeners() {
		return valueChangeListeners;
	}

	@Override
	public List<LocalizedException> validate(String propertyName, Object value) {
		return getModel().validate(propertyName, value);
	}

	@Override
	public IEditorServices<?> getServices() {
		return services;
	}

	@Override
	public ILayoutEditorModel getLayoutEditorModel() {
		return layoutEditorModel;
	}

	@Override
	public Collection<IBeansEditorModel<?>> getDetail() {
		return getModel().getDetail();
	}

	@Override
	public void loadDetailBeanEditor(IBeansEditorModel<?> detail) {
		editorListener.loadDetailBeanEditor(detail);
	}

	@Override
	public boolean isFieldVisible(String fieldname) {

		return masterBean != null ? NestedFieldsServices.isFieldVisible(fieldname, masterBean.getClass(), getModel().getBean().getClass()) : true;
	}

	/**
	 * Establecer el objeto masterBean (relación master/detail de este editor).
	 * 
	 * @param masterBean
	 */
	public void setMasterBean(Object masterBean) {
		this.masterBean = masterBean;
	}

	/**
	 * Objeto masterBean (relación master/detail de este editor).
	 * 
	 * @return
	 */
	public Object getMasterBean() {
		return masterBean;
	}

	public ICustomLayoutEditor<?> getCustomLayout() {
		return customLayout;
	}

	public void setCustomLayout(ICustomLayoutEditor<?> customLayout) {
		this.customLayout = customLayout;
	}

	/**
	 * Obtiene el bean editado. Si el editor no incluye un IDataBean en su
	 * modelo, se obtendrá un valor null.
	 * 
	 * @return
	 */
	public Object getBean() {
		return getModel().getBean();
	}

	/**
	 * Establecer un layout para distribuir la edición de los campos existentes
	 * en el editor.
	 * 
	 * @param layoutEditorModel
	 */
	public void setLayoutEditorModel(ILayoutEditorModel layoutEditorModel) {
		this.layoutEditorModel = layoutEditorModel;
	}

}
