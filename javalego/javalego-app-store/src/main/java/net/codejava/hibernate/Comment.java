package net.codejava.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.javalego.model.keys.Icon;

/**
 * Comentario
 * @author roberto
 *
 */
@Entity
public class Comment {

	/**
	 * Identificador de registro secuencial. Común para todas las entidades.
	 */
	@Id
	@GeneratedValue
	@Column(name="comment_id")
	protected Long id;
	
	private Date date = new Date();
	
	private String comment;
	
	private int rating;
	
	public Comment() {}
	
	public int getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

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

	public Icon getIcon() {
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
