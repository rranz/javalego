package com.javalego.store.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.javalego.exception.LocalizedException;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.ICategory;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.Type;
import com.javalego.store.items.impl.Category;
import com.javalego.store.items.impl.Comment;
import com.javalego.store.items.impl.Company;
import com.javalego.store.items.impl.Demo;
import com.javalego.store.items.impl.Developer;
import com.javalego.store.items.impl.License;
import com.javalego.store.items.impl.Member;
import com.javalego.store.items.impl.News;
import com.javalego.store.items.impl.Product;
import com.javalego.store.items.impl.Project;
import com.javalego.store.items.impl.Provider;
import com.javalego.store.items.impl.Repository;
import com.javalego.store.items.impl.ScreenShot;
import com.javalego.store.items.impl.Version;
import com.javalego.store.ui.demos.FormDireccion;
import com.javalego.store.ui.demos.FormEmpresas;
import com.javalego.store.ui.demos.MenuPanelsDemo;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.icons.ProviderIcons;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.ui.UIContext;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.javalego.util.DateUtils;
import com.vaadin.navigator.View;

/**
 * Datos de test
 * 
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataServices extends AbstractDataServices {

	/**
	 * Lista de códigos de vistas para realizar demos
	 */
	public static final String DEMOVIEW_MENUPANELS = "MENUPANELS", DEMOVIEW_DIRECCION = "DIRECCION", DEMOVIEW_EMPRESAS = "EMPRESAS";

	private static final Logger logger = Logger.getLogger(MockDataServices.class);

	private List<Product> products;

	private List<License> licenses;

	private List<Provider> providers;

	private List<Repository> repositories;

	private List<Category> businessCategories;

	private List<Member> members = new ArrayList<Member>();

	private List<Category> architectureCategories;

	private HashMap<String, View> demoViews;

	private boolean loadedMembers;

	public MockDataServices() {
	}

	/**
	 * Añadir dos miembros para pruebas
	 * 
	 * @return
	 */
	private List<Member> getMockMembers() {

		List<Member> list = new ArrayList<Member>();

		Developer m = new Developer("roberto", "ROBERTO", "GARCIA PEREZ", new Date());
		m.getSocial().setEmail("mailto:robertoperez@gmail.com");
		m.getSocial().setWeb("http://google.es");
		m.getSocial().setTwitter("https://twitter.com/javalego");
		m.getSocial().setFacebook("https://facebook.com");
		m.getSocial().setGooglePlus("https://plus.google.com");

		list.add(m);
		Company c = new Company("JAVALEGO", "Mobile Apps Company S.L.", new Date());
		c.getSocial().setEmail("mailto:javalego@gmail.com");
		c.getSocial().setWeb("http://www.javalego.com");
		c.getSocial().setTwitter("https://twitter.com/javalego");
		c.getSocial().setFacebook("https://facebook.com/javalego");
		c.getSocial().setGooglePlus("https://plus.google.com/javalego");
		list.add(c);

		return list.size() == 0 ? null : list;
	}

	/**
	 * Datos ficticios de un projecto
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Project getProject() {

		Version version = new Version("4.0 SNAPSHOT", "Notas de la release");

		Project project = new Project(getAllMembers().get(0), Type.BUSINESS, "javalego_store", "Vaadin UI", "Vaadin UI", ProviderIcons.PROJECT, version, AGPL30);
		project.addProvider(new Provider("VAADIN", "Vaadin UI", "http://vaadin.com", ProviderIcons.VAADIN));
		project.addProvider(new Provider("ANDROID", "Android", "http://play.google.com", ProviderIcons.ANDROID));

		project.getCode().setRepository(new Repository("GITHUB", "GitHub", "https://github.com/rranz/javalego_vaadin"));
		project.getSocial().setTwitter("https://twitter.com/javalego");
		project.getSocial().setBlog("http://javalego.wordpress.com/");
		project.getSocial().setFacebook("https://facebook.com");
		project.getSocial().setGooglePlus("https://plus.google.com");
		project.getCode().setMetrics("https://sonar.springsource.org/dashboard/index/org.springframework.data:spring-data-jpa");
		project.getSocial().setForum("http://forum.spring.io/forum/spring-projects/container");

		project.getComments().add(
				new Comment(DateUtils.getDate(2013, 11, 26),
						"Creo que los componentes que ofrece este proyecto son de bastante calidad y que se pueden integrar en cualquier aplicación creada con Vaadin.", getAllMembers().get(0), 5));

		// List<ProjectVersion> list2 = new ArrayList<ProjectVersion>();
		// list2.add(project.getCurrentVersion());
		// project.setVersions(list2);
		return project;

	}

	@Override
	public synchronized List<Product> getAllProducts() {
		return products;
	}

	@Override
	public synchronized List<Provider> getAllProviders() {

		if (providers == null) {
			providers = new ArrayList<Provider>();
			providers.add(new Provider("VAADIN", "Vaadin UI", "http://vaadin.com", ProviderIcons.VAADIN));
			providers.add(new Provider("ANDROID", "Android", "http://play.google.com", ProviderIcons.ANDROID));
			providers.add(new Provider("SPRING", "Spring", "http://spring.io", ProviderIcons.SPRING));
			providers.add(new Provider("SHIRO", "Apache Shiro", "http://shiro.apache.org", ProviderIcons.SHIRO));
			providers.add(new Provider("JERSEY", "REST Jersey", "https://jersey.java.net", ProviderIcons.JERSEY));
			providers.add(new Provider("HIBERNATE", "Hibernate", "http://hibernate.org", ProviderIcons.HIBERNATE));
			providers.add(new Provider("J2EE", "Java J2EE", "http://www.java.com", ProviderIcons.J2EE));
		}

		return providers;
	}

	@Override
	public synchronized List<Category> getCategories(Type type) {

		if (businessCategories == null) {

			businessCategories = new ArrayList<Category>();
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.SOLUTIONS, MenuIcons2.SOLUTION));
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.MODULES, MenuIcons2.MODULE));
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.FORMS, MenuIcons2.FORM));
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.SERVICES, MenuIcons2.SERVICES));
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.PROCESSES, MenuIcons2.PROCESS));
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.RULES, MenuIcons2.RULES));
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.INTERFACES, MenuIcons2.INTERFACE));
			businessCategories.add(new Category(Type.BUSINESS, LocaleStore.MISCELLANEOUS, MenuIcons2.TOOLS));

			architectureCategories = new ArrayList<Category>();
			architectureCategories.add(new Category(Type.ARCHITECTURE, LocaleStore.UI, MenuIcons2.MONITOR));
			architectureCategories.add(new Category(Type.ARCHITECTURE, LocaleStore.DATA, MenuIcons2.DATA));
			architectureCategories.add(new Category(Type.ARCHITECTURE, LocaleStore.REPORTS, MenuIcons2.REPORT));
			architectureCategories.add(new Category(Type.ARCHITECTURE, LocaleStore.WORKFLOWS, MenuIcons2.PROCESS));
			architectureCategories.add(new Category(Type.ARCHITECTURE, LocaleStore.CMS, MenuIcons2.DOCUMENTS));
			architectureCategories.add(new Category(Type.ARCHITECTURE, LocaleStore.MISCELLANEOUS, MenuIcons2.TOOLS));

		}

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
	public List<License> getAllLicenses() {

		if (licenses == null) {
			licenses = new ArrayList<License>();
			licenses.add(AGPL30);
			licenses.add(APACHE2);
			licenses.add(BSD2);
			licenses.add(BSD3);
			licenses.add(CREATIVE);
			licenses.add(CDDL);
			licenses.add(ECLIPSE);
			licenses.add(GPL);
			licenses.add(LGPL);
			licenses.add(MIT);
			licenses.add(MOZILLA);
		}

		return licenses;
	}

	@Override
	public List<Repository> getAllRepositories() {

		if (repositories == null) {
			repositories = new ArrayList<Repository>();

			repositories.add(GITHUB);
			repositories.add(GITLAB);
			repositories.add(JIRA);
		}
		return repositories;
	}

	public List<Project> getAllProjects() {

		List<Project> list = new ArrayList<Project>();

		list.add(getFullProject());

		return list.size() == 0 ? null : list;
	}

	@Override
	public synchronized View findView(String name) {

		//MOSTRAR VIEW EN OTRA PÁGINA A PARTIR DE UNA URL.AUTOSENSE
		//PROBAR MOSTRAR ESTAS VISTAS EN OTRA APLICACIÓN PARA DEMOS
		
		if (demoViews == null) {
			demoViews = new HashMap<String, View>();
			try {
				demoViews.put(DEMOVIEW_DIRECCION, new FormDireccion());
				demoViews.put(DEMOVIEW_EMPRESAS, new FormEmpresas());
				demoViews.put(DEMOVIEW_MENUPANELS, new MenuPanelsDemo());
			}
			catch (LocalizedException e) {
				e.printStackTrace();
				logger.error("ERROR CREATE DEMOS STORE. ", e);
			}
		}

		return demoViews.get(name);
	}

	@Override
	public List<Product> getProducts(ICategory category, String filter) {

		List<Product> list = null;

		if (category.getCode() == LocaleStore.FORMS) {
			list = new ArrayList<Product>();
			list.add(getForm());
			list.add(getFormAddress());
		}
		else if (category.getCode() == LocaleStore.UI) {
			list = new ArrayList<Product>();
			list.add(getProduct());
			list.add(getProduct2());
		}

		return list == null || list.size() == 0 ? null : list;
	}

	@Override
	public List<Product> getProducts(String filter) {
		return getAllProducts();
	}
	
	private Project getFullProject() {

		Project p = getProject(Type.ARCHITECTURE, true);

		List<IProduct> list = new ArrayList<IProduct>();
		list.add(getProduct());
		list.add(getProduct2());
		p.setProducts(list);

		return p;
	}

	private Project getProject(Type type, boolean android) {

		Version version = new Version("4.0 SNAPSHOT", "Notas de la release");

		Project project = new Project(getAllMembers().get(0), type, "javalego_store", "Vaadin UI", "Vaadin UI", ProviderIcons.PROJECT, version, AGPL30);
		project.addProvider(new Provider("VAADIN", "Vaadin UI", "http://vaadin.com", ProviderIcons.VAADIN));
		if (android) {
			project.addProvider(new Provider("ANDROID", "Android", "http://play.google.com", ProviderIcons.ANDROID));
		}

		project.getCode().setRepository(new Repository("GITHUB", "GitHub", "https://github.com"));
		project.getCode().setUrlRepository("https://github.com/rranz/javalego_vaadin");
		project.getSocial().setTwitter("https://twitter.com/javalego");
		project.getSocial().setBlog("http://javalego.wordpress.com/");
		project.getSocial().setFacebook("https://facebook.com");
		project.getSocial().setGooglePlus("https://plus.google.com");
		project.getCode().setMetrics("https://sonar.springsource.org/dashboard/index/org.springframework.data:spring-data-jpa");
		project.getSocial().setForum("http://forum.spring.io/forum/spring-projects/container");

		project.getComments().add(
				new Comment(DateUtils.getDate(2013, 11, 26),
						"Creo que los componentes que ofrece este proyecto son de bastante calidad y que se pueden integrar en cualquier aplicación creada con Vaadin.", getAllMembers().get(0), 5));

		// List<ProjectVersion> list2 = new ArrayList<ProjectVersion>();
		// list2.add(project.getCurrentVersion());
		// project.setVersions(list2);
		return project;

	}

	private Product getForm() {

		Product p = new Product("FORM", "Empresas", "Empresas", getCategories(Type.BUSINESS).get(2));
		p.setDemo(new Demo(DEMOVIEW_EMPRESAS, null));
		p.setLicense(AGPL30);
		p.setProject(getProject(Type.BUSINESS, true));
		return p;
	}

	private Product getFormAddress() {

		Product p = new Product("FORMADDRESS", "Dirección", "Dirección", getCategories(Type.BUSINESS).get(2));
		p.setLicense(AGPL30);
		p.setDemo(new Demo(DEMOVIEW_DIRECCION, "/code/FormDireccion.java"));
		p.setProject(getProject(Type.BUSINESS, false));
		return p;
	}

	private Product getProduct() {

		Product p = new Product("BUTTONS", "Buttons UI", "Buttons UI", getCategories(Type.ARCHITECTURE).get(0));
		p.setLicense(AGPL30);
		try {
			p.getScreenshots().add(new ScreenShot("Imagen 1", "Representación 1 del button de código", ResourceIconsVaadin.getCurrent().getBytes(ProviderIcons.BUTTON_COMPONENT)));
			p.getScreenshots().add(new ScreenShot("Imagen 2", "Representación 2 del button de código", ResourceIconsVaadin.getCurrent().getBytes(ProviderIcons.MENUPANELS)));
			p.getComments().add(new Comment(DateUtils.getDate(2013, 11, 26), "Botones de un estilo muy minimalista y profesional. Muy bonitos.", getAllMembers().get(0), 5));
			p.setProject(getProject(Type.ARCHITECTURE, false));
		}
		catch (LocalizedException e) {
			e.printStackTrace();
		}
		return p;
	}

	private Product getProduct2() {

		Product p = new Product("MENU", "Menus UI", "Menus UI", getCategories(Type.ARCHITECTURE).get(0));
		p.setLicense(AGPL30);
		try {
			p.setDemo(new Demo(DEMOVIEW_MENUPANELS, "/code/MenuPanelsDemo.java"));
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR PRODUCT2", e);
		}
		p.getComments().add(
				new Comment(DateUtils.getDate(2013, 11, 26),
						"Perfecto para incluirlo como menú principal de la aplicación. Permite su personalización a través de hojas de estilos. Muy similiar al utilizado en Google Play.",
						getAllMembers().get(0), 5));

		p.setProject(getProject(Type.ARCHITECTURE, true));
		return p;
	}

	@Override
	public synchronized List<Member> getAllMembers() {

		if (!loadedMembers) {
			members.addAll(getMockMembers());
			loadedMembers = true;
		}

		return members;
	}

	@Override
	public void addMember(Member member) {
		members.add(member);
	}

	@Override
	public boolean isReadOnly(IBaseItem bean) {
		return false;
	}

	@Override
	public List<News> getAllNews() {

		List<News> news = new ArrayList<News>();

		news.add(new News(DateUtils.getDate(2014, 2, 2), UIContext.getText(LocaleStore.NEWS1), UIContext.getText(LocaleStore.NEWS1D)));

		news.add(new News(DateUtils.getDate(2013, 11, 25), UIContext.getText(LocaleStore.NEWS2), UIContext.getText(LocaleStore.NEWS2D)));

		news.add(new News(DateUtils.getDate(2013, 11, 26), UIContext.getText(LocaleStore.NEWS3), UIContext.getText(LocaleStore.NEWS3D)));

		return news;
	}

	@Override
	public List<Project> getProjects(Type type) throws LocalizedException {
		return getAllProjects();
	}

}
