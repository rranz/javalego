package com.javalego.store.items.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.javalego.store.items.IProvider;
import com.javalego.store.ui.icons.ProviderIcons;

/**
 * Proveedor de implementación del producto o proyecto.
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
public class Provider extends BaseModel implements IProvider {

	private static final long serialVersionUID = -3482786052003678958L;

	private String url;
	
	@Override
	@Column(name="provider_id", unique=true)
	public Long getId() {
		return super.getId();
	}
	
	@Enumerated(EnumType.STRING) 
	private ProviderIcons icon;

	public Provider() {}
	
	/**
	 * Constructor
	 * @param name
	 * @param title
	 * @param url
	 */
	public Provider(String name, String title, String url, ProviderIcons icon) {
		super(name, title);
		this.url = url;
		this.icon = icon;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public ProviderIcons getIcon() {
		return icon;
	}

	public void setIcon(ProviderIcons icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return title;
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
