package com.javalego.store.items.impl;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import com.javalego.entity.impl.IdNumberEntityImpl;
import com.javalego.store.items.IBaseModel;


/**
 * Implementación de los datos básicos de items
 * 
 * @author ROBERTO RANZ
 *
 */
@MappedSuperclass
public abstract class BaseModel extends IdNumberEntityImpl implements IBaseModel {

	private static final long serialVersionUID = 7096080570023425639L;

	@Column(unique=true)
	protected String name;
	
	protected String title;
	
	@Lob
	protected String description;
	
	public BaseModel() {}
	
	/**
	 * Constructor
	 * @param name
	 */
	public BaseModel(String name) {
		this.name = name;
	}

	/**
	 * Constructor
	 * @param name
	 * @param title 
	 * @param description 
	 */
	public BaseModel(String name, String title) {
		this.name = name;
		this.title = title;
	}
	
	/**
	 * Constructor
	 * @param name
	 * @param title 
	 * @param description 
	 */
	public BaseModel(String name, String title, String description) {
		this(name, title);
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
		return name + (title != null ? " - " + title : "");
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public void setDescription(String description) {
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
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
}
