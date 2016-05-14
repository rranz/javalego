package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Campo que representa el ID del registro cuyo valor es secuencial y gestionado
 * por la base de datos.
 * 
 * @author ROBERTO RANZ
 */
public class IdFieldModel extends IntegerFieldModel {

	private static final long serialVersionUID = 1107999322100083812L;

	/**
	 * Constructor
	 */
	public IdFieldModel() {
		readOnly = true;
		visible = false;
		name = "id";
		setTitle(ID.ID);
	}

	enum ID implements Key {
		ID
	}
}
