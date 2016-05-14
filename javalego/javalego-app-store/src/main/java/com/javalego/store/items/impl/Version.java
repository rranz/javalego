package com.javalego.store.items.impl;

import javax.persistence.Entity;

import com.javalego.store.items.IVersion;

/**
 * Versión
 * @author ROBERTO RANZ
 *
 */
@Entity
public class Version extends BaseModel implements IVersion {

	private static final long serialVersionUID = 3942547465909461594L;

	private String notes;

	public Version() {}
	
	public Version(String name, String notes) {
		this.name = name;
		this.notes = notes;
	}
	
	@Override
	public String toHtml() {
		return name + (notes != null ? " " + notes : "");
	}

	@Override
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
