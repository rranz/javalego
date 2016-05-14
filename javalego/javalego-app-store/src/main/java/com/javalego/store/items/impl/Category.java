package com.javalego.store.items.impl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.javalego.store.items.ICategory;
import com.javalego.store.items.Type;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;

/**
 * Categoría de productos o proyectos.
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
//@Table(name="categories",uniqueConstraints={@UniqueConstraint(columnNames={"TYPE", "CODE"})})
public class Category extends BaseModel implements ICategory, Serializable {

	private static final long serialVersionUID = 544752546394787909L;

	@Enumerated(EnumType.STRING) 
	private MenuIcons2 icon;
	
	@Enumerated(EnumType.STRING) 
	private Type type;

	@Enumerated(EnumType.STRING) 
	//@Column(unique = true, nullable = false)
	private LocaleStore code;
	
	public Category() {}
	
	public Category(Type type, LocaleStore code, MenuIcons2 icon) {
		this.code = code;
		this.type = type;
		this.icon = icon;
	}

	@Override
	public MenuIcons2 getIcon() {
		return icon;
	}

	public void setIcon(MenuIcons2 icon) {
		this.icon = icon;
	}
		
	@Override
	public String toString() {
		return UIContext.getText(code);
	}

	@Override
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public LocaleStore getCode() {
		return code;
	}

	public void setCode(LocaleStore code) {
		this.code = code;
	}
	
}
