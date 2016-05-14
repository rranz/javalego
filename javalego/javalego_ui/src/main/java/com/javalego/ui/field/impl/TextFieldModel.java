package com.javalego.ui.field.impl;

import com.javalego.model.keys.Key;

/**
 * Propiedades de tipo texto Texto.
 * 
 * @author ROBERTO RANZ
 */
public class TextFieldModel extends AbstractFieldModel {

	private static final long serialVersionUID = -4076154394899590522L;

	/**
	 * Edición en mayúsculas o minúsculas.
	 */
	static public final String CHARCASE_UPPER = "upper", CHARCASE_LOWER = "lower", CHARCASE_NONE = "none";

	/**
	 * Tipo de edición.
	 */
	protected String charCase = CHARCASE_NONE;

	/**
	 * Ignorar los acentos sobre las vocales mayúsculas
	 */
	private boolean ignoreCapitalAccents = true;

	/**
	 * Llenar por la izquierda con el carácter definido en esta propiedad y con
	 * respecto a la longitud máxima definida.
	 */
	private String fillCharacterLeft;

	/**
	 * Admite sólo dígitos.
	 */
	private boolean onlyNumbers;

	public TextFieldModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param uppercase
	 */
	public TextFieldModel(String name, Key title, boolean uppercase) {
		super(name, title);
		setUppercase(uppercase);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param uppercase
	 */
	public TextFieldModel(Key title, boolean uppercase) {
		super(title);
		setUppercase(uppercase);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public TextFieldModel(Key title) {
		super(title);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public TextFieldModel(String name, Key title) {
		super(name, title);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 */
	public TextFieldModel(String name, Key title, Key description) {
		super(name, title, description);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param size
	 * @param columns
	 */
	public TextFieldModel(String name, Key title, int size, int columns) {
		this(name, title);
		setMaxSize(size);
		setColumns(columns);
	}

	public String getFillCharacterLeft() {
		return fillCharacterLeft;
	}

	public void setFillCharacterLeft(String fillCharacterLeft) {
		this.fillCharacterLeft = fillCharacterLeft;
	}

	public String getCharCase() {
		return charCase;
	}

	public void setCharCase(String charCase) {
		this.charCase = charCase;
	}

	public boolean isOnlyNumbers() {
		return onlyNumbers;
	}

	public TextFieldModel setOnlyNumbers(boolean onlyNumbers) {
		this.onlyNumbers = onlyNumbers;
		return this;
	}

	public boolean isIgnoreCapitalAccents() {
		return ignoreCapitalAccents;
	}

	public void setIgnoreCapitalAccents(boolean ignoreCapitalAccents) {
		this.ignoreCapitalAccents = ignoreCapitalAccents;
	}

	public boolean isLowercase() {
		return charCase.equals(CHARCASE_LOWER);
	}

	public TextFieldModel setLowercase(boolean lowercase) {
		charCase = lowercase ? CHARCASE_LOWER : CHARCASE_NONE;
		return this;
	}

	public boolean isUppercase() {
		return charCase.equals(CHARCASE_UPPER);
	}

	public TextFieldModel setUppercase(boolean uppercase) {
		charCase = uppercase ? CHARCASE_UPPER : CHARCASE_NONE;
		return this;
	}

	public boolean isNonecase() {
		return charCase.equals(CHARCASE_NONE);
	}

	public void setNoneCase() {
		charCase = CHARCASE_NONE;
	}

}
