package com.javalego.store.items.impl;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;

/**
 * Desarrollador de tipo Empresa
 * 
 * @author ROBERTO RANZ
 *
 */
@Entity
public class Company extends Member {

	private static final long serialVersionUID = 3549079320871141715L;

	@OneToOne(cascade = {CascadeType.ALL})
	private Social social = new Social();
	
	public Company() {}
	
	/**
	 * Constructor
	 * @param name Campo clave o principal
	 * @param title
	 * @param registration
	 */
	public Company(String name, String title, Date registration) {
		super(name, registration);
		this.title = title;
	}

	@Override
	public String toHtml() {
		return  "<strong>" + (name != null ? name : "") + "</strong><br>" + title;
	}
	
	@Override
	public String toString() {
		return name + " - " + title + " (" + UIContext.getText(LocaleStore.COMPANY) + ")";
	}	

	@Override
	public String toTile() {
		return toHtml();
	}

	@Override
	public Social getSocial() {
		return social;
	}

	public void setSocial(Social social) {
		this.social = social;
	}

}
