package com.javalego.store.ui.demos;

import com.javalego.model.keys.Key;
import com.javalego.model.resources.locale.Languages;
import com.javalego.model.resources.locale.Locale;

/**
 * Códigos de textos utilizado para definir los textos del modelo de datos de los editores (Ej.: EditorEmpresas, ...)
 *  
 * @author ROBERTO RANZ
 * 
 */
public enum Textos implements Key {

	@Languages(locales = { 
			@Locale(value = "Nombre"),
			@Locale(locale = "en", value = "Name")
			})
	NOMBRE,

	@Languages(locales = { 
			@Locale(value = "Descripción"),
			@Locale(locale = "en", value = "Description")
			})
	DESCRIPCION,	
	
	@Languages(locales = { 
			@Locale(value = "Datos completos"),
			@Locale(locale = "en", value = "Full data")
			})
	DATOS_COMPLETOS,	
	
	@Languages(locales = { 
			@Locale(value = "Razón social"),
			@Locale(locale = "en", value = "Company name")
			})
	RAZONSOCIAL,	
	
	@Languages(locales = { 
			@Locale(value = "Domicilio"),
			@Locale(locale = "en", value = "Address")
			})
	DIRECCION,	
	
	@Languages(locales = { 
			@Locale(value = "Código postal"),
			@Locale(locale = "en", value = "Zip code")
			})
	CODIGOPOSTAL,	

	@Languages(locales = { 
			@Locale(value = "Foto"),
			@Locale(locale = "en", value = "Photo")
			})
	FOTO,
	
	@Languages(locales = { 
			@Locale(value = "País"),
			@Locale(locale = "en", value = "Country")
			})
	PAIS,	
	
	@Languages(locales = { 
			@Locale(value = "Correo electrónico"),
			@Locale(locale = "en", value = "Email")
			})
	EMAIL,	
	
	@Languages(locales = { 
			@Locale(value = "Teléfono de empresa"),
			@Locale(locale = "en", value = "Phone number")
			})
	TELEFONO,	
	
	@Languages(locales = { 
			@Locale(value = "Dirección web"),
			@Locale(locale = "en", value = "URL")
			})
	URL,	
	
	@Languages(locales = { 
			@Locale(value = "Localidad"),
			@Locale(locale = "en", value = "City")
			})
	LOCALIDAD,	
	
	@Languages(locales = { 
			@Locale(value = "Provincia"),
			@Locale(locale = "en", value = "State")
			})
	PROVINCIA,	
	
	@Languages(locales = { 
			@Locale(value = "Representante"),
			@Locale(locale = "en", value = "Agent")
			})
	REPRESENTANTE,	
	
	@Languages(locales = { 
			@Locale(value = "Fecha creación"),
			@Locale(locale = "en", value = "Created date")
			})
	FECHACREACION,	

	@Languages(locales = { 
			@Locale(value = "Empresas"),
			@Locale(locale = "en", value = "Companies")
			})
	EMPRESAS,	
		
	@Languages(locales = { 
			@Locale(value = "Empresa"),
			@Locale(locale = "en", value = "Company")
			})
	EMPRESA,	
		
	@Languages(locales = { 
			@Locale(value = "Proveedores"),
			@Locale(locale = "en", value = "Providers")
			})
	PROVEEDORES,	
		
	@Languages(locales = { 
			@Locale(value = "Proveedor"),
			@Locale(locale = "en", value = "Provider")
			})
	PROVEEDOR,	
		
	@Languages(locales = { 
			@Locale(value = "Clientes"),
			@Locale(locale = "en", value = "Customers")
			})
	CLIENTES,	
		
	@Languages(locales = { 
			@Locale(value = "Cliente"),
			@Locale(locale = "en", value = "Customer")
			})
	CLIENTE,	
		
	@Languages(locales = { 
			@Locale(value = "Contactos"),
			@Locale(locale = "en", value = "Contacts")
			})
	CONTACTOS,	
		
	@Languages(locales = { 
			@Locale(value = "Contacto"),
			@Locale(locale = "en", value = "Contact")
			})
	CONTACTO,	
		
	@Languages(locales = { 
			@Locale(value = "Categorías"),
			@Locale(locale = "en", value = "Categories")
			})
	CATEGORIAS,	
		
	@Languages(locales = { 
			@Locale(value = "Categoría"),
			@Locale(locale = "en", value = "Categorie")
			})
	CATEGORIA, 
	
	@Languages(locales = { 
			@Locale(value = "Exportar datos"),
			@Locale(locale = "en", value = "Export data")
			})
	EXPORTAR_DATOS, 
	
	@Languages(locales = { 
			@Locale(value = "Filtrar localidad Madrid"),
			@Locale(locale = "en", value = "Filter state Madrid")
			})
	FILTRAR_MADRID, 
	
	@Languages(locales = { 
			@Locale(value = "CIF"),
			@Locale(locale = "en", value = "CIF")
			})
	CIF, 
	
	@Languages(locales = { 
			@Locale(value = "Datos generales"),
			@Locale(locale = "en", value = "General data")
			})
	DATOS_GENERALES,	
		
	@Languages(locales = { 
			@Locale(value = "Datos adicionales"),
			@Locale(locale = "en", value = "Additional data")
			})
	DATOS_ADICIONALES, 
	
	@Languages(locales = { 
			@Locale(value = "Tipo de vía"),
			@Locale(locale = "en", value = "Type of road")
			})
	VIA, 
	
	@Languages(locales = { 
			@Locale(value = "Puerta"),
			@Locale(locale = "en", value = "Door")
			})
	PUERTA, 
	
	@Languages(locales = { 
			@Locale(value = "Escalera"),
			@Locale(locale = "en", value = "Floor")
			})
	ESCALERA, 
	
	@Languages(locales = { 
			@Locale(value = "Bloque"),
			@Locale(locale = "en", value = "Block")
			})
	BLOQUE, 
	
	@Languages(locales = { 
			@Locale(value = "Lista de países"),
			@Locale(locale = "en", value = "List countries")
			})
	LISTA_PAISES, 
	
	@Languages(locales = { 
			@Locale(value = "Provincias"),
			@Locale(locale = "en", value = "States")
			})
	PROVINCIAS, 
	
	@Languages(locales = { 
			@Locale(value = "Ciudades"),
			@Locale(locale = "en", value = "Cities")
			})
	CIUDADES,	
		
	
}
