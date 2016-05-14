package com.javalego.ui.field;

import java.util.Collection;

import com.javalego.model.BaseModel;
import com.javalego.model.validator.Validator;

/**
 * Modelo de datos estándar para la edición de campos en una interface de
 * usuario (UI).
 * 
 * @author ROBERTO RANZ
 *
 */
public interface FieldModel extends BaseModel {

	String ALIGNMENT_LEFT = "left";

	String ALIGNMENT_RIGHT = "right";

	/**
	 * Número de caracteres a visualizar
	 * 
	 * @return
	 */
	int getColumns();

	/**
	 * Aviso (no genera excepción de validación).
	 * 
	 * @return
	 */
	boolean isRequiredWarning();

	/**
	 * Mensaje para campo requerido definido como warning (no genera excepción
	 * de validación).
	 * 
	 * @return
	 */
	String getMessageRequiredWarning();

	/**
	 * Visible en la edición.
	 * 
	 * @return
	 */
	boolean isVisibleEditing();

	/**
	 * Aplicar cambios en el objeto cuando salimos de su edición.
	 * 
	 * @return
	 */
	boolean isImmediate();

	/**
	 * Texto mostrado cuando tiene valor null.
	 * 
	 * @return
	 */
	String getNullRepresentation();

	/**
	 * Número máximo de caracteres.
	 * 
	 * @return
	 */
	int getMaxSize();

	/**
	 * Número mínimo de caracteres.
	 * 
	 * @return
	 */
	int getMinSize();

	/**
	 * Valor por defecto.
	 * 
	 * @return
	 */
	Object getDefaultValue();

	/**
	 * Alineación
	 * 
	 * @return
	 */
	String getAlignment();

	/**
	 * Solo lectura
	 * 
	 * @return
	 */
	boolean isReadOnly();

	/**
	 * Visible
	 * 
	 * @return
	 */
	boolean isVisible();

	/**
	 * Visible en rejillas.
	 * 
	 * @return
	 */
	boolean isVisibleBrowser();

	/**
	 * Requerido
	 * 
	 * @return
	 */
	boolean isRequired();

	/**
	 * Visible
	 * 
	 * @param visible
	 */
	FieldModel setVisible(boolean visible);

	/**
	 * Establecer el ancho de caracteres a visualizar
	 * 
	 * @param displayWidth
	 */
	FieldModel setColumns(int displayWidth);

	/**
	 * Establecer el número máximo de caracteres permitido.
	 * 
	 * @param size
	 */
	FieldModel setMaxSize(int size);

	/**
	 * Establecer la propiedad de solo lectura.
	 * 
	 * @param readOnly
	 */
	FieldModel setReadOnly(boolean readOnly);

	/**
	 * Establece el campo como requerido.
	 * 
	 * @param required
	 * @return
	 */
	FieldModel setRequired(boolean required);

	/**
	 * Número mínimo de caracteres
	 * 
	 * @param minSize
	 * @return
	 */
	FieldModel setMinSize(int minSize);

	/**
	 * Texto mostrado cuando tiene valor null.
	 * 
	 * @param nullRepresentation
	 * @return
	 */
	FieldModel setNullRepresentation(String nullRepresentation);

	/**
	 * Aplicar cambios en el objeto cuando salimos de su edición.
	 * 
	 * @param immediate
	 * @return
	 */
	FieldModel setImmediate(boolean immediate);

	/**
	 * Obtiene la lista de validadores vinculada a cada modelo de campo (Ej.:
	 * valor máximo, número mínimo caracteres, ...)
	 * 
	 * @return
	 */
	Collection<Validator> getValidators();

}
