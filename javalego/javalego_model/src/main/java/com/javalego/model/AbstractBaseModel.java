package com.javalego.model;

import com.javalego.model.keys.Key;


/**
 * Implementación básica de un modelo de datos.
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class AbstractBaseModel implements BaseModel {

	private static final long serialVersionUID = -6405265513539704407L;

	protected String name;

	private Key title;

	private Key description;

	public AbstractBaseModel() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public AbstractBaseModel(String name) {
		this.name = name;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 *            Idiomas soportados para obtener el texto del título
	 * @param description
	 *            Idiomas soportados para obtener el texto de la descripción
	 */
	public AbstractBaseModel(String name, Key title) {
		this.name = name;
		this.title = title;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 *            Idiomas soportados para obtener el texto del título
	 * @param description
	 *            Idiomas soportados para obtener el texto de la descripción
	 */
	public AbstractBaseModel(String name, Key title, Key description) {
		this(name, title);
		this.description = description;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 *            Idiomas soportados para obtener el texto del título
	 */
	public AbstractBaseModel(Key title) {
		this(title.name().toLowerCase(), title);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 *            Idiomas soportados para obtener el texto del título
	 * @param description
	 *            Idiomas soportados para obtener el texto de la descripción
	 */
	public AbstractBaseModel(Key title, Key description) {
		this.title = title;
		this.description = description;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + (title != null ? " - " + title.name() : "");
	}

	/**
	 * Establecer el valor del título a partir de anotaciones que incluyen la
	 * lista de de idiomas soportados
	 * 
	 * @param title
	 */
	@Override
	public void setTitle(Key title) {
		this.title = title;
	}

	/**
	 * Establecer el valor de la descripción a partir de anotaciones que
	 * incluyen la lista de de idiomas soportados
	 * 
	 * @param title
	 */
	@Override
	public void setDescription(Key description) {
		this.description = description;
	}

	@Override
	public boolean hasTitle() {
		return title != null;
	}

	@Override
	public boolean hasDescription() {
		return description != null;
	}

	@Override
	public Key getTitle() {
		return title;
	}

	@Override
	public Key getDescription() {
		return description;
	}
}
