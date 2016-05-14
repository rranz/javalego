package com.javalego.ui.field.impl;

import java.util.Collection;

import com.javalego.model.keys.Key;

/**
 * Lista de valores
 * 
 * @author ROBERTO RANZ
 */
public class ListFieldModel extends AbstractFieldModel implements IListValuesFieldModel {

	private static final long serialVersionUID = -7263431798948883834L;

	/**
	 * Lista de valores posibles.
	 */
	protected Object[] items;

	/**
	 * Constructor
	 */
	public ListFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param items
	 *            Lista de valores seleccionables.
	 */
	public ListFieldModel(String name, Key title, Object[] items) {
		super(name, title);
		this.items = items;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param items
	 *            Lista de valores seleccionables.
	 */
	public ListFieldModel(Key title, Object[] items) {
		super(title);
		this.items = items;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param items
	 *            Lista de valores seleccionables.
	 */
	public ListFieldModel(Key title, Collection<?> items) {
		super(title);
		if (items != null) {
			this.items = items.toArray();
		}
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param items
	 *            Lista de valores seleccionables.
	 */
	public ListFieldModel(String name, Key title, Collection<?> items) {
		super(name, title);
		if (items != null) {
			this.items = items.toArray();
		}
	}

	@Override
	public Object[] getListValues() {
		return items;
	}

	@Override
	public String[] getListLabels() {
		return null;
	}

	@Override
	public String[] getListColors() {
		return null;
	}

}
