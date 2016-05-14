package com.javalego.ui.vaadin.component.input;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.factory.FieldModelFactory;


/**
 * Factoría de generación de clase InputBox en base al tipo de variable.
 * 
 * @author ROBERTO RANZ
 */
public class InputBoxFactory {

	private InputBoxFactory() {
	}

	/**
	 * Obtener una instancia de InputBox para obtener la edición de una propiedad adaptada al objeto pasado como parámetro.
	 * 
	 * @param value
	 * @param title
	 * @param displayWidth
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static IInputBox getInstance(Object value, Key title, int displayWidth, int size) throws Exception {

		if (value == null) {
			throw new LocalizedException("No puede pasar una valor = null.");
		}
		InputBox input = new InputBox();
		input.addProperty(getProperty(value, title, displayWidth, size), value);
		input.load();

		return input;
	}

	/**
	 * Obtener una propiedad adaptada al valor pasado como parámetro y configurando su edición.
	 * 
	 * @param value
	 * @param title
	 * @param displayWidth
	 * @param size
	 * @return
	 */
	public static FieldModel getProperty(Object value, Key title, int displayWidth, int size) {

		// Revisar si ya existe este código para obtener el tipo de propiedad en base a un objeto.
		FieldModel property = FieldModelFactory.getFieldModel(value.getClass());
		property.setTitle(title);

		if (displayWidth > 0) {
			property.setColumns(displayWidth);
		}

		if (size > 0) {
			property.setMaxSize(size);
		}

		return property;
	}

}
