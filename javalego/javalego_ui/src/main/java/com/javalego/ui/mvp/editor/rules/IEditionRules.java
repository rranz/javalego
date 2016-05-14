package com.javalego.ui.mvp.editor.rules;

import java.util.List;


/**
 * Métodos que permiten establecer reglas de edición del tipo: readOnly, setValue, enabled, visible, ...
 * 
 * @author ROBERTO RANZ
 */
public interface IEditionRules {

	/**
	 * Establecer el campo como requerido.
	 * @param fieldName
	 * @param required
	 */
	void setRequired(String fieldName, boolean required);

	/**
	 * Establecer el error del campo requerido.
	 * @param fieldName
	 * @param requiredError
	 */
	void setRequiredError(String fieldName, String requiredError);

	/**
	 * Establecer description del campo.
	 * @param fieldName
	 * @param description
	 */
	void setDescription(String fieldName, String description);

	/**
	 * Establecer la etiqueta del campo.
	 * @param fieldName
	 * @param caption
	 */
	void setCaption(String fieldName, String caption);

	/**
	 * Establecer el valor del campo.
	 * @param fieldName
	 * @param value
	 */
	void setValue(String fieldName, Object value);
	
	/**
	 * Renovar la lista de valores de un campo enumerado.
	 * @param fieldName
	 * @param values
	 */
	void setEnumValues(String fieldName, List<?> values);

	/**
	 * Establecer enabled del campo.
	 * @param fieldName
	 * @param enabled
	 */
	void setEnabled(String fieldName, boolean enabled);

	/**
	 * Establecer visible del campo.
	 * @param fieldName
	 * @param visible
	 */
	void setVisible(String fieldName, boolean visible);

	/**
	 * Establecer readOnly del campo.
	 * @param fieldName
	 * @param readOnly
	 */
	void setReadOnly(String fieldName, boolean readOnly);

	/**
	 * Obtener el valor del campo.
	 * @param fieldName
	 * @return
	 */
	Object getValue(String fieldName);

	/**
	 * Required de un campo.
	 * @param fieldName
	 * @return
	 */
	boolean isRequired(String fieldName);
	
	/**
	 * Campo es visible.
	 * @param fieldName
	 * @return
	 */
	boolean isVisible(String fieldName);
	
	/**
	 * Campo es enabled.
	 * @param fieldName
	 * @return
	 */
	boolean isEnabled(String fieldName);
	
	/**
	 * Campo readOnly.
	 * @param fieldName
	 * @return
	 */
	boolean isReadOnly(String fieldName);
	
	/**
	 * Etiqueta del campo.
	 * @param fieldName
	 * @return
	 */
	String getCaption(String fieldName);

	/**
	 * Required error del campo.
	 * @param fieldName
	 * @return
	 */
	String getRequiredError(String fieldName);

	/**
	 * Descripción del campo.
	 * @param fieldName
	 * @return
	 */
	String getDescription(String fieldName);

}
