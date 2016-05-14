package com.javalego.ui.field.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.model.AbstractBaseModel;
import com.javalego.model.keys.Key;
import com.javalego.model.validator.StringLengthValidator;
import com.javalego.model.validator.Validator;
import com.javalego.ui.field.FieldModel;

/**
 * Implementación básica de un modelo de campo para su edición en cualquier
 * tecnología (web, mobile, ...)
 * 
 * @author ROBERTO RANZ
 */
public abstract class AbstractFieldModel extends AbstractBaseModel implements FieldModel {

	private static final long serialVersionUID = -3918282294856076206L;

	/**
	 * Alineación
	 */
	public static final String ALING_LEFT = "left", ALING_RIGHT = "right", ALING_CENTER = "center";

	/**
	 * Tamaño máximo del campo. Esta propiedad se utilizará en los campos de
	 * texto para permitir un número máximo de caracteres en los controles de la
	 * edición.
	 */
	protected int size;

	/**
	 * Mínimo número de caracteres.
	 */
	// .,.r
	protected int minSize;

	/**
	 * Valor inicial que será incluido al añadir un registro.
	 */
	protected Object defaultValue;

	/**
	 * Alineación.
	 */
	protected String alignment;

	/**
	 * Sólo lectura
	 */
	protected boolean readOnly = false;

	/**
	 * Visible
	 */
	protected boolean visible = true;

	/**
	 * Visibilidad de esta propiedad en el browser. No confundir con visible que
	 * se establece para el browser y para la edición del registro.
	 */
	protected boolean visibleBrowser = true;

	/**
	 * Requerido
	 */
	protected boolean required = false;

	/**
	 * Notifica al servidor el cambio de valor del componente que tiene el
	 * binding con la propiedad. Sólo se utiliza en aplicaciones Web. Si el
	 * valor de la propiedad no influye en recálculos de otros campos dentro de
	 * la edición del objeto, puede configurar este valor a false para evitar
	 * llamadas al servidor innecesarias y evitar relentizar la edición de
	 * registros, reduciendo además el tráfico en la red.
	 */
	protected boolean immediate = true;

	/**
	 * Tipo de cambio aplicado cuando estamos extendiendo una vista de datos
	 * dentro de la sección de campos o propiedades.
	 */
	protected String nullRepresentation;

	/**
	 * Número de caracteres que deseamos visualizar aunque su tamaño sea mayor.
	 */
	protected int columns;

	/**
	 * Campo obligatorio que no para la grabación del registro. Además, el color
	 * será diferente.
	 */
	protected boolean requiredWarning;

	/**
	 * Cuando requiredWarning = true y deseamos mostrar un mensaje al usuario de
	 * aviso cuando no ha especificado un valor en esta propiedad, definimos un
	 * mensaje en esta propiedad.
	 */
	protected String messageRequiredWarning;

	/**
	 * Visualizar esta propiedad cuando se edite un registro aunque
	 * visible="false" y hideInRelation = "true".
	 */
	protected boolean visibleEditing;

	/**
	 * Constructor
	 */
	public AbstractFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public AbstractFieldModel(String name) {
		this.name = name;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param readOnly
	 */
	public AbstractFieldModel(String name, boolean readOnly) {
		this.name = name;
		this.readOnly = readOnly;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public AbstractFieldModel(String name, Key title) {
		super(name, title);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 */
	public AbstractFieldModel(Key title) {
		super(title);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 */
	public AbstractFieldModel(String name, Key title, Key description) {
		super(name, title, description);
	}

	@Override
	public int getColumns() {
		return columns;
	}

	@Override
	public FieldModel setColumns(int columns) {
		this.columns = columns;
		return this;
	}

	@Override
	public boolean isRequiredWarning() {
		return requiredWarning;
	}

	public FieldModel setRequiredWarning(boolean requiredWarning) {
		this.requiredWarning = requiredWarning;
		return this;
	}

	@Override
	public String getMessageRequiredWarning() {
		return messageRequiredWarning;
	}

	public void setMessageRequiredWarning(String messageRequiredWarning) {
		this.messageRequiredWarning = messageRequiredWarning;
	}

	public void setAlignmentCenter() {
		setAlignment(ALING_CENTER);
	}

	public void setAlignmentRight() {
		setAlignment(ALING_RIGHT);
	}

	public void setAlignmentLeft() {
		setAlignment(ALING_LEFT);
	}

	@Override
	public boolean isVisibleEditing() {
		return visibleEditing;
	}

	public void setVisibleEditing(boolean visibleEditing) {
		this.visibleEditing = visibleEditing;
	}

	@Override
	public boolean isImmediate() {
		return immediate;
	}

	@Override
	public FieldModel setImmediate(boolean immediate) {
		this.immediate = immediate;
		return this;
	}

	@Override
	public String getNullRepresentation() {
		return nullRepresentation;
	}

	@Override
	public FieldModel setNullRepresentation(String nullRepresentation) {
		this.nullRepresentation = nullRepresentation;
		return this;
	}

	@Override
	public int getMaxSize() {
		return size;
	}

	@Override
	public FieldModel setMaxSize(int size) {
		this.size = size;
		return this;
	}

	@Override
	public int getMinSize() {
		return minSize;
	}

	@Override
	public FieldModel setMinSize(int minSize) {
		this.minSize = minSize;
		return this;
	}

	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	@Override
	public FieldModel setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public FieldModel setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	@Override
	public boolean isVisibleBrowser() {
		return visibleBrowser;
	}

	public void setVisibleBrowser(boolean visibleBrowser) {
		this.visibleBrowser = visibleBrowser;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	/**
	 * Campo requerido.
	 * 
	 * @param required
	 * @return
	 */
	@Override
	public FieldModel setRequired(boolean required) {
		this.required = required;
		return this;
	}

	@Override
	public Collection<Validator> getValidators() {

		List<Validator> list = null;

		if (minSize > 0 || size > 0) {
			if (list == null) {
				list = new ArrayList<Validator>();
			}
			list.add(new StringLengthValidator(minSize > 0 ? minSize : null, size > 0 ? size : null));
		}

		return list;
	}
}
