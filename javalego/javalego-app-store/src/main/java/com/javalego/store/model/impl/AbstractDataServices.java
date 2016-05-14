package com.javalego.store.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.security.SecurityContext;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.IBaseModel;
import com.javalego.store.items.ICategory;
import com.javalego.store.items.IComment;
import com.javalego.store.items.IMember;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.items.Type;
import com.javalego.store.items.impl.Comment;
import com.javalego.store.items.impl.Company;
import com.javalego.store.items.impl.Developer;
import com.javalego.store.items.impl.License;
import com.javalego.store.items.impl.News;
import com.javalego.store.items.impl.Product;
import com.javalego.store.items.impl.Project;
import com.javalego.store.items.impl.Provider;
import com.javalego.store.items.impl.Repository;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.ui.locale.LocaleStore;

/**
 * Servicios de datos de la aplicación.
 * 
 * Métodos comunes a los diferentes tipos de servicios de datos (mock y datos
 * reales bbdd).
 * 
 * @author ROBERTO RANZ
 *
 */
public abstract class AbstractDataServices implements StoreDataServices {

	@Override
	public Product newInstanceProduct(IProject project) throws LocalizedException {

		try {
			Product product = Product.class.newInstance();
			product.setProject(project);
			return product;
		}
		catch (InstantiationException | IllegalAccessException e) {
			throw new LocalizedException("ERROR NEW INSTANCE PRODUCT", e);
		}
	}

	@Override
	public Project newInstanceProject(Type type) throws LocalizedException {

		try {
			Project project = Project.class.newInstance();
			project.setType(type);
			project.setAuthor(getSessionMember());
			return project;
		}
		catch (InstantiationException | IllegalAccessException e) {
			throw new LocalizedException("ERROR NEW INSTANCE PRODUCT", e);
		}
	}

	@Override
	public Collection<? extends IBaseItem> getItems(Key type, String filter) throws LocalizedException {

		if (type == LocaleStore.ARCHITECTURE_PROJECTS) {
			return getProjects(Type.ARCHITECTURE);
		}
		else if (type == LocaleStore.BUSINESS_PROJECTS) {
			return getProjects(Type.BUSINESS);
		}
		else if (type == LocaleStore.DEVELOPERS) {
			return getDevelopers();
		}
		else if (type == LocaleStore.COMPANIES) {
			return getCompanies();
		}
		else if (type == LocaleStore.PROVIDERS) {
			return getAllProviders();
		}
		else if (type == LocaleStore.LICENSES) {
			return getAllLicenses();
		}
		else if (type == LocaleStore.REPOSITORIES) {
			return getAllRepositories();
		}
		else if (type == LocaleStore.NEWS) {
			return getAllNews();
		}
		else {
			// Productos por el tipo de la opción seleccionada por el usuario en
			// arquitectura y negocio.
			ICategory category = getCategory(type);
			if (category != null) {
				return getProducts(category, filter);
			}
			else if (type != null) {
				return getProducts(filter);
			}
			else {
				return null;
			}
		}
	}

	/**
	 * Obtener la categoría del tipo.
	 * 
	 * @param type
	 * @return
	 * @throws LocalizedException
	 */
	private ICategory getCategory(Key type) throws LocalizedException {

		for (ICategory c : getCategories(Type.ARCHITECTURE)) {
			if (c.getCode().name() == type.name()) {
				return c;
			}
		}

		for (ICategory c : getCategories(Type.BUSINESS)) {
			if (c.getCode().name() == type.name()) {
				return c;
			}
		}

		return null;
	}

	@Override
	public IBaseItem getInstance(Key key) throws LocalizedException {

		if (key == LocaleStore.ARCHITECTURE_PROJECTS || key == LocaleStore.ARCHITECTURE_PRODUCTS || key == LocaleStore.BUSINESS_PROJECTS) {
			return newInstanceProject(key == LocaleStore.BUSINESS_PROJECTS ? Type.BUSINESS : Type.ARCHITECTURE);
		}
		else if (key == LocaleStore.LICENSES) {
			return new License();
		}
		else if (key == LocaleStore.REPOSITORIES) {
			return new Repository();
		}
		else if (key == LocaleStore.NEWS) {
			return new News();
		}
		else if (key == LocaleStore.PROVIDERS) {
			return new Provider();
		}
		else {
			throw new LocalizedException("TYPE " + key + " NOT FOUND. ERROR GET NEW INSTANCE");
		}
	}

	@Override
	public boolean isInsertable(Key type) {

		return type == LocaleStore.ARCHITECTURE_PROJECTS || type == LocaleStore.ARCHITECTURE_PRODUCTS || type == LocaleStore.BUSINESS_PROJECTS || type == LocaleStore.PROVIDERS
				|| type == LocaleStore.LICENSES || type == LocaleStore.NEWS || type == LocaleStore.REPOSITORIES;
	}

	@Override
	public Colors getColor(IBaseModel item) throws LocalizedException {

		if (item instanceof IProduct) {
			return getCategories(Type.BUSINESS).contains(((IProduct) item).getCategory()) ? Colors.RED : Colors.BLUE;
		}
		else if (item instanceof IProject) {
			return Colors.INDIGO;
		}
		else if (item instanceof IMember) {
			return Colors.ORANGE;
		}

		return null;
	}

	@Override
	public Icon getIcon(IBaseModel item) {

		if (item instanceof IBaseItem) {
			return ((IBaseItem) item).getIcon();
		}
		else {
			return null;
		}
	}

	@Override
	public IComment newInstanceComment() throws LocalizedException {

		Comment comment = new Comment();
		comment.setAuthor(getSessionMember());
		return comment;
	}

	@Override
	public IMember getSessionMember() throws LocalizedException {

		return findMember(String.valueOf(SecurityContext.getCurrent().getServices().getPrincipal()));
	}

	@Override
	public List<Developer> getDevelopers() throws LocalizedException {

		List<Developer> list = new ArrayList<Developer>();
		for (IMember member : getAllMembers()) {
			if (member instanceof Developer) {
				list.add((Developer) member);
			}
		}
		return list;
	}

	@Override
	public List<Company> getCompanies() throws LocalizedException {

		List<Company> list = new ArrayList<Company>();
		for (IMember member : getAllMembers()) {
			if (member instanceof Company) {
				list.add((Company) member);
			}
		}
		return list;

	}

	@Override
	public IMember findMember(String name) throws LocalizedException {

		for (IMember member : getAllMembers()) {
			if (member.getName().equals(name)) {
				return member;
			}
		}

		return null;
	}
}
