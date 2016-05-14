package com.javalego.ui.editor.data.impl;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.editor.data.IValueDataEditor;

/**
 * Dato básico a editar.
 * @author roberto
 *
 */
public class PrimitiveDataEditor extends AbstractDataEditor implements IValueDataEditor {

	/**
	 * Valor a editar
	 */
	private Object value;
	
	/**
	 * Constructor
	 * @param name
	 * @param title
	 * @param value
	 */
	public PrimitiveDataEditor(String name, Key title, Object value) {
		super(name, title);
		this.value = value;
	}
	
	@Override
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public List<LocalizedException> validate(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
