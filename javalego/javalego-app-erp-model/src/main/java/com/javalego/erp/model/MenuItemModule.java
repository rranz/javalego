package com.javalego.erp.model;

import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.ui.menu.MenuItem;

/**
 * Item de menú que representa un módulo de la aplicación.
 * 
 * @author ROBERTO 
 *
 */
public class MenuItemModule extends MenuItem {

	private static final long serialVersionUID = 7044717907191781676L;
	
	/**
	 * Editor de beans
	 */
	private Class<?> editor;

	/**
	 * Constructor
	 * @param name
	 * @param title
	 * @param icon
	 * @param editor
	 */
	public MenuItemModule(Key name, Key title, Icon icon, Class<?> editor) {
		super(name, title, icon);
		this.setEditor(editor);
	}

	public Class<?> getEditor() {
		return editor;
	}

	public void setEditor(Class<?> editor) {
		this.editor = editor;
	}
	
}
