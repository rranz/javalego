package com.javalego.store.items.impl;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.javalego.store.items.ICode;
import com.javalego.store.items.IRepository;

/**
 * Recursos relacionados con el código.
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
public class Code extends BaseModel implements ICode, Serializable {

	private static final long serialVersionUID = -2678418966895968404L;
	
	@ManyToOne(targetEntity=Repository.class)
	private IRepository repository;
	
	private String urlRepository;
	
	private String metrics;
	
	private String doc;
	
	private String javadoc;
	
	@Override
	public IRepository getRepository() {
		return repository;
	}

	/**
	 * Repositorio de código (Github, Jira, ...) donde está alojado nuestro código.
	 * @param repository
	 */
	public void setRepository(IRepository repository) {
		this.repository = repository;
	}

	/**
	 * Url de acceso a nuestro proyecto en el repositorio.
	 * @param url
	 */
	public void setUrlRepository(String url) {
		this.urlRepository = url;
	}

	@Override
	public String getMetrics() {
		return metrics;
	}

	@Override
	public String getDoc() {
		return doc;
	}

	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public void setJavadoc(String javadoc) {
		this.javadoc = javadoc;
	}

	@Override
	public String getJavadoc() {
		return javadoc;
	}

	@Override
	public String getUrlRepository() {
		return urlRepository;
	}

	
}
