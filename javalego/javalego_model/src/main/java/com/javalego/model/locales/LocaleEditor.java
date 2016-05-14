package com.javalego.model.locales;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos para su uso en editores.
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum LocaleEditor implements Key {

	@Languages(locales = { 
			@Locale(value = "Borrar"),
			@Locale(locale = "en", value = "Delete")
			})
	DELETE,

	@Languages(locales = { 
			@Locale(value = "Añadir"),
			@Locale(locale = "en", value = "New")
			})
	INSERT,

	@Languages(locales = { 
			@Locale(value = "Aceptar"),
			@Locale(locale = "en", value = "OK")
			})
	OK,

	@Languages(locales = { 
			@Locale(value = "Cancelar"),
			@Locale(locale = "en", value = "Cancel")
			})
	CANCEL,

	@Languages(locales = { 
			@Locale(value = "Salir"),
			@Locale(locale = "en", value = "Exit")
			})
	EXIT,

	@Languages(locales = { 
			@Locale(value = "Recargar datos"),
			@Locale(locale = "en", value = "Refresh")
			})
	REFRESH,

	@Languages(locales = { 
			@Locale(value = "Primera página"),
			@Locale(locale = "en", value = "First page")
			})
	FIRST_PAGE,

	@Languages(locales = { 
			@Locale(value = "Última página"),
			@Locale(locale = "en", value = "Last page")
			})
	LAST_PAGE,

	@Languages(locales = { 
			@Locale(value = "Siguiente página"),
			@Locale(locale = "en", value = "Next page")
			})
	NEXT_PAGE,

	@Languages(locales = { 
			@Locale(value = "Página anterior"),
			@Locale(locale = "en", value = "Prior page")
			})
	PRIOR_PAGE, 
	
	@Languages(locales = { 
			@Locale(value = "Páginas"),
			@Locale(locale = "en", value = "Pages")
			})
	PAGES,
	
	@Languages(locales = { 
			@Locale(value = "Registros"),
			@Locale(locale = "en", value = "Records")
			})
	RECORDS, 
	
	@Languages(locales = { 
			@Locale(value = "Filtrar"),
			@Locale(locale = "en", value = "Filter")
			})
	FILTER,
	
	@Languages(locales = { 
			@Locale(value = "Eliminar filtro"),
			@Locale(locale = "en", value = "Remove filter")
			})
	REMOVE_FILTER, 
	
	@Languages(locales = { 
			@Locale(value = "Eliminar"),
			@Locale(locale = "en", value = "Remove")
			})
	REMOVE, 
	
	@Languages(locales = { 
			@Locale(value = "Editar"),
			@Locale(locale = "en", value = "Edit")
			})
	EDIT, 
	
	@Languages(locales = { 
			@Locale(value = "Detalle"),
			@Locale(locale = "en", value = "Detail")
			})
	DETAIL, 
	
	@Languages(locales = { 
			@Locale(value = "Acciones"),
			@Locale(locale = "en", value = "Actions")
			})
	ACTIONS, 
	
	@Languages(locales = { 
			@Locale(value = "Vistas"),
			@Locale(locale = "en", value = "Views")
			})
	VIEWS, 
	
	@Languages(locales = { 
			@Locale(value = "Informe", description="Informe de los registros actualmente cargados"),
			@Locale(locale = "en", value = "Report", description="Report records loaded")
			})
	REPORT, 
	
	@Languages(locales = { 
			@Locale(value = "Todos"),
			@Locale(locale = "en", value = "All")
			})
	ALL,
	
	@Languages(locales = { 
			@Locale(value = "Documentación"),
			@Locale(locale = "en", value = "Documentation")
			})	
	DOC,	

	@Languages(locales = { 
			@Locale(value = "Propiedades"),
			@Locale(locale = "en", value = "Properties")
			})
	PROPERTIES,

	@Languages(locales = { 
			@Locale(value = "Tabla de campos"),
			@Locale(locale = "en", value = "Grid")
			})
	GRID,

	@Languages(locales = { 
			@Locale(value = "Buscar"),
			@Locale(locale = "en", value = "Search")
			})
	SEARCH,

	@Languages(locales = { 
			@Locale(value = "Usuario"),
			@Locale(locale = "en", value = "User name")
			})
	USER,

	@Languages(locales = { 
			@Locale(value = "Confirmar contraseña"),
			@Locale(locale = "en", value = "Password again")
			})
	PASSWORD_AGAIN,

	@Languages(locales = { 
			@Locale(value = "Contraseña"),
			@Locale(locale = "en", value = "Password")
			})
	PASSWORD,

	@Languages(locales = { 
			@Locale(value = "Nombre de usuario"),
			@Locale(locale = "en", value = "User name")
			})
	USERNAME,

	@Languages(locales = { 
			@Locale(value = "País"),
			@Locale(locale = "en", value = "Country")
			})
	COUNTRY,

	@Languages(locales = { 
			@Locale(value = "Idiomas"),
			@Locale(locale = "en", value = "Locales")
			})
	LOCALES, 
	
	@Languages(locales = { 
			@Locale(value = "Cambiar de idioma"),
			@Locale(locale = "en", value = "Change locale")
			})
	CHANGE_LANG,

	@Languages(locales = { 
			@Locale(value = "English"),
			@Locale(locale = "en", value = "Inglés")
			})
	ENGLISH,

	@Languages(locales = { 
			@Locale(value = "Español"),
			@Locale(locale = "en", value = "Spanish")
			})
	SPANISH, 
	
	@Languages(locales = { 
			@Locale(value = "Sí"),
			@Locale(locale = "en", value = "Yes")
			})
	YES,

	@Languages(locales = { 
			@Locale(value = "No"),
			@Locale(locale = "en", value = "Not")
			})
	NO, 
	
	@Languages(locales = { 
			@Locale(value = "Aviso"),
			@Locale(locale = "en", value = "Warning")
			})
	WARN,

	@Languages(locales = { 
			@Locale(value = "Información"),
			@Locale(locale = "en", value = "Information")
			})
	INFO,

	@Languages(locales = { 
			@Locale(value = "Error"),
			@Locale(locale = "en", value = "Error")
			})
	ERROR, 
	
	@Languages(locales = { 
			@Locale(value = "Autenticación de usuario"),
			@Locale(locale = "en", value = "Login")
			})
	LOGIN, 
	
	@Languages(locales = { 
			@Locale(value = "Cerrar sesión"),
			@Locale(locale = "en", value = "Logout")
			})
	LOGOUT, 
	
	@Languages(locales = { 
			@Locale(value = "Sesión finalizada"),
			@Locale(locale = "en", value = "Finish session")
			})
	END_SESSION, 
	
	@Languages(locales = { 
			@Locale(value = "Alertas"),
			@Locale(locale = "en", value = "Alerts")
			})
	ALERTS,

	@Languages(locales = { 
			@Locale(value = "Perfil de usuario"),
			@Locale(locale = "en", value = "User profile")
			})
	PROFILE, 
	
	@Languages(locales = { 
			@Locale(value = "Nombre"),
			@Locale(locale = "en", value = "First name")
			})
	FIRSTNAME, 
	
	@Languages(locales = { 
			@Locale(value = "Datos personales"),
			@Locale(locale = "en", value = "Personal data")
			})
	PERSONAL_DATA, 
	
	@Languages(locales = { 
			@Locale(value = "Apellidos"),
			@Locale(locale = "en", value = "Last name")
			})
	LASTNAME, 
	
	@Languages(locales = { 
			@Locale(value = "Correo"),
			@Locale(locale = "en", value = "Email")
			})
	EMAIL, 
	
	@Languages(locales = { 
			@Locale(value = "Teléfono"),
			@Locale(locale = "en", value = "Phone number")
			})
	PHONE, 
	
	@Languages(locales = { 
			@Locale(value = "Foto"),
			@Locale(locale = "en", value = "Photo")
			})
	PHOTO,

}
