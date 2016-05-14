package com.javalego.ui.field.impl.state;

import java.io.Serializable;

/**
 * Validación que se realizará para establecer este estado en el momento de la
 * grabación del registro. Si se aplica la validación, el sistema mostrará un
 * mensaje al usuario indicando que la grabación establecerá el estado al que
 * está asociado esta validación.
 * 
 * @author ROBERTO RANZ
 */
public abstract class BasicValidation implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String LEVEL_ERROR = "error", LEVEL_WARNING = "warning", LEVEL_INFORMATION = "information", LEVEL_OK = "ok";

	/**
	 * Nivel de validación: warning, aviso o error (no se puede establecer esta
	 * opción).
	 */
	private String level = LEVEL_WARNING;

	public abstract String[] getMessages();

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

}
