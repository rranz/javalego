package com.javalego.ui.field.factory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Date;

import com.javalego.ui.UIContext;
import com.javalego.ui.field.FieldModel;
import com.javalego.ui.field.frontend.FieldFrontEnd;
import com.javalego.ui.field.frontend.FieldFrontEndImpl;
import com.javalego.ui.field.impl.BooleanFieldModel;
import com.javalego.ui.field.impl.DateFieldModel;
import com.javalego.ui.field.impl.DecimalFieldModel;
import com.javalego.ui.field.impl.EnumFieldModel;
import com.javalego.ui.field.impl.ImageFieldModel;
import com.javalego.ui.field.impl.IntegerFieldModel;
import com.javalego.ui.field.impl.TextFieldModel;
import com.javalego.ui.field.impl.TimeFieldModel;
import com.javalego.util.ReflectionUtils;

/**
 * Factoría de creación de modelo de campos en base al tipo de campo de la
 * clase.
 * 
 * @author ROBERTO RANZ
 */
public class FieldModelFactory implements Serializable {

	private static final long serialVersionUID = 9072633496455242543L;

	private FieldModelFactory() {
	}

	/**
	 * Obtener el tipo de dato a partir de una propiead de una clase.
	 * 
	 * @param _class
	 * @return
	 */
	static public FieldModel getFieldModel(Class<?> _class, String name) throws Exception {

		// Propiedades de una propiedad que es una referencia a una clase. (Ej.:
		// banco.id).
		Field f = ReflectionUtils.getField(_class, name);

		if (f.getType() == String.class) {
			return new TextFieldModel();
		}
		else
			return getFieldModel(f.getType());
	}

	/**
	 * Obtener el tipo de dato a partir de una propiead de una clase.
	 * 
	 * @param _class
	 * @return
	 */
	static public FieldModel getFieldModel(Class<?> _class) {

		if (_class == Integer.class || _class == int.class)
			return new IntegerFieldModel();
		else if (_class == Float.class || _class == float.class)
			return new DecimalFieldModel();
		else if (_class == Double.class || _class == double.class)
			return new DecimalFieldModel();
		else if (_class == Long.class || _class == long.class)
			return new IntegerFieldModel();
		else if (_class == Date.class)
			return new DateFieldModel();
		else if (_class == Time.class)
			return new TimeFieldModel();
		else if (_class == Boolean.class || _class == boolean.class)
			return new BooleanFieldModel();
		else if (_class == byte[].class)
			return new ImageFieldModel();
		else if (_class == String.class)
			return new TextFieldModel();
		else if (ReflectionUtils.isList(_class))
			return new EnumFieldModel();
		else
			return new TextFieldModel();
	}

	/**
	 * Obtiene el tipo de propiedad apropiado a la propiedad de la clase pasada
	 * como parámetro.
	 * 
	 * @param propertyName
	 * @param refClass
	 * @return
	 */
	static public FieldModel getTypeProperty(String propertyName, Object view) {

		FieldModel p = getTypeProperty(propertyName, view != null ? view.getClass() : null);
		return p;
	}

	/**
	 * Obtener un campo frontend a partir de un modelo de campo.
	 * 
	 * @param model
	 * @return
	 */
	static public FieldFrontEnd getFieldFrontEnd(FieldModel model) {

		FieldFrontEndImpl field = new FieldFrontEndImpl();

		field.setName(model.getName());
		field.setLabel(UIContext.getTitle(model));
		field.setDescription(UIContext.getDescription(model));
		field.setRequired(model.isRequired());
		field.setVisible(model.isVisible());
		field.setType(model.getClass().getSimpleName());

		return field;
	}
}
