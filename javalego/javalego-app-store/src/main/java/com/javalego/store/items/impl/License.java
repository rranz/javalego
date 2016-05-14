package com.javalego.store.items.impl;

import javax.persistence.Entity;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.ILicense;

@Entity
public class License extends BaseModel implements ILicense {

	private static final long serialVersionUID = 1540269505862535L;
	
	private String url;
	
	public License() {
	}
	
	public License(String name) {
		this.name = name;
	}
	
	@Override
	public String getUrl() {
		return url;
	}
	
	@Override
	public String getTitle() {
		return title == null ? name : title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Icon getIcon() {
		return null; // MenuIcons2.LICENSE;
	}

	@Override
	public String toHtml() {
		return toString();
	}

	@Override
	public String toTile() {
		return toString();
	}

}
