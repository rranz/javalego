package com.javalego.store.items.impl;

import java.io.Serializable;

import javax.persistence.Entity;

import com.javalego.store.items.IDemo;

/**
 * Datos para visualizar un demo de producto o proyecto.ç
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
public class Demo extends BaseModel implements IDemo, Serializable {

	private static final long serialVersionUID = -7384661047402467959L;

	private String urlCode;

	private String view;

	private String url;

	public Demo() {}
	
	public Demo(String view, String urlCode) {
		this.view = view;
		this.urlCode = urlCode;
	}
	
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getView() {
		return view;
	}

	@Override
	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	public void setView(String view) {
		this.view = view;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
