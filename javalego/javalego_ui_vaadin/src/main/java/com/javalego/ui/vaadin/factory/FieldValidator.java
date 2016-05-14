package com.javalego.ui.vaadin.factory;

import com.javalego.ui.UIContext;
import com.vaadin.data.Validator;

/**
 * Validador de campos Vaadin genérico que usa los validadores JavaLego (también usados para Android).
 *  
 * @author ROBERTO RANZ
 *
 */
public class FieldValidator implements Validator {

	private static final long serialVersionUID = -8749128678233470898L;
	
	private com.javalego.model.validator.Validator validator;

	public FieldValidator(com.javalego.model.validator.Validator validator) {
		this.validator = validator;
	}
	
	@Override
	public void validate(Object value) throws InvalidValueException {
		
		try {
			validator.validate(value, getErrorMessage(validator, value));
		}
		catch (com.javalego.model.validator.Validator.InvalidValueException e) {
			throw new InvalidValueException(e.getLocalizedMessage());
		}
		
	}

	/**
	 * Obtener el mensaje de error localizado según la tipología del validador.
	 * @param validator
	 * @param value
	 * @return
	 */
	private String getErrorMessage(com.javalego.model.validator.Validator validator, Object value) {
		
		String text = UIContext.getText(validator.getKeyErrorMessage());
		
		text = validator.getAdapterErrorMessage(text);
		
		return text.replace("\\{0\\}", value != null ? value.toString() : "");
	}

}
