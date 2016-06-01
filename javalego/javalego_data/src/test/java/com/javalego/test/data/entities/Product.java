package com.javalego.test.data.entities;

import com.javalego.entity.impl.EntityId;

public class Product extends EntityId {

	private String title;
	
	private String description;

	public Product(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
