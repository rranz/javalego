package com.javalego.test.data.entities;

import com.javalego.entity.impl.EntityId;

public class Invoice extends EntityId {

	private String title;
	
	private double total;

	public Invoice(String title, double total) {
		this.title = title;
		this.total = total;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
