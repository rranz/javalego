package com.javalego.store.model.impl;

import java.util.Collection;
import java.util.List;

import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
import com.javalego.store.items.IAuthor;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.ICategory;
import com.javalego.store.items.ILicense;
import com.javalego.store.items.IMember;
import com.javalego.store.items.INews;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.items.IProvider;
import com.javalego.store.items.IRepository;
import com.javalego.store.items.Type;
import com.javalego.store.items.impl.Category;
import com.javalego.store.items.impl.License;
import com.javalego.store.items.impl.Member;
import com.javalego.store.items.impl.News;
import com.javalego.store.items.impl.Product;
import com.javalego.store.items.impl.Project;
import com.javalego.store.items.impl.Provider;
import com.javalego.store.items.impl.Repository;
import com.vaadin.navigator.View;

/**
 * Datos reales obtenidos del proveedor de datos existente en el contexto de
 * aplicación del entorno.
 * 
 * @author ROBERTO RANZ
 *
 */
@SuppressWarnings("unchecked")
public class DataServiceImpl extends AbstractDataServices {

	private Collection<ILicense> licenses;

	private Collection<IRepository> repositories;

	private Collection<ICategory> businessCategories;

	private Collection<ICategory> architectureCategories;

	private DataProvider<Entity> d;

	public DataServiceImpl() {
		d = DataContext.getProvider();
	}

	@Override
	public synchronized List<IProduct> getAllProducts() throws LocalizedException {

		return (List<IProduct>) d.getList(Product.class);
	}

	@Override
	public synchronized Collection<IProvider> getAllProviders() throws LocalizedException {

		return (Collection<IProvider>) d.getList(Provider.class);
	}

	@Override
	public synchronized Collection<ICategory> getCategories(Type type) throws LocalizedException {

		// Cargar inicial
		if (businessCategories == null) {
			businessCategories = (Collection<ICategory>) d.getList(Category.class, "type = '" + Type.BUSINESS.name() + "'", null);
			architectureCategories = (Collection<ICategory>) d.getList(Category.class, "type = '" + Type.ARCHITECTURE.name() + "'", null);
		}

		// Seleccionar por tipo
		if (type == Type.BUSINESS) {
			return businessCategories;
		}
		else if (type == Type.ARCHITECTURE) {
			return architectureCategories;
		}
		else {
			return null;
		}
	}

	@Override
	public Collection<ILicense> getAllLicenses() throws LocalizedException {

		if (licenses == null) {
			licenses = (Collection<ILicense>) d.getList(License.class);
		}

		return licenses;
	}

	@Override
	public Collection<IRepository> getAllRepositories() throws LocalizedException {

		if (repositories == null) {
			repositories = (Collection<IRepository>) d.getList(Repository.class);
		}
		return repositories;
	}

	@Override
	public Collection<IProject> getProjects(Type type) throws LocalizedException {

		return (Collection<IProject>) d.getList(Project.class, "type = '" + type.name() + "'");
	}

	@Override
	public synchronized View findView(String name) {

		return new MockDataServices().findView(name);
	}

	@Override
	public Collection<IProduct> getProducts(ICategory category, String filter) throws LocalizedException {

		return (Collection<IProduct>) d.getList(Product.class, "category.code = '" + category.getCode().name() + "'");
	}

	@Override
	public Collection<IProduct> getProducts(String filter) throws LocalizedException {

		return (Collection<IProduct>) d.getList(Product.class, "title like '" + filter + "%'");
	}

	@Override
	public synchronized Collection<IMember> getAllMembers() throws LocalizedException {

		return (Collection<IMember>) d.getList(Member.class);
	}

	@Override
	public void addMember(IMember member) throws LocalizedException {

		d.save((Member) member);
	}

	@Override
	public boolean isReadOnly(IBaseItem bean) {

		if (SecurityContext.getCurrent().getServices() != null) {
			// Comparar con el principal de la sesión de usuario para ver si es
			// el mismo nombre que creó el item.
			if (bean instanceof IAuthor) {
				return !(SecurityContext.getCurrent().getServices().hasRole("admin") || SecurityContext.getCurrent().getServices().getPrincipal()
						.equals(((IAuthor) bean).getAuthor().getName()));
			}
			else {
				return !SecurityContext.getCurrent().getServices().hasRole("admin");
			}
		}
		return true;
	}

	@Override
	public Collection<INews> getAllNews() throws LocalizedException {

		return (Collection<INews>) d.getList(News.class);
	}

}
