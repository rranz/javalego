package com.javalego.ui.editor.data.impl;

import com.javalego.model.keys.Key;
import com.javalego.ui.editor.data.IItemEditor;

/**
 * Dato básico a editar.
 * @author roberto
 *
 */
public abstract class AbstractDataEditor implements IItemEditor {

	private Key title;
	
	private String name;

	/**
	 * Constructor
	 * @param name
	 * @param title
	 * @param value
	 */
	public AbstractDataEditor(String name, Key title) {
		this.name = name;
		this.title = title;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Key getTitle() {
		return title;
	}

	public void setTitle(Key title) {
		this.title = title;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
