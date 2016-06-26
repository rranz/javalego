package com.javalego.ui.vaadin.view.editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.javalego.errors.CommonErrors;
import com.javalego.exception.LocalizedException;
import com.javalego.ui.UIContext;
import com.javalego.ui.actions.IActionEditor;
import com.javalego.ui.actions.ISelectActionEditor;
import com.javalego.ui.editor.data.IDataFieldModel;
import com.javalego.ui.editor.data.IItemEditor;
import com.javalego.ui.editor.data.IValueDataEditor;
import com.javalego.ui.editor.data.bean.IDataBean;
import com.javalego.ui.editor.data.bean.IDataBeanFieldModel;
import com.javalego.ui.editor.data.impl.PrimitiveDataFieldModel;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.mvp.editor.IEditorViewListener;
import com.javalego.ui.mvp.editor.actions.EditorActionsPresenter;
import com.javalego.ui.mvp.editor.actions.IEditorActionsModel;
import com.javalego.ui.mvp.editor.rules.IEditionRules;
import com.javalego.ui.mvp.editor.services.NestedFieldsServices;
import com.javalego.ui.vaadin.component.AbstractFieldActions;
import com.javalego.ui.vaadin.component.image.ImageField;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.factory.FieldsFactoryVaadin;
import com.javalego.ui.vaadin.view.actions.EditorActionsView;
import com.javalego.ui.vaadin.view.editor.validators.SelectDataEditorValidator;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

/**
 * Administrador de los datos de un editor.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class DataEditorManager implements Serializable, IEditionRules {

	private static final long serialVersionUID = 3653861514833582708L;

	private static final Logger logger = Logger.getLogger(DataEditorManager.class);

	/**
	 * Aplicar o no el cambio del componente a la propiedad del bean.
	 */
	private static final boolean BUFFERED = true;

	/**
	 * DataSet con la lista de bindings de Vaadin utilizados para generar los
	 * componentes visuales y binding.
	 */
	private List<FieldGroup> fieldGroups = new ArrayList<FieldGroup>();

	/**
	 * Grupo de campos (Vaadin)
	 */
	private FieldGroup fieldGroup;

	/**
	 * Set de propiedades para bindar los campos del bean (Vaadin).
	 */
	private PropertysetItem item = new PropertysetItem();

	/**
	 * Lista de los datos a editar y el campo vaadin generado.
	 */
	private HashMap<Field<?>, IItemEditor> fields = new HashMap<Field<?>, IItemEditor>();

	/**
	 * Listener del editor de bean
	 */
	public IEditorViewListener editorListener;

	/**
	 * Observer de los pasos de creación del editor.
	 */
	private IEditorViewObserver observer;

	/**
	 * Constructor
	 * 
	 * @param editorListener
	 */
	public DataEditorManager(IEditorViewListener editorListener) {
		this.editorListener = editorListener;
	}

	/**
	 * Constructor
	 * 
	 * @param editorListener
	 * @param observer
	 */
	public DataEditorManager(IEditorViewListener editorListener, IEditorViewObserver observer) {
		this.editorListener = editorListener;
		this.observer = observer;
	}

	/**
	 * Generar los componentes UI a partir de la definición de los datos a
	 * editar.
	 * 
	 * @param layout
	 * @param data
	 * @param listener
	 * @return Lista de campos creados.
	 * @throws LocalizedException
	 */
	public final List<Component> getComponents(List<IItemEditor> data) throws LocalizedException {

		List<Component> components = new ArrayList<Component>();

		for (IItemEditor de : data) {

			// Tipologías
			if (de instanceof IDataBean) {

				IDataBean<?> bean = (IDataBean<?>) de;

				final BeanFieldGroup<Object> fieldGroup = getFieldGroup(bean.getBean());

				for (IDataFieldModel dataField : bean.getFields()) {

					// No editar campos no visibles (Ej.: master/detail campos
					// del objeto master o nested bean).
					if (editorListener != null && !editorListener.isFieldVisible(dataField.getFieldModel().getName())) {
						continue;
					}

					// Crear campo, configurarlo en base al modelo e incluir su
					// componente en la lista actual.
					addComponent(components, de, addField(dataField, bean, fieldGroup), dataField.getActions(), dataField.getFieldModel());
				}
			}
			// Campo de un bean
			else if (de instanceof IDataBeanFieldModel) {

				IDataBeanFieldModel fm = (IDataBeanFieldModel) de;

				// No editar campos no visibles (Ej.: master/detail campos
				// del objeto master o nested bean).
				if (editorListener != null && !editorListener.isFieldVisible(fm.getFieldModel().getName())) {
					continue;
				}

				// Crear campo, configurarlo en base al modelo e incluir su
				// componente en la lista actual.
				addComponent(components, de, addField(fm, fm.getDataBean(), getFieldGroup(fm.getDataBean().getBean())), fm.getActions(), fm.getFieldModel());

			}
			// Valor simple
			else if (de instanceof PrimitiveDataFieldModel) {

				PrimitiveDataFieldModel fm = (PrimitiveDataFieldModel) de;

				addField(components, de, fm.getFieldModel().getName(), new ObjectProperty<Object>(fm.getValue()));
			}
			// Valor sin tipo
			else if (de instanceof IValueDataEditor) {

				addField(components, de, de.getName(), new ObjectProperty<Object>(((IValueDataEditor) de).getValue()));
			}
			// Establecemos un valor por defecto de tipo String si es un dato
			// básico IDataEditor que no contiene valor.
			else {
				addField(components, de, de.getName(), new ObjectProperty<Object>(new String()));
			}
		}

		return components;
	}

	/**
	 * Obtener y configurar el campo vaadin para la edición
	 * 
	 * @param dataField
	 * @param bean
	 * @param fieldGroup
	 * @return
	 * @throws LocalizedException
	 */
	private Field<?> addField(IDataFieldModel dataField, IDataBean<?> bean, final FieldGroup fieldGroup) throws LocalizedException {

		Field<?> field = FieldsFactoryVaadin.getCurrent().getUI(fieldGroup, dataField.getFieldModel(), bean != null ? bean.getBean() : null, editorListener.isReadOnly(),
				isNested(dataField.getFieldModel()));

		// Establecer el identificador único en base al nombre del modelo. Nos
		// permitirá asociar el Field con el modelo.
		field.setId(dataField.getName());

		fields.put(field, dataField);

		// Establecer el modelo de datos en ciertas tipologías de
		// campos específicas.
		if (field instanceof ImageField) {
			((ImageField) field).setFieldModel((ImageFieldModel) dataField.getFieldModel(), editorListener.isReadOnly());
		}

		return field;
	}

	/**
	 * Configurar el campo y añadirlo a la lista de componentes.
	 * 
	 * @param components
	 * @param dataEditor
	 * @param field
	 * @param actions
	 * @param fieldModel
	 * @throws LocalizedException
	 */
	private void addComponent(List<Component> components, IItemEditor dataEditor, Field<?> field, IActionEditor[] actions, FieldModel fieldModel) throws LocalizedException {

		components.add(configField(dataEditor, field, actions, fieldModel));
	}

	/**
	 * Comprueba si el campo es anidado o referencia de otra entidad.
	 * 
	 * @param model
	 * @return
	 */
	private boolean isNested(FieldModel model) {

		return NestedFieldsServices.isNestedField(editorListener.getServices(), model);
	}

	/**
	 * Añadir al Item la propiedad y devolver el componente del campo.
	 * 
	 * @param listener
	 * @param dataEditor
	 * @param name
	 * @param value
	 * @throws LocalizedException
	 */
	private void addField(List<Component> components, IItemEditor dataEditor, String name, Property<?> value) throws LocalizedException {

		if (fieldGroup == null) {
			fieldGroup = new FieldGroup(item);
			fieldGroup.setBuffered(BUFFERED);
			// Añadir al dataset para controlar los componentes de bindado
			// utilizados en el editor y poder recuperar estos datos.
			fieldGroups.add(fieldGroup);
		}

		item.addItemProperty(name, value);

		Field<?> field = null;

		if (dataEditor instanceof IDataFieldModel) {

			IDataFieldModel dm = (IDataFieldModel) dataEditor;

			// Crear campo, configurarlo en base al modelo e incluir su
			// componente en la lista actual.
			addComponent(components, dataEditor, addField(dm, null, fieldGroup), dm.getActions(), dm.getFieldModel());
		}
		else {
			field = fieldGroup.buildAndBind(name);
			
			if (field != null) {
				components.add(field);
			}
		}

		fields.put(field, dataEditor);
	}

	/**
	 * Añadir evento de cambio de valor y configurar la representación de nulos.
	 * 
	 * @param de
	 * 
	 * @param listener
	 * @param fieldGroup
	 * @param field
	 * @param fieldModel
	 * @param iOptions
	 * @throws LocalizedException
	 */
	private Component configField(final IItemEditor dataEditor, final Field<?> field, final IActionEditor[] actions, final FieldModel fieldModel) throws LocalizedException {

		// Ejecutar método de validación al modificar el contenido del campo
		if (actions != null) {

			for (final IActionEditor action : actions) {

				// Añadir validador al campo
				if (action instanceof ISelectActionEditor && dataEditor instanceof IValueDataEditor) {

					field.addValidator(new SelectDataEditorValidator((ISelectActionEditor) action, editorListener, (IValueDataEditor) dataEditor));

					// Añadir evento de cambio de valor del campo.
					field.addValueChangeListener(new ValueChangeListener() {
						private static final long serialVersionUID = 2031619573792347287L;

						@Override
						public void valueChange(ValueChangeEvent event) {
							try {
								((ISelectActionEditor) action).valueChangeEvent(editorListener, (IValueDataEditor) dataEditor, editorListener.getCurrentValue(dataEditor.getName()));
							}
							catch (LocalizedException e) {
								MessagesUtil.error(e);
							}
						}
					});
				}
			}
		}

		// Ejecutar los eventos definidos cuando cambia el valor del campo.
		if (editorListener.hasValueChangeListeners()) {

			field.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 6722517050578322275L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					// Añadir listener para observar el cambio de cualquier
					// campo.
					editorListener.fireValueChange(fieldModel.getName());
				}
			});
		}

		// Aplicar modelo al componente
		FieldsFactoryVaadin.getCurrent().applyFieldModel(field, null, false);

		// Incluir las claves de los valores posibles para los campos nested de
		// otras entidades.
		boolean nested = fieldModel != null ? isNested(fieldModel) : false;

		// Configurar componente para campos Nested
		if (nested && field instanceof ComboBox) {

			final ComboBox combo = (ComboBox) field;
			combo.setImmediate(true);

			// Añadir lista de claves posibles.
			Collection<?> values = NestedFieldsServices.getNestedKeys(editorListener.getServices(), fieldModel.getName());
			if (values != null) {
				for (Object value : values) {
					combo.addItem(value);
				}
			}

			// Listener de cambio de clave aplicado al componente visual.
			final com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeListener nlistener = NestedFieldsServices.getNestedServices(editorListener.getServices(), fieldModel.getName())
					.getValueChangeListener();

			if (nlistener != null) {

				combo.addValueChangeListener(new Property.ValueChangeListener() {
					private static final long serialVersionUID = 7990106912573914176L;

					@Override
					public void valueChange(ValueChangeEvent event) {

						// Instanciar ValueChangeEvent para enviar los datos del
						// campo nested actualmente en edición y poder actuar en
						// base a esta información (reglas de edición).
						nlistener.valueChange(new com.javalego.ui.mvp.editor.rules.IEditionRulesListener.ValueChangeEvent() {
							private static final long serialVersionUID = 3063355422996860582L;

							@Override
							public Object getValue() {
								return combo.getValue();
							}

							@Override
							public String getFieldName() {
								return fieldModel.getName();
							}

							@Override
							public Object getOldValue() {
								return editorListener.getOldValue(getFieldName());
							}

							@Override
							public IEditionRules getEditorRules() {
								return DataEditorManager.this;
							}

							@Override
							public void discard() {
							}
						});
					}
				});

			}
		}

		// Crear field composite como phonefield, emailfield, ...
		AbstractFieldActions group = null;

		// Incluir la lista de acciones definidas en el modelo para el campo a
		// editar.
		if (actions != null) {

			EditorActionsPresenter p = new EditorActionsPresenter(new IEditorActionsModel() {
				@Override
				public Collection<IActionEditor> getActions() {
					return Arrays.asList(actions);
				}
			}, new EditorActionsView(editorListener, dataEditor));
			p.load();

			if (group == null) {
				group = new AbstractFieldActions((AbstractField<?>) field);
				group.setCaption(field.getCaption());
				field.setCaption(null);
				field.addStyleName(CssVaadin.getRequired());
			}
			group.addComponent((Component) p.getView());
		}

		// Notificar la creación del campo
		if (field != null && observer != null) {
			observer.createdField(dataEditor.getName(), field);
		}

		return group != null ? group : field;
	}

	/**
	 * Obtener un FieldGroup
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private BeanFieldGroup<Object> getFieldGroup(Object bean) {

		// Comprobar que no exista ya un fieldgroup asociado a este bean.
		for (FieldGroup fg : fieldGroups) {
			if (fg instanceof BeanFieldGroup && ((BeanFieldGroup) fg).getItemDataSource().getBean() == bean) {
				return (BeanFieldGroup) fg;
			}
		}

		BeanFieldGroup fieldGroup = new BeanFieldGroup(bean.getClass());

		// fieldGroup.setFieldFactory(new FieldGroupFieldFactory() {
		// @SuppressWarnings({ "rawtypes" })
		// @Override
		// public <T extends Field> T createField(Class<?> dataType, Class<T>
		// fieldType) {
		// // if (Date.class.isAssignableFrom(dataType)){
		// // return (T) new DateField();
		// // }
		// return fieldFactory.createField(dataType, fieldType);
		// }
		// });

		fieldGroup.setBuffered(BUFFERED);

		fieldGroup.setItemDataSource(bean);

		fieldGroups.add(fieldGroup);

		return fieldGroup;
	}

	/**
	 * Establecer readOnly una propiedad.
	 * 
	 * @param fieldName
	 */
	@Override
	public void setReadOnly(String fieldName, boolean readOnly) {

		Field<?> field = getField(fieldName);

		if (field != null)
			field.setReadOnly(readOnly);
	}

	/**
	 * Establecer enabled una propiedad.
	 * 
	 * @param fieldName
	 */
	@Override
	public void setEnabled(String fieldName, boolean enabled) {

		Field<?> field = getField(fieldName);

		if (field != null)
			field.setEnabled(enabled);
	}

	/**
	 * Establecer visible una propiedad.
	 * 
	 * @param fieldName
	 */
	@Override
	public void setVisible(String fieldName, boolean visible) {

		Field<?> field = getField(fieldName);

		if (field != null)
			field.setVisible(visible);
	}

	/**
	 * Establecer en valor de una propiedad.
	 * 
	 * @param fieldName
	 * @param value
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void setValue(String fieldName, Object value) {

		Field<?> field = getField(fieldName);

		if (field != null && field instanceof AbstractField) {
			if (field.isReadOnly()) {
				try {
					field.setReadOnly(false);
					((AbstractField) field).setConvertedValue(value);
				}
				finally {
					field.setReadOnly(true);
				}
			}
			else {
				((AbstractField) field).setConvertedValue(value);
			}
		}
	}

	/**
	 * Establecer caption de una propiedad.
	 * 
	 * @param fieldName
	 * @param value
	 */
	@Override
	public void setCaption(String fieldName, String caption) {

		Field<?> field = getField(fieldName);

		if (field != null) {
			field.setCaption(caption);
		}
	}

	/**
	 * Establecer description en la propiedad.
	 * 
	 * @param fieldName
	 * @param value
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void setDescription(String fieldName, String description) {

		Field<?> field = getField(fieldName);

		if (field != null && field instanceof AbstractField) {
			((AbstractField) field).setDescription(description);
		}
	}

	/**
	 * Establecer el error de requerido en la propiedad.
	 * 
	 * @param fieldName
	 * @param value
	 */
	@Override
	public void setRequiredError(String fieldName, String requiredError) {

		Field<?> field = getField(fieldName);

		if (field != null) {
			field.setRequiredError(requiredError);
		}
	}

	/**
	 * Establecer required en la propiedad.
	 * 
	 * @param fieldName
	 * @param value
	 */
	@Override
	public void setRequired(String fieldName, boolean required) {

		Field<?> field = getField(fieldName);

		if (field != null) {
			field.setRequired(required);
		}
	}

	/**
	 * Buscar la propiedad en la lista de fieldGroups.
	 * 
	 * @param fieldName
	 * @return
	 */
	public Field<?> getField(String fieldName) {

		// Comprobar que no exista ya un fieldgroup asociado a este bean.
		for (FieldGroup fg : fieldGroups) {
			Field<?> field = fg.getField(fieldName);
			if (field != null) {
				return field;
			}
		}
		logger.error("FIELD '" + fieldName + "' NOT EXIST.");
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getValue(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null) {
			return field instanceof AbstractField ? ((AbstractField) field).getConvertedValue() : field.getValue();
		}
		else
			return null;
	}

	@Override
	public boolean isRequired(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null)
			return field.isRequired();
		else
			return false;
	}

	@Override
	public boolean isVisible(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null)
			return field.isVisible();
		else
			return true;
	}

	@Override
	public boolean isEnabled(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null)
			return field.isEnabled();
		else
			return true;
	}

	@Override
	public boolean isReadOnly(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null)
			return field.isReadOnly();
		else
			return false;
	}

	@Override
	public String getCaption(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null)
			return field.getCaption();
		else
			return null;
	}

	@Override
	public String getRequiredError(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null)
			return field.getRequiredError();
		else
			return null;
	}

	@Override
	public String getDescription(String fieldName) {

		Field<?> field = getField(fieldName);
		if (field != null)
			return field.getDescription();
		else
			return null;
	}

	/**
	 * Aplicar los cambios realizados en los campos
	 * 
	 * @throws LocalizedException
	 */
	public List<LocalizedException> commit() throws LocalizedException {

		List<LocalizedException> list = validate();

		// Si existen errores de validación se cancela el commit
		if (list != null && list.size() > 0) {
			return list;
		}
		else {
			// Commit de todos los grupos de campos
			for (FieldGroup fg : fieldGroups) {
				try {
					fg.commit();
				}
				catch (CommitException e) {
					throw new LocalizedException(e);
				}
			}
			return null;
		}
	}

	/**
	 * Aplicar los cambios realizados en los campos
	 * 
	 * @throws LocalizedException
	 */
	public List<LocalizedException> validate() {

		List<LocalizedException> list = new ArrayList<LocalizedException>();

		for (FieldGroup fg : fieldGroups) {

			for (Field<?> field : fg.getFields()) {
				try {
					// Validación de campo sin valor
					if (field.isRequired() && field.getValue() == null) {
						list.add(UIContext.getException(CommonErrors.REQUIRED_FIELD, String.valueOf(field.getCaption() == null ? findDataEditor(field).getTitle() : field.getCaption())));
					}
					else {
						// Aplicar validación Vaadin (Validator Beans Java
						// especification) basada en anotaciones de beans.
						field.validate();
					}

					// Validación del modelo de datos
					// editorListener.validate(findDataEditor(field).getName(),
					// field.getValue());
				}
				catch (InvalidValueException e) {

					// Obtener el texto con los errores de validación
					// gestionados por Vaadin.
					String text = "";
					if (e.getLocalizedMessage() != null) {
						text = e.getLocalizedMessage();
					}
					else {
						for (InvalidValueException item : e.getCauses()) {
							text += item.getLocalizedMessage() + " ";
						}
					}

					list.add(UIContext.getException(new Exception(text.trim()), CommonErrors.INVALID_FIELD,
							String.valueOf(field.getCaption() == null ? findDataEditor(field).getTitle() : field.getCaption())));
				}

			}
		}
		return list.size() > 0 ? list : null;
	}

	/**
	 * Localizar el campo a editar a partir del Field generado en Vaadin para su
	 * edición.
	 * 
	 * @param field
	 * @return
	 */
	private IItemEditor findDataEditor(Field<?> field) {

		return fields.get(field);
	}

	/**
	 * Descartar los cambios realizados
	 * 
	 * @throws LocalizedException
	 */
	public void discard() throws LocalizedException {

		for (FieldGroup fg : fieldGroups) {
			fg.discard();
		}
	}

	/**
	 * Obtener la lista de valores en edición.
	 * 
	 * @return
	 */
	public Collection<?> getValues() {

		List<Object> values = new ArrayList<Object>();

		for (FieldGroup fg : fieldGroups) {
			for (Field<?> field : fg.getFields()) {
				values.add(field.getValue());
			}
		}
		return values.size() > 0 ? values : null;
	}

	/**
	 * Obtener la lista de valores de cada campo en edición.
	 * 
	 * @return
	 */
	public Map<String, ?> getFieldValues() {

		HashMap<String, Object> values = new HashMap<String, Object>();

		for (FieldGroup fg : fieldGroups) {
			for (Field<?> field : fg.getFields()) {
				values.put(field.getId(), field.getValue());
			}
		}
		return values.size() > 0 ? values : null;
	}

	/**
	 * Descartar cambios en un campo.
	 * 
	 * @param fieldName
	 */
	public void discard(String fieldName) {

		Field<?> field = getField(fieldName);

		if (field != null) {
			field.discard();
		}
	}

	@Override
	public void setEnumValues(String fieldName, List<?> values) {

		Field<?> field = getField(fieldName);

		if (field != null && field instanceof AbstractSelect) {

			((AbstractSelect) field).removeAllItems();

			if (values != null && values.size() > 0) {

				for (Object value : values) {
					((AbstractSelect) field).addItem(value);
				}
			}
		}
	}

}
