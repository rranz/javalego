package com.javalego.store.items.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.IComment;
import com.javalego.store.items.IMember;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.component.rating.Rating;

/**
 * Comentario
 * @author roberto
 *
 */
@Entity
public class Comment extends BaseModel implements IComment {

	private static final long serialVersionUID = 8637283827610743922L;

	private Date date = new Date();
	
	@ManyToOne(targetEntity=Member.class)
	private IMember author;
	
	private String comment;
	
	private int rating;
	
	public Comment() {}
	
	@Override
	@Column(name="comment_id")
	public Long getId() {
		return super.getId();
	}
	
	/**
	 * Constructor
	 * @param date
	 * @param comment
	 * @param author
	 * @param rating (entre 1 y 5) = estrellas.
	 */
	public Comment(Date date, String comment, IMember author, int rating) {
		this.date = date;
		this.comment = comment;
		this.author = author;
		this.rating = rating;
	}
	
	@Override
	public int getRating() {
		return rating;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public IMember getAuthor() {
		return author;
	}

	@Override
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toHtml() {
		return "<i>" + new SimpleDateFormat().format(date) + "</i><p align='justify'>" + comment + "</p><br>" + UIContext.getText(LocaleStore.AUTHOR) + ": " + (author != null ? author.toHtml() : "") + "<br>" + Rating.getHtml(rating);
	}

	@Override
	public String toTile() {
		return toHtml();
	}

	public void setAuthor(IMember author) {
		this.author = author;
	}

}
