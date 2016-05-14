package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Propiedad de tipo TextArea
 * 
 * @author ROBERTO RANZ
 */
public class TextAreaFieldModel extends TextFieldModel {

	private static final long serialVersionUID = -2514640921063841262L;

	/**
	 * Número de filas de edición.
	 */
	private int rows;

	/**
	 * Representar la propiedad mediante un botón que abre un diálogo de edición
	 * del texto.
	 */
	private boolean button;

	/**
	 * Imagen que deseamos utilizar cuando definimos button = true y no existen
	 * anotaciones.
	 */
	private String iconButtonEmpty;

	/**
	 * Imagen que deseamos utilizar cuando definimos button = true y existen
	 * anotaciones.
	 */
	private String iconButtonNotEmpty;

	public TextAreaFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public TextAreaFieldModel(Key title) {
		super(title);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public TextAreaFieldModel(String name, Key title) {
		super(name, title);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param uppercase
	 */
	public TextAreaFieldModel(Key title, boolean uppercase) {
		super(title);
		setUppercase(uppercase);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public boolean isButton() {
		return button;
	}

	public void setButton(boolean button) {
		this.button = button;
	}

	public String getIconButtonEmpty() {
		return iconButtonEmpty;
	}

	public void setIconButtonEmpty(String iconButtonEmpty) {
		this.iconButtonEmpty = iconButtonEmpty;
	}

	public String getIconButtonNotEmpty() {
		return iconButtonNotEmpty;
	}

	public void setIconButtonNotEmpty(String iconButtonNotEmpty) {
		this.iconButtonNotEmpty = iconButtonNotEmpty;
	}

}
