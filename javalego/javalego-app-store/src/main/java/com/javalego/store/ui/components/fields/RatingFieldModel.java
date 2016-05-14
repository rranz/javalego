package com.javalego.store.ui.components.fields;

import com.javalego.model.keys.Key;
import com.javalego.ui.field.impl.AbstractFieldModel;

/**
 * Campo de evaluación 1 a 5 puntos representado por un componente donde se muestran estrellas marcadas por cada puntuación.
 * 
 * @author ROBERTO RANZ
 *
 */
public class RatingFieldModel extends AbstractFieldModel {

	private static final long serialVersionUID = -2555202177229854178L;

	public RatingFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 */
	public RatingFieldModel(Key title) {
		super(title);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public RatingFieldModel(String name, Key title) {
		super(name, title);
	}
}
