package com.javalego.ui.vaadin.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.model.locales.LocaleWarnings;
import com.javalego.model.validator.Validator;
import com.javalego.ui.UIContext;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.impl.AbstractNumberFieldModel;
import com.javalego.ui.field.impl.BooleanFieldModel;
import com.javalego.ui.field.impl.CountryFieldModel;
import com.javalego.ui.field.impl.CurrencyFieldModel;
import com.javalego.ui.field.impl.DateFieldModel;
import com.javalego.ui.field.impl.DecimalFieldModel;
import com.javalego.ui.field.impl.EmailFieldModel;
import com.javalego.ui.field.impl.EnumFieldModel;
import com.javalego.ui.field.impl.IListValuesFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.IntegerFieldModel;
import com.javalego.ui.field.impl.ListFieldModel;
import com.javalego.ui.field.impl.PasswordFieldModel;
import com.javalego.ui.field.impl.PercentFieldModel;
import com.javalego.ui.field.impl.PhoneNumberFieldModel;
import com.javalego.ui.field.impl.SocialFieldModel;
import com.javalego.ui.field.impl.TextAreaFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.field.impl.UrlFieldModel;
import com.javalego.ui.field.impl.state.StateFieldModel;
import com.javalego.ui.mvp.editor.services.IEditorServices;
import com.javalego.ui.mvp.nestedbean.INestedBeanServices;
import com.javalego.ui.vaadin.component.CountryField;
import com.javalego.ui.vaadin.component.DateFieldExt;
import com.javalego.ui.vaadin.component.EmailField;
import com.javalego.ui.vaadin.component.PasswordFieldExt;
import com.javalego.ui.vaadin.component.PhoneField;
import com.javalego.ui.vaadin.component.SocialField;
import com.javalego.ui.vaadin.component.TextFieldExt;
import com.javalego.ui.vaadin.component.UrlField;
import com.javalego.ui.vaadin.component.image.ImageField;
import com.javalego.ui.vaadin.component.numbers.CurrencyField;
import com.javalego.ui.vaadin.component.numbers.DecimalField;
import com.javalego.ui.vaadin.component.numbers.IntegerField;
import com.javalego.ui.vaadin.component.numbers.PercentField;
import com.javalego.util.ReflectionUtils;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Generación componente UI en base al modelo de campo para Vaadin.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class FieldsFactoryVaadin {

	private static final Logger logger = Logger.getLogger(FieldsFactoryVaadin.class);

	/**
	 * Instancia actual
	 */
	private static FieldsFactoryVaadin current = null;

	/**
	 * Lista de tipologías de modelos y sus componentes vaadin que deben
	 * generarse. Esta lista de puede modificar añadiendo o sustituyendo
	 * componentes existentes por si cada aplicación necesita utilizar otros
	 * componentes especializados.
	 */
	private HashMap<String, Class<? extends Component>> list = new HashMap<String, Class<? extends Component>>();

	private FieldsFactoryVaadin() {
	}

	/**
	 * Obtener la instancia actual
	 * 
	 * @return
	 */
	public synchronized static FieldsFactoryVaadin getCurrent() {

		if (current == null) {
			current = new FieldsFactoryVaadin();

			// Inicializar componentes por defecto.
			current.list.put(TextAreaFieldModel.class.getCanonicalName(), TextArea.class);
			current.list.put(TextFieldModel.class.getCanonicalName(), TextFieldExt.class);
			current.list.put(DateFieldModel.class.getCanonicalName(), DateFieldExt.class);
			current.list.put(IntegerFieldModel.class.getCanonicalName(), IntegerField.class);
			current.list.put(CurrencyFieldModel.class.getCanonicalName(), CurrencyField.class);
			current.list.put(DecimalFieldModel.class.getCanonicalName(), DecimalField.class);
			current.list.put(PercentFieldModel.class.getCanonicalName(), PercentField.class);
			current.list.put(BooleanFieldModel.class.getCanonicalName(), CheckBox.class);
			current.list.put(PasswordFieldModel.class.getCanonicalName(), PasswordFieldExt.class);
			current.list.put(ImageFieldModel.class.getCanonicalName(), ImageField.class);
			current.list.put(UrlFieldModel.class.getCanonicalName(), UrlField.class);
			current.list.put(PhoneNumberFieldModel.class.getCanonicalName(), PhoneField.class);
			current.list.put(EmailFieldModel.class.getCanonicalName(), EmailField.class);
			current.list.put(CountryFieldModel.class.getCanonicalName(), CountryField.class);
			current.list.put(EnumFieldModel.class.getCanonicalName(), ComboBox.class);
			current.list.put(StateFieldModel.class.getCanonicalName(), ComboBox.class);
			current.list.put(ListFieldModel.class.getCanonicalName(), ComboBox.class);
			current.list.put(SocialFieldModel.class.getCanonicalName(), SocialField.class);
		}
		return current;
	}

	/**
	 * Cambiar el componente UI asociado al modelo definido por defecto .
	 * 
	 * @param fieldModel
	 * @param classComponent
	 */
	public void changeFieldComponent(Class<? extends FieldModel> fieldModel, Class<? extends Component> classComponent) {

		current.list.put(fieldModel.getCanonicalName(), classComponent);
	}

	/**
	 * Añadir un nuevo componente UI asociado al modelo definido por defecto .
	 * 
	 * @param fieldModel
	 * @param classComponent
	 */
	public void addFieldComponent(Class<?> fieldModel, Class<? extends Component> classComponent) {

		current.list.put(fieldModel.getCanonicalName(), classComponent);
	}

	/**
	 * Obtener el componente UI en base al modelo.
	 * 
	 * @param fieldModel
	 * @return
	 */
	public Field<?> getUI(FieldModel fieldModel) {

		Class<?> _class = list.get(fieldModel.getClass().getCanonicalName());

		if (_class != null) {
			try {
				return (Field<?>) (_class != null ? _class.newInstance() : null);
			}
			catch (InstantiationException e) {
				logger.error("ERROR NEW INSTANCE '" + _class.getCanonicalName(), e);
			}
			catch (IllegalAccessException e) {
				logger.error("ERROR NEW INSTANCE '" + _class.getCanonicalName(), e);
			}
		}

		// Componente por defecto.
		return new TextFieldExt();
	}

	/**
	 * Generar Field en base a un BeanFieldGroup de donde podemos generar el
	 * binding u obtener el componente por defecto.
	 * 
	 * @param beanFieldGroup
	 * @param fieldModel
	 * @param bean
	 * @return
	 * @throws LocalizedException
	 */
	public Field<?> getUI(FieldGroup beanFieldGroup, FieldModel fieldModel, Object bean, boolean readOnly, boolean nested) throws LocalizedException {

		if (fieldModel == null) {
			throw new LocalizedException("NOT FOUND FIELDMODEL");
		}
		else if (fieldModel.getName() == null) {
			throw new LocalizedException("NOT NAME ON FIELDMODEL");
		}

		// logger.debug("FIELD: " + fieldModel.getName() + " - " +
		// fieldModel.getTitle());

		// Generar la instancia de los objetos de entidades relacionadas para
		// evitar el error en Vaadin 7 cuando el valor de la propiedad no existe
		// e intentamos establecer su datasource. (en Vaadin 6 se ignaraba).

		// boolean nested = fieldModel.getName().indexOf(".") > 0;

		if (bean != null && fieldModel.getName().indexOf(".") > 0) {
			try {
				ReflectionUtils.generateNestedClassProperty(bean, fieldModel.getName());
			}
			catch (Exception e) {
				logger.error("ERROR CREATE NESTED CLASS PROPERTY '" + fieldModel.getName(), e);
			}
		}

		// Obtener componente UI de Vaadin asociado al campo a editar del bean.
		Field<?> field = null;

		if (nested) {
			field = new ComboBox();
		}
		else {
			field = getUI(fieldModel);
		}

		if (field == null) {
			// Delegar la generación y binding a Vaadin.
			field = beanFieldGroup.buildAndBind(UIContext.getTitle(fieldModel), fieldModel.getName());
		}
		else {
			// Bindar
			// NOTA: si la propiedad setter del campo no devuelve void field se
			// establece a readOnly. (TODO Vaadin)
			beanFieldGroup.bind(field, fieldModel.getName());
		}

		// REALIZA UN COMMIT DEL VALOR NULO AUNQUE SEA REQUIRED (SI NO PONEMOS
		// ESTE ATRIBUTO SI EL VALOR ES NULL NO APLICA EL VALOR AL OBJETO Y NO
		// GENERA LOS EVENTOS DE CAMBIO Y EL CAMPO TIENE UN VALOR QUE PUEDE
		// AFECTAR A LOS CÁLCULOS DE REGLAS DE NEGOCIO.
		field.setInvalidCommitted(true);

		// Aplicar la configuraicón del modelo
		applyFieldModel(field, fieldModel, readOnly);

		return field;
	}

	/**
	 * 
	 * @param field
	 * @param fieldModel
	 */
	public void applyFieldModel(Field<?> field, FieldModel fieldModel, boolean readOnly) {

		// Transformación por defecto.
		if (field instanceof AbstractTextField) {
			((AbstractTextField) field).setNullRepresentation("");
		}

		// Aplicar modelo
		if (fieldModel != null) {

			field.setReadOnly(fieldModel.isReadOnly() || readOnly);

			field.setCaption(UIContext.getTitle(fieldModel));

			if (fieldModel.isImmediate()) {
				// Aplicar cambios si buffered == false. Si immediate == false
				// no notificará los cambios.
				if (field instanceof AbstractField) {
					((AbstractField<?>) field).setImmediate(true);
				}
			}

			// Redes sociales
			if (fieldModel instanceof SocialFieldModel && field instanceof SocialField) {
				((SocialField) field).setSocial(((SocialFieldModel) fieldModel).getSocial());
			}

			// Si es true, no se notifican los cambios de valor hasta que se
			// realice un commit.
			// field.setBuffered(false);

			field.setRequired(fieldModel.isRequired());

			// Validadores
			addValidators(field, fieldModel);

			if (!fieldModel.isVisible() && !fieldModel.isVisibleEditing()) {
				field.setVisible(false);
			}

			// Transformación por defecto.
			if (field instanceof AbstractTextField) {

				AbstractTextField af = (AbstractTextField) field;

				// Obtener la descripción del key title
				if (fieldModel.getDescription() == null && fieldModel.getTitle() != null) {
					af.setDescription(UIContext.getDescription(fieldModel.getTitle()));
				}

				// DisplayWidth
				if (fieldModel.getColumns() > 0) {
					af.setColumns(fieldModel.getColumns() > 40 ? 40 : fieldModel.getColumns());
				}
				else if (fieldModel.getMaxSize() > 0) {
					af.setColumns(fieldModel.getMaxSize() > 40 ? 40 : fieldModel.getMaxSize());
				}

				// MaxLength
				if (fieldModel.getMaxSize() > 0) {
					af.setMaxLength(fieldModel.getMaxSize());
				}

				// CharCase (uppercase o lowercase)
				if (field instanceof TextFieldExt && fieldModel instanceof TextFieldModel && ((TextFieldModel) fieldModel).isUppercase()) {
					((TextFieldExt) field).setUpperCase(true);
				}
				else if (field instanceof TextFieldExt && fieldModel instanceof TextFieldModel && ((TextFieldModel) fieldModel).isLowercase()) {
					((TextFieldExt) field).setLowerCase(true);
				}

				if (fieldModel.hasDescription()) {
					af.setDescription(UIContext.getDescription(fieldModel));
				}
			}

			// Lista de valores
			if (fieldModel instanceof IListValuesFieldModel && field instanceof ComboBox) {

				ComboBox combo = (ComboBox) field;

				IListValuesFieldModel list = (IListValuesFieldModel) fieldModel;

				Object[] values = list.getListValues();

				if (values != null && values.length > 0) {

					for (Object value : values) {
						combo.addItem(value);
					}
				}
			}

			// Email
			if (fieldModel instanceof EmailFieldModel) {
				field.addValidator(new EmailValidator(UIContext.getText(LocaleWarnings.EMAIL)));
			}
			// Numérico
			else if (fieldModel instanceof AbstractNumberFieldModel) {
				// Alineación
				field.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);

				// field.addValidator(new
				// com.vaadin.data.validator.IntegerValidator(UIContext.getText(LocaleWarnings.NUMERIC)));
			}
		}
		// Configurar style para Valo para componentes no generados por meccanoj
		// y generados por Vaadin
		else {
			if (field instanceof TextArea) {
				((TextArea) field).addStyleName(ValoTheme.TEXTAREA_SMALL);
			}
			else if (field instanceof DateField) {
				((DateField) field).addStyleName(ValoTheme.DATEFIELD_SMALL);
			}
			else if (field instanceof AbstractField) {
				((AbstractField<?>) field).addStyleName(ValoTheme.TEXTFIELD_SMALL);
			}
		}
	}

	/**
	 * Añadir validadores al campo a editar
	 * 
	 * @param fieldModel
	 */
	private void addValidators(Field<?> field, FieldModel fieldModel) {

		Collection<Validator> validators = fieldModel.getValidators();
		
		if (validators != null && validators.size() > 0) {
			for(Validator validator : validators) {
				field.addValidator(new FieldValidator(validator));
			}
		}
	}

	/**
	 * Cambiar el componente asociado a un fieldModel.
	 * 
	 * @param fieldModel
	 * @param component
	 */
	public void changeField(Class<? extends FieldModel> fieldModel, Class<? extends Component> component) {

		if (list.get(fieldModel) == null) {
			logger.error("FIELD MODEL '" + fieldModel.getClass().getCanonicalName() + "' NO EXIST.");
		}
		else {
			list.put(fieldModel.getCanonicalName(), component);
		}
	}

	/**
	 * Añadir un nuevo componente asociado a un fieldModel.
	 * 
	 * @param fieldModel
	 * @param component
	 */
	public void addField(Class<? extends FieldModel> fieldModel, Class<? extends Component> component) {

		if (list.get(fieldModel) != null) {
			logger.error("FIELD MODEL '" + fieldModel.getClass().getCanonicalName() + "' EXIST.");
		}
		else {
			list.put(fieldModel.getCanonicalName(), component);
		}
	}

	/**
	 * Buscar el campo nested en los servicios definidos para este tipo de
	 * campos.
	 * 
	 * @param name
	 * @param find
	 *            Buscar en campos del mismo nested bean que no sea el definido
	 *            en el servicio como campo de edición.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public INestedBeanServices getNestedField(IEditorServices services, String name, boolean find) {

		if (services == null) {
			return null;
		}

		Map<String, INestedBeanServices> list = services.getNestedFieldModel();
		INestedBeanServices s = list != null && list.size() > 0 ? list.get(name) : null;

		if (s == null && find) {
			String root = name.substring(0, name.indexOf(".") + 1);
			for (Entry<String, INestedBeanServices> ns : list.entrySet()) {
				if (ns.getKey().indexOf(root) == 0) {
					return ns.getValue();
				}
			}

			return null;
		}
		else {
			return s;
		}
	}

}
