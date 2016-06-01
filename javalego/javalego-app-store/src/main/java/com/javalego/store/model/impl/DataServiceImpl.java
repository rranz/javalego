package com.javalego.store.model.impl;

import java.util.List;

import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.exception.LocalizedException;
import com.javalego.security.SecurityContext;
import com.javalego.store.items.IAuthor;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.ICategory;
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
public class DataServiceImpl extends AbstractDataServices {

	private List<License> licenses;

	private List<Repository> repositories;

	private List<Category> businessCategories;

	private List<Category> architectureCategories;

	private DataProvider d;

	public DataServiceImpl() {
		d = DataContext.getProvider();
	}

	@Override
	public synchronized List<Product> getAllProducts() throws LocalizedException {

		return d.findAll(Product.class);
	}

	@Override
	public synchronized List<Provider> getAllProviders() throws LocalizedException {

		return d.findAll(Provider.class);
	}

	@Override
	public synchronized List<Category> getCategories(Type type) throws LocalizedException {

		// Cargar inicial
		if (businessCategories == null) {
			businessCategories = d.findAll(Category.class, "type = '" + Type.BUSINESS.name() + "'", null);
			architectureCategories = d.findAll(Category.class, "type = '" + Type.ARCHITECTURE.name() + "'", null);
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
	public List<License> getAllLicenses() throws LocalizedException {

		if (licenses == null) {
			licenses = d.findAll(License.class);
		}

		return licenses;
	}

	@Override
	public List<Repository> getAllRepositories() throws LocalizedException {

		if (repositories == null) {
			repositories = d.findAll(Repository.class);
		}
		return repositories;
	}

	@Override
	public List<Project> getProjects(Type type) throws LocalizedException {

		return d.findAll(Project.class, "type = '" + type.name() + "'");
	}

	@Override
	public synchronized View findView(String name) {

		return new MockDataServices().findView(name);
	}

	@Override
	public List<Product> getProducts(ICategory category, String filter) throws LocalizedException {

		return d.findAll(Product.class, "category.code = '" + category.getCode().name() + "'");
	}

	@Override
	public List<Product> getProducts(String filter) throws LocalizedException {

		return d.findAll(Product.class, "title like '" + filter + "%'");
	}

	@Override
	public synchronized List<Member> getAllMembers() throws LocalizedException {

		return d.findAll(Member.class);
	}

	@Override
	public void addMember(Member member) throws LocalizedException {

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
	public List<News> getAllNews() throws LocalizedException {

		return d.findAll(News.class);
	}

}
