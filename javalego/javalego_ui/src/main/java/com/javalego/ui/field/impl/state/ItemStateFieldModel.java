package com.javalego.ui.field.impl.state;

import java.io.Serializable;
import java.util.ArrayList;

import com.javalego.model.keys.Icon;

/**
 * Elemento de un estado (opción disponible).
 * 
 * @author ROBERTO RANZ
 */
public class ItemStateFieldModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Nivel de aviso que fijan la prioridad de evaluación para establecer el
	 * estado del campo. Primero los errores y después los avisos.
	 */
	public static final String LEVEL_NONE = "none", LEVEL_WARNING = "warning", LEVEL_ERROR = "error";

	/**
	 * Título representativo.
	 */
	private String title;

	/**
	 * Descripción
	 */
	private String description;

	/**
	 * Imagen representativa del estado (se usará en JTable ver renderizado de
	 * propiedades).
	 */
	private Icon iconSmall;

	/**
	 * Imagen grande. Se usuará en el control de selección de opciones.
	 */
	private Icon iconBig;

	/**
	 * Imagen representativa del estado (se usará en JTable ver renderizado de
	 * propiedades).
	 */
	private String color;

	/**
	 * Mensaje que deseamos mostrar al usuario cuando cambiar el estado a esta
	 * situación. (ver errores e incidencias principalmente).
	 */
	private String userMessage;

	/**
	 * Posibilidad de edición de anotaciones.
	 */
	private boolean notes;

	private String level = LEVEL_NONE;

	/**
	 * Valor que será almacenado en el campo para establecer su estado global.
	 */
	private String value;

	/**
	 * Validaciones a realizar antes de la grabación del registro para
	 * establecer el estado de este campo.
	 */
	private ArrayList<BasicValidation> validations = new ArrayList<BasicValidation>();

	/**
	 * Estado por defecto cuando se activan estados por el sistema y se
	 * solucionan. Ej.: INCIDENCIA (se se cumplimentan los campos que originan
	 * la incidencia, pasamos a este estado).
	 */
	private boolean defaultState;

	/**
	 * Seleccionable.
	 */
	private boolean enabled = true;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Icon getIconSmall() {
		return iconSmall;
	}

	public void setIconSmall(Icon iconSmall) {
		this.iconSmall = iconSmall;
	}

	public Icon getIconBig() {
		return iconBig;
	}

	public void setIconBig(Icon iconBig) {
		this.iconBig = iconBig;
	}

	public boolean isNotes() {
		return notes;
	}

	public void setNotes(boolean notes) {
		this.notes = notes;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ArrayList<BasicValidation> getValidations() {
		return validations;
	}

	public void setValidations(ArrayList<BasicValidation> validations) {
		this.validations = validations;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public boolean isWarning() {
		return level.equals(LEVEL_WARNING);
	}

	public boolean isError() {
		return level.equals(LEVEL_ERROR);
	}

	public boolean isDefaultState() {
		return defaultState;
	}

	public void setDefaultState(boolean defaultState) {
		this.defaultState = defaultState;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	@Override
	public String toString() {
		return title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
