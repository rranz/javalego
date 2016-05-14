package com.javalego.ui.field.frontend;

/**
 * Implementación de los datos básico de un campo usando en aplicaciones
 * frontend (angular, javascript, ...)
 * 
 * @author ROBERTO RANZ
 *
 */
public class FieldFrontEndImpl implements FieldFrontEnd {

	private String name;

	private String label;

	private String description;

	private String type;

	private boolean required;

	private boolean visible;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
