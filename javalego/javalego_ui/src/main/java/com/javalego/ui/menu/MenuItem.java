package com.javalego.ui.menu;

import java.util.ArrayList;
import java.util.List;

import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.UIContext;

/**
 * Implementación de un item de menú
 * 
 * @author ROBERTO RANZ
 *
 */
public class MenuItem implements IMenuItem {

	private static final long serialVersionUID = -9057647713890565811L;

	private boolean checkable;

	private boolean separator;

	private boolean visible;

	private boolean enabled;

	private List<IMenuItem> subItems = new ArrayList<IMenuItem>();

	private Colors color;

	private Icon icon;

	private String description;

	private String title;

	private Key name;

	private IMenuItem parent;
	
	private Object data;

	public MenuItem() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 * @param icon
	 * @param color
	 */
	public MenuItem(Key name, String title, String description, Icon icon, Colors color) {
		this.name = name;
		this.title = title;
		this.description = description;
		this.icon = icon;
		this.color = color;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param color
	 */
	public MenuItem(Key name, Key title, Colors color) {
		this.name = name;
		this.title = UIContext.getText(title);
		this.color = color;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public MenuItem(Key name) {
		this.name = name;
		this.title = UIContext.getText(name);
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param color
	 */
	public MenuItem(Key name, Colors color) {
		this.name = name;
		this.title = UIContext.getText(name);
		this.color = color;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param icon
	 * @param color
	 */
	public MenuItem(Key name, Key title, Icon icon, Colors color) {
		this.name = name;
		this.title = UIContext.getText(title);
		this.icon = icon;
		this.color = color;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param icon
	 * @param color
	 */
	public MenuItem(Key name, Icon icon, Colors color) {
		this.name = name;
		this.title = UIContext.getText(name);
		this.icon = icon;
		this.color = color;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param icon
	 */
	public MenuItem(Key name, Key title, Icon icon) {
		this.name = name;
		this.title = UIContext.getText(title);
		this.icon = icon;
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param icon
	 */
	public MenuItem(Key name, Icon icon) {
		this.name = name;
		this.title = UIContext.getText(name);
		this.icon = icon;
	}
	
	/**
	 * Establecer el título a mostrar en el menú.
	 * @param title
	 * @return
	 */
	@Override
	public MenuItem setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Establecer la descripción a mostrar en el menú.
	 * @param title
	 * @return
	 */
	public MenuItem setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Establecer el nombre de referencia del menú utilizando un valor enumerado.
	 * @param name
	 * @return
	 */
	public MenuItem setName(Key name) {
		this.name = name;
		return this;
	}
	
	/**
	 * Establecer el título del menú utilizando un valor enumerado.
	 * @param name
	 * @return
	 */
	public MenuItem setTitle(Key title) {
		this.title = UIContext.getText(title);
		return this;
	}
	
	/**
	 * Establecer el color del menú utilizando un css style.
	 * @param name
	 * @return
	 */
	@Override
	public MenuItem setColor(Colors color) {
		this.color = color;
		return this;
	}
	
	/**
	 * Establecer el icono mostrado al lado del título del menú.
	 * @param name
	 * @return
	 */
	public MenuItem setIcon(Icon icon) {
		this.icon = icon;
		return this;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param color
	 */
	public MenuItem(Key name, String title, Colors color) {
		this.name = name;
		this.title = title;
		this.color = color;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 */
	public MenuItem(Key name, String title) {
		this.name = name;
		this.title = title;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param color
	 */
	public MenuItem(String title, Colors color) {
		this.title = title;
		this.color = color;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 */
	public MenuItem(String title) {
		this.title = title;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param description
	 * @param icon
	 * @param color
	 */
	public MenuItem(String title, String description, Icon icon, Colors color) {
		this.title = title;
		this.description = description;
		this.icon = icon;
		this.color = color;
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 * @param icon
	 */
	public MenuItem(Key name, String title, String description, Icon icon) {
		this(name, title, description, icon, null);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param description
	 * @param icon
	 */
	public MenuItem(String title, String description, Icon icon) {
		this(null, title, description, icon, null);
	}

	/**
	 * Añadir SubItem
	 * 
	 * @param subItem
	 */
	public void addSubItem(IMenuItem subItem) {
		if (subItem == null) {
			return;
		}
		subItem.setParent(this);
		subItems.add(subItem);
	}

	@Override
	public Key getName() {
		return name;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public Colors getColor() {
		return color;
	}

	@Override
	public List<IMenuItem> getSubItems() {
		return subItems;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public boolean isSeparator() {
		return separator;
	}

	@Override
	public boolean isCheckable() {
		return checkable;
	}

	@Override
	public IMenuItem getParent() {
		return parent;
	}

	@Override
	public void setParent(IMenuItem parent) {
		this.parent = parent;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

}
