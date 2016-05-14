package net.codejava.hibernate;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 * Producto de la arquitectura
 * 
 * @author ROBERTO RANZ
 * 
 */
@Entity
public class Product {

	/**
	 * Identificador de registro secuencial. Común para todas las entidades.
	 */
	@Id
	@GeneratedValue
	@Column(name = "product_id")
	protected Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "product_comments", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
	private Set<Comment> comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}
