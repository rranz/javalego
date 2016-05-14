package com.javalego.store.items.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.INews;

@Entity
public class News extends BaseModel implements INews {

	private static final long serialVersionUID = 709749433458303303L;

	private Date date;
	
	public News() {}
	
	public News(Date date, String title, String description) {
		super(null, title, description);
		this.date = date;
	}

	@Override
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toHtml() {
		return "<p><font size=2><i>" + new SimpleDateFormat().format(date) + "</i><br><b>" + getTitle() + "</b><br>" + getDescription() + "</font></p>";
	}

	@Override
	public Icon getIcon() {
		return null;
	}

	@Override
	public String toTile() {
		return toHtml();
	}	
}
