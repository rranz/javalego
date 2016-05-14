package com.javalego.ui.options;

import com.javalego.model.AbstractBaseModel;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;

/**
 * Opción básica para reutilizar datos y métodos estándar.
 * 
 * @author ROBERTO RANZ
 *
 */
public class Option extends AbstractBaseModel implements IOption {
	
	private static final long serialVersionUID = 1762498367439221397L;

	private Icon icon;
	
	private Colors color;
	
	/**
	 * Definición en varios idiomas del título y descripción (opcional) de la opción.
	 */
	private Key languages;

	/**
	 * Constructor que establece las propiedades title, description e icono. 
	 * 
	 * @param title
	 * @param description
	 * @param icon 
	 */
	public Option(Key title, Key description, Icon icon) {
		super(title, description);
		this.icon = icon;
	}

	/**
	 * Constructor que establece las propiedades: icono. 
	 * 
	 * @param icon 
	 */
	public Option(Icon icon) {
		this.icon = icon;
	}

	/**
	 * Constructor que establece las propiedades title, description y color
	 * 
	 * @param title
	 * @param description
	 * @param color
	 */
	public Option(Key title, Key description, Colors color) {
		super(title, description);
		this.color = color;
	}
	
	/**
	 * Constructor que establece las propiedades title y description e icono
	 * 
	 * @param title
	 * @param description
	 * @param color 
	 * @param icon 
	 */
	public Option(Key title, Key description, Colors color, Icon icon) {
		super(title, description);
		this.color = color;
		this.icon = icon;
	}	
	
	/**
	 * Constructor que establece las propiedades del idioma de donde se obtendrán el título y descripción.
	 * 
	 * @param languages Definición en varios idiomas del título y descripción (opcional) de la opción.
	 * @param color
	 * @param icon
	 */
	public Option(Key languages, Colors color, Icon icon) {
		this.languages = languages;
		this.color = color;
		this.icon = icon;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	@Override
	public Colors getColor() {
		return color;
	}

	public void setColor(Colors color) {
		this.color = color;
	}

	public Key getLanguages() {
		return languages;
	}

	public void setLanguages(Key languages) {
		this.languages = languages;
	}

}
