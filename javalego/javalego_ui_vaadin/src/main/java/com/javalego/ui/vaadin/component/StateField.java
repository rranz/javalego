package com.javalego.ui.vaadin.component;

import com.javalego.ui.field.impl.state.StateFieldModel;
import com.vaadin.ui.ComboBox;

/**
 * Componente de visualización de estados de una propiedad.
 * 
 * @author ROBERTO RANZ
 * 
 */
public class StateField extends AbstractFieldActions {

	private static final long serialVersionUID = 7881228538680082620L;

	@SuppressWarnings("unused")
	private final ComboBox combo;

	/**
	 * Constructor
	 * 
	 * @param property
	 * @param combo
	 */
	public StateField(final StateFieldModel property, ComboBox combo) {

		super(combo);

		this.combo = combo;

		setHideActions(false);
	}
}
