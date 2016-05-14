package com.javalego.store.items.impl;

import java.util.ArrayList;
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
import javax.persistence.PrePersist;

import com.javalego.model.keys.Icon;
import com.javalego.store.items.IComment;
import com.javalego.store.items.IMember;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.items.IProvider;
import com.javalego.store.items.Type;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.icons.ProviderIcons;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;

/**
 * Proyecto de arquitectura
 * 
 * @author ROBERTO RANZ
 * 
 */
@Entity
public class Project extends BaseModel implements IProject {

	private static final long serialVersionUID = 262042830639502890L;

	// Versionado de edición de registros para garantizar bloqueo optimista
	// cuando dos usuarios editan simultáneamente un registro.
	// TODO: se genera una excepción de que la fila no está actualizada
//	@javax.persistence.Version
//	@Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
//	private int version;

	@Enumerated(EnumType.STRING)
	private ProviderIcons icon;

	@ManyToOne
	private License license;

	@Enumerated(EnumType.STRING)
	private Type type;

	@OneToOne(cascade = { CascadeType.ALL })
	private Version currentVersion = new Version();

	@OneToOne(cascade = { CascadeType.ALL })
	private Social social = new Social();

	@OneToOne(cascade = { CascadeType.ALL })
	private Code code = new Code();

	@OneToOne(cascade = { CascadeType.ALL })
	private Demo demo;

	// No tiene casacadetype.all porque el miembro ya debe de existir en base de
	// datos y se generaría un error de detached de la entidad.
	@ManyToOne(targetEntity = Member.class)
	private IMember author;

	private String comment_providers;

	/**
	 * Valor de los proveedores seleccionados para poder realizar búsquedas por texto de proyectos y productos.
	 */
	private String text_providers;
	
	// orphanRemoval permite actualizar el borrado y cambios realizados en los productos. Si es false no actúa.
	@OneToMany(fetch = FetchType.EAGER, targetEntity = Product.class, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval=true)
	private Collection<IProduct> products = new ArrayList<IProduct>();

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Comment.class, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinTable(name = "project_comments", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
	private Set<IComment> comments = new HashSet<IComment>();

	// @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade =
	// CascadeType.ALL)
	// private Collection<ProjectVersion> versions = new
	// ArrayList<ProjectVersion>();

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Provider.class)
	@JoinTable(name = "project_providers", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "provider_id"))
	private Set<IProvider> providers = new HashSet<IProvider>();

	public Project() {
	}
	
	public Project(IMember author, Type type, String name, String title, String description, ProviderIcons icon, Version version, License license) {
		super(name, title, description);
		this.icon = icon;
		this.author = author;
		this.type = type;
		this.currentVersion = version;
		this.license = license;
	}

	@PrePersist void onPrePersist() {
		text_providers = providers != null  && providers.size() > 0 ? providers.toString() : null;
	}
	
	@Override
	public Icon getIcon() {
		return type == Type.ARCHITECTURE ? MenuIcons2.BRIEFCASE : MenuIcons2.BUSINESS;
	}

	@Override
	public String toHtml() {
		return "<b>" + name + "</b><br><i>" + getTitle() + "</i>" + (author != null ? "<br>" + UIContext.getText(LocaleStore.BY) + " " + author.toHtml() : "")
				+ (providers != null ? "<br>" + getProviderNames() : null);
	}

	@Override
	public String getProviderNames() {
		String text = "";
		if (providers != null) {
			for (IProvider p : providers) {
				text += (text.equals("") ? "" : " - ") + p.getName();
			}
		}
		return text;
	}

	@Override
	public Version getCurrentVersion() {
		return currentVersion;
	}

	@Override
	public Collection<IProduct> getProducts() {
		return products;
	}

	public void setIcon(ProviderIcons icon) {
		this.icon = icon;
	}

	public void setProducts(Collection<IProduct> products) {
		this.products = products;
	}

	// @Override
	// public Collection<ProjectVersion> getVersions() {
	// return versions;
	// }
	//
	// public void setVersions(Collection<ProjectVersion> versions) {
	// this.versions = versions;
	// }
	//
	public void setCurrentVersion(Version currentVersion) {
		this.currentVersion = currentVersion;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	@Override
	public License getLicense() {
		return license;
	}

	@Override
	public Collection<IProvider> getProviders() {
		return providers;
	}

	public void addProvider(IProvider provider) {
		providers.add(provider);
	}

	@Override
	public Collection<IComment> getComments() {
		return comments;
	}

	public void setComments(HashSet<IComment> comments) {
		this.comments = comments;
	}

	@Override
	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	@Override
	public String getEnvironment() {
		return null;
	}

	public void setProviders(Collection<IProvider> providers) {
		this.providers = (Set<IProvider>) providers;
	}

	@Override
	public Demo getDemo() {
		return demo;
	}

	public void setDemo(Demo demo) {
		this.demo = demo;
	}

	@Override
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public IMember getAuthor() {
		return author;
	}

	public void setAuthor(IMember author) {
		this.author = author;
	}

	@Override
	public String toTile() {
		return "<font size=+1><strong>" + getTitle() + "</strong></font><br>" + getDescription() + "</br><i>" + UIContext.getText(LocaleStore.BY) + " "
				+ (getAuthor() != null ? getAuthor().toHtml() : "") + "</i>";
	}

	@Override
	public String getComment_providers() {
		return comment_providers;
	}

	public void setComment_providers(String comment_providers) {
		this.comment_providers = comment_providers;
	}

	@Override
	public Social getSocial() {
		return social;
	}

	public void setSocial(Social social) {
		this.social = social;
	}

	public String getText_providers() {
		return text_providers;
	}

	public void setText_providers(String text_providers) {
		this.text_providers = text_providers;
	}

//	public int getVersion() {
//		return version;
//	}
//
//	public void setVersion(int version) {
//		this.version = version;
//	}

}
