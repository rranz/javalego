package com.javalego.store.ui.locale;

import com.javalego.model.keys.IconKey;
import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;
import com.javalego.store.ui.icons.MenuIcons2;

/**
 * Códigos de textos utilizado para definir los textos del modelo de datos de los editores (Ej.: EditorEmpresas, ...)
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum LocaleStore implements IconKey {

	@Languages(locales = { 
			@Locale(value = "Nombre"),
			@Locale(locale = "en", value = "Name")
			})
	NAME,

	@Languages(locales = { 
			@Locale(value = "de"),
			@Locale(locale = "en", value = "by")
			})
	BY,

	@Languages(locales = { 
			@Locale(value = "Descripción"),
			@Locale(locale = "en", value = "Overview")
			})
	DESCRIPTION,	
	
	@Languages(locales = { 
			@Locale(value = "Tipo"),
			@Locale(locale = "en", value = "Type")
			})
	TYPE,	
	
	@Languages(locales = { 
			@Locale(value = "Título"),
			@Locale(locale = "en", value = "Title")
			})
	TITLE,	

	@Languages(locales = { 
			@Locale(value = "Licencia"),
			@Locale(locale = "en", value = "License")
			})
	LICENSE,	

	@Languages(locales = { 
			@Locale(value = "Licencias"),
			@Locale(locale = "en", value = "Licenses")
			})
	LICENSES,	

	@Languages(locales = { 
			@Locale(value = "Categoría"),
			@Locale(locale = "en", value = "Category")
			})
	CATEGORY,	

	@Languages(locales = { 
			@Locale(value = "Productos"),
			@Locale(locale = "en", value = "Products")
			})
	PRODUCTS,	

	@Languages(locales = { 
			@Locale(value = "Equipo"),
			@Locale(locale = "en", value = "Team")
			})
	TEAM,	

	@Languages(locales = { 
			@Locale(value = "Producto"),
			@Locale(locale = "en", value = "Product")
			})
	PRODUCT,	

	@Languages(locales = { 
			@Locale(value = "Proyecto"),
			@Locale(locale = "en", value = "Project")
			})
	PROJECT,	

	@Languages(locales = { 
			@Locale(value = "Versión actual"),
			@Locale(locale = "en", value = "Current Version")
			})
	VERSION,	
	
	@Languages(locales = { 
			@Locale(value = "Tecnologías", description="Tecnologías utilizadas"),
			@Locale(locale = "en", value = "Tecnologies", description="Used tecnologies")
			})
	PROVIDERS,	

	@Languages(locales = { 
			@Locale(value="Comentarios", description = "Comentarios tecnologías"),
			@Locale(locale = "en", value="Comments", description = "Comments tecnologies")
			})
	COMMENT_PROVIDERS,	
	
	@Languages(locales = { 
			@Locale(value = "Redes sociales (direcciones web)"),
			@Locale(locale = "en", value = "Social (urls)")
			})
	SOCIAL,		
	
	@Languages(locales = { 
			@Locale(value = "Botones UI"),
			@Locale(locale = "en", value = "Buttons UI")
			})
	BUTTONS, 
	
	@Languages(locales = { 
			@Locale(value = "Menús UI"),
			@Locale(locale = "en", value = "Menus UI")
			})
	MENUS, 
	
	@Languages(locales = { 
			@Locale(value = "Formularios"),
			@Locale(locale = "en", value = "Forms")
			})
	FORMS,

	@Languages(locales = { 
			@Locale(value = "Nuevo formulario básico para el mantenimiento de Empresas."),
			@Locale(locale = "en", value = "Nuevo formulario básico para el mantenimiento de Empresas.")
			})
	NEWS1,
	
	@Languages(locales = { 
			@Locale(value = "Hemos incluido el primer formulario dentro de la sección de Productos de Negocio para mostrar un prototipo de cómo la plataforma puede generar mantenimientos CRUD mediante configuración. El mantenimiento contiene contiene secciones que se generan mediante la configuración o se pueden incluir de forma personalizada de acuerdo a sus necesidades de negocio. Los datos utilizados no son reales y solo se incluyen para poder mostrar su funcionalidad.<br>Para ejecutar el formulario debe pulsar el botón de DEMO que se encuentra en la barra de opciones (icono pantalla)."),
			@Locale(locale = "en", value = "Hemos incluido el primer formulario dentro de la sección de Productos de Negocio para mostrar un prototipo de cómo la plataforma puede generar mantenimientos CRUD mediante configuración. El mantenimiento contiene contiene secciones que se generan mediante la configuración o se pueden incluir de forma personalizada de acuerdo a sus necesidades de negocio. Los datos utilizados no son reales y solo se incluyen para poder mostrar su funcionalidad.<br>Para ejecutar el formulario debe pulsar el botón de DEMO que se encuentra en la barra de opciones (icono pantalla).")
			})
	NEWS1D,
	
	@Languages(locales = { 
			@Locale(value = "Ya está disponible el acceso a un nuevo portal web denominado JAVALEGO."),
			@Locale(locale = "en", value = "Ya está disponible el acceso a un nuevo portal web denominado JAVALEGO.")
			})
	NEWS2,
	
	@Languages(locales = { 
			@Locale(value = "Desde hoy se encuentra disponible este portal para que cualquier persona o empresa que esté interesada en divulgar sus proyectos y productos pueda registrarlos. Previamente, deberá registrarse en la plataforma accediendo al área de clientes."),
			@Locale(locale = "en", value = "Desde hoy se encuentra disponible este portal para que cualquier persona o empresa que esté interesada en divulgar sus proyectos y productos pueda registrarlos. Previamente, deberá registrarse en la plataforma accediendo al área de clientes.")
			})
	NEWS2D,
	
	@Languages(locales = { 
			@Locale(value = "Publicación del Proyecto de Componentes Vaadin"),
			@Locale(locale = "en", value = "Publicación del Proyecto de Componentes Vaadin")
			})
	NEWS3,
	
	@Languages(locales = { 
			@Locale(value = "Dentro de la sección de Proyectos, se ha subido un proyecto que ofrece una serie de componentes UI desarrollados con el framework Web Vaadin que pueden ser de gran utilidad a los desarrolladores de este framework ya que ofrecen una gran calidad gráfica y funcional. Esperamos sus comentarios."),
			@Locale(locale = "en", value = "Dentro de la sección de Proyectos, se ha subido un proyecto que ofrece una serie de componentes UI desarrollados con el framework Web Vaadin que pueden ser de gran utilidad a los desarrolladores de este framework ya que ofrecen una gran calidad gráfica y funcional. Esperamos sus comentarios.")
			})
	NEWS3D, 
	
	@Languages(locales = { 
			@Locale(value = "Editar una Dirección"),
			@Locale(locale = "en", value = "Editar una Dirección")
			})
	P_ADDRESS, 
	
	@Languages(locales = { 
			@Locale(value = "Formulario de edición de una dirección"),
			@Locale(locale = "en", value = "Formulario de edición de una dirección")
			})
	P_ADDRESS_D, 
	
	@Languages(locales = { 
			@Locale(value = "Css Buttons"),
			@Locale(locale = "en", value = "Css Buttons")
			})
	P_BUTTON, 
	
	@Languages(locales = { 
			@Locale(value = "Estilos de botones"),
			@Locale(locale = "en", value = "Estilos de botones")
			})
	P_BUTTON_D, 
	
	@Languages(locales = { 
			@Locale(value = "Menu Panels"),
			@Locale(locale = "en", value = "Menu Panels")
			})
	P_MENU, 
	
	@Languages(locales = { 
			@Locale(value = "Menú basado en paneles"),
			@Locale(locale = "en", value = "Menú basado en paneles")
			})
	P_MENU_D, 
	
	@Languages(locales = { 
			@Locale(value = "Empresas"),
			@Locale(locale = "en", value = "Companies")
			})
	P_COMPANIES, 
	
	@Languages(locales = { 
			@Locale(value = "Mantenimiento de Empresas"),
			@Locale(locale = "en", value = "CRUD Companies")
			})
	P_COMPANIES_D, 
	
	@Languages(locales = { 
			@Locale(value = "Componentes Vaadin"),
			@Locale(locale = "en", value = "Vaadin Components")
			})
	VAADIN_UI, 
	
	@Languages(locales = { 
			@Locale(value = "Desarrollo de Componentes UI con Vaadin"),
			@Locale(locale = "en", value = "Development Componentes Vaadin UI")
			})
	VAADIN_UI_D, 
	
	@Languages(locales = { 
			@Locale(value = "Vaadin"),
			@Locale(locale = "en", value = "Vaadin")
			})
	Vaadin, 
	
	@Languages(locales = { 
			@Locale(value = "Android"),
			@Locale(locale = "en", value = "Android")
			})
	Android, 
	
	@Languages(locales = { 
			@Locale(value = "GitHub"),
			@Locale(locale = "en", value = "GitHub")
			})
	GitHub,


	@Languages(locales = { 
			@Locale(value = "Idioma"),
			@Locale(locale = "en", value = "Language")
			})
	LANG,

	@Languages(locales = { 
			@Locale(value = "Cambiar idioma"),
			@Locale(locale = "en", value = "Change language")
			})
	CHANGE_LANG,	
	
	@Languages(locales = { 
			@Locale(value = "Perfil de usuario"),
			@Locale(locale = "en", value = "User profile")
			})
	USER_PROFILE,	
	
	@Languages(locales = { 
			@Locale(value = "Español"),
			@Locale(locale = "en", value = "Spanish")
			})
	SPANISH,

	@Languages(locales = { 
			@Locale(value = "Inglés"),
			@Locale(locale = "en", value = "English")
			})
	ENGLISH,

	@Languages(locales = { 
			@Locale(value = "Comunidad Java para el Desarrollo de Productos de Arquitecturas y de Negocio"),
			@Locale(locale = "en", value = "Community Java development products Architectures and Business."),
			})
	SLOGAN, 

	@Languages(locales = { 
			@Locale(value = "<b>AVISO:</b> ESTA WEB ESTA EN FASE DE CONSTRUCCIÓN. ALGUNOS DE LOS DATOS QUE SE MUESTRAN SON FICTICIOS Y SIRVEN PARA MOSTRAR UNICAMENTE FUNCIONALIDADES DEL SISTEMA."),
			@Locale(locale = "en", value = "<b>. WARNING: </ b> THIS WEBSITE IS UNDERGOING SOME CONSTRUCTION DATA SHOWN ARE FICTITIOUS AND SERVE ONLY TO SHOW SYSTEM FUNCTIONS."),
			})
	WARNING,
		
	@Languages(locales = { 
			@Locale(value = "Términos del Servicio y Uso"),
			@Locale(locale = "en", value = "Terms of Service and Use"),
			})
	USE, 
	
	@Languages(locales = { 
			@Locale(value = "Productos de Negocio"),
			@Locale(locale = "en", value = "Business Products"),
			})
	BUSINESS_PRODUCTS,

	@Languages(locales = { 
			@Locale(value = "Productos de Arquitectura"),
			@Locale(locale = "en", value = "Architecture Products"),
			})
	ARCHITECTURE_PRODUCTS,
	
	@Languages(locales = { 
			@Locale(value = "Proyectos"),
			@Locale(locale = "en", value = "Proyects"),
			})
	PROYECTS,
	
	@Languages(locales = { 
			@Locale(value = "Comunidad"),
			@Locale(locale = "en", value = "Community"),
			})
	COMMUNITY,
	
	@Languages(locales = { 
			@Locale(value = "Servicios Cloud"),
			@Locale(locale = "en", value = "Cloud Services"),
			})
	CLOUD_SERVICES,
	
	@Languages(locales = { 
			@Locale(value = "Ayuda"),
			@Locale(locale = "en", value = "Help"),
			})
	HELP,
	
	@Languages(locales = { 
			@Locale(value = "Plataforma"),
			@Locale(locale = "en", value = "Platform"),
			})
	PLATFORM, 
	
	@Languages(locales = { 
			@Locale(value = "Tienda"),
			@Locale(locale = "en", value = "Store"),
			})
	STORE, 
	
	@Languages(locales = { 
			@Locale(value = "Noticias"),
			@Locale(locale = "en", value = "News"),
			})
	NEWS, 
	
	@Languages(locales = { 
			@Locale(value = "Buscar"),
			@Locale(locale = "en", value = "Find"),
			})
	FIND, 
	
	@Languages(locales = { 
			@Locale(value = "Buscar en la tienda"),
			@Locale(locale = "en", value = "Find Store"),
			})
	FIND_STORE, 
	
	@Languages(locales = { 
			@Locale(value = "Favoritos"),
			@Locale(locale = "en", value = "Favorites"),
			})
	FAVORITES, 
	
	@Languages(locales = { 
			@Locale(value = "Inicio"),
			@Locale(locale = "en", value = "Home"),
			})
	HOME, 
	
	@Languages(locales = { 
			@Locale(value = "Negocio"),
			@Locale(locale = "en", value = "Business"),
			})
	BUSINESS, 
	
	@Languages(locales = { 
			@Locale(value = "Arquitectura"),
			@Locale(locale = "en", value = "Architecture"),
			})
	ARCHITECTURE, 
	
	@Languages(locales = { 
			@Locale(value = "Interface de Usuario (UI)"),
			@Locale(locale = "en", value = "User Interface (UI)"),
			})
	UI, 
	
	@Languages(locales = { 
			@Locale(value = "Proyectos de Arquitectura"),
			@Locale(locale = "en", value = "Architecture Projects"),
			})
	ARCHITECTURE_PROJECTS, 
	
	@Languages(locales = { 
			@Locale(value = "Proyectos"),
			@Locale(locale = "en", value = "Projects"),
			})
	PROJECTS, 
	
	@Languages(locales = { 
			@Locale(value = "Projectos de Negocio"),
			@Locale(locale = "en", value = "Business Projects"),
			})
	BUSINESS_PROJECTS, 
	
	@Languages(locales = { 
			@Locale(value = "Desarrolladores"),
			@Locale(locale = "en", value = "Developers"),
			})
	DEVELOPERS, 
	
	@Languages(locales = { 
			@Locale(value = "Empresas"),
			@Locale(locale = "en", value = "Companies"),
			})
	COMPANIES, 
	
	@Languages(locales = { 
			@Locale(value = "Cloud computing"),
			@Locale(locale = "en", value = "Cloud computing"),
			})
	CLOUD, 
	
	@Languages(locales = { 
			@Locale(value = "Tour"),
			@Locale(locale = "en", value = "Tour"),
			})
	TOUR, 
	
	@Languages(locales = { 
			@Locale(value = "Documentación"),
			@Locale(locale = "en", value = "Documentation"),
			})
	DOCUMENTATION, 
	
	@Languages(locales = { 
			@Locale(value = "Guías"),
			@Locale(locale = "en", value = "Guides"),
			})
	GUIDES, 
	
	@Languages(locales = { 
			@Locale(value = "Soporte"),
			@Locale(locale = "en", value = "Support"),
			})
	SUPPORT, 
	
	@Languages(locales = { 
			@Locale(value = "Forum"),
			@Locale(locale = "en", value = "Forum"),
			})
	FORUM, 
	
	@Languages(locales = { 
			@Locale(value = "Soluciones"),
			@Locale(locale = "en", value = "Solutions"),
			})
	SOLUTIONS, 
	
	@Languages(locales = { 
			@Locale(value = "Módulos"),
			@Locale(locale = "en", value = "Modules"),
			})
	MODULES, 
	
	@Languages(locales = { 
			@Locale(value = "Servicios"),
			@Locale(locale = "en", value = "Services"),
			})
	SERVICES, 
	
	@Languages(locales = { 
			@Locale(value = "Procesos"),
			@Locale(locale = "en", value = "Processes"),
			})
	PROCESSES, 
	
	@Languages(locales = { 
			@Locale(value = "Reglas"),
			@Locale(locale = "en", value = "Rules"),
			})
	RULES, 
	
	@Languages(locales = { 
			@Locale(value = "Interfaces"),
			@Locale(locale = "en", value = "Interfaces"),
			})
	INTERFACES, 
	
	@Languages(locales = { 
			@Locale(value = "Miscaléneos"),
			@Locale(locale = "en", value = "Miscellaneous"),
			})
	MISCELLANEOUS, 
	
	@Languages(locales = { 
			@Locale(value = "Datos"),
			@Locale(locale = "en", value = "Data"),
			})
	DATA, 
	
	@Languages(locales = { 
			@Locale(value = "Informes"),
			@Locale(locale = "en", value = "Reports"),
			})
	REPORTS, 
	
	@Languages(locales = { 
			@Locale(value = "Flujos de trabajo"),
			@Locale(locale = "en", value = "Workflows"),
			})
	WORKFLOWS, 
	
	@Languages(locales = { 
			@Locale(value = "Gestores de contenidos CMS"),
			@Locale(locale = "en", value = "CMS"),
			})
	CMS, 

	@Languages(locales = { 
			@Locale(value = "Información de acceso"),
			@Locale(locale = "en", value = "Login information"),
			})
	LOGIN, 

	@Languages(locales = { 
			@Locale(value = "Esta aplicación está actualmente en fase beta. Los datos incluídos son ficticios y no persistentes. Incluya el usuario 'admin' y password 'admin' para iniciar sesión."),
			@Locale(locale = "en", value = "This application is currently in beta. The included data are fictional and not persistent. Include the username 'admin' and password 'admin' to login."),
			})
	LOGIN_DATA, 


	@Languages(locales = { 
			@Locale(value = "Demo", description="Datos de configuración de acceso a la demo"),
			@Locale(locale = "en", value = "Demo", description="Config demo")
			})
	DEMO,	
		
	@Languages(locales = { 
			@Locale(value = "Url", description="Dirección web donde se puede acceder a la demo"),
			@Locale(locale = "en", value = "Url", description="Url demo")
			})
	DEMO_URL,	
	
	@Languages(locales = { 
			@Locale(value = "Url código", description = "Url del código de ejemplo"),
			@Locale(locale = "en", value = "Url code", description = "Url example code")
			})
	DEMO_URLCODE,	
	
	@Languages(locales = { 
			@Locale(value = "Código Vista", description = "Código o nombre clave de la vista Vaadin para mostrar la demo"),
			@Locale(locale = "en", value = "View Code", description = "Key or name view Vaadin demo")
			})
	DEMO_VIEW,	
	
	@Languages(locales = { 
			@Locale(value = "TEST", description = "Entorno de TEST"),
			@Locale(locale = "en", value = "TEST", description = "TEST Environment")
			})
	TEST, 
	
	@Languages(locales = { 
			@Locale(value = "Comentarios", description = "Comentarios de los usuarios"),
			@Locale(locale = "en", value = "Comments", description = "Users Comments")
			})
	COMMENTS, 
	
	@Languages(locales = { 
			@Locale(value = "Comentario", description = "Comentario de usuario"),
			@Locale(locale = "en", value = "Comment", description = "User Comment")
			})
	COMMENT,
	
	@Languages(locales = { 
			@Locale(value = "Recursos de código (direcciones web)", description = "Ubicación de los distintos recursos del código fuente del proyecto"),
			@Locale(locale = "en", value = "Source Code Resources (urls)", description = "Location of various resources project source code")
			})
	CODE, 
	
	@Languages(locales = { 
			@Locale(value = "Tipo Repositorio", description = "Tipo de Repositorio de código"),
			@Locale(locale = "en", value = "Repository type", description = "Repository type")
			})
	REPOSITORY, 
	
	@Languages(locales = { 
			@Locale(value = "Repositorios", description = "Tipos de Repositorio de código"),
			@Locale(locale = "en", value = "Repositories", description = "Repositories type")
			})
	REPOSITORIES, 
	
	@Languages(locales = { 
			@Locale(value = "Repositorio", description="Repositorio de código"),
			@Locale(locale = "en", value = "Repository", description="Repository code")
			})
	URL_REPOSITORY, 
	
	@Languages(locales = { 
			@Locale(value = "Documentación"),
			@Locale(locale = "en", value = "Documentation")
			})
	DOC, 
	
	@Languages(locales = { 
			@Locale(value = "JavaDoc"),
			@Locale(locale = "en", value = "JavaDoc")
			})
	JAVADOC, 
	
	@Languages(locales = { 
			@Locale(value = "Métricas"),
			@Locale(locale = "en", value = "Metrics")
			})
	METRICS, 
	
	@Languages(locales = { 
			@Locale(value = "Empresa"),
			@Locale(locale = "en", value = "Company")
			})
	COMPANY, 
	
	@Languages(locales = { 
			@Locale(value = "Desarrollador"),
			@Locale(locale = "en", value = "Developer")
			})
	DEVELOPER, 
	
	@Languages(locales = { 
			@Locale(value = "Autor"),
			@Locale(locale = "en", value = "Author")
			})
	AUTHOR, 
	
	@Languages(locales = { 
			@Locale(value = "Imágenes"),
			@Locale(locale = "en", value = "Screenshots")
			})
	SCREENSHOTS, 
	
	@Languages(locales = { 
			@Locale(value = "Fecha"),
			@Locale(locale = "en", value = "Date")
			})
	DATE, 
	
	@Languages(locales = { 
			@Locale(value = "Imagen"),
			@Locale(locale = "en", value = "Image")
			})
	IMAGE, 
	
	@Languages(locales = { 
			@Locale(value = "Notas", description = "Notas de la versión"),
			@Locale(locale = "en", value = "Notes", description = "Version notes")
			})
	VERSION_NOTES, 
	
	@Languages(locales = { 
			@Locale(value = "Datos generales"),
			@Locale(locale = "en", value = "General Data"),
			})
	CRUDS,
	
	@Languages(locales = { 
			@Locale(value = "JAVALEGO Tienda"),
			@Locale(locale = "en", value = "JAVALEGO Store")
			})
	APP; 

	
	// TODO pendiente de definir su función en store para campos de edición como Social.
	@Override
	public com.javalego.model.keys.Icon Icon(Key key) {
		if (key == PRODUCT) {
			return MenuIcons2.BLOG;
		}
		return null;
	}	
	
}
