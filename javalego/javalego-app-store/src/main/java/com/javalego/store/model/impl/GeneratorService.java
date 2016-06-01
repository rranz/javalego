package com.javalego.store.model.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.data.DataContext;
import com.javalego.data.DataProvider;
import com.javalego.entity.Entity;
import com.javalego.exception.LocalizedException;
import com.javalego.security.model.IRole;
import com.javalego.security.model.IUser;
import com.javalego.store.items.ICategory;
import com.javalego.store.items.ILicense;
import com.javalego.store.items.IMember;
import com.javalego.store.items.INews;
import com.javalego.store.items.IProvider;
import com.javalego.store.items.IRepository;
import com.javalego.store.items.Type;
import com.javalego.store.items.impl.Category;
import com.javalego.store.items.impl.Comment;
import com.javalego.store.items.impl.License;
import com.javalego.store.items.impl.Member;
import com.javalego.store.items.impl.News;
import com.javalego.store.items.impl.Product;
import com.javalego.store.items.impl.Project;
import com.javalego.store.items.impl.Provider;
import com.javalego.store.items.impl.Repository;
import com.javalego.store.items.impl.Role;
import com.javalego.store.items.impl.ScreenShot;
import com.javalego.store.items.impl.User;
import com.javalego.store.items.impl.Version;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.ui.icons.ProviderIcons;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.javalego.util.DateUtils;

/**
 * Servicios de generación de datos genéricos como Categorías, repositorios, ...
 * 
 * @author ROBERTO
 *
 */
@SuppressWarnings("unused")
public class GeneratorService {

	private static final Logger logger = Logger.getLogger(GeneratorService.class);

	private DataProvider d;

	private StoreDataServices s = new DataServiceImpl();

	public GeneratorService() {
		d = DataContext.getProvider();
	}

	/**
	 * Proceso de generación de datos estándar de la tienda.
	 * 
	 * @throws LocalizedException
	 */
	public void execute() throws LocalizedException {

		try {
			if (s.getAllProviders().size() == 0) {
				providers();
				members();
				categories();
				repositories();
				licenses();
				news();
				projects();
			}
		}
		catch (Exception e) {
			logger.error("Error generando datos iniciales en la base de datos. " + e.getLocalizedMessage());
		}

	}

	private void projects() throws LocalizedException {

		Version version = new Version("4.0 SNAPSHOT", "Notas de la release");

		ICategory c = s.getCategories(Type.BUSINESS).get(0);
		IMember m = s.getAllMembers().get(0);
		ILicense l = s.getAllLicenses().get(0);
		IRepository r = s.getAllRepositories().get(0);
		IProvider p = s.getAllProviders().get(0);
		IProvider p2 = s.getAllProviders().get(1);

		Project project = new Project(m, Type.BUSINESS, "javalego_store", "Vaadin UI", "Vaadin UI", ProviderIcons.PROJECT, version, (License) l);
		project.addProvider(p);
		project.addProvider(p2);

		project.getCode().setRepository(r);
		project.getSocial().setTwitter("https://twitter.com/javalego");
		project.getSocial().setBlog("http://javalego.wordpress.com/");
		project.getSocial().setFacebook("https://facebook.com");
		project.getSocial().setGooglePlus("https://plus.google.com");
		project.getCode().setMetrics("https://sonar.springsource.org/dashboard/index/org.springframework.data:spring-data-jpa");
		project.getSocial().setForum("http://forum.spring.io/forum/spring-projects/container");

		project.getComments().add(
				new Comment(DateUtils.getDate(2013, 11, 26),
						"Creo que los componentes que ofrece este proyecto son de bastante calidad y que se pueden integrar en cualquier aplicación creada con Vaadin.", m, 5));

		Product pr = new Product("BUTTONS", "Buttons UI", "Buttons UI", c);
		pr.setLicense(l);
		try {
			pr.getScreenshots().add(new ScreenShot("Imagen 1", "Representación 1 del button de código", ResourceIconsVaadin.getCurrent().getBytes(ProviderIcons.BUTTON_COMPONENT)));
			pr.getScreenshots().add(new ScreenShot("Imagen 2", "Representación 2 del button de código", ResourceIconsVaadin.getCurrent().getBytes(ProviderIcons.MENUPANELS)));
			pr.getComments().add(new Comment(DateUtils.getDate(2013, 11, 26), "Botones de un estilo muy minimalista y profesional. Muy bonitos.", m, 5));
			pr.setProject(project);
		}
		catch (LocalizedException e) {
			e.printStackTrace();
		}

		project.getProducts().add(pr);

		d.save(project);
	}

	/**
	 * Generar base de datos si no existe.
	 * 
	 * SE AUTOGENERA CON createDatabaseIfNotExist en la url de conexión con
	 * mysql. (application.properties)
	 * 
	 * @throws LocalizedException
	 */
	@Deprecated
	private void database() throws LocalizedException {

		// Connection c = null;
		// try {
		// c = DriverManager.getConnection("jdbc:mysql://localhost/", "root",
		// "");
		// //c.createStatement().executeUpdate("DROP DATABASE IF EXISTS store");
		// c.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS store");
		// }
		// catch (SQLException e) {
		// throw new LocalizedException("Error creating database", e);
		// }
		// finally {
		// if (c != null) {
		// try {
		// c.close();
		// }
		// catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
		// }
	}

	/**
	 * Tecnologías
	 * 
	 * @throws LocalizedException
	 */
	private void providers() throws LocalizedException {

		for (IProvider item : new MockDataServices().getAllProviders()) {
			d.save((Provider) item);
		}
	}

	/**
	 * Noticias iniciales
	 * 
	 * @throws LocalizedException
	 */
	private void news() throws LocalizedException {

		for (INews item : new MockDataServices().getAllNews()) {
			d.save((News) item);
		}
	}

	/**
	 * Repositorios
	 * 
	 * @throws LocalizedException
	 */
	private void repositories() throws LocalizedException {

		for (IRepository item : new MockDataServices().getAllRepositories()) {
			d.save((Repository) item);
		}
	}

	/**
	 * Licencias
	 * 
	 * @throws LocalizedException
	 */
	private void licenses() throws LocalizedException {

		for (ILicense item : new MockDataServices().getAllLicenses()) {
			d.save((License) item);
		}

	}

	/**
	 * Categorías
	 * 
	 * @throws LocalizedException
	 */
	private void categories() throws LocalizedException {

		for (ICategory item : new MockDataServices().getCategories(Type.ARCHITECTURE)) {
			d.save((Category) item);
		}

		for (ICategory item : new MockDataServices().getCategories(Type.BUSINESS)) {
			d.save((Category) item);
		}
	}

	/**
	 * Grabar miembros y usuarios
	 * 
	 * @throws LocalizedException
	 */
	private void members() throws LocalizedException {

		MockUserServices mock = new MockUserServices();

		for (IRole item : mock.getAllRoles()) {
			d.save((Role) item);
		}

		for (IUser item : mock.getAllUsers()) {
			d.save((User) item);
		}

		for (IMember item : new MockDataServices().getAllMembers()) {
			d.save((Member) item);
		}
	}

}
