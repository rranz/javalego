package com.javalego.ui.editor.data.impl;

import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Key;
import com.javalego.ui.editor.data.IValueDataEditor;

/**
 * Edición de datos de valores primitivos. String, Date, Long, Double, Integer, ...
 * 
 * @author ROBERTO RANZ
 *
 */
public class ValueDataEditor implements IValueDataEditor {

	private String name;
	
	private Key title;

	public ValueDataEditor(String name, Key title, Object value) {
		this.name = name;
		this.title = title;
		this.value = value;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(Key title) {
		this.title = title;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	private Object value;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Key getTitle() {
		return title;
	}

	@Override
	public List<LocalizedException> validate(Object value) {
		return null;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
