package com.javalego.store.items.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.javalego.store.items.ICategory;
import com.javalego.store.items.IComment;
import com.javalego.store.items.ILicense;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.items.IScreenShot;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;

/**
 * Producto de la arquitectura
 * 
 * @author ROBERTO RANZ
 * 
 */
@Entity
public class Product extends BaseModel implements IProduct {

	private static final long serialVersionUID = 4292993160371399084L;

	@Enumerated(EnumType.STRING)
	private MenuIcons2 icon;

	@ManyToOne(targetEntity = License.class)
	private ILicense license;

	@ManyToOne(targetEntity = Project.class)
	private IProject project;

	@ManyToOne(targetEntity = Category.class)
	private ICategory category;

	@OneToOne(cascade = CascadeType.ALL)
	private Demo demo;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Comment.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "product_comments", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
	private Set<IComment> comments = new HashSet<IComment>();

	@OneToMany(fetch = FetchType.EAGER, targetEntity = ScreenShot.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "product_screenshot", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "screenshot_id"))
	private Set<IScreenShot> screenshots = new HashSet<IScreenShot>();

	public Product() {
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param title
	 * @param description
	 * @param category
	 */
	public Product(String name, String title, String description, ICategory category) {
		super(name, title, description);
		this.category = category;
	}

	@Override
	public MenuIcons2 getIcon() {
		return icon != null ? icon : category != null ? category.getIcon() : null;
	}

	@Override
	public String toHtml() {
		return "<strong>" + getTitle() + "</strong><br>" + (description != null ? description : "")
				+ (project.getAuthor() != null ? "<br>" + UIContext.getText(LocaleStore.BY) + " " + project.getAuthor().toHtml() : "");
	}

	@Override
	public ICategory getCategory() {
		return category;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	@Override
	public ILicense getLicense() {
		return license;
	}

	/**
	 * Licencia
	 * 
	 * @param license
	 * @return
	 */
	public void setLicense(ILicense license) {
		this.license = license;
	}

	/**
	 * Categoría
	 * 
	 * @param category
	 * @return
	 */
	public void setCategory(ICategory category) {
		this.category = category;
	}

	@Override
	public Demo getDemo() {
		return demo;
	}

	public void setDemo(Demo demo) {
		this.demo = demo;
	}

	@Override
	public String toTile() {
		return "<font size=+1><strong>" + getTitle() + "</strong></font><br>" + (description != null ? description : "") + "</br><i>" + UIContext.getText(LocaleStore.BY) + " "
				+ (getProject().getAuthor() != null ? getProject().getAuthor().toHtml() : "") + "</i>";
	}

	@Override
	public Collection<IComment> getComments() {
		return comments;
	}

	public void setComments(HashSet<IComment> comments) {
		this.comments = comments;
	}

	@Override
	public Collection<IScreenShot> getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(HashSet<IScreenShot> screenshots) {
		this.screenshots = screenshots;
	}
}
