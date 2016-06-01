package com.javalego.store.model;

import java.util.Collection;
import java.util.List;

import com.javalego.data.BusinessService;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Colors;
import com.javalego.model.keys.Icon;
import com.javalego.model.keys.Key;
import com.javalego.store.items.IBaseItem;
import com.javalego.store.items.IBaseModel;
import com.javalego.store.items.ICategory;
import com.javalego.store.items.IComment;
import com.javalego.store.items.IMember;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.items.Type;
import com.javalego.store.items.impl.Category;
import com.javalego.store.items.impl.Company;
import com.javalego.store.items.impl.Developer;
import com.javalego.store.items.impl.License;
import com.javalego.store.items.impl.Member;
import com.javalego.store.items.impl.News;
import com.javalego.store.items.impl.Product;
import com.javalego.store.items.impl.Project;
import com.javalego.store.items.impl.Provider;
import com.javalego.store.items.impl.Repository;
import com.vaadin.navigator.View;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public interface StoreDataServices extends BusinessService {

	// Lista de repositorios por defecto
	public static final Repository GITHUB = new Repository("GITHUB", "GitHub", "https://github.com");
	public static final Repository GITLAB = new Repository("GITLAB", "GitLab", "https://gitlab.com");
	public static final Repository JIRA = new Repository("JIRA", "Jira", "https://jira.com");			

	// Lista de licencias por defecto
	public static final License AGPL30 = new License("AGPL 3.0");
	public static final License APACHE2 = new License("Apache License 2.0");
	public static final License BSD2 = new License("BSD 2");
	public static final License BSD3 = new License("BSD 3");
	public static final License CREATIVE = new License("Creative Commons");
	public static final License CDDL = new License("Common Development and Distribution License (CDDL-1.0)");
	public static final License ECLIPSE = new License("Eclipse Public License (EPL-1.0)");
	public static final License GPL = new License("GNU General Public License (GPL)");
	public static final License LGPL = new License("GNU Library or 'Lesser' General Public License (LGPL)");
	public static final License MIT = new License("MIT license (MIT)");
	public static final License MOZILLA = new License("Mozilla Public License 2.0 (MPL-2.0)");	

	/**
	 * Generar un nuevo Producto para su edición.
	 * 
	 * @param project
	 *            Proyecto al que está vinculado
	 * @param key
	 *            tipología de producto
	 * @return
	 */
	IProduct newInstanceProduct(IProject project) throws LocalizedException;

	/**
	 * Generar un nuevo Proyecto para su edición.
	 * 
	 * @param key
	 *            tipología de proyecto
	 * @return
	 */
	IProject newInstanceProject(Type type) throws LocalizedException;

	/**
	 * Listado de productos
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<Product> getAllProducts() throws LocalizedException;

	/**
	 * Licencias de software
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<License> getAllLicenses() throws LocalizedException;

	/**
	 * Proveedores o tecnologías
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<Provider> getAllProviders() throws LocalizedException;

	/**
	 * Lista de proyectos por tipología
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<Project> getProjects(Type type) throws LocalizedException;

	/**
	 * Lista de repositorios de código
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<Repository> getAllRepositories() throws LocalizedException;

	/**
	 * Obtener la lista de categorías por tipo de organización (negocio o
	 * arquitectura).
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<Category> getCategories(Type type) throws LocalizedException;

	/**
	 * Localiza una vista definida en el store para mostrar una demo de un
	 * producto o proyecto.
	 * 
	 * @param name
	 * @return
	 * @throws LocalizedException
	 */
	View findView(String name);

	/**
	 * Obtener la lista de productos de una categoría.
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<Product> getProducts(ICategory category, String filter) throws LocalizedException;

	/**
	 * Obtener la lista de productos filtrados por coincidencia de texto
	 * 
	 * @return
	 * @throws LocalizedException 
	 */
	List<Product> getProducts(String filter) throws LocalizedException;

	/**
	 * Obtener la lista de productos o proyectos a partir del nombre clave de
	 * clasificación.
	 * 
	 * @param key
	 * @param filter
	 * @return
	 * @throws LocalizedException 
	 */
	Collection<? extends IBaseModel> getItems(Key key, String filter) throws LocalizedException;

	/**
	 * Obtener un nuevo objeto dependiendo del tipo de item.
	 * 
	 * @param type
	 * @return
	 * @throws LocalizedException
	 */
	IBaseModel getInstance(Key type) throws LocalizedException;

	/**
	 * Comprueba si el tipo de componente tiene opción de insertar registros
	 * cuando estamos visualizando un lista de estos tipos de componentes. (Ej.:
	 * Productos NO y Proyectos SI).
	 * 
	 * @param type
	 * @return
	 */
	boolean isInsertable(Key type);

	/**
	 * Lista de todos los miembros registrados.
	 * @return
	 * @throws LocalizedException 
	 */
	List<Member> getAllMembers() throws LocalizedException;
	
	/**
	 * Lista de desarrolladores registrados.
	 * @return
	 * @throws LocalizedException 
	 */
	Collection<Developer> getDevelopers() throws LocalizedException;
	
	/**
	 * Lista de empresas registradas.
	 * @return
	 * @throws LocalizedException 
	 */
	Collection<Company> getCompanies() throws LocalizedException;
	
	/**
	 * Localizar un miembro por su nombre o principal.
	 * @param name
	 * @return
	 * @throws LocalizedException 
	 */
	IMember findMember(String name) throws LocalizedException;

	/**
	 * Obtener el color asociado al item pasado como parámetro.
	 * @param item
	 * @return
	 * @throws LocalizedException 
	 */
	Colors getColor(IBaseModel item) throws LocalizedException;

	/**
	 * Generar un nuevo comentario
	 * @return
	 * @throws LocalizedException 
	 */
	IComment newInstanceComment() throws LocalizedException;

	/**
	 * Obtener el color asociado al item pasado como parámetro.
	 * @param item
	 * @return
	 */
	Icon getIcon(IBaseModel item);
	
	/**
	 * Obtiene el miembro de la plataforma vinculado con el usuario de sesión actual.
	 * @return
	 * @throws LocalizedException 
	 */
	IMember getSessionMember() throws LocalizedException;

	/**
	 * Añadir un nuevo miembro a la plataforma
	 * @param member
	 * @throws LocalizedException 
	 */
	void addMember(Member member) throws LocalizedException;

	/**
	 * Comprobar acceso al bean (sólo lectura).
	 * @param bean
	 * @return
	 */
	boolean isReadOnly(IBaseItem bean);

	/**
	 * Obtener la lista de noticias recientes
	 * @return
	 * @throws LocalizedException 
	 */
	List<News> getAllNews() throws LocalizedException;	
}
