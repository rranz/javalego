package com.javalego.store.items.impl;

import javax.persistence.Entity;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.IRepository;

/**
 * Repositorio donde se encuentra alojado el código fuente.
 * @author roberto
 *
 */
@Entity
public class Repository extends BaseModel implements IRepository {

	private static final long serialVersionUID = 491473393614791191L;
	
	private String url;

	public Repository() {}
	
	public Repository(String name, String title, String url) {
		super(name, title);
		this.url = url;
	}

	@Override
	public String toHtml() {
		return "<b>" + name + "</b><br>" + getTitle();
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toTile() {
		return toHtml();
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}	
}
